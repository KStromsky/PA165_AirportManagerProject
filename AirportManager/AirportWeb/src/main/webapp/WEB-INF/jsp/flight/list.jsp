<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Flights">
    <jsp:attribute name="body">
        <div class="form-group row">
            <div class="col-md-2">
                <a href="${pageContext.request.contextPath}/flight/new" class="btn btn-primary">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                    New Flight
                </a>
            </div>
        </div>
        <div class="col-md-12">
            <label> </label>
        </div>
        <form action="${pageContext.request.contextPath}/flight">

            <div class="col-md-3">
                <label>Location Type</label>
                <select name="locationType" Class="form-control">
                    <c:forEach items="${locations}" var="l">
                        <option value="${l}" ${param.locationType == l ? 'selected' : ''}>${l.toString().toLowerCase()}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-3">
                <label>Location</label>
                <select class="form-control" name="location">
                    <option value="" selected>None</option>
                    <c:forEach items="${destinations}" var="location">
                        <option value="${location.id}" ${param.location == location.id ? 'selected' : ''}>${location.location}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="col-md-4">
                <label class="col-md-12">&nbsp; </label>
                <div class="col-md-4">
                    <button class="btn btn-primary btn-block" type="submit">Filter</button>
                </div>
                <div class="col-md-4">
                    <a href="${pageContext.request.contextPath}/flight" class="btn btn-danger btn-block">Clear</a>
                </div>
            </div>
        </form>
    </div>
    <hr>
    <table class="table table-hover table-condensed fixed">
        <thead>
            <tr>
                <th>Arrival</th>
                <th>Departure</th>
                <th>Origin</th>
                <th>Destination</th>
                <th>Airplane</th>
                <th>Stewards</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${flights}" var="flight">
                <tr>
                    <td class="col-md-2"><c:out value="${flight.arrival}"/></td>
                    <td class="col-md-3"><c:out value="${flight.departure}"/></td>
                    <td class="col-md-3"><c:out value="${flight.origin.location}"/></td>
                    <td class="col-md-3"><c:out value="${flight.destination.location}"/></td>
                    <td class="col-md-3"><c:out value="${flight.airplane.name}"/></td>
                    <td class="col-md-3"><c:out value="${flight.stewards.size()}"/></td>
                    <td class="col-md-1">
                        <a href="${pageContext.request.contextPath}/flight/detail/${flight.id}" class="btn btn-info btn-block">View</a>
                    </td>
                    <td class="col-md-1">
                        <form method="post" action="${pageContext.request.contextPath}/flight/delete/${flight.id}">
                            <button type="submit" class="btn btn-primary btn-danger">Delete</button>
                        </form>
                    </td>
                    <td class="col-md-1">
                        <a href="${pageContext.request.contextPath}/flight/edit/${flight.id}" class="btn btn-primary">Edit</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</jsp:attribute>
</my:pagetemplate>