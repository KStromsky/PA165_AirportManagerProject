<%-- author: Michal Zbranek --%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%-- declare my own tags --%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%-- declare JSTL libraries --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="title" value="${\"Airport\"}"/>

<%-- call my own tag defined in /WEB-INF/tags/pagetemplate.tag, provide title attribute --%>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body"><%-- provide page-fragment attribute to be rendered by the my:layout tag --%>

        <c:choose>
            <c:when test="${valid}" >
                <h2 class="col-md-12"><c:out value="User ${user} is loggeg in" /></h2>
                <form method="post" action="${pageContext.request.contextPath}/logout" cssClass="form-horizontal">
                    <div class="form-group">
                        <div class="col-lg-12" >
                            <label />
                        </div>
                        <div class="col-lg-1">
                            <button type="submit" class="btn btn-block btn-danger">Log Out</button>
                        </div>
                    </div>
                </form>
            </c:when>
            <c:otherwise>
                <form:form method="post" action="${pageContext.request.contextPath}/login"
                           modelAttribute="userAuth" cssClass="form-horizontal">
                    <fieldset>
                        <legend>Please login to start using the system.</legend>
                        <div class="form-group ${firstname_error?'has-error':''}">
                            <form:label path="userName" cssClass="col-sm-1 control-label">User Name</form:label>
                            <div class="col-sm-4">
                                <form:input path="userName" cssClass="form-control"/>
                                <form:errors path="userName" cssClass="help-block"/>
                            </div>
                        </div>
                        <div class="form-group ${surname_error?'has-error':''}">
                            <form:label path="password" cssClass="col-sm-1 control-label">Password</form:label>
                            <div class="col-sm-4">
                                <form:input path="password" cssClass="form-control"/>
                                <form:errors path="password" cssClass="help-block"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-lg-1 col-lg-offset-1">
                                <button type="submit" class="btn btn-block btn-primary">Login</button>
                            </div>
                        </div>
                    </fieldset>
                </form:form>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
</my:pagetemplate>