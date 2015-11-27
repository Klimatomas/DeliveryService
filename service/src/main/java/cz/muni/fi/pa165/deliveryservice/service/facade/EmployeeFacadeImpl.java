package cz.muni.fi.pa165.deliveryservice.service.facade;

import cz.muni.fi.pa165.deliveryservice.api.dto.EmployeeDTO;
import cz.muni.fi.pa165.deliveryservice.api.dto.PersonAuthenticateDTO;
import cz.muni.fi.pa165.deliveryservice.api.facade.EmployeeFacade;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Employee;
import cz.muni.fi.pa165.deliveryservice.service.BeanMappingService;
import cz.muni.fi.pa165.deliveryservice.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by Tomas Milota on 27.11.2015.
 */
@Transactional
@Service
public class EmployeeFacadeImpl implements EmployeeFacade {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public EmployeeDTO findById(Long employeeId) {
        Employee employee = employeeService.findById(employeeId);
        return (employee == null) ? null : beanMappingService.mapTo(employee, EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO findByEmail(String email) {
        Employee employee = employeeService.findByEmail(email);
        return (employee == null) ? null : beanMappingService.mapTo(employee, EmployeeDTO.class);
    }

    @Override
    public void create(EmployeeDTO employeeDTO, String unencryptedPassword) {
        Employee employee = beanMappingService.mapTo(employeeDTO, Employee.class);
        employeeService.create(employee, unencryptedPassword);
        employeeDTO.setId(employee.getId());
    }

    @Override
    public Collection<EmployeeDTO> getAll() {
        return beanMappingService.mapTo(employeeService.getAll(), EmployeeDTO.class);
    }

    @Override
    public boolean authenticate(PersonAuthenticateDTO p) {
        Employee employee = employeeService.findById(p.getPersonId());
        return employeeService.authenticate(employee, p.getPassword());
    }
}