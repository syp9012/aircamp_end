<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>에어캠프</title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<main class="main-page">
	<section class="search-container wrap">
		<div class="type-btns">
		    <div class="bg"></div>
		    <button class="btn-camp on">캠핑장</button>
		    <button class="btn-tour">관광지</button>
		</div>
		<form id="search-form" action="/camp/list">
			<input type="hidden" name="page" value="1">
			<div class="box local">
				<p class="title">지역</p>
				<select id="citySelect" name="addr1" title="도/특별시" onchange="populateDistricts()">
					  <option value="">전체/도</option>
					  <option value="서울시">서울시</option><option value="부산시">부산시</option>
					  <option value="대구시">대구시</option><option value="인천시">인천시</option>
					  <option value="광주시">광주시</option><option value="대전시">대전시</option>
					  <option value="울산시">울산시</option><option value="세종시">세종시</option>
					  <option value="경기도">경기도</option><option value="강원도">강원도</option>
					  <option value="충청북도">충청북도</option><option value="충청남도">충청남도</option>
					  <option value="전라북도">전라북도</option><option value="전라남도">전라남도</option>
					  <option value="경상북도">경상북도</option><option value="경상남도">경상남도</option>
					  <option value="제주도">제주도</option>
				</select>
				<select id="districtSelect" name="addr2" title="도/특별시를 선택하세요">
				  <option value="">전체/시/군/구</option>
				</select>
			</div>
			<div class="box keyword">
				<p class="title">검색어</p>
				<input id="keyword" name="keyword" title="검색어를 입력하세요." placeholder="검색어를 입력하세요." type="text">
			</div>
			<button type="submit" class="btn-submit">검색</button>
		</form>
	</section>
	
	<section class="slide-container wrap">
		<h2>🏕️ 지금 추천하는 캠핑장</h2>
		<div class="silde-wrap slide-camp">
			<c:forEach var="list" items="${campList}">
				<a href="/camp/detail?camp_no=${list.camp_no}" class="content">
					<img src="${list.camp_image}">
					<p class="title">[${list.camp_do_name} ${list.camp_city_name}] ${list.camp_name}</p>
				</a>
			</c:forEach>
		</div>
	</section>
 	<section class="slide-container wrap">
 		<h2>오늘 가기 좋은 관광지 🚌</h2>
		<div class="silde-wrap slide-tour">
			<c:forEach var="list" items="${tourList}">
	 			<div class="content">
					<a href="/tour/detail?tour_id=${list.tour_id}"> 
						<img src="${list.tour_images.split(',')[0]}">
						<p class="title">
							[${list.tour_addr1.split(' ')[0]} ${list.tour_addr1.split(' ')[1]}] ${list.tour_name}
						</p>
					</a>
				</div>
			</c:forEach>
		</div>
	</section>
	<section class="notice-container wrap">
 		<h2>📣 에어캠프의 최신 소식을 알아보아요!</h2>
		<table class="notice-table">
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>날짜</th>
				<th>조회수</th>
				<th>작성자</th>
			</tr>
			<c:forEach var="list" items="${noticeList}">
				<tr>
					<td>${list.no_no}</td>
					<td><a href="/notice/detail?page=${search.page}&keyword=${search.keyword}&no_no=${list.no_no}">${list.no_subject}</a></td>
					<td>${list.no_date}</td>
					<td>${list.no_readcount}</td>
					<td>관리자</td>
				</tr>
			</c:forEach>
		</table>
	</section>
	<script>
		$(document).ready(function() {
			$('.slide-camp').slick({
				autoplay: true,
                autoplaySpeed: 3000,
                lazyLoad: 'progressive',
                slidesToShow: 4,
                arrows: false,
                dots: false
			});
		});
		$(document).ready(function() {
			$('.slide-tour').slick({
				autoplay: true,
                autoplaySpeed: 3000,
                lazyLoad: 'progressive',
                slidesToShow: 4,
                arrows: false,
                dots: false
			});
		});
	</script>
	</main>			
	<%@ include file="footer.jsp"%>
</body>
</html>