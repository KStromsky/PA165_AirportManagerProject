<%-- author: Michal Zbranek --%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Destination detail information">
    <jsp:attribute name="body">
        <div class="row">
        <table class="table">
            <tr>
                <td class="col-md-2"><b>ID</b></td>
                <td>${destination.id}</td>
            </tr>
            <tr>
                <td class="col-md-2"><b>Location</b></td>
                <td>${destination.location}</td>
            </tr>
            <tr>
                <td><input type="button" value="Back" onclick="history.go(-1)" class="btn btn-danger"></td>
            </tr>
        </table>
        </div>
    </jsp:attribute>
</my:pagetemplate>