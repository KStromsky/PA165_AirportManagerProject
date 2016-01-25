<%-- 
    Document   : list
    Created on : 18.12.2015, 11:01:10
    Author     : Kuba
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Airplanes">
    <jsp:attribute name="body">

        <div class="form-group row">
            <div class="col-md-12">
                <a href="${pageContext.request.contextPath}/airplane/new" class="btn btn-primary">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                    New Airplane
                </a>
            </div>
            <div class="col-md-12">
                    <label> </label>
                </div>
            <form action="${pageContext.request.contextPath}/airplane">
                <div class="col-md-3">
                    <label>Name</label>
                    <input class="form-control" name="name"  value="${param.name}">
                </div>
                <div class="col-md-3">
                    <label>Type</label>
                    <input class="form-control" name="type"  value="${param.type}">
                </div>
                <div class="col-md-1">
                    <label>Capacity</label>
                    <input class="form-control" type="number" min="0" max="100000" name="capacity"  value="${param.capacity}">
                </div>
                
                <div class="col-md-4">
                    <label class="col-md-12">&nbsp; </label>
                    <div class="col-md-4">
                        <button class="btn btn-primary btn-block" type="submit">Filter</button>
                    </div>
                    <div class="col-md-4">
                        <a href="${pageContext.request.contextPath}/airplane" class="btn btn-danger btn-block">Clear</a>
                    </div>
                </div>
            </form>
        </div>
        <hr>

        <table class="table table-hover table-condensed fixed">
            <thead>
                <tr>
                    <th>Airplane name</th>
                    <th>Capacity</th>
                    <th>Airplane type</th>
                    <th>Flights</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${airplanes}" var="airplane">
                    <tr>
                        <td class="col-md-3"><c:out value="${airplane.name}"/></td>
                        <td class="col-md-2"><c:out value="${airplane.capacity}"/></td>
                        <td class="col-md-3"><c:out value="${airplane.type}"/></td>
                        <td class="col-md-2"><c:out value="${airplaneFlights.get(airplane.id).size()}"/></td>
                        <td class="col-md-3">
                            <a href="${pageContext.request.contextPath}/airplane/detail/${airplane.id}" class="btn btn-info btn-block">View</a>
                        </td>
                        <td class="col-md-2">
                            <form method="post" action="${pageContext.request.contextPath}/airplane/delete/${airplane.id}">
                                <button type="submit" ${!airplaneFlights.get(airplane.id).isEmpty() ? 'disabled' : ''} class="btn btn-primary btn-danger btn-block">Delete</button>
                            </form>
                        </td>
                        <td class="col-md-2">
                        <a href="${pageContext.request.contextPath}/airplane/edit/${airplane.id}" class="btn btn-primary">Edit</a>
                    </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </jsp:attribute>
</my:pagetemplate>
