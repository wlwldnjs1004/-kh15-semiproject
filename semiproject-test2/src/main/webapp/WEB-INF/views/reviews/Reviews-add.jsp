<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	
	
<!-- summernote cdn -->
<link href="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote-lite.min.css" rel="stylesheet">
<style>
	 .note-editor {
		border: 1px solid #636e72 !important;
	}
	.note-editable {
	    background-color: white !important;
	} 
</style>
<script src="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote-lite.min.js"></script>
<script src="/js/summernote-custom.js"></script>

	<form enctype="application/x-www-form-urlencoded"></form>
    <div class="container w-800">
        <div class="cell center">
            <h1>후기 작성창</h1>
        </div>
        
        <form action="" method="post">
        <div class="cell center">
           
            <input class="field w-100" type="text" name="" placeholder="제목">
        </div>
        
            <div class="cell my-40">
            <textarea name="boardContent"></textarea>
        </div>

        <div class="cell">
            <button type="submit" class="btn btn-positive w-100">작성하기</button>
        </div>
        </form>
    </div>
	
	
