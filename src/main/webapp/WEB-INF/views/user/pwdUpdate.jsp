<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
</head>
<body>
<c:if test="${result == 1 }">
	<script>
		location.href="/user/myPage";
	</script>
</c:if>
<c:if test="${result != 1 }">
	<script>
		alert("현재 비밀번호가 틀렸습니다.");
		history.go(-1);
	</script>
</c:if>