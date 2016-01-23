<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<my:pagetemplate title="New Steward">
    <jsp:attribute name="body">

        <form:form method="post" action="${pageContext.request.contextPath}/steward/create"
                   modelAttribute="stewardCreate" cssClass="form-horizontal">
            <div class="form-group ${personalIdentificator_error?'has-error':''}">
                <form:label path="personalIdentificator" cssClass="col-sm-1 control-label">Personal Identificator</form:label>
                    <div class="col-sm-4">
                    <form:input path="personalIdentificator" cssClass="form-control"/>
                    <form:errors path="personalIdentificator" cssClass="help-block"/>
                </div>
            </div>
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
            <div class="form-group ${username_error?'has-error':''}">
                <form:label path="username" cssClass="col-sm-1 control-label">Username</form:label>
                    <div class="col-sm-4">
                    <form:input path="username" cssClass="form-control"/>
                    <form:errors path="username" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group">
                <form:label path="password" cssClass="col-sm-1 control-label">Password</form:label>
                    <div class="col-sm-4">
                    <form:input type="password" path="password" cssClass="form-control"/>
                    <form:errors path="password" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group">
                <form:label path="gender" cssClass="col-sm-1 control-label">Gender</form:label>
                    <div class="col-sm-4">
                    <form:select path="gender" cssClass="form-control">
                        <c:forEach items="${genders}" var="g">
                            <form:option value="${g}">${g}</form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="gender" cssClass="error"/>
                </div>
            </div>
            <div class="form-group ${dateOfBirth_error?'has-error':''}">
                <form:label path="dateOfBirth" cssClass="col-sm-1 control-label">Date of Birth</form:label>
                    <div class="col-sm-4">
                    <form:input type="date" path="dateOfBirth" cssClass="form-control"/>
                    <form:errors path="dateOfBirth" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${employmentDate_error?'has-error':''}">
                <form:label path="employmentDate" cssClass="col-sm-1 control-label">Employment Date</form:label>
                    <div class="col-sm-4">
                    <form:input type="date" path="employmentDate" cssClass="form-control" value="${dateNow}"/>
                    <form:errors path="employmentDate" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group col-sm-4">
                <a href="${pageContext.request.contextPath}/steward" class="btn btn-danger">Back</a>
                <button class="btn btn-primary" type="submit">Create steward</button>
            </div>
        </form:form>

    </jsp:attribute>
</my:pagetemplate>