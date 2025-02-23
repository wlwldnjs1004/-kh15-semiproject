<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>



<!-- Lightpick 라이브러리 -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/lightpick@1.6.2/css/lightpick.min.css">
<script src="https://cdn.jsdelivr.net/npm/lightpick@1.6.2/lightpick.min.js"></script>

<!-- kakao post api -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="/js/member/join.js"></script>
    
<form class="form-check" action="" method="post" autocomplete="off">
	<div class="container w-500">
	    <div class="cell center">
	        <h1>가입 정보 입력</h1>
	    </div>
	    <div class="cell">
	        <label>아이디 <i class="fa-solid fa-asterisk red"></i></label>
	        <input type="text" name="memberId" class="field w-100">
	        <div class="success-feedback">멋진 아이디입니다!</div>
	        <div class="fail-feedback">아이디는 알파벳으로 시작하며 숫자를 포함한 8~20자로 작성하세요</div>
	        <div class="fail2-feedback">아이디가 이미 사용중입니다</div>
	    </div>
	    <div class="cell">
	        <label>비밀번호 <i class="fa-solid fa-asterisk red"></i></label>
	        <input type="password" name="memberPw" class="field w-100">
	        <div class="success-feedback">비밀번호가 올바른 형식입니다</div>
	        <div class="fail-feedback">알파벳 대문자, 소문자, 숫자, 특수문자를 반드시 한 글자 이상 포함해서 8~16자로 작성하세요</div>
	    </div>
	    <div class="cell">
	        <label>비밀번호 확인 <i class="fa-solid fa-asterisk red"></i></label>
	        <input type="password" id="pw-reinput" class="field w-100">
	        <div class="success-feedback">비밀번호가 일치합니다</div>
	        <div class="fail-feedback">비밀번호가 일치하지 않습니다</div>
	        <div class="fail2-feedback">비밀번호를 먼저 형식에 맞게 작성하세요</div>
	    </div>
	    <div class="cell">
	        <label>닉네임 <i class="fa-solid fa-asterisk red"></i></label>
	        <input type="text" name="memberNickname" class="field w-100">
	        <div class="success-feedback">멋진 닉네임이네요!</div>
	        <div class="fail-feedback">닉네임은 한글 또는 숫자 2~10자로 작성하세요</div>
	        <div class="fail2-feedback">닉네임이 이미 사용중입니다</div>
	    </div>
	    <div class="cell">
	        <label>이메일 <i class="fa-solid fa-asterisk red"></i></label>
	        <input type="email" inputmode="email" name="memberEmail" class="field w-100">
	        <div class="fail-feedback">이메일은 필수 항목입니다</div>
	    </div>
	    <div class="cell">
	        <label>생년월일</label>
	        <input type="text" name="memberBirth" class="field w-100">
	    </div>
	    <div class="cell">
	        <label>연락처</label>
	        <input type="tel" name="memberContact" class="field w-100">
	        <div class="fail-feedback">010으로 시작하는 휴대전화번호를 작성하세요</div>
	    </div>
	    <div class="cell">
	        <label>주소</label>
	    </div>
	    <div class="cell">
	        <input type="text" name="memberPost" size="6" maxlength="6" class="field" placeholder="우편번호" readonly>
	        <button type="button" class="btn btn-neutral btn-address-search">
	            <i class="fa-solid fa-magnifying-glass"></i>
	        </button>
	        <button type="button" class="btn btn-negative btn-address-clear"
	                                                                    style="display: none;">
	                <i class="fa-solid fa-xmark"></i>
	            </button>
	        </div>
	        <div class="cell">
	            <input type="text" name="memberAddress1" class="field w-100" placeholder="기본주소" readonly>
	        </div>
	        <div class="cell">
	            <input type="text" name="memberAddress2" class="field w-100" placeholder="상세주소">
	            <div class="fail-feedback">주소는 모두 작성하셔야 합니다</div>
	        </div>
	
	        <div class="cell mt-30">
	            <button type="submit" class="btn btn-positive w-100">회원가입</button>
	        </div>
	    </div>
	</form>


