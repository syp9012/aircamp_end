<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>에어캠프</title>
<style>
.detail-container .img-container .img-wrap .box {height:35rem !important;}
</style>
</head>
<body>
	<%@ include file="../header.jsp"%>
	<main class="wrap">
		<section class="detail-container">
			<div class="img-container">
				<div class="img-wrap">
					<div class="box">
						<c:choose>
						    <c:when test="${empty camp.camp_image}">
					        	<img src="images/noimage.jpg">
						    </c:when>
						    <c:otherwise>
						    	<img src="${camp.camp_image}">
						    </c:otherwise>
						</c:choose>
				    </div>
			    </div>
			</div>
			<div class="info-container">
				<h3>${camp.camp_name} [${camp.camp_owner}]</h3>
				<div class="type-btns">
				    <div class="bg"></div>
				    <button class="btn01 on">기본정보</button>
				    <button class="btn02">이용안내</button>
				    <button class="btn03">상세정보</button>
				</div>
				<table class="btn01-area">
					<tr><th>주소</th><td>${camp.camp_addr1}</td></tr>
					<tr><th>우편번호</th><td>${camp.camp_zipcode}</td></tr>
				</table>
				<table class="btn02-area">
					<tr><th>문의</th><td>${camp.camp_tel}</td></tr>
					<tr><th>웹페이지</th><td><a href="${camp.camp_url}" target='_blank'>${camp.camp_url}</a></td></tr>
					<tr><th>예약페이지</th><td><a href="${camp.camp_reserve_url}" target='_blank'>${camp.camp_reserve_url}</a></td></tr>
					<tr><th>예약방법</th><td>${camp.camp_reserve_type}</td></tr>
					<tr><th>반려동물 동반가능여부</th><td>${camp.camp_able_animal}</td></tr>
				</table>
				<table class="btn03-area">
					<tr><th>캠핑장 타입</th><td>${camp.camp_type}</td></tr>
					<tr><th>캠핑장 환경</th><td>${camp.camp_nature_type}</td></tr>
					<tr><th>운영일, 운영기간</th><td>${camp.camp_open_date} / ${camp.camp_open_season}</td></tr>
					<tr><th>주변 이용가능 시설</th><td>${camp.camp_able_fclty}</td></tr>
				</table>
			</div>
		</section>
		<section class="desc-container">
			<div class="intro-wrap">
				<h3>⭐ 개요</h3>
				<p class="intro">${camp.camp_intro}</p>
				<p class="desc">${camp.camp_desc}</p>
			</div>
			<div class="ico-wrap">
				<h3>⛺ 캠핑장 시설</h3>
				<p>시설정보 : ${camp.camp_add_fclty}</p>
				<p>편의시설 : 화장실(${camp.camp_toilet_no})&nbsp;&nbsp;샤워기(${camp.camp_shower_no})&nbsp;&nbsp;개수대(${camp.camp_sink_no})</p>
				<p>보험 가입여부 : ${camp.camp_insurance}</p>
			</div>
			<div class="map-wap">
				<h3>🚗 찾아가는 길</h3>
				<div id="map" style="width:100%;height:400px;"></div>
			</div>
		</section>
	</main>
	<%@ include file="../footer.jsp"%>
	<script>
		//탭 영역 버튼 및 하단 테이블 제어
		$('.btn02-area, .btn03-area').hide();
	    $('.btn01').click(function() {
	        $(this).addClass('on');
	        $('.type-btns .bg').css('left','5px');
	        $('.btn02, .btn03').removeClass('on');
	        $('.btn02-area, .btn03-area').hide();
	        $('.btn01-area').show();
	    });
	    $('.btn02').click(function() {
	        $(this).addClass('on');
	        $('.type-btns .bg').css('left','33%');
	        $('.btn01, .btn03').removeClass('on');
	        $('.btn01-area, .btn03-area').hide();
	        $('.btn02-area').show();
	    });
	    $('.btn03').click(function() {
	        $(this).addClass('on');
	        $('.type-btns .bg').css('left','66%');
	        $('.btn01, .btn02').removeClass('on');
	        $('.btn01-area, .btn02-area').hide();
	        $('.btn03-area').show();
	    });
	    //하단 테이블의 td 에 값이 비어있다면 행 출력 X
	    $('.info-container table tr').each(function() {
	        var isEmpty = true;
	        $(this).find('td').each(function() {
	            if ($(this).text().trim() !== '') {
	                isEmpty = false;
	                return false; // 반복문 종료
	            }
	        });
	        if (isEmpty) {
	            $(this).hide();
	        }
	    });
	</script>
	<script>
		//지도 영역과 좌표, 확대레벨 설정
		var mapContainer = document.getElementById('map'),
		mapOption = {
		    center: new kakao.maps.LatLng(${camp.camp_locy}, ${camp.camp_locx}),
		    level: 3 
		};  
		// 지도 생성  
		var map = new kakao.maps.Map(mapContainer, mapOption); 
		var coords = new kakao.maps.LatLng(${camp.camp_locy}, ${camp.camp_locx});
		var marker = new kakao.maps.Marker({
		    map: map,
		    position: coords
		});
		
		// 장소 설명 표시
		var infowindow = new kakao.maps.InfoWindow({
		    content: '<div style="width:150px;text-align:center;padding:6px 0;">${camp.camp_name}</div>'
		});
		infowindow.open(map, marker);
		
		// 지도 중심을 좌표로 옮기기
		map.setCenter(coords);
	</script>
</body>
</html>