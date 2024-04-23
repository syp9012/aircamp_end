<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>에어캠프</title>
</head>
<body>
	<%@ include file="../header.jsp"%>
	<section class="search-container sub">
		<div class="wrap">
			<h2><span class="ico-circle"></span> 상세검색</h2>
			<form id="search-form" action="/tour/list">
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
		</div>
	</section>
	<main class="wrap">
		<section class="list-container">
			<p class="count">총 <span class="maincolor">${tourCount}개의</span> 관광지가 검색되었습니다</p>
			<div class="list-wrap">
				<c:choose>
					<c:when test="${tourCount > 0}">
						<c:forEach var="list" items="${tourList}">
						<div class="list">
							<a href="/tour/detail?tour_id=${list.tour_id}" class="inner"> 
								<c:choose>
									<c:when test="${empty list.tour_images}">
										<img src="${contextpath}/images/noimage.jpg">
									</c:when>
									<c:otherwise>
										<img src="${list.tour_images.split(',')[0]}">
									</c:otherwise>
								</c:choose>
								<div class="text-box">			
									<span class="title">${list.tour_name}</span>
									<span class="desc">${list.tour_desc}</span>
								</div>
							</a>
						</div>
						</c:forEach>
					</c:when>
			     	<c:otherwise>
				        <div class="none">검색된 캠핑장이 없습니다.</div>
				    </c:otherwise>
			    </c:choose>
			</div>
			<div class="pagination-wrap">
				<c:if test="${tourCount > 0}">
				        <!-- 첫페이지, 이전블록 으로 이동 -->
				        <c:if test="${startPage > 1}">
				            <a href="/tour/list?page=1&addr1=${search.addr1}&addr2=${search.addr2}&keyword=${search.keyword}" class="ico">
								<i class="fa-solid fa-chevron-left"></i><i class="fa-solid fa-chevron-left"></i>
							</a>
				        </c:if>
				        <c:if test="${startPage > 10}">
				            <a href="/tour/list?page=${startPage - 10}&addr1=${search.addr1}&addr2=${search.addr2}&keyword=${search.keyword}" class="ico">
								<i class="fa-solid fa-chevron-left"></i>
							</a>
				        </c:if>
				
				        <c:forEach var="i" begin="${startPage}" end="${endPage}">
							<c:if test="${i == search.page}">
								<a href="/tour/list?page=${i}&addr1=${search.addr1}&addr2=${search.addr2}&keyword=${search.keyword}" class="on">${i}</a>
							</c:if>
							<c:if test="${i != search.page}">
								<a href="/tour/list?page=${i}&addr1=${search.addr1}&addr2=${search.addr2}&keyword=${search.keyword}">${i}</a>
							</c:if>
				        </c:forEach>
				
				        <!-- 다음블록, 마지막페이지 로 이동 -->
				        <c:if test="${endPage < maxPage}">
				            <a href="/tour/list?page=${startPage + 10}&addr1=${search.addr1}&addr2=${search.addr2}&keyword=${search.keyword}" class="ico">
								<i class="fa-solid fa-chevron-right"></i>
							</a>
				        </c:if>
				        <c:if test="${endPage < maxPage}">
				            <a href="/tour/list?page=${maxPage}&addr1=${search.addr1}&addr2=${search.addr2}&keyword=${search.keyword}" class="ico">
								<i class="fa-solid fa-chevron-right"></i><i class="fa-solid fa-chevron-right"></i>
							</a>
				        </c:if>
				</c:if>
			</div>
		</section>
	</main>
	<%@ include file="../footer.jsp"%>
</body>
</html>