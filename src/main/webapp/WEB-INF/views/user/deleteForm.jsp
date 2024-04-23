<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>에어캠프</title>
<script>
function pwdCheck() {
	if ($.trim($("#u_pwd").val()) == "") {
		alert("비밀번호를 입력하세요");
		$("#u_pwd").val("").focus();
		return false;
	}
}
</script>

</head>
<body>
<form method="post" action="join" onsubmit="return boardCheck();">
		<div id="board-form">
		<h2>회원가입</h2>
		<form method="post" action="join" onsubmit="return boardCheck();">
			<table class="board-table">
				<tr>
					<th>비밀번호</th>
					<td colspan="2"><input type="password" name="u_pwd" id="u_pwd" /></td>
				</tr>
			
			<div class="board-btns">
				<input type="submit" value="삭제">
				<input type="button" value="뒤로가기" onclick="history.go(-1);">
			</div>
		</form>
</body>
</html>