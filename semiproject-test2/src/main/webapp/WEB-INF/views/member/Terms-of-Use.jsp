<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>






 <script type="text/javascript">
        $(function(){
 
            //체크박스 모듈화
            //[1] .check-all 선택 시 .check-item으로 상태 전파
            //[2] .check-item 선택 시 .check-all에 대한 체크 여부 검토
            $(".check-all").on("input", function(){
                //this의 체크 상태를 모든 체크박스로 전파
                var isCheck = $(this).prop("checked");
                $(".check-all, .check-item").prop("checked", isCheck);
            });
            $(".check-item").on("input", function(){
                var all = $(".check-item").length;
                //var checked = $(".check-item:checked").length;
                var checked = $(".check-item").filter(":checked").length;
                var allCheck = all == checked;

                $(".check-all").prop("checked", allCheck);
            });
        });
    </script>

<form action="">
 <div class="container w-500">
        <div class="cell center">
            <h1>이용약관 </h1>
        </div>

    
   

        <div class="cell">
            <input type="checkbox" class="check-all"> 전체동의
        </div>

        <div class="cell my-50">
            <textarea class="field w-100 target" rows="7">개인 이용 약관 (예시)

1.본 약관은 회사가 제공하는 서비스의 이용 조건을 규정합니다.
2.이용자는 본 약관에 동의함으로써 서비스를 사용할 수 있습니다.
3.회사는 서비스 개선을 위해 필요 시 약관을 변경할 수 있습니다.
4.이용자는 법령 및 공공질서를 준수하며 서비스를 이용해야 합니다.
5.회사는 서비스 제공과 관련하여 이용자의 개인정보를 보호합니다.
6.이용자의 부정 이용이 발견될 경우 서비스 이용이 제한될 수 있습니다.
7.회사는 불가피한 사유로 서비스 제공을 중단할 수 있으며, 사전 공지합니다.
8.본 약관과 관련된 분쟁은 대한민국 법률에 따릅니다.</textarea>
        </div>
      
        <div class="cell mt-20">
            <input type="checkbox" class="check-item"> [필수]동의
        </div>
        <div class="cell">
            <textarea class="field w-100 target" rows="7">개인정보 수신 동의 이용약관:

1.본인은 서비스 이용을 위해 필요한 개인정보 수집에 동의합니다.
2.수집된 개인정보는 서비스 제공 및 개선을 위해 사용됩니다.
3.개인정보는 제3자에게 제공되지 않으며, 보안이 철저하게 유지됩니다.
4.본인은 개인정보 처리에 대한 권리를 행사할 수 있습니다.
5.개인정보 수집에 동의하지 않을 경우 일부 서비스 이용에 제한이 있을 수 있습니다.</textarea>
        </div>

        <div class="cell mt-50">
            <input type="checkbox" class="check-item">[선택]동의 
        </div>
        <div class="cell">
            <textarea class="field w-100 target" rows="7">개인정보 이벤트 수신 동의 이용약관:

1.본인은 서비스 관련 이벤트 및 프로모션 정보 수신에 동의합니다.
2.수신되는 정보는 이메일, SMS, 앱 알림 등 다양한 방식으로 제공됩니다.
3.본인의 개인정보는 이벤트 관련 정보 발송에만 사용되며, 제3자에게 제공되지 않습니다.
4.언제든지 수신 동의를 철회할 수 있으며, 철회 후에는 더 이상 이벤트 정보가 발송되지 않습니다.
5.이벤트 정보 수신에 동의하지 않으면 해당 이벤트 참여가 제한될 수 있습니다.</textarea>
        </div>
        <div class="cell my-50">
            <input type="checkbox" class="check-all"> 전체 동의 하기
        </div>
        <div class="cell my-0">
            <button class="btn btn-positive w-100">다음</button>
        </div>
    </div>

</form>

</body>
</html>