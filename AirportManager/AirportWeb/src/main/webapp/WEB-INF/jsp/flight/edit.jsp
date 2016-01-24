<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<my:pagetemplate title="Edit Flight">
    <jsp:attribute name="body">
        <form:form method="post" action="${pageContext.request.contextPath}/flight/update/${flight.id}"
                   modelAttribute="flightUpdate" cssClass="form-horizontal">
            <div class="form-group ${arrival_error?'has-error':''}">
                <form:label path="arrival" cssClass="col-sm-1 control-label">Arrival</form:label>
                <div class="col-sm-4">
                    <form:input type="date" pattern="yyyy-MM-dd" path="arrival" cssClass="form-control"/>
                    <form:errors path="arrival" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${departure_error?'has-error':''}">
                <form:label path="departure" cssClass="col-sm-1 control-label">Departure</form:label>
                <div class="col-sm-4">
                    <form:input type="date" pattern="yyyy-MM-dd" path="departure" cssClass="form-control"/>
                    <form:errors path="departure" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${origin_error?'has-error':''}">
                <form:label path="origin" cssClass="col-sm-1 control-label">Origin</form:label>
                <div class="col-sm-4">
                    <form:select path="origin" cssClass="form-control">
                        <c:forEach items="${destinations}" var="d">
                            <form:option value="${d.id}">${d.location}</form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="origin" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${destination_error?'has-error':''}">
                <form:label path="destination" cssClass="col-sm-1 control-label">Destination</form:label>
                <div class="col-sm-4">
                    <form:select path="destination" cssClass="form-control">
                        <c:forEach items="${destinations}" var="d">
                            <form:option value="${d.id}">${d.location}</form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="destination" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${airplane_error?'has-error':''}">
                <form:label path="airplane" cssClass="col-sm-1 control-label">Airplane</form:label>
                <div class="col-sm-4">
                    <form:select path="airplane" cssClass="form-control">
                        <c:forEach items="${airplanes}" var="a">
                            <form:option value="${a.id}">${a.name}</form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="airplane" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${stewards_error?'has-error':''}">
                <form:label path="stewards" cssClass="col-sm-1 control-label">Stewards</form:label>
                <div class="col-sm-4">
                    <form:select multiple="true" path="stewards" cssClass="form-control">
                        <c:forEach items="${stewards}" var="s">
                            <form:option value="${s.id}">${s.personalIdentificator}</form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="stewards" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group col-sm-4">
                <a href="${pageContext.request.contextPath}/flight" class="btn btn-danger">Back</a>
                <button class="btn btn-primary" type="submit">Edit flight</button>
            </div>
        </form:form>
    </jsp:attribute>
</my:pagetemplate>