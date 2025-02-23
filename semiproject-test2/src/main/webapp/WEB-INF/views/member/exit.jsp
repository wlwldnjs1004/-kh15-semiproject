<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
    

<div class="container w-500">
    <div class="cell center">
        <h1>비밀번호 확인</h1>
    </div>
    <div class="cell">

            <textarea class="field w-100 target" rows="7">개인정보 수집 및 이용 동의서

                1. 개인정보 수집 및 이용 목적
                본 서비스는 다음과 같은 목적으로 개인정보를 수집 및 이용합니다.
                
                서비스 제공 및 운영
                
                고객 문의 응대 및 지원
                
                법적 의무 준수
                
                2. 수집하는 개인정보 항목
                
                필수 항목: 이름, 연락처(전화번호, 이메일)
                
                선택 항목: 생년월일, 주소
                
                3. 개인정보 보유 및 이용 기간
                수집된 개인정보는 서비스 제공 목적이 달성된 후 즉시 파기됩니다. 단, 관련 법령에 따라 보관이 필요한 경우 해당 기간 동안 보관될 수 있습니다.
                
                4. 개인정보 제3자 제공 및 위탁
                본 서비스는 이용자의 동의 없이 개인정보를 외부에 제공하지 않습니다. 다만, 원활한 서비스 운영을 위해 외부 업체에 개인정보 처리를 위탁할 수 있으며, 이 경우 사전에 안내드립니다.
                
              5. 동의 거부 권리 및 불이익
                이용자는 개인정보 제공에 대한 동의를 거부할 권리가 있습니다. 다만, 필수 항목의 제공을 거부할 경우 서비스 이용이 제한될 수 있습니다.
                
                본인은 위 내용을 충분히 이해하였으며, 이에 동의합니다.</textarea>
        </div>
   <div class="cell">
    <input type="checkbox" class="check-item" >[필수]동의
   </div>

    <div class="cell my-30">
       
        <form class="form-check" action="exit" method="post">
            <label>비밀번호(필수)</label>
            <input class="field w-100" type="password" name="memberPw" >
        <div class="cell my-30 mt-10">

            <label>비밀번번호 재확인(필수)</label>
            <input class="field w-100" type="password">
        </div>
            
        <div class="cell">
            <label>탈퇴사유(생략가능)</label>
            <textarea class="field w-100 target" rows="7" cols="20">
        
            </textarea>
        </div>
    <div class="cell right">
        <button class="btn btn-neutral w-25 mt-20 mx-10">돌아가기</button>
        <button class="btn btn-negative w-25 mt-20">회원 탈퇴</button>
    </div>
</form>

<c:if test="${param.error != null}">
	<h2 style="color:red">비밀번호가 일치하지 않습니다</h2>
</c:if>
    </div>
</div>  
    