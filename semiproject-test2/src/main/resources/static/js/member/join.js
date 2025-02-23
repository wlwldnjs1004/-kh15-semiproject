//회원 가입 jQuery
$(function() {

	//상태 변수
	var status = {
		memberId: false,
		memberPw: false,
		memberPwReinput: false,
		memberNickname: false,
		memberEmail: false,
		memberBirth: true,
		memberContact: true,
		memberAddress: true,
		ok: function() {
			return this.memberId && this.memberPw
				&& this.memberPwReinput && this.memberNickname
				&& this.memberEmail && this.memberBirth
				&& this.memberContact && this.memberAddress;
		},
	};

	//아이디 관련 처리
	$("[name=memberId]").on("input", function() {
		var current = $(this).val();
		var convert = current.replace(/[^a-z0-9]+/g, "");
		$(this).val(convert);
	});
	$("[name=memberId]").blur(function() {
		var regex = /^[a-z][a-z0-9]{7,19}$/;
		var memberId = $(this).val();

		if (regex.test(memberId)) {//형식통과
			$.ajax({
				url: "/rest/member/checkMemberId",
				method: "post",
				data: { memberId: memberId },
				success: function(response) {
					//response가 true면 사용가능이므로 status.memberId = true
					//response가 false면 사용중이므로 status.memberId = false
					status.memberId = response;
					$("[name=memberId]").removeClass("success fail fail2")
						.addClass(response ? "success" : "fail2");
				}
			});
		}
		else {//형식위반
			status.memberId = false;
			$("[name=memberId]").removeClass("success fail fail2")
				.addClass("fail");
		}
	});

	//비밀번호 관련 처리
	$("[name=memberPw]").blur(function() {
		var regex = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$])[A-Za-z0-9!@#$]{8,16}$/;
		var isValid = regex.test($(this).val());
		$(this).removeClass("success fail").addClass(isValid ? "success" : "fail");
		status.memberPw = isValid;
	});

	//비밀번호 확인 관련 처리
	$("#pw-reinput").blur(function() {
		var memberPw = $("[name=memberPw]").val();
		var memberPwReinput = $(this).val();
		$(this).removeClass("success fail fail2");
		//if(memberPw.length == 0) {//비밀번호 미입력
		if (status.memberPw == false) {//비밀번호가 형식에 부합하지 않음
			$(this).addClass("fail2");
			status.memberPwReinput = false;
		}
		else if (memberPw != memberPwReinput) {//비밀번호 다름
			$(this).addClass("fail");
			status.memberPwReinput = false;
		}
		else {//비밀번호 일치
			$(this).addClass("success");
			status.memberPwReinput = true;
		}
	});

	//닉네임 관련 처리
	$("[name=memberNickname]").blur(function() {
		var regex = /^[가-힣0-9]{2,10}$/;
		var memberNickname = $(this).val();

		if (regex.test(memberNickname)) {//형식 ok
			$.ajax({
				url: "/rest/member/checkMemberNickname",
				method: "post",
				data: { memberNickname: memberNickname },
				success: function(response) {
					status.memberNickname = response;
					$("[name=memberNickname]").removeClass("success fail fail2")
						.addClass(response ? "success" : "fail2");
				}
			});
		}
		else {//형식 nok
			status.memberNickname = false;
			$("[name=memberNickname]").removeClass("success fail fail2")
				.addClass("fail");
		}
	});

	//이메일 관련 처리
	$("[name=memberEmail]").blur(function() {
		var isValid = $(this).val().length > 0;
		$(this).removeClass("success fail").addClass(isValid ? "success" : "fail");
		status.memberEmail = isValid;
	});

	//연락처 관련 처리
	$("[name=memberContact]").on("input", function() {
		var current = $(this).val();
		var convert = current.replace(/[^0-9]+/g, "");

		// (+추가) 자동 형식 변환 (ex) 010-1234-5678
		// - convert가 3글자 이하라면 대시를 추가할 필요가 없음
		// - convert가 4~7글자라면 대시 1개 추가
		// - convert가 8글자 이상이라면 대시 2개를 추가
		// if(convert.length <= 3) {
		//     //값 변환 없음(통과)
		// }
		// else if(convert.length <= 7) {
		//     convert = convert.replace(/([0-9]{3})([0-9]{1,4})/, "$1-$2");
		// }
		// else {
		//     convert = convert.replace(/([0-9]{3})([0-9]{4})([0-9]{1,4})/, "$1-$2-$3");
		// }

		$(this).val(convert);
	});
	$("[name=memberContact]").blur(function() {
		var regex = /^010[0-9]{8}$/;
		//(주의) 필수항목이 아니기 때문에 미입력도 통과로 간주
		var isValid = $(this).val().length == 0 || regex.test($(this).val());
		$(this).removeClass("success fail").addClass(isValid ? "success" : "fail");
		status.memberContact = isValid;
	});

	//주소 관련 처리
	$("[name=memberPost]").on("input", function() {
		var current = $(this).val();
		var convert = current.replace(/[^0-9]+/g, "");
		$(this).val(convert);
	});
	$("[name=memberPost], [name=memberAddress1], .btn-address-search").click(function() {
		new daum.Postcode({
			oncomplete: function(data) {
				// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

				// 각 주소의 노출 규칙에 따라 주소를 조합한다.
				// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
				var addr = ''; // 주소 변수
				var extraAddr = ''; // 참고항목 변수

				//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
				if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
					addr = data.roadAddress;
				} else { // 사용자가 지번 주소를 선택했을 경우(J)
					addr = data.jibunAddress;
				}

				// 우편번호와 주소 정보를 해당 필드에 넣는다.
				document.querySelector("[name=memberPost]").value = data.zonecode;
				document.querySelector("[name=memberAddress1]").value = addr;
				// 커서를 상세주소 필드로 이동한다.
				document.querySelector("[name=memberAddress2]").focus();

				// 값 설정 이후 주소 삭제 버튼을 표시
				displayClearButton();
			}
		}).open();
	});
	$("[name=memberAddress2]").blur(function() {
		var memberPost = $("[name=memberPost]").val();
		var memberAddress1 = $("[name=memberAddress1]").val();
		var memberAddress2 = $("[name=memberAddress2]").val();

		var isEmpty = memberPost.length == 0 && memberAddress1.length == 0 && memberAddress2.length == 0;
		var isFill = memberPost.length > 0 && memberAddress1.length > 0 && memberAddress2.length > 0;
		var isValid = isEmpty || isFill;
		$("[name=memberPost], [name=memberAddress1], [name=memberAddress2]")
			.removeClass("success fail").addClass(isValid ? "success" : "fail");
		status.memberAddress = isValid;
	});
	$("[name=memberAddress2]").on("input", function() {
		displayClearButton();
	});
	$(".btn-address-clear").click(function() {
		$("[name=memberPost]").val("");
		$("[name=memberAddress1]").val("");
		$("[name=memberAddress2]").val("");
		status.memberAddress = true;
		displayClearButton();
	});

	//주소 삭제 버튼을 표시/제거하는 함수
	function displayClearButton() {
		var post = $("[name=memberPost]").val();
		var address1 = $("[name=memberAddress1]").val();
		var address2 = $("[name=memberAddress2]").val();

		var exist = post.length > 0 || address1.length > 0 || address2.length > 0;
		if (exist) {
			$(".btn-address-clear").fadeIn();
		}
		else {
			$(".btn-address-clear").fadeOut();
		}
	}

	//생년월일 관련 처리
	var picker = new Lightpick({
		field: document.querySelector("[name=memberBirth]"), /* 적용대상 설정 */
		format: "YYYY-MM-DD", /* 출력형식 변경 */
		firstDay: 7, /* 일요일부터 나오게 설정 */
		maxDate: moment(), /* 종료일을 오늘로 설정 */
	});
	$("[name=memberBirth]").blur(function() {
		var regex = /^(19[0-9]{2}|20[0-9]{2})-((02-(0[1-9]|1[0-9]|2[0-8]))|((0[469]|11)-(0[1-9]|1[0-9]|2[0-9]|30))|((0[13578]|1[02])-(0[1-9]|1[0-9]|2[0-9]|3[01])))$/;
		var isValid = $(this).val().length == 0 || regex.test($(this).val());
		$(this).removeClass("success fail").addClass(isValid ? "success" : "fail");
		status.memberBirth = isValid;
	});

	//폼 검사
	$(".form-check").submit(function() {
		$("[name], #pw-reinput").trigger("blur");
		return status.ok();
	});

});