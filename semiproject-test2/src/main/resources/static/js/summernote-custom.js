//에디터 처리
$(function(){
    $("[name=boardContent]").summernote({
        height:250,//높이(px)
        minHeight:200, //최소높이(px)
        maxHeight:400, //최대높이(px)

        placeholder:"타인에 대한 무분별한 비방 시 예고 없이 삭제될 수 있습니다",

        //메뉴(toolbar) 설정
        toolbar: [
            //['메뉴명', ['버튼명', '버튼명', ...]]
            ["font", ["style", "fontname", "fontsize", "forecolor", "backcolor"]],
            ["style", ["bold", "italic", "underline", "strikethrough"]],
            ["attach", ["picture"]],
            ["tool", ["ol", "ul", "table", "hr", "fullscreen"]],
            // ["action", ["undo", "redo"]],
        ],

        //상황에 맞는 callback 함수들
        callbacks: {
            onImageUpload: function(files){
                //예상 시나리오
                //1. 서버로 사용자가 선택한 이미지를 업로드
                //  - 이미지는 multipart/form-data 형태여야 한다
                //  - 상황상 form을 쓸 수가 없으므로 ajax를 써야 한다
                //2. 업로드한 이미지에 접근할 수 있는 정보 획득
                //3. 획득한 정보로 <img> 생성
                //4. 에디터에 추가
                //- $("[name=boardContent]").summernote("insertNode", 이미지태그객체);

                //console.log(files);
                if(files.length == 0) return;

                var form = new FormData();//form을 대신할 도구
                for(var i=0; i < files.length; i++) {
                    form.append("attach", files[i]);
                }

                $.ajax({
                    processData: false,//파일업로드를 위해 반드시 필요한 설정
                    contentType: false,//파일업로드를 위해 반드시 필요한 설정
                    url:"/rest/board/uploads",
                    method:"post",
                    data: form,
                    success:function(response){//첨부파일번호들(List<Integer>)
                        for(var i=0; i < response.length; i++) {
                            var tag = $("<img>").attr("src", "/attachment/download?attachmentNo="+response[i])
                                                         .addClass("summernote-img")
														 .attr("data-attachment-no", response[i]);
                            $("[name=boardContent]").summernote("insertNode", tag[0]);
                        }
                    }
                });
            },
        },
    });
});

//페이지를 벗어나는 행위를 어렵게 만들어보자!
//- flag가 true면 페이지 이탈 가능, false면 불가능
$(function(){
	$("[name=boardContent]").on("summernote.change", function(){
		checkInputState();		
	});
	$("[name=boardTitle]").on("input", function(){
		checkInputState();
	});	
	$(".form-check").submit(function(){
		$(window).off("beforeunload");
		return true;
	});
	
	function checkInputState() {
		var titleEmpty = $("[name=boardTitle]").val().length == 0;
		var contentEmpty = $("[name=boardContent]").val().length == 0;
		var isEmpty = titleEmpty && contentEmpty;
		
		if(isEmpty) {
			$(window).off("beforeunload");
		}
		else {
			$(window).off("beforeunload");
			$(window).on("beforeunload", function(){ return false; });
		}
	}
});