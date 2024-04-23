<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>에어캠프</title>
<script>
	function check() {
		if ($.trim($("#u_pwd").val()) == "") {
			alert("현재 비밀번호를 입력하세요!");
			$("#id").val("").focus();
			return false;
		}
		if ($.trim($("#u_newpwd").val()) == "") {
			alert("신규 비밀번호를 입력하세요!");
			$("#id").val("").focus();
			return false;
		}
		if ($.trim($("#u_pwd2").val()) == "") {
			alert("비밀번호 확인을 입력하세요!");
			$("#id").val("").focus();
			return false;
		}
	}
</script>
</head>
<body>
	<%@ include file="../header.jsp"%>
	<main class="user-page wrap">
		<h2>마이페이지</h2>
		<div class="user-tab">
			<ul>
				<li><a href="/user/myPage">개인정보</a></li>
				<li><a href="/user/update">정보수정</a></li>
				<li class="on"><a href="/user/pwdUpdate">비밀번호 변경</a></li>
			</ul>
		</div>
		<form action="/user/pwdUpdate" method="post" onsubmit="return check()">
			<input type="hidden" name="u_id" value="${sessionScope.u_id }" />
			<table>
				<tr>
					<th>아이디</th>
					<td>${sessionScope.u_id }</td>
				</tr>
				<tr>
					<th>현재 비밀번호</th>
					<td><input type="password" id="u_pwd" name="u_pwd"></td>
				</tr>
				<tr>
					<th>신규 비밀번호</th>
					<td><input type="password" id="u_newpwd" name="u_newpwd"></td>
				</tr>
				<tr>
					<th>비밀번호 확인</th>
					<td><input type="password" id="u_pwd2"></td>
				</tr>
			</table>
			<div class="btns">
				<input type="submit" value="등록">
			</div>
		</form>
	</main>
	<%@ include file="../footer.jsp"%>
</body>
</html>

