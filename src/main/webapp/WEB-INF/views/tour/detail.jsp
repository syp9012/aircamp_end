<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>에어캠프</title>
</head>
<body>
	<%@ include file="../header.jsp"%>
	<main class="wrap">
		<section class="detail-container">
			<div class="img-container">
				<c:choose>
				    <c:when test="${empty tour.tour_images}">
				    	<div class="img-wrap">
				        	<img src="images/noimage.jpg">
				        </div>
				    </c:when>
				    <c:otherwise>
						<div class="img-wrap">
							<c:forEach var="img" items="${imgarray}" begin="0" end="9">
								<div class="box">
							    	<img src="${img}">
							    </div>
							</c:forEach>
					    </div>
						<div class="img-nav">
							<c:forEach var="img" items="${imgarray}" begin="0" end="9">
								<div class="box">
							    	<img src="${img}">
							    </div>
							</c:forEach>
					    </div>
				    </c:otherwise>
				</c:choose>
			</div>
			<div class="info-container">
				<h3>${tour.tour_name}</h3>
				<div class="type-btns">
				    <div class="bg"></div>
				    <button class="btn01 on">기본정보</button>
				    <button class="btn02">이용안내</button>
				    <button class="btn03">상세정보</button>
				</div>
				<table class="btn01-area">
					<tr><th>주소</th><td>${tour.tour_addr1}</td></tr>
					<tr><th>우편번호</th><td>${tour.tour_zipcode}</td></tr>
				</table>
				<table class="btn02-area">
					<tr><th>문의</th><td>${tour.tour_infocenter}</td></tr>
					<tr><th>개장일</th><td>${tour.tour_open_date}</td></tr>
					<tr><th>쉬는날</th><td>${tour.tour_dayoff}</td></tr>
					<tr><th>이용시간</th><td>${tour.tour_usetime}</td></tr>
				</table>
				<table class="btn03-area">
					<tr><th>유모차 대여가능여부</th><td>${tour.tour_babycarry}</td></tr>
					<tr><th>반려동물 동반가능여부</th><td>${tour.tour_animal_able}</td></tr>
					<tr><th>이용 가능 연령</th><td>${tour.tour_age_able}</td></tr>
					<tr><th>주차장 여부</th><td>${tour.tour_parking}</td></tr>
				</table>
			</div>
		</section>
		<section class="desc-container">
			<div class="intro-wrap">
				<h3>⭐ 개요</h3>
				<p>${tour.tour_desc}</p>
			</div>
			<div class="map-wap">
				<h3>🚗 찾아가는 길</h3>
				<div id="map" style="width:100%;height:400px;"></div>
			</div>
		</section>
	</main>
	<%@ include file="../footer.jsp"%>
	<script>
		$(document).ready(function() {
			 $('.img-wrap').slick({
			  slidesToShow: 1,
			  slidesToScroll: 1,
			  arrows: false,
			  fade: true,
			  asNavFor: '.img-nav'
			});
			$('.img-nav').slick({
			  slidesToShow: 3,
			  slidesToScroll: 1,
			  arrows: false,
			  dots: true,
			  asNavFor: '.img-wrap',
			  focusOnSelect: true
			});
		});
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
	  	//지도 영역과 좌표, 확대레벨 설정
		var mapContainer = document.getElementById('map'),
		mapOption = {
		    center: new kakao.maps.LatLng(${tour.tour_locy}, ${tour.tour_locx}),
		    level: 3 
		};  
		// 지도 생성  
		var map = new kakao.maps.Map(mapContainer, mapOption); 
		var coords = new kakao.maps.LatLng(${tour.tour_locy}, ${tour.tour_locx});
		var marker = new kakao.maps.Marker({
		    map: map,
		    position: coords
		});
		
		// 장소 설명 표시
		var infowindow = new kakao.maps.InfoWindow({
		    content: '<div style="width:150px;text-align:center;padding:6px 0;">${tour.tour_name}</div>'
		});
		infowindow.open(map, marker);
		
		// 지도 중심을 좌표로 옮기기
		map.setCenter(coords);
	</script>
</body>
</html>