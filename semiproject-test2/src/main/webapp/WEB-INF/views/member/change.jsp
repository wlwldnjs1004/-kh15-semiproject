<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h1>개인정보 변경</h1>

<form action="change" method="post">
	* 닉네임 <input type="text" name="memberNickname" required value="${memberDto.memberNickname}"> <br><br>
	* 이메일 <input type="email" name="memberEmail" required value="${memberDto.memberEmail}"> <br><br>
	생년월일 <input type="date" name="memberBirth" value="${memberDto.memberBirth}"> <br><br>
	연락처 <input type="tel" name="memberContact" value="${memberDto.memberContact}"> <br><br>
	주소 <br>
	<input type="text" name="memberPost" size="6" value="${memberDto.memberPost}"> <br>
	<input type="text" name="memberAddress1" size="60" value="${memberDto.memberAddress1}"> <br>
	<input type="text" name="memberAddress2" size="60" value="${memberDto.memberAddress2}"> <br>
	<br>
	* 비밀번호 확인 <input type="password" name="memberPw" required> <br><br>
	<button>정보 수정</button>
</form>

<c:if test="${param.error != null}">
	<h2 style="color:red">비밀번호가 일치하지 않습니다</h2>
</c:if>
