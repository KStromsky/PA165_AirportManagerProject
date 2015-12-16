<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Stewards">
    <jsp:attribute name="body">

        <div class="form-group row">
            <div class="col-md-2">
                <a href="${pageContext.request.contextPath}/steward/new" class="btn btn-primary">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                New Steward
                </a>
            </div>

            <form action="${pageContext.request.contextPath}/steward">
<!--                <div class="col-md-offset-4 col-md-2 col-xs-4">
                    <select class="form-control" name="loanable">
                        <option value="true" {param.loanable ? 'selected' : ''}>Loanable</option>
                        <option value="false" {empty param.loanable || param.loanable ? '' : 'selected'}>Unloanable</option>

                    </select>
                </div>-->
                <div class="col-md-1">
                    <button class="btn btn-primary" type="submit">Filter</button>
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
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${stewards}" var="steward">
                <tr>
                    <td class="col-md-2"><c:out value="${steward.personalIdentificator}"/></td>
                    <td class="col-md-3"><c:out value="${steward.firstname}"/></td>
                    <td class="col-md-3"><c:out value="${steward.surname}"/></td>
                    <td class="col-md-1">
                        <a href="${pageContext.request.contextPath}/steward/detail/${steward.id}" class="btn btn-info btn-block">View</a>
                    </td>
                    <td class="col-md-1">
                        <form method="post" action="${pageContext.request.contextPath}/steward/delete/${steward.id}">
                            <button type="submit" class="btn btn-primary">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </jsp:attribute>
</my:pagetemplate>