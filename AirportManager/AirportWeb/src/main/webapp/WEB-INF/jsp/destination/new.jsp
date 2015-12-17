<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<my:pagetemplate title="New Destination">
    <jsp:attribute name="body">

        <form:form method="post" action="${pageContext.request.contextPath}/destination/create"
                   modelAttribute="destinationCreate" cssClass="form-horizontal">
            <div class="form-group ${location_error?'has-error':''}">
                <form:label path="location" cssClass="col-sm-1 control-label">Location</form:label>
                    <div class="col-sm-4">
                    <form:input path="location" cssClass="form-control"/>
                    <form:errors path="location" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group col-sm-4">
                <a href="${pageContext.request.contextPath}/location" class="btn btn-danger">Back</a>
                <button class="btn btn-primary" type="submit">Create destination</button>
            </div>
        </form:form>

    </jsp:attribute>
</my:pagetemplate>