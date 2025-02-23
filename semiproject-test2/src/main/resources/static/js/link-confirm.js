$(function(){
    //class=link-confirm 인 항목은 확인창 출력 후 이동하도록 구현
    //→ inline javascript에서는 return true/false로 허용/차단 설정
    //→ standalone javascript에서는 e.preventDefault()로 차단 설정
    //→ jQuery는 둘다 가능
    $(".link-confirm").click(function(e){
        //data-comment가 있으면 해당 값을 우선 적용, 없으면 기본메세지 출력
        var comment = $(this).data("comment") || "정말 이동하시겠습니까?";
        return window.confirm(comment);
    });
});