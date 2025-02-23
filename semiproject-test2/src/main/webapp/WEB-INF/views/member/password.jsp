<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<form action="password" method="post">
<div class="container w-400">
	<div class="cell center">
		<h1>비밀번호 변경</h1>
	</div>
	<div class="cell">
		<label>기존 비밀번호</label>
		<input type="password" name="currentPw" required class="field w-100">
	</div>
	<div class="cell">
		<label>신규 비밀번호</label>
		<input type="password" name="newPw" required class="field w-100">
	</div>
	<div class="cell">
		<button type="submit" class="field w-100">변경하기</button>
	</div>
	
	<!-- error message -->
	<c:if test="${param.error == '1'}">
	<div class="cell center">
		<h3 class="red">비밀번호가 일치하지 않습니다</h3>
	</div>
	</c:if>
	<c:if test="${param.error == '2'}">
	<div class="cell center">
		<h3 class="red">같은 비밀번호로는 변경할 수 없습니다</h3>
	</div>
	</c:if>
</div>
		
</form>


