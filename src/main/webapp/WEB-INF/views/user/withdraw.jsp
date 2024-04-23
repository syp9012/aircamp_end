<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>에어캠프</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
function boardCheck() {
	if ($.trim($("#u_pwd").val()) == "") {
		alert("비밀번호를 입력하세요");
		$("#u_pwd").val("").focus();
		return false;
	}
}
</script>
</head>
<body>
	<%@ include file="../header.jsp"%>
	<main class="user-page wrap">
		<h2>회원탈퇴</h2>
		<form method="post" action="withdraw" onsubmit="return boardCheck();">
			<table>
				<tr>
					<th>아이디</th>
					<td>${u_id}</td>
				</tr>
				<tr>
					<th>닉네임</th>
					<td>${u_nickname}</td>
				</tr>
				<tr>
					<th>비밀번호 확인</th>
					<td><input type="password" name="u_pwd" id="u_pwd" /></td>
				</tr>
			</table>
			<div class="btns">
				<input type="submit" value="탈퇴하기">
				<input type="button" value="뒤로가기" onclick="history.go(-1);">
			</div>
		</form>
	</main>
	<%@ include file="../footer.jsp"%>
</body>
</html>