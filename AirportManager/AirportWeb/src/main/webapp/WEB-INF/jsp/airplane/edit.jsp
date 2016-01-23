<%-- author: Michal Zbranek --%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Edit airplane">
<jsp:attribute name="body">

    <form:form method="post" action="${pageContext.request.contextPath}/airplane/update/${airplane.id}"
               modelAttribute="airplane" cssClass="form-horizontal">

        <div class="form-group ${name_error?'has-error':''}">
            <form:label path="capacity" cssClass="col-sm-2 control-label">Capacity</form:label>
            <div class="col-sm-10">
                <form:input path="capacity" cssClass="form-control"/>
                <form:errors path="capacity" cssClass="help-block"/>
            </div>
        </div>
            <div class="form-group col-sm-4">
                <a href="${pageContext.request.contextPath}/airplane" class="btn btn-danger">Back</a>
        <button class="btn btn-primary" type="submit">Edit airplane</button>
        </div>
    </form:form>

</jsp:attribute>
</my:pagetemplate>