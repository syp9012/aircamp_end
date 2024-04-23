<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호</title>
</head>
<body>


<c:if test="${result == 0}">
	<script>
	alert("회원아이디와 이름이 맞지 않습니다!");
	history.go(-1);
</script>
</c:if>   
<c:if test="${result == 1}">
	<script>
		alert("이메일로 임시 비밀번호가 발급되었습니다.");
		location.href="main";
	</script>
</c:if>   
</body>
</html>