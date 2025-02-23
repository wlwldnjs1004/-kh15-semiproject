<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


	
<div class="container w-500">
    <div class="cell center">
        <h1>후기 목록</h1>
    </div>
<div class="cell center">
		<form action="list" method="get">
			<select name="column" class="field">
				<option value="" ${param.column == '' ? 'selected' : ''}>??</option>
				<option value="" ${param.column == '' ? 'selected' : ''}>??</option>
			</select>
			<input type="text" name="keyword" value="${param.keyword}" class="field w-100">
			<button class="btn btn-positive">검색</button>
		</form>
	</div>
	
    <div class="cell right">
        <a href="#" class="btn btn-neutral">공지 등록</a>
    </div>
    <div class="cell">
        <table class="table table-border table-stripe">
            <thead>
                <tr>
                    <th>번호</th>
                    <th>지역</th>
                    <th>작성자</th>
                    <th style="width:450px; max-width:450px;">제목</th>
                    <th>조회수</th>
                    <th>작성일</th>
                    
                </tr>
            </thead>
            <tbody class="center">
                <tr>
                    <td>1</td>
                    <td style="max-width:200px">서울</td>
					<td>작성자</td>
				 	<td>제목</td>
				 	<td>조회수</td>
                    <td><fmt:formatDate value="${작성일}" 						    
						  				pattern="y년 M월 d일 a h시 m분 s초"/></td>
					
                </tr>
            </tbody>
        </table>
    </div>
</div>
