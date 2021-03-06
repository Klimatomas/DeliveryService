package cz.muni.fi.pa165.mvc.security;

import cz.muni.fi.pa165.deliveryservice.api.dto.EmployeeDTO;
import cz.muni.fi.pa165.deliveryservice.api.dto.PersonAuthenticateDTO;
import cz.muni.fi.pa165.deliveryservice.api.facade.EmployeeFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.persistence.NoResultException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;

@WebFilter(urlPatterns = {"/employee/*", "/customer/delete/*", "/customer/list/name/*", "/customer/detail/email/*"})
public class AdminProtectFilter implements Filter {

    final static Logger log = LoggerFactory.getLogger(AdminProtectFilter.class);

    @Override
    public void doFilter(ServletRequest r, ServletResponse s, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) r;
        HttpServletResponse response = (HttpServletResponse) s;

        String auth = request.getHeader("Authorization");
        if (auth == null) {
            response401(response);
            return;
        }
        String[] creds = parseAuthHeader(auth);
        String logname = creds[0];
        String password = creds[1];

        //get Spring context and UserFacade from it
        EmployeeFacade employeeFacade = WebApplicationContextUtils.getWebApplicationContext(r.getServletContext()).getBean(EmployeeFacade.class);

        EmployeeDTO matchingUser = null;
        try {
            matchingUser = employeeFacade.findByEmail(logname);
        } catch (NoResultException e) {
            // nothing
        }
        if (matchingUser == null) {
            log.warn("no user with email {}", logname);
            response401(response);
            return;
        }
        PersonAuthenticateDTO personAuthenticateDTO = new PersonAuthenticateDTO();
        personAuthenticateDTO.setPersonId(matchingUser.getId());
        personAuthenticateDTO.setPassword(password);
        if (!employeeFacade.authenticate(personAuthenticateDTO)) {
            log.warn("wrong credentials: user={} password={}", creds[0], creds[1]);
            response401(response);
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("authenticatedUser", matchingUser);
        request.setAttribute("authenticatedUser", matchingUser);
        chain.doFilter(request, response);
    }


    private String[] parseAuthHeader(String auth) {
        return new String(DatatypeConverter.parseBase64Binary(auth.split(" ")[1])).split(":", 2);
    }

    private void response401(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("WWW-Authenticate", "Basic realm=\"type email and password\"");
        response.getWriter().println("<html><body><h1>401 Unauthorized</h1> Try again</body></html>");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}