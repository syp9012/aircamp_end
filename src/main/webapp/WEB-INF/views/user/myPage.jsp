<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>에어캠프</title>
</head>
<body>
	<%@ include file="../header.jsp"%>
	<main class="user-page wrap">
		<section class="mypage-container">
			<h2>마이페이지</h2>
			<div class="user-tab">
				<ul>
					<li class="on"><a href="/user/myPage">개인정보</a></li>
					<li><a href="/user/update">정보수정</a></li>
					<li><a href="/user/pwdUpdate">비밀번호 변경</a></li>
				</ul>
			</div>
			<div class="info-wrap">
				<div class="profile">
					<c:if test="${empty u_profile}">
		                <img src="${contextpath}/images/noprofile.png">
		          	</c:if>
		           	<c:if test="${!empty u_profile}">
						<img src="<%=request.getContextPath() %>/upload/${u_profile}">
					</c:if>
					<h3>${u_nickname}님</h3>
					<span class="title"> 안녕하세요! 에어캠프에 오신것을 환영합니다!</span>
				</div>
				<div class="desc">
					<div class="right">
						<div><span class="title">아이디</span><span class="content">${u_id}</span></div>
						<div><span class="title">휴대폰</span><span class="content phone">${u_phone}</span></div>
						<p class="withdraw"><a href="/user/withdraw">회원탈퇴 <i class="fa-solid fa-chevron-right"></i></a></p>
					</div>
					<script>
						var u_phone = `${u_phone}`;
						var formattedPhone = u_phone.substring(0, 3) + '-' + u_phone.substring(3, 7) + '-' + u_phone.substring(7);
						$('.phone').text(formattedPhone);
					</script>
					<div class="left">
						<span id="randomText" class="ff-incheon"></span>
					</div>
					<script>
				    var texts = [
				        "바보는 방황을 하고<br>현명한 사람은 여행을 한다",
				        "친구를 알고자 하거든<br>사흘만 같이 여행을 하라",
				        "캠핑이란 남을 따라하는게 아닌,<br>자기 멋에 맞춰 즐기는 것이다",
				        "캠핑이 가고싶어.<br>캠 핑 조 아",
				        "관광이 하고싶어.<br>관 광 조 아",
				        "챗지피티는 내 친구야<br>-에어캠프",
				        "To travel is to live<br>-안데르센"
				    ];
				    var randomIndex = Math.floor(Math.random() * texts.length);
				    var randomText = texts[randomIndex];
				    document.getElementById("randomText").innerHTML = randomText;
				</script>
				</div>
			</div>
		</section>
	</main>
	<%@ include file="../footer.jsp"%>
</body>
</html>