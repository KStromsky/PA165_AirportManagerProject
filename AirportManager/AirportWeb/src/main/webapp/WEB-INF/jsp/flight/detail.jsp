<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Flight detail information">
    <jsp:attribute name="body">
        <div class="row">
        <table class="table">
            <tr>
                <td class="col-md-2"><b>Id</b></td>
                <td>${flight.id}</td>
            </tr>
            <tr>
                <td class="col-md-2"><b>Arrival</b></td>
                <td><fmt:formatDate pattern="yyyy-MM-dd" value="${flight.arrival}"/></td>
            </tr>
            <tr>
                <td class="col-md-2"><b>Departure</b></td>
                <td><fmt:formatDate pattern="yyyy-MM-dd" value="${flight.departure}"/></td>
            </tr>
            <tr>
                <td class="col-md-2"><b>Origin</b></td>
                <td>${flight.origin.location}</td>
            </tr>
            <tr>
                <td class="col-md-2"><b>Destination</b></td>
                <td>${flight.destination.location}</td>
            </tr>
            <tr>
                <td class="col-md-2"><b>Airplane</b></td>
                <td>${flight.airplane.name}</td>
            </tr>
            <tr>
                <td><input type="button" value="Back" onclick="history.go(-1)" class="btn btn-danger"></td>
            </tr>
            <c:if test="${flight.stewards.size() > 0}">
                <table class="table">
                    <thead>
                        <tr>
                            <th>First Name</th>
                            <th>Surname</th>
                            <th>Personal ID</th>
                            <th>Gender</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${flight.stewards}" var="steward">
                            <tr>
                                <td class="col-md-1 align-middle"><c:out value="${steward.firstname}"/></td>
                                <td class="col-md-1 align-middle"><c:out value="${steward.surname}"/></td>
                                <td class="col-md-1 align-middle"><c:out value="${steward.personalIdentificator}"/></td>
                                <td class="col-md-1 align-middle"><c:out value="${steward.gender}"/></td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/steward/detail/${steward.id}" class="btn btn-info">View</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </jsp:attribute>
</my:pagetemplate>