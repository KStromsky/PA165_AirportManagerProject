<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Stewards">
    <jsp:attribute name="body">

        <div class="form-group row">
            <div class="col-md-12">
                <a href="${pageContext.request.contextPath}/steward/new" class="btn btn-primary">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                    New Steward
                </a>
            </div>
            <div class="col-md-12">
                <label> </label>
            </div>
            <form action="${pageContext.request.contextPath}/steward">

                <div class="col-md-2">
                    <label>Available From</label>
                    <input class="form-control" type="date" name="dateFromStr" pattern="\d{4}-\d{2}-\d{2}" title="Date format yyyy-MM-dd" value="${param.dateFromStr}">
                </div>
                <div class="col-md-2">
                    <label>Available To</label>
                    <input class="form-control" type="date" name="dateToStr" pattern="\d{4}-\d{2}-\d{2}" title="Date format yyyy-MM-dd" value="${param.dateToStr}">
                </div>
                <div class="col-md-3">
                    <label>Destination</label>
                    <select class="form-control" name="destination">
                        <option value="" selected>None</option>
                        <c:forEach items="${destinations}" var="destination">
                            <option value="${destination.id}" ${param.destination == destination.id ? 'selected' : ''}>${destination.location}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-md-4">
                    <label class="col-md-12">&nbsp; </label>
                    <div class="col-md-4">
                        <button class="btn btn-primary btn-block" type="submit">Filter</button>
                    </div>
                    <div class="col-md-4">
                        <a href="${pageContext.request.contextPath}/steward" class="btn btn-danger btn-block">Clear</a>
                    </div>
                </div>
            </form>
        </div>
        <hr>

        <table class="table table-hover table-condensed fixed">
            <thead>
                <tr>
                    <th>PersonalIdentificator</th>
                    <th>First Name</th>
                    <th>Surname</th>
                    <th>Username</th>
                    <th>Flights Count</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${stewards}" var="steward">
                    <tr>
                        <td class="col-md-2"><c:out value="${steward.personalIdentificator}"/></td>
                        <td class="col-md-2"><c:out value="${steward.firstname}"/></td>
                        <td class="col-md-2"><c:out value="${steward.surname}"/></td>
                        <td class="col-md-2"><c:out value="${steward.username}"/></td>
                        <td class="col-md-2"><c:out value="${stewardsFlights.get(steward.id).size()}"/></td>
                        <td class="col-md-1">
                            <a href="${pageContext.request.contextPath}/steward/detail/${steward.id}" class="btn btn-info btn-block">View</a>
                        </td>
                        <td class="col-md-1">
                            <a href="${pageContext.request.contextPath}/steward/edit/${steward.id}" class="btn btn-primary btn-block">Edit</a>
                        </td>
                        <td class="col-md-1">
                            <form method="post" action="${pageContext.request.contextPath}/steward/delete/${steward.id}">
                                <button type="submit" ${stewardsFlights.get(steward.id).size() > 0 ? "disabled" : ""} class="btn btn-primary">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </jsp:attribute>
</my:pagetemplate>