<%-- author: Michal Zbranek --%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Destinations">
    <jsp:attribute name="body">

        <div class="form-group row">
            <div class="col-md-2">
                <a href="${pageContext.request.contextPath}/destination/new" class="btn btn-primary">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                New Destination
                </a>
            </div>

            <form action="${pageContext.request.contextPath}/destination">
<!--                <div class="col-md-offset-4 col-md-2 col-xs-4">
                    <select class="form-control" name="loanable">
                        <option value="true" {param.loanable ? 'selected' : ''}>Loanable</option>
                        <option value="false" {empty param.loanable || param.loanable ? '' : 'selected'}>Unloanable</option>

                    </select>
                </div>-->
            </form>
        </div>
        <hr>

        <table class="table table-hover table-condensed fixed">
            <thead>
            <tr>
                <th>Location</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${destinations}" var="destination">
                <tr>
                    <td class="col-md-4"><c:out value="${destination.location}"/></td>
                    <td class="col-md-4">
                        <a href="${pageContext.request.contextPath}/destination/detail/${destination.id}" class="btn btn-info btn-block">View</a>
                    </td>
                    <td class="col-md-2">
                        <form method="post" action="${pageContext.request.contextPath}/destination/delete/${destination.id}">
                            <button type="submit" class="btn btn-primary btn-danger btn-block">Delete</button>
                        </form>
                    </td>
                    <td class="col-md-2">
                        <a href="${pageContext.request.contextPath}/destination/edit/${destination.id}" class="btn btn-primary">Edit</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </jsp:attribute>
</my:pagetemplate>