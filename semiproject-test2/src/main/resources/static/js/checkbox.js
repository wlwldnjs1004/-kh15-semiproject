$(function () {
    //체크박스 모듈화
    //[1] .check-all 선택 시 .check-item으로 상태 전파
    //[2] .check-item 선택 시 .check-all에 대한 체크 여부 검토
    $(".check-all").on("input", function () {
        //this의 체크 상태를 모든 체크박스로 전파
        var isCheck = $(this).prop("checked");
        $(".check-all, .check-item").prop("checked", isCheck);
    });
    $(".check-item").on("input", function () {
        var all = $(".check-item").length;
        //var checked = $(".check-item:checked").length;
        var checked = $(".check-item").filter(":checked").length;
        var allCheck = all == checked;

        $(".check-all").prop("checked", allCheck);
    });
});