<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>에어캠프</title>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=8c9e55110c1603351ffdbd52b18185d7"></script>
<!-- jQuery 라이브러리 추가 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

</head>
<body>

<div id="map" style="width: 500px; height: 400px;"></div>

<script>
let mapContainer = document.getElementById('map'), // 지도를 표시할 div
mapOption = {
    center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
    level: 12 // 지도의 확대 레벨
};

let map = new kakao.maps.Map(mapContainer, mapOption),
customOverlay = new kakao.maps.CustomOverlay({})


let polygons = [];

init("json/sido.json") // 초기 시작

kakao.maps.event.addListener(map, 'zoom_changed', function () {
level = map.getLevel()

// 모든 폴리곤을 지우는 함수
function removePolygon() { 
for (let i = 0; i < polygons.length; i++) {
    polygons[i].setMap(null);
}
areas = [];
polygons = [];
}

// 폴리곤 생성
function init(path) {

//path 경로의 json 파일 파싱
$.getJSON(path, function (geojson) {
    var units = geojson.features; // json key값이 "features"인 것의 value를 통으로 가져온다.

    $.each(units, function (index, unit) { // 1개 지역씩 꺼내서 사용. val은 그 1개 지역에 대한 정보를 담는다
        var coordinates = []; //좌표 저장할 배열
        var name = ''; // 지역 이름
        var cd_location = '';
        coordinates = unit.geometry.coordinates; // 1개 지역의 영역을 구성하는 다각형의 모든 좌표 배열
        name = unit.properties.SIG_KOR_NM; // 1개 지역의 이름
        cd_location = unit.properties.SIG_CD;


        var ob = new Object();
        ob.name = name;
        ob.path = [];
        ob.location = cd_location;
        $.each(coordinates[0], function (index, coordinate) { 
            ob.path
                .push(new kakao.maps.LatLng(coordinate[1],
                    coordinate[0]));
        });

        areas[index] = ob;
    });//each
});//getJSON

// 지도에 영역데이터를 폴리곤으로 표시
for (var i = 0, len = areas.length; i < len; i++) {
    displayArea(areas[i]);
}

function displayArea(area) {

    var polygon = new kakao.maps.Polygon({
        map: map,
        path: area.path,
        strokeWeight: 2,
        strokeColor: '#004c80',
        strokeOpacity: 0.8,
        fillColor: '#fff',
        fillOpacity: 0.7
    });
    polygons.push(polygon);


    kakao.maps.event.addListenerOnce(polygon, 'mouseover', function (mouseEvent) {
        polygon.setOptions({fillColor: '#09f'});
        customOverlay.setContent('<div class="area">' + area.name + '</div>');
        customOverlay.setPosition(mouseEvent.latLng);
        customOverlay.setMap(map);
    });

    kakao.maps.event.addListener(polygon, 'mousemove', function (mouseEvent) {

        customOverlay.setPosition(mouseEvent.latLng);
    });

    kakao.maps.event.addListenerOnce(polygon, 'mouseout', function () {
        polygon.setOptions({fillColor: '#fff'});
        customOverlay.setMap(null);
    });

}
}
</script>
</body>
</html>
