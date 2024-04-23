<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <c:choose>
        <c:when test="${result == 0}">
        	<script>
            	location.href = "main";
            </script>
        </c:when>
        <c:when test="${result == 1}">
        	<script>
	            alert("회원정보가 틀렸습니다");
	            history.go(-1);
           	</script>
        </c:when>
        <c:when test="${result == -1}">
        	<script>
	            alert("비밀번호가 틀렸습니다");
	            history.go(-1);
            </script>
        </c:when>
    </c:choose>
</body>
</html>