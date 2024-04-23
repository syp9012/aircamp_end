<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>에어캠프</title>
<script>
	function check() {
		if ($.trim($("#u_id").val()) == "") {
			alert("아이디를 입력하세요!");
			$("#id").val("").focus();
			return false;
		}
	}
</script>
</head>
<body>
	<%@ include file="../header.jsp"%>
	<main class="user-page wrap">
		<div id="pwd_wrap">
			<c:if test="${empty pwdok}">
				<h2 class="pwd_title">비밀번호 찾기</h2>
				<form method="post" action="/user/pwdFind" onsubmit="return check()">
					<table id="pwd_t">
						<tr>
							<th>아이디</th>
							<td><input name="u_id" id="u_id" size="14" class="input_box" /></td>
						</tr>
						<tr>
					</table>
					<div class="btns">
						<input type="submit" value="찾기" />
						<input type="button" value="뒤로가기" onclick="history.go(-1);"> 
					</div>
				</form>
			</c:if>
		</div>
		<c:if test="${!empty pwdok}">
			<script>
				alert("이메일을 확인하세요.");
			</script>
			<h2 class="pwd_title2">비번찾기 결과</h2>
			<table id="pwd_t2">
				<tr>
					<th>검색한 비번:</th>
					<td>${pwdok}</td>
				</tr>
			</table>
			<div id="pwd_close2">
				<input type="button" value="닫기" class="input_button"
					onclick="self.close();" />
				<!-- close()메서드로 공지창을 닫는다. self.close()는 자바스크립트이다. -->
			</div>
		</c:if>
	</main>
	<%@ include file="../footer.jsp"%>
</body>
</html>

