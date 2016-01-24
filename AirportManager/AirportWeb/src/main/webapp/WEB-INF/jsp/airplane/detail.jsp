<%-- 
    Document   : detail
    Created on : 18.12.2015, 11:00:47
    Author     : Kuba
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Airplane detail information">
    <jsp:attribute name="body">
        <div>
            <table class="table">
                <tr>
                    <td class="col-md-2"><b>ID</b></td>
                    <td>${airplane.id}</td>
                </tr>
                <tr>
                    <td class="col-md-2"><b>Name of Airplane</b></td>
                    <td>${airplane.name}</td>
                </tr>
                <tr>
                    <td class="col-md-2"><b>Capacity</b></td>
                    <td>${airplane.capacity}</td>
                </tr>
                <tr>
                    <td class="col-md-2"><b>Airplane type</b></td>
                    <td>${airplane.type}</td>
                </tr>
                <tr>
                    <td><input type="button" value="Back" onclick="history.go(-1)" class="btn btn-danger"></td>
                </tr>
            </table>
            <c:if test="${flights.size() > 0}">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Origin</th>
                            <th>Destination</th>
                            <th>Arrival</th>
                            <th>Departure</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${flights}" var="flight">
                            <tr>
                                <td class="col-md-1 align-middle"><c:out value="${flight.origin.location}"/></td>
                                <td class="col-md-1 align-middle"><c:out value="${flight.destination.location}"/></td>
                                <td class="col-md-1 align-middle"><fmt:formatDate pattern="yyyy-MM-dd" value="${flight.arrival}"/></td>
                                <td class="col-md-1 align-middle"><fmt:formatDate pattern="yyyy-MM-dd" value="${flight.departure}"/></td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/flight/detail/${flight.id}" class="btn btn-info">View</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </jsp:attribute>
</my:pagetemplate>