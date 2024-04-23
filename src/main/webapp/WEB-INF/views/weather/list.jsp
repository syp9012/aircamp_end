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
	<main class="wrap">
		<h2 class="kmap-title">${loc}의 날씨</h2>
		<div class="kmap">
			<div class="mapwrap">
				<img src="images/maps/${loc}.jpg" alt="" usemap="#mapArea" id="Map">
				<map name="mapArea" id="mapArea">
					<area shape="poly" onfocus="this.blur()" coords="49,59,51,58,56,55,69,46,78,75,66,75,49,74,51,68,48,59" href="javascript:;" alt="서울">
					<area shape="poly" onfocus="this.blur()" coords="47,45,47,45,47,39,51,36,51,29,65,14,80,28,89,34,96,41,93,45,95,59,105,71,102,82,103,88,101,95,85,100,75,105,52,99,45,91,42,83,46,74,79,74,68,46,49,59,43,55,43,47" href="javascript:;" alt="경기도">
					<area shape="poly" onfocus="this.blur()" coords="15,53,28,41,42,51,44,58,47,63,51,71,46,73,34,73,22,60,22,54" href="javascript:;" alt="인천">
					<area shape="poly" onfocus="this.blur()" coords="66,17,92,16,103,17,110,9,128,15,140,1,137,10,159,43,189,91,161,92,142,105,136,93,133,89,125,85,104,93,105,72,98,63,113,59,95,64,91,49,100,40,83,32,78,25,86,27" href="javascript:;" alt="강원도">
					<area shape="poly" onfocus="this.blur()" coords="40,84,26,101,14,116,26,125,23,131,29,138,34,137,38,145,37,148,37,158,49,164,61,159,62,152,76,154,74,146,72,132,76,126,71,118,78,106,56,100,48,100" href="javascript:;" alt="충남">
					<area shape="poly" onfocus="this.blur()" coords="90,133,72,146,74,153,97,165,91,145,93,133" href="javascript:;" alt="대전">
					<area shape="poly" onfocus="this.blur()" coords="139,97,130,122,121,124,108,134,110,141,114,145,116,153,110,168,99,168,96,160,94,152,92,141,94,125,82,104,91,100,117,84,133,90" href="javascript:;" alt="충북">
					<area shape="poly" onfocus="this.blur()" coords="185,93,169,90,153,94,145,105,135,105,124,118,118,126,111,133,109,142,118,154,109,170,120,175,130,180,140,152,162,164,149,190,154,194,178,179,195,183,189,140,192,109,189,98,179,87" href="javascript:;" alt="경북">
					<area shape="poly" onfocus="this.blur()" coords="147,189,127,188,131,185,132,166,138,149,164,164,145,191" href="javascript:;" alt="대구">
					<area shape="poly" onfocus="this.blur()" coords="167,187,155,195,185,210,195,181,175,181,166,186" href="javascript:;" alt="울산">
					<area shape="poly" onfocus="this.blur()" coords="177,201,135,226,137,230,155,243,183,220,182,211" href="javascript:;" alt="부산">
					<area shape="poly" onfocus="this.blur()" coords="106,172,97,179,93,200,99,218,102,226,110,249,139,247,155,244,139,233,137,223,174,202,159,196,148,188,124,180,107,171" href="javascript:;" alt="경남">
					<area shape="poly" onfocus="this.blur()" coords="42,160,49,164,59,154,74,152,85,160,109,169,95,182,93,208,76,210,55,198,45,199,33,203,35,189,41,176,45,171,41,168,41,161" href="javascript:;" alt="전북">
					<area shape="poly" onfocus="this.blur()" coords="1,274,9,284,26,274,33,268,47,275,64,275,77,271,91,262,92,255,107,252,104,230,96,209,76,211,55,198,26,205,38,217,63,210,69,221,65,230,53,230,44,228,43,221,29,206,7,217,8,233,3,252,1,269" href="javascript:;" alt="전남">
					<area shape="poly" onfocus="this.blur()" coords="44,214,40,218,41,224,47,227,65,230,69,218,58,208,43,213" href="javascript:;" alt="광주">
					<area shape="poly" onfocus="this.blur()" coords="17,306,26,296,46,289,61,292,52,308,42,312,23,313,17,307" href="javascript:;" alt="제주">
					<area shape="poly" onfocus="this.blur()" coords="80,101,73,118,77,125,73,135,74,144,93,136,93,124" href="javascript:;" alt="세종">
				</map>
			</div>
			<div></div>
			<div class="box-weather">
				<div>
					<h4><span class="ico_circle"></span>3일 예보</h4>
					<table>
						<tr>
							<th>날짜</th>
							<c:forEach var="s" items="${sw}">
								<td>${s.sw_date}</td>
							</c:forEach>
							</tr>
						<tr>
							<th>기온 (℃)</th>
							<c:forEach var="s" items="${sw}">
								<td>${s.sw_temp}℃</td>
							</c:forEach>
							</tr>
						<tr>
							<th>강수확률</th>
							<c:forEach var="s" items="${sw}">
								<td>${s.sw_rainper}%</td>
							</c:forEach>
						</tr>
					</table>
				</div>
				<div class="dust">
					<h4><span class="ico_circle"></span>3일 대기오염</h4>
					<table>
						<tr>
							<th>날짜</th>
							<td>오늘 예보</td>
							<td>내일 예보</td>
							<td>모레 예보</td>
						</tr>
						<tr>
							<th>미세먼지 (PM10)</th>
							<c:forEach var="d" items="${dust}">
								<td>
						            <c:if test="${d.dust_grade == '좋음'}"><img src="images/weather_dust01.png"></c:if>
						            <c:if test="${d.dust_grade == '보통'}"><img src="images/weather_dust02.png"></c:if>
						            <c:if test="${d.dust_grade == '나쁨'}"><img src="images/weather_dust03.png"></c:if>
									${d.dust_grade}
								</td>
							</c:forEach>
						</tr>
					</table>
				</div>
				<div>
					<h4><span class="ico_circle"></span>중기 예보</h4>
					<table>
						<tr>
							<th>날짜</th>
							<c:forEach var="m" items="${mw}" begin="0" end="3">
								<td><fmt:formatDate value="${m.mw_date}"  pattern="yyyy-MM-dd"/></td>
							</c:forEach>
						</tr>
						<tr>
							<th>최저/최고기온 (℃)</th>
							<c:forEach var="m" items="${mw}" begin="0" end="3">
								<td>${m.mw_mintep}℃ / ${m.mw_maxtep}℃</td>
							</c:forEach>
						</tr>
						<tr>
							<th>날짜</th>
							<c:forEach var="m" items="${mw}" begin="4" end="7">
								<td><fmt:formatDate value="${m.mw_date}"  pattern="yyyy-MM-dd"/></td>
							</c:forEach>
						</tr>
						<tr>
							<th>최저/최고기온 (℃)</th>
							<c:forEach var="m" items="${mw}" begin="4" end="7">
								<td>${m.mw_mintep}℃ / ${m.mw_maxtep}℃</td>
							</c:forEach>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</main>
	<%@ include file="../footer.jsp"%>
	<script>
	$(document).ready(function() {
	    var map = $("#Map");
	    //지역별 url 요청 보내기
	    $('#mapArea > area').click(function() {
	        var loc = $(this).attr('alt');
		    location.href="weather?loc="+loc;
	    });
     	//이미지 변경 제어
        $('#mapArea > area').mouseenter(function() {
            var loc = $(this).attr('alt'); //해당 구역의 alt 속성 값
            map.attr('src', 'images/maps/' + loc + '.jpg'); //<area alt> 에 있는 loc 로 변경
        }).mouseout(function() {
        	map.attr('src', `images/maps/${loc}.jpg`); //벡틱으로 el 불러와서 모델에 저장된 loc 로 변경
        });
	});
	</script>
</body>
</html>