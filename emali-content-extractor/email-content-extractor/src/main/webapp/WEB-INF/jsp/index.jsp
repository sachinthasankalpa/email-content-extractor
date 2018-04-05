<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${error ne null}">
	<div class="alert alert-danger">${error}</div>
</c:if>
<div class="jumbotron">
	
	<p><a class="btn btn-lg btn-primary" href="<spring:url value="${loginUrl}" />">Sign in to Outlook</a></p>
</div>