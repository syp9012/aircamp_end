<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>에어캠프</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
//아이디 정규표현식 함수
function checkId(id) {
	return /^[a-z0-9]{4,12}$/.test(id);
}
//핸드폰번호 자동하이픈 기능
const autoHyphen = (target) => {
    target.value = target.value
     .replace(/[^0-9]/g, '')
     .replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3").replace(/(\-{1,2})$/g, "");
}
//핸드폰번호 하이픈제거 함수
const removeHyphen = (phone) => {
    return phone.replace(/-/g, '');
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
	if ($.trim($("#u_pwd2").val()) == "") {
		alert("비밀번호 확인을 입력하세요");
		$("#u_pwd2").val("").focus();
		return false;
	}
	 if($.trim($("#u_pwd").val()) != $.trim($("#u_pwd2").val())){
		 alert("비번이 다릅니다!");
		 $("#u_pwd").val("");
		 $("#u_pwd2").val("");
		 $("#u_pwd").focus();
		 return false;
	 }
	if ($.trim($("#u_nickname").val()) == "") {
		alert("닉네임을 입력하세요");
		$("#u_nickname").val("").focus();
		return false;
	}
	if ($.trim($("#u_phone").val()) == "") {
		alert("핸드폰번호를 입력하세요");
		$("#u_phone").val("").focus();
		return false;
	}
    
    //중복체크 관련
    var chkresult = $("#id-result td").text();
    if (chkresult == "") {
        alert("아이디 중복검사를 해주세요");
        return false;
    } else if (chkresult == "이미 존재하는 아이디입니다") {
    	alert("중복된 아이디로 가입이 불가합니다");
    	return false;
    }
    
	//핸드폰번호에서 하이픈을 제거하고 숫자만 포함하는 문자열로 변경- 수정필요
	const u_phone = removeHyphen(document.getElementById("u_phone").value);
    document.getElementById("u_phone").value = u_phone;
}
function idCheck() { 
	var u_id =  $("#u_id").val();
	
	//아이디가 미입력시
	if (u_id === "") {
		alert("아이디를 입력하세요");
		$("#u_id").val("").focus();
		return false;
	} else { //아이디 입력시
		$.ajax({
		    type : 'POST',
		    url : "idCheck",
		    data: {"u_id" : u_id},
		    success : function(result) {
		        if (result==1) { //중복 ID
     		    	$("#id-result td").html("<font color=red>이미 존재하는 아이디입니다<font>");
	           		$("#u_id").focus();
		        } else { //사용가능 ID
		     	    $("#id-result td").html("<font color=blue>사용가능한 아이디입니다</font>");
     	    		$("#u_pwd").focus();
		        }
		    }
		}); //$.ajax() end
	}
}

</script>
</head>
<body>
	<%@ include file="../header.jsp"%>
	<main class="user-page wrap">
		<h2>회원가입</h2>
		<form method="post" action="join" onsubmit="return boardCheck();">
			<table>
				<tr>
					<th rowspan="2">아이디</th>
					<td class="colspan2">
						<input type="text" name="u_id" id="u_id" maxlength="12" placeholder="4~12자 사이의 영문(소문자),숫자"/>
						<input type="button" id="id-check" value="중복검사" onclick="return idCheck();">
					</td>
				</tr>
				<tr id="id-result">
					<td colspan="2"></td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td colspan="2"><input type="password" name="u_pwd" id="u_pwd" /></td>
				</tr>
				<tr>
					<th>비밀번호 확인</th>
					<td colspan="2"><input type="password" name="u_pwd2" id="u_pwd2" /></td>
				</tr>
				<tr>
					<th>닉네임</th>
					<td colspan="2"><input type="text" name="u_nickname" id="u_nickname" maxlength="8" /></td>
				</tr>
				<tr>
					<th>핸드폰번호</th>
					<td colspan="2">
					<input type="text" name="u_phone" id="u_phone"  maxlength="13"
						oninput="autoHyphen(this)" pattern="010-[0-9]{3,4}-[0-9]{4}"
						autocomplete="off" />
					</td>
				</tr>
			</table>
			<div class="btns">
				<input type="submit" value="가입하기">
				<input type="button" value="뒤로가기" onclick="history.go(-1);">
			</div>
		</form>
	</main>
	<%@ include file="../footer.jsp"%>
</body>
</html>