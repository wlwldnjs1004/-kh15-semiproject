<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


 <style>
        a {
    text-decoration: none; /* 밑줄 제거 */
    color: black; /* 기본 글자 색 (원하는 색으로 변경 가능) */
}

a:hover {
    color: #3498db; /* 마우스를 올렸을 때 변경할 색상 */
}
    </style>


    <form action="login" method="post">
        <div class="container w-400">
            <div class="cell center">
                <h1>로그인</h1>
            </div>
            <div class="cell">
                <label>ID</label>
                 <input type="text" name="memberId" required class="field w-100">
            </div>
            <div class="cell">
                <label>pass word</label>
                <input type="password" name="memberPw" required class="field w-100">
            </div>
          <div class="cell right">
            <label><a href="">아이디를 잊으셨나요?</a></label>
          </div>
            <div class="cell mt-20 center">
                <button type="submit" class="btn btn-positive w-25">로그인하기</button>
            </div>
        
            
            <c:if test="${param.error != null}">
            <div class="cell center">
                <h2 class="red">로그인 정보가 일치하지 않습니다</h2>
            </div>
            </c:if>
        </div>
        
        </form>

