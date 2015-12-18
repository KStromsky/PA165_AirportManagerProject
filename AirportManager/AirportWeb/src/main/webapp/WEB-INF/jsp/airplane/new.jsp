<%-- 
    Document   : new
    Created on : 18.12.2015, 11:01:24
    Author     : Kuba
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<my:pagetemplate title="New Airplane">
    <jsp:attribute name="body">

        <form:form method="post" action="${pageContext.request.contextPath}/airplane/create"
                   modelAttribute="airplaneCreate" cssClass="form-horizontal">
            <div class="form-group ${name_error?'has-error':''}">
                <form:label path="name" cssClass="col-sm-1 control-label">Name of airplane</form:label>
                    <div class="col-sm-4">
                    <form:input path="name" cssClass="form-control"/>
                    <form:errors path="name" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${capacity_error?'has-error':''}">
                <form:label path="capacity" cssClass="col-sm-1 control-label">Capacity</form:label>
                    <div class="col-sm-4">
                    <form:input path="capacity" cssClass="form-control"/>
                    <form:errors path="capacity" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${type_error?'has-error':''}">
                <form:label path="type" cssClass="col-sm-1 control-label">Type of airplane</form:label>
                    <div class="col-sm-4">
                    <form:input path="type" cssClass="form-control"/>
                    <form:errors path="type" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group col-sm-4">
                <a href="${pageContext.request.contextPath}/airplane" class="btn btn-danger">Back</a>
                <button class="btn btn-primary" type="submit">Create airplane</button>
            </div>
        </form:form>

    </jsp:attribute>
</my:pagetemplate>
