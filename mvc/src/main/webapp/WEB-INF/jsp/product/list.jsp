<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="products">
<jsp:attribute name="body">

    <table class="table table-striped">
        <caption>products</caption>
        <thead>
        <tr>
            <th>Unique ID</th>
            <th>Product Name</th>
            <th>Date of Takeover</th>
            <th>Weight</th>
            <th>Price</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${products}" var="product">
            <tr>
                <td>${product.id}</td>
                <td><c:out value="${product.name}"/></td>
                <td><my:LocalDate date="${product.addedDate}"/></td>
                <td><c:out value="${product.weight} kg"/></td>
                <td><c:out value="${product.price} €"/></td>
                <td>
                    <my:a href="/product/detail/id=${product.id}" class="btn btn-primary">View</my:a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</jsp:attribute>
</my:pagetemplate>