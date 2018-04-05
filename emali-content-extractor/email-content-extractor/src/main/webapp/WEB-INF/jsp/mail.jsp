<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${error ne null}">
	<div class="alert alert-danger">Error: ${error}</div>
</c:if>

<table class="table">
	<caption>Defects</caption>
	<thead>
		<tr>

			<th>Roll No</th>
			<th>Defect</th>
			<th>Defect Position</th>
			<th>Issue</th>
			<th>Process Method</th>
			<th>Remark</th>
			<th>Date and Time</th>
		</tr>
	</thead>
	<tbody>

<c:if test="${size>=1}">
		<c:forEach begin="0" end="${size -1}" var="index">
			<tr>
				<td><c:out
						value="${defects.getJSONObject(index).getString('rollNo')}" /></td>
				<td><c:out
						value="${defects.getJSONObject(index).getString('defectD')}" /></td>
				<td><c:out
						value="${defects.getJSONObject(index).getString('defectPosition')}" /></td>
				<td><c:out
						value="${defects.getJSONObject(index).getString('issueI')}" /></td>

				<td><c:out
						value="${defects.getJSONObject(index).getString('processMethod')}" /></td>
				<td><c:out
						value="${defects.getJSONObject(index).getString('remarkR')}" /></td>
						<td><c:out
						value="${defects.getJSONObject(index).getString('datetimeD')}" /></td>

			</tr>
		</c:forEach>
		</c:if>
	</tbody>
</table>