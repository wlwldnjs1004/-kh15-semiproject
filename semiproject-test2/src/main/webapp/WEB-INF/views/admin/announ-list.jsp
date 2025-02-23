<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="container w-500">
    <div class="cell center">
        <h1>공지 리스트</h1>
    </div>
    <div class="cell right">
        <a href="#" class="btn btn-neutral">공지 등록</a>
    </div>
    <div class="cell">
        <table class="table table-border table-stripe">
            <thead>
                <tr>
                    <th>글번호</th>
                    <th style="width:450px; max-width:450px;">제목</th>
                    <th>작성일</th>
                </tr>
            </thead>
            <tbody class="center">
                <tr>
                    <td>1</td>
                    <td style="max-width:200px">공지사항</td>
                    <td><fmt:formatDate value="${시간}" 
										pattern="y년 M월 d일 a h시 m분 s초"/></td>
                </tr>
            </tbody>
        </table>
    </div>
</div>
