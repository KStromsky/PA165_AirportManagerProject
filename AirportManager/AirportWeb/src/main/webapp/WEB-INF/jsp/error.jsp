<%-- author: Milan Skipala --%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%-- declare my own tags --%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%-- declare JSTL libraries --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="title" value="${\"Invalid Page\"}"/>

<%-- call my own tag defined in /WEB-INF/tags/pagetemplate.tag, provide title attribute --%>
<my:pagetemplate title="${title}">
<jsp:attribute name="body"><%-- provide page-fragment attribute to be rendered by the my:layout tag --%>
     <form class="form-horizontal">
        <fieldset>
          <legend>The page you are trying to access does not exists!</legend>
          <img src="http://zenit.senecac.on.ca/wiki/imgs/404-not-found.gif" >
            <div class="col-lg-10">
              <a href="${pageContext.request.contextPath}" class="btn btn-primary">Return Home</a>
            </div>
        </fieldset>
     </form>
</jsp:attribute>
</my:pagetemplate>