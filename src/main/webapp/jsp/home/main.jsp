<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int loginedMemberId = (int) request.getAttribute("loginedMemberId");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 페이지</title>
</head>
<body>
	<h1>메인 페이지</h1>
	<!--로그인을 하지 않은 경우(로그인한 아이디의 정보가 없는 경우)
	    로그인과 회원가입이 나타남   -->
	<%
	if(loginedMemberId == -1){
    %>
		<div><a href="../member/login">로그인</a></div>
		<div><a href="../member/join">회원가입</a></div>
    <%
	}
    %>
    <!-- 로그인을 한 경우(로그인한 아이디의 정보가 있는 경우)
         로그아웃이 나타남  -->
    <%
    if(loginedMemberId != -1){
    %>	
    	<div><a href="../member/doLogout">로그아웃</a></div>
    <%
    }
    %>
	<div><a href="../article/list">게시물 리스트</a></div>
	
</body>
</html>