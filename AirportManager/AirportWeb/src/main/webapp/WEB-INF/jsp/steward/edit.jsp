<%-- author: Michal Zbranek --%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Edit steward">
    <jsp:attribute name="body">

        <form:form method="post" action="${pageContext.request.contextPath}/steward/update/${steward.id}"
                   modelAttribute="steward" cssClass="form-horizontal">

            <div class="form-group ${firstname_error?'has-error':''}">
                <form:label path="firstname" cssClass="col-sm-1 control-label">First Name</form:label>
                    <div class="col-sm-4">
                    <form:input path="firstname" cssClass="form-control"/>
                    <form:errors path="firstname" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${surname_error?'has-error':''}">
                <form:label path="surname" cssClass="col-sm-1 control-label">Surname</form:label>
                    <div class="col-sm-4">
                    <form:input path="surname" cssClass="form-control"/>
                    <form:errors path="surname" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${password_error?'has-error':''}">
                <form:label path="password" cssClass="col-sm-1 control-label">Password</form:label>
                    <div class="col-sm-4">
                    <form:input type="password" path="password" cssClass="form-control"/>
                    <form:errors path="password" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group col-sm-4">
                <a href="${pageContext.request.contextPath}/steward" class="btn btn-danger">Back</a>
                <button class="btn btn-primary" type="submit">Edit steward</button>
            </div>
        </form:form>

    </jsp:attribute>
</my:pagetemplate>