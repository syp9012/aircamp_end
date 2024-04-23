<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>에어캠프</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
//아이디 유효성검사
function checkId(id) {
	return /^[a-z0-9]{4,12}$/.test(id);
}

function boardCheck() {
	var userId = $.trim($("#u_id").val());
	if (userId === "") {
		alert("아이디를 입력하세요");
		$("#u_id").val("").focus();
		return false;
	}
	if (!checkId(userId)) {
        alert("아이디는 4~12자 이내로 소문자와 숫자만 사용할 수 있습니다");
        $("#u_id").val("").focus();
        return false;
    }
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
		<div id="board-form">
			<h2>로그인</h2>
			<form method="post" action="login" onsubmit="return boardCheck()">
				<table>
					<tr>
						<th>아이디</th>
						<td><input type="text" name="u_id" id="u_id" maxlength="12" /></td>
					</tr>
					<tr>
						<th>비밀번호</th>
						<td colspan="2"><input type="password" name="u_pwd" id="u_pwd" /></td>
					</tr>
				</table>
				<div class="btns">
					<input type="submit" value="로그인">
					<input type="button" value="비밀번호 찾기" onClick="location.href='/user/pwdFind'">
				</div>
				<div class="btns-social">
               <a href="https://kauth.kakao.com/oauth/authorize?client_id=bea908dc4fb5d657540741dd3523eefd&redirect_uri=http://13.124.184.222:8080/oauth/kakao/callback&response_type=code">
                  <img src="/images/ico_kakao.png" >
               </a>
               <a href="https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=gWHz98Kmv8VTU8BIGrB2&redirect_uri=http://13.124.184.222:8080/oauth/naver/callback&state=1234">
                  <img src="/images/ico_naver.png" >
               </a>
            </div>
			</form>
		</div>
	</main>
	<%@ include file="../footer.jsp"%>
</body>
</html>