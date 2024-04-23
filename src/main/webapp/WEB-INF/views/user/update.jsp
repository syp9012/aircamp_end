<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>에어캠프</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
function openDaumPostcode() {
	new daum.Postcode({
		oncomplete : function(data) {
			document.getElementById('u_zipcode').value = data.zonecode;
			document.getElementById('u_addr1').value = data.address;
		}
	}).open();
}
function domain_list(select) {
    var u_domain = document.getElementById("u_domain");
    var u_email = document.getElementById("u_email");
    var selectedOption = select.options[select.selectedIndex];

    if (selectedOption.value === "") {
        // 선택한 목록이 없는 경우
        return true;
    }
    if (selectedOption.value === "0") {
        // 직접입력을 선택한 경우
        u_domain.value = "";
        u_domain.readOnly = false;
        u_domain.focus();
    } else {
        // 리스트 목록을 선택한 경우
        u_domain.value = selectedOption.value;
        u_domain.readOnly = true;
    }
}

function boardCheck() {
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
		<h2>마이페이지</h2>
		<div class="user-tab">
			<ul>
				<li><a href="/user/myPage">개인정보</a></li>
				<li class="on"><a href="/user/update">정보수정</a></li>
				<li><a href="/user/pwdUpdate">비밀번호 변경</a></li>
			</ul>
		</div>
		<form method="post" action="update" onsubmit="return boardCheck();" enctype="multipart/form-data">
		<input type="hidden" name= "u_id" value="${user.u_id}">
		<input type="hidden" name="u_social" value="normal">
			<table>
				<tr>
					<th>아이디</th>
					<td>${user.u_id}</td>
				</tr>
				<tr>
					<th>닉네임</th>
					<td><input type="text" name="u_nickname" id="u_nickname" value="${user.u_nickname}" /></td>
				</tr>
				<tr>
					<th>핸드폰</th>
					<td>
						<input type="text" name="u_phone" id="u_phone" placeholder="ex) 01012345678"
						maxlength="11" autocomplete="off" value="${user.u_phone}" />
					</td>
				</tr>
				<tr>
					<th>생년월일</th>
					<td><input type="text" name="u_birth" id="u_birth" placeholder="ex) 1990401" maxlength="8" value="${user.u_birth}" /></td>
				</tr>
				<tr>
					<th>우편번호</th>
					<td class="colspan2">
						<input name="u_zipcode" id="u_zipcode" size="5" readonly onclick="openDaumPostcode()" value="${user.u_zipcode}" /> 
						<input type="button" value="검색" onclick="openDaumPostcode()" />
					</td>
				</tr>
				<tr>
					<th>주소</th>
					<td><input name="u_addr1" id="u_addr1" readonly onclick="openDaumPostcode()" value="${user.u_addr1}" /></td>
				</tr>
				<tr>
					<th>상세주소</th>
					<td><input name="u_addr2" id="u_addr2" value="${user.u_addr2}" /></td>
				</tr>
				<tr>
				    <th>이메일</th>
				    <td class="colspan2">
				    	<input name="u_email" id="u_email" value="${user.u_email}"/>@
					    <input name="u_domain" id="u_domain" value="${user.u_domain}" />
				     	<select onchange="domain_list(this)">
				    	<option value="">선택</option>
						    <option value="gmail.com" <c:if test="${user.u_domain == 'gmail.com'}">${'selected'}</c:if>>gmail.com</option>
						    <option value="naver.com" <c:if test="${user.u_domain == 'naver.com'}">${'selected'}</c:if>>naver.com</option>
						    <option value="daum.net" <c:if test="${user.u_domain == 'daum.net'}">${'selected'}</c:if>>daum.net</option>
						    <option value="nate.com" <c:if test="${user.u_domain == 'nate.com'}">${'selected'}</c:if>>nate.com</option>
						    <option value="0">직접입력</option>
					    </select> 
			    	</td>
			   	</tr>
			   	<tr>
		            <th>프로필사진</th>
		            <td><input type="file" id="u_profile" name="u_profile1"></td>
	         	</tr>
		   		<tr>
					<th>비밀번호 확인</th>
					<td><input type="password" name="u_pwd" id="u_pwd" /></td>
				</tr>
			</table>
			<div class="btns">
				<input type="submit" value="수정하기">
			</div>
		</form>
	</main>
	<%@ include file="../footer.jsp"%>
</body>
</html>