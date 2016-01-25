<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="New Flight">
    <jsp:attribute name="body">
        <form:form method="post" action="${pageContext.request.contextPath}/flight/create"
                   modelAttribute="flightCreate" cssClass="form-horizontal">
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
                <form:label path="originId" cssClass="col-sm-1 control-label">Origin</form:label>
                <div class="col-sm-4">
                    <form:select path="originId" cssClass="form-control">
                        <c:forEach items="${destinations}" var="d">
                            <form:option value="${d.id}">${d.location}</form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="originId" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${destination_error?'has-error':''}">
                <form:label path="destinationId" cssClass="col-sm-1 control-label">Destination</form:label>
                <div class="col-sm-4">
                    <form:select path="destinationId" cssClass="form-control">
                        <c:forEach items="${destinations}" var="d">
                            <form:option value="${d.id}">${d.location}</form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="destinationId" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${airplane_error?'has-error':''}">
                <form:label path="airplaneId" cssClass="col-sm-1 control-label">Airplane</form:label>
                <div class="col-sm-4">
                    <form:select path="airplaneId" cssClass="form-control">
                        <c:forEach items="${airplanes}" var="a">
                            <form:option value="${a.id}">${a.name}</form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="airplaneId" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group ${stewards_error?'has-error':''}">
                <form:label path="stewardsIds" cssClass="col-sm-1 control-label">Stewards</form:label>
                <div class="col-sm-4">
                    <form:select multiple="true" path="stewardsIds" cssClass="form-control">
                        <c:forEach items="${stewards}" var="s">
                            <form:option value="${s.id}">${s.personalIdentificator}</form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="stewardsIds" cssClass="help-block"/>
                </div>
            </div>
            <div class="form-group col-sm-4">
                <a href="${pageContext.request.contextPath}/flight" class="btn btn-danger">Back</a>
                <button class="btn btn-primary" type="submit">Create flight</button>
            </div>
        </form:form>
    </jsp:attribute>
</my:pagetemplate>
