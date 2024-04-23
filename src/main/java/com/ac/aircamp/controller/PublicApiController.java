package com.ac.aircamp.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.el.parser.ParseException;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.ac.aircamp.model.CampApi;
import com.ac.aircamp.model.Dust;
import com.ac.aircamp.model.MidWeather;
import com.ac.aircamp.model.ShortWeather;
import com.ac.aircamp.model.Tour;
import com.ac.aircamp.service.PublicApiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PublicApiController {

	private final PublicApiService publicApiService;
	
	 // 단기 날짜
	 LocalDate currentDate = LocalDate.now();
     // 포맷 지정
     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
     // 날짜를 지정한 포맷으로 변환하여 출력
     String formattedDate = currentDate.format(formatter);
	
	@Value("${spring.api.serviceKey}")
	private String serviceKey;

	//단기 매일 08시 
	//중기 매일 06시
	//미세먼지 매일 17시
	
	// 날씨 Delete API
	@Scheduled(cron = "0 9 6 * * *")
	public void MWDelete() {
		log.info("MWDelete");
		publicApiService.mwDelete();
		
	}
	
	// 중기날씨 Insert API
	@Scheduled(cron = "0 10 6 * * *")
	public void MWInsert() throws Exception{        
		log.info("MWInsert");
	        String[] tempLocalCode = {"11B10101","11B20201","11B20601","11D20501","11C20401",
	        							"11C20101","11C20404","11C10301","11G00201","11F20501",
	        							"11F20401","11F10201","11H20201","11H20101","11H20301",
	        							"11H10701","11H10501"};
	        
	        // Date 객체를 사용하여 현재 날짜를 가져옵니다.
	        Date today = new Date();

	        // 날짜를 "yyyyMMdd" 형식의 문자열로 변환합니다.
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	        String formattedDate = sdf.format(today);
	        int insertResult=0;
	        
	        for(String tempCode : tempLocalCode) {
	        // 1. URL을 만들기 위한 StringBuilder.
	        // 2. 오픈 API의 요청 규격에 맞는 파라미터 생성. serviceKey 부분에 반드시 내가 인증받은 인코딩 키를 넣어주어야 한다!!
	        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/MidFcstInfoService/getMidTa"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
	        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON)*/
	        urlBuilder.append("&" + URLEncoder.encode("regId","UTF-8") + "=" + URLEncoder.encode(tempCode, "UTF-8")); /*지역코드가 11B00000 인 코드를 구해옴*/
	        urlBuilder.append("&" + URLEncoder.encode("tmFc","UTF-8") + "="  + URLEncoder.encode(formattedDate, "UTF-8")+ URLEncoder.encode("0600", "UTF-8")); /*3/21*/

	        // 3. URL 객체 생성 (toString으로 string으로 변환)
	        URL url = new URL(urlBuilder.toString());

	        // 4. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성.
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

	        // 5. 통신을 위한 메소드 SET (Get 요청)
	        conn.setRequestMethod("GET");

	        // 6. 통신을 위한 Content-type SET. (json으로 설정해야됨)
	        conn.setRequestProperty("Content-type", "application/json");

	        // 7. 통신 응답 코드 확인.

	        // 8. 전달받은 데이터를 BufferedReader 객체로 저장. 오류가 날 경우 error 발생
	        BufferedReader rd;
	        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300){
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        } else {
	            rd = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
	        }
	        
	        // 9. 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장.
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = rd.readLine()) != null) {
	        sb.append(line);
	        }
	        
	        
	        
	        // JSON 문자열로 바꾸기
	        String result = sb.toString();
	        
	        // JSON 문자열을 JsonNode 객체로 파싱하여 객체 모델로 변환
	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode rootNode = objectMapper.readTree(result);
	        String[][] eightDays = {{"taMin3","taMax3"},{"taMin4","taMax4"},{"taMin5","taMax5"},{"taMin6","taMax6"},
	        						{"taMin7","taMax7"},{"taMin8","taMax8"},{"taMin9","taMax9"},{"taMin10","taMax10"}};
	        
	      
	        Date currentDate = new Date();
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(currentDate);
	        calendar.add(calendar.DAY_OF_YEAR, 3);
	        
	        
	        for(int i=0; i<8; i++) {
               	MidWeather midTemp = new MidWeather();
	        	String loc = rootNode.get("response").get("body").get("items").get("item").get(0).get("regId").asText();

        		
	        	if(loc.equals("11B10101")) {
        			loc="서울시";
        		}else if(loc.equals("11B20201")) {
        			loc="인천시";
        		}else if(loc.equals("11B20601")) {
        			loc="경기도";
        		}else if(loc.equals("11D20501")) {
        			loc="강원도";
        		}else if(loc.equals("11C20401")) {
        			loc="대전시";
        		}else if(loc.equals("11C20101")) {
        			loc="충청남도";
        		}else if(loc.equals("11C20404")) {
        			loc="세종시";
        		}else if(loc.equals("11C10301")) {
        			loc="충청북도";
        		}else if(loc.equals("11G00201")) {
        			loc="제주도";
        		}else if(loc.equals("11F20501")) {
        			loc="광주시";
        		}else if(loc.equals("11F20401")) {
        			loc="전라남도";
        		}else if(loc.equals("11F10201")) {
        			loc="전라북도";
        		}else if(loc.equals("11H20201")) {
        			loc="부산시";
        		}else if(loc.equals("11H20101")) {
        			loc="울산시";
        		}else if(loc.equals("11H20301")) {
        			loc="경상남도";
        		}else if(loc.equals("11H10701")) {
        			loc="대구시";
        		}else if(loc.equals("11H10501")) {
        			loc="경상북도";
        		}
        		
	        	
        		midTemp.setMw_loc(loc);
        		midTemp.setMw_mintep(rootNode.get("response").get("body").get("items").get("item").get(0).get(eightDays[i][0]).asText());
        		midTemp.setMw_maxtep(rootNode.get("response").get("body").get("items").get("item").get(0).get(eightDays[i][1]).asText());
        		Date date = calendar.getTime();
        		SimpleDateFormat day = new SimpleDateFormat("yyyyMMdd");
    	        String formattedDay = sdf.format(date);		
    	        date = day.parse(formattedDay);
        		
        		midTemp.setMw_date(date);
	        	calendar.add(calendar.DAY_OF_YEAR, 1);
	        	publicApiService.mwInsert(midTemp);
	        }
	        
	        // 10. 객체 해제
	        rd.close();
	        conn.disconnect();
	        }
	  }
	
	// 중기날씨 Update API
	@Scheduled(cron = "0 11 6 * * *")
	public void MWupdate() throws Exception{ 
			log.info("MWUpdate");
	        String[] landLocalCode = {"11B00000","11D20000","11C20000","11C10000","11G00000",
	        						"11F20000","11F10000","11H20000","11H10000"};
	        //지역별 매칭 
	        String[][] cityName = {{"서울시","인천시","경기도"},
	        						{"강원도"},
	        						{"대전시","충청남도","세종시"},
	        						{"충청북도"},
	        						{"제주도"},
	        						{"광주시","전라남도"},
	        						{"전라북도"},
	        						{"부산시","울산시","경상남도"},
	        						{"대구시","경상북도"}};
	        
	        
	        // Date 객체를 사용하여 현재 날짜를 가져옵니다.
	        Date today = new Date();

	        // 날짜를 "yyyyMMdd" 형식의 문자열로 변환합니다.
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	        String formattedDate = sdf.format(today);
	        
	        for(int i =0; i<9; i++) {
	        // 1. URL을 만들기 위한 StringBuilder.
	        // 2. 오픈 API의 요청 규격에 맞는 파라미터 생성. serviceKey 부분에 반드시 내가 인증받은 인코딩 키를 넣어주어야 한다!!
	        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/MidFcstInfoService/getMidLandFcst"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
	        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON)*/
	        urlBuilder.append("&" + URLEncoder.encode("regId","UTF-8") + "=" + URLEncoder.encode(landLocalCode[i], "UTF-8")); /*지역코드가 11B00000 인 코드를 구해옴*/
	        urlBuilder.append("&" + URLEncoder.encode("tmFc","UTF-8") + "=" + URLEncoder.encode(formattedDate, "UTF-8")+ URLEncoder.encode("0600", "UTF-8")); /*3/21*/

	        // 3. URL 객체 생성 (toString으로 string으로 변환)
	        URL url = new URL(urlBuilder.toString());

	        // 4. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성.
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

	        // 5. 통신을 위한 메소드 SET (Get 요청)
	        conn.setRequestMethod("GET");

	        // 6. 통신을 위한 Content-type SET. (json으로 설정해야됨)
	        conn.setRequestProperty("Content-type", "application/json");

	        // 7. 통신 응답 코드 확인.

	        // 8. 전달받은 데이터를 BufferedReader 객체로 저장. 오류가 날 경우 error 발생
	        BufferedReader rd;
	        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300){
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        } else {
	            rd = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
	        }
	        
	        // 9. 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장.
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = rd.readLine()) != null) {
	        sb.append(line);
	        }
	        
	        // 문자열로 바꾸기
	        String result = sb.toString();

	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode rootNode = objectMapper.readTree(result);
	        String[] eightDays = {"rnSt3Am","rnSt4Am","rnSt5Am","rnSt6Am","rnSt7Am","rnSt8","rnSt9","rnSt10"};
	        
	        for(int k = 0;k<cityName.length; k++) {
	        	for(int j =0; j<cityName[k].length; j++) {
	        		Date currentDate = new Date();
	    	        Calendar calendar = Calendar.getInstance();
	    	        calendar.setTime(currentDate);
	    	        calendar.add(calendar.DAY_OF_YEAR, 3);
	    	        
			        for(String day : eightDays) {
			        	MidWeather midRainper = new MidWeather();
			        	midRainper.setMw_loc(cityName[k][j]);
			        	midRainper.setMw_rainper(rootNode.get("response").get("body").get("items").get("item").get(0).get(day).asText());
		        		
			        	Date date = calendar.getTime();
		        		SimpleDateFormat day1 = new SimpleDateFormat("yyyyMMdd");
		    	        String formattedDay = sdf.format(date);		
		    	        date = day1.parse(formattedDay);
		        		
		        		midRainper.setMw_date(date);
			        	calendar.add(calendar.DAY_OF_YEAR, 1);
			        	
			        	publicApiService.mwUpdate(midRainper);
	        		}
	        	}
	        }
	        // 10. 객체 해제
	        rd.close();
	        conn.disconnect();
	        }
	  }
	
	
	// 변수 설정
	private String apiURL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";

	// 구하고자 하는 시간과 좌표 대입
	private String[] nx = {"73", "87", "60", "68", "69", "51", "91", "56", "63", "89", "102", "52", "60", "66", "67", "98", "58" };
	private String[] ny = {"134", "106", "120", "100", "107", "67", "77", "124", "89", "90", "84", "38", "127", "103", "100", "76", "74"};
	private String baseDate = formattedDate;
	private String baseTime = "0800";
	private String dataType = "JSON";
	
	// 좌표별 지역이름 - ex(73,134 = 강원도)
	private String[] loc = {"강원도", "경상북도", "경기도", "충청남도", "충청북도", "전라남도", "경상남도", "인천시", "전라북도", "대구시", "울산시", "제주도", "서울시", "세종시", "대전시", "부산시", "광주시"};
	
	
	// 단기날씨 Delete API
	@Scheduled(cron = "0 10 8 * * *")
//	@Scheduled(cron = "0/45 * * * * *")
	public void delete() throws Exception{
		log.info("SWDelete");
		publicApiService.swDelete();
	}
	
	// 단기날씨 Insert API
	@Scheduled(cron = "0 11 8 * * *")
//	@Scheduled(cron = "0/40 * * * * *")
    public void insert() throws Exception {
		log.info("SWInsert");
		for(int i = 0; i<nx.length; i++) {
			
		
		StringBuilder urlBuilder = new StringBuilder(apiURL);
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("700", "UTF-8")); // 표 개수
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); // 페이지 수
		urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8")); // 받으려는 타입
		urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); // 조회하고 싶은 날짜
		urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); // 조회하고싶은 시간
		urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(nx[i], "UTF-8")); // x좌표
		urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(ny[i], "UTF-8")); // y좌표

		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		String result = sb.toString();

		// 테스트를 위해 출력

		JSONParser jsonParser = new JSONParser();
		org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) jsonParser.parse(result);
		org.json.simple.JSONObject parse_response = (org.json.simple.JSONObject) jsonObject.get("response");
		org.json.simple.JSONObject parse_body = (org.json.simple.JSONObject) parse_response.get("body"); // response 로 부터 body 찾아오기
		org.json.simple.JSONObject parse_items = (org.json.simple.JSONObject) parse_body.get("items"); // body 로 부터 items 받아오기
		// items 로 부터 itemList : 뒤에 [ 로 시작하므로 jsonArray 이다.
		org.json.simple.JSONArray parse_item = (org.json.simple.JSONArray) parse_items.get("item");

		for (int j = 0; j < parse_item.size(); j++) {
			ShortWeather apibean = new ShortWeather();

			org.json.simple.JSONObject tmp = (org.json.simple.JSONObject) parse_item.get(j);
			String sw_category = (String) tmp.get("category");
			String sw_fcstValue = (String) tmp.get("fcstValue");
			String sw_locx = String.valueOf(tmp.get("nx"));
			String sw_locy = String.valueOf(tmp.get("ny"));
			String sw_date = (String) tmp.get("fcstDate");
			String sw_time = (String) tmp.get("fcstTime");


			// 모든 카테고리에 공통으로 설정되는 필드들
			
			apibean.setSw_loc(loc[i]); // 각 xy좌표값에 맞는 지역 삽입
			apibean.setSw_date(sw_date);
			apibean.setSw_time(sw_time);

			// 카테고리가 맞을 경우에만
			if (sw_category.equals("POP")) {
				apibean.setSw_rainper(sw_fcstValue);
				publicApiService.swInsert(apibean);
			}

		}

	}

		
    }
	
	
	// 단기날씨 Update API
	@Scheduled(cron = "0 12 8 * * *")
//	@Scheduled(cron = "0/40 * * * * *")
	public void swupdate() throws Exception {
		log.info("SWUnsert");
		for(int i = 0; i<nx.length; i++) {
			
		
		StringBuilder urlBuilder = new StringBuilder(apiURL);
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("700", "UTF-8")); // 표 개수
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); // 페이지 수
		urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8")); // 받으려는 타입
		urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); // 조회하고 싶은 날짜
		urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); // 조회하고싶은 시간
		urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(nx[i], "UTF-8")); // x좌표
		urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(ny[i], "UTF-8")); // y좌표

		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		String result = sb.toString();

		JSONParser jsonParser = new JSONParser();
		org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) jsonParser.parse(result);
		org.json.simple.JSONObject parse_response = (org.json.simple.JSONObject) jsonObject.get("response");
		org.json.simple.JSONObject parse_body = (org.json.simple.JSONObject) parse_response.get("body"); // response 로 부터 body 찾아오기
		org.json.simple.JSONObject parse_items = (org.json.simple.JSONObject) parse_body.get("items"); // body 로 부터 items 받아오기
		// items 로 부터 itemList : 뒤에 [ 로 시작하므로 jsonArray 이다.
		org.json.simple.JSONArray parse_item = (org.json.simple.JSONArray) parse_items.get("item");

		for (int j = 0; j < parse_item.size(); j++) {
			ShortWeather apibean = new ShortWeather();

			org.json.simple.JSONObject tmp = (org.json.simple.JSONObject) parse_item.get(j);
			String sw_category = (String) tmp.get("category");
			String sw_fcstValue = (String) tmp.get("fcstValue");
			String sw_date = (String) tmp.get("fcstDate");
			String sw_time = (String) tmp.get("fcstTime");

			apibean.setSw_loc(loc[i]); // 각 xy좌표값에 맞는 지역 삽입
			apibean.setSw_date(sw_date);
			apibean.setSw_time(sw_time);

			// 카테고리가 맞을 경우에만
			if (sw_category.equals("TMP")) {
				apibean.setSw_temp(sw_fcstValue);
				publicApiService.swUpdate(apibean);
			}

		}

	}
		
    }
	
	// 미세먼지 Delete API
	@Scheduled(cron = "0 10 23 * * *")
	public void dustDelete() {
		log.info("DustDelete");
        int dustDelete = publicApiService.dustDelete();
	}
	
	// 미세먼지 Insert API
	@Scheduled(cron = "0 11 23 * * *")
//	@Scheduled(cron = "0/40 * * * * *")
	public void dustInsert() throws Exception {
        // 1. URL을 만들기 위한 StringBuilder.
		log.info("DustInsert");
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMinuDustFrcstDspth"); /*URL*/

        // 주의사항 
        // if 조건문을 통해 검색조건으로 넘어오는 지역값을 로컬코드를 변환 필요.
        
        
        String informCode = "PM10";
        LocalDate currentDate = LocalDate.now();
        // 포맷 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 날짜를 지정한 포맷으로 변환하여 출력
        String formattedDate = currentDate.format(formatter);
        
       
        
        // 2. 오픈 API의 요청 규격에 맞는 파라미터 생성. serviceKey 부분에 반드시 내가 인증받은 인코딩 키를 넣어주어야 한다!!
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "="+serviceKey); 
        urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("searchDate","UTF-8") + "=" + URLEncoder.encode(formattedDate, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("informCode","UTF-8") + "=" + URLEncoder.encode(informCode, "UTF-8")); 

        // 3. URL 객체 생성 (toString으로 string으로 변환)
        URL url = new URL(urlBuilder.toString());

        // 4. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성.
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        // 5. 통신을 위한 메소드 SET (Get 요청)
        conn.setRequestMethod("GET");

        // 6. 통신을 위한 Content-type SET. (json으로 설정해야됨)
        conn.setRequestProperty("Content-type", "application/json");

        // 7. 통신 응답 코드 확인.

        // 8. 전달받은 데이터를 BufferedReader 객체로 저장. 오류가 날 경우 error 발생
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300){
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
        }
        //가지고온 문자열을 String result에 저장
        String result = rd.readLine();
        
        //String의 데이터로는 원하는 데이터를 파싱할 수 없기 때문에 JSONParser로 JSON 객체로 만들어준다.
        JSONParser jsonParser = new JSONParser();
        org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject)jsonParser.parse(result);
        //JSON Key 값이 respone인 값을 가져온다
        org.json.simple.JSONObject response = (org.json.simple.JSONObject)jsonObject.get("response");
        //JSON Key 값이 body인 값을 가져온다
        org.json.simple.JSONObject body = (org.json.simple.JSONObject)response.get("body");

        
        //body 이후는 날짜별 데이터가 배열에 들어있다. 배열로 파싱한다.
        List<Dust> list = new ArrayList<>();
        org.json.simple.JSONArray items = (org.json.simple.JSONArray)body.get("items");
		for(int i=0;i<=2;i++){
			
			org.json.simple.JSONObject dustArray = (org.json.simple.JSONObject)items.get(i);
			String informData = (String)dustArray.get("informData");
			String informGrade = (String)dustArray.get("informGrade");
			String[] splits = informGrade.split(",");
			for(String split : splits) {
				
				Dust dust = new Dust();
				dust.setDust_date(informData);
				String [] splitData = split.split(":");
				String region = splitData[0].trim();
				dust.setDust_loc(splitData[0].trim());
				
				String grade = splitData[1].trim();
				dust.setDust_grade(splitData[1].trim());
				list.add(dust);
			}
		}
		
		int dustResult = publicApiService.dustInsert(list);
        // 10. 객체 해제
        rd.close();
        conn.disconnect();
		
		
	}
	//관광지 Insert API
	public void tour() throws Exception {
		// url만들기 위한 StringBuilder
		
		for(int pageNo=259; pageNo<300; pageNo++) {
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551011/KorService1/areaBasedList1");
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" +serviceKey);
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(pageNo), "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("IOS", "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "=" + URLEncoder.encode("12", "UTF-8"));
		
		
		
		// url객체 생성 toString으로 string타입으로 변환
		URL url = new URL(urlBuilder.toString());
		//urlBuilder.toString()
		
		// Connection 객체 생성
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		// 통신을 위한 메소드set( get 요청 )
		conn.setRequestMethod("GET");

		// 통신을 위한 Content-type SET. (json타입으로 설정)
		conn.setRequestProperty("Content-type", "application/json");

		// 통신 응답 코드확인

		// 전달받은 데이터를 BufferReader객체로 저장하고 오류가 날 경우 error
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}

		// 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		
		  // JSON 문자열로 바꾸기
        String result = sb.toString();
        
        
        // JSON 문자열을 JsonNode 객체로 파싱하여 객체 모델로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(result);
        
        
           	
        String code=rootNode.get("response").get("body").get("items").get("item").get(0).get("contentid").asText();
        	
        
		rd.close();
		conn.disconnect();
		


		/*------ contentId 리스트로 추출한 코드로 디테일통합 검색 ------*/
		

		Tour tour=new Tour();
		
		
		StringBuilder newUrlBuilder = new StringBuilder("http://apis.data.go.kr/B551011/KorService1/detailCommon1");
	    newUrlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
	            + serviceKey);
	    newUrlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("50", "UTF-8"));
	    newUrlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
	    newUrlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("IOS", "UTF-8"));
	    newUrlBuilder .append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8"));
	    newUrlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
	    newUrlBuilder.append("&" + URLEncoder.encode("contentId", "UTF-8") + "=" + URLEncoder.encode(code, "UTF-8"));
	    newUrlBuilder.append("&" + URLEncoder.encode("defaultYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));
	    newUrlBuilder.append("&" + URLEncoder.encode("firstImageYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));
	    newUrlBuilder.append("&" + URLEncoder.encode("areacodeYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));
	    newUrlBuilder.append("&" + URLEncoder.encode("addrinfoYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));
	    newUrlBuilder.append("&" + URLEncoder.encode("addrinfoYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));
	    newUrlBuilder.append("&" + URLEncoder.encode("mapinfoYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));
	    newUrlBuilder.append("&" + URLEncoder.encode("overviewYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));

	    URL url01 = new URL(newUrlBuilder.toString());
	    HttpURLConnection conn01 = (HttpURLConnection) url01.openConnection(); // 수정한 부분
	    conn01.setRequestMethod("GET");
	    conn01.setRequestProperty("Content-type", "application/json");

	    BufferedReader rd01 = null;
	    if (conn01.getResponseCode() >= 200 && conn01.getResponseCode() <= 300) {
	        rd01 = new BufferedReader(new InputStreamReader(conn01.getInputStream()));
	    } else {
	        rd01 = new BufferedReader(new InputStreamReader(conn01.getErrorStream()));
	    }
	    StringBuilder sb01 = new StringBuilder();
	    String line01;
	    while ((line01 = rd01.readLine()) != null) {
	        sb01.append(line01);
	    }
		  // JSON 문자열로 바꾸기
        String result01 = sb01.toString();
        
        
        // JSON 문자열을 JsonNode 객체로 파싱하여 객체 모델로 변환
        ObjectMapper objectMapper01 = new ObjectMapper();
        JsonNode rootNode01 = objectMapper.readTree(result01);

	    tour.setTour_id(rootNode01.get("response").get("body").get("items").get("item").get(0).get("contentid").asText());
	    tour.setTour_name(rootNode01.get("response").get("body").get("items").get("item").get(0).get("title").asText());
	    tour.setTour_url(rootNode01.get("response").get("body").get("items").get("item").get(0).get("homepage").asText());
	    tour.setTour_tel(rootNode01.get("response").get("body").get("items").get("item").get(0).get("tel").asText());
	    tour.setTour_city(rootNode01.get("response").get("body").get("items").get("item").get(0).get("sigungucode").asText());
	    tour.setTour_addr1(rootNode01.get("response").get("body").get("items").get("item").get(0).get("addr1").asText());
	    tour.setTour_addr2(rootNode01.get("response").get("body").get("items").get("item").get(0).get("addr2").asText());
	    tour.setTour_zipcode(rootNode01.get("response").get("body").get("items").get("item").get(0).get("zipcode").asText());
	    tour.setTour_locx(rootNode01.get("response").get("body").get("items").get("item").get(0).get("mapx").asText());
	    tour.setTour_locy(rootNode01.get("response").get("body").get("items").get("item").get(0).get("mapy").asText());
	    tour.setTour_desc(rootNode01.get("response").get("body").get("items").get("item").get(0).get("overview").asText());
        
        
	   // rd01.close();
	    //conn01.disconnect();
	    

	    // 
	    StringBuilder newnewUrlBuilder = new StringBuilder("http://apis.data.go.kr/B551011/KorService1/detailIntro1");
	    newnewUrlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
	            + "=9y36a0v9gk20aw%2BkVR4YvZq7yeKsfA%2Bfx%2F%2FwCXT9fO6ek6ps07MJePHlvMvQskqMcG17JPY1Nej%2FTAvyN3WRvQ%3D%3D");
	    newnewUrlBuilder.append("&" + URLEncoder.encode("contentId", "UTF-8") + "=" + URLEncoder.encode(code, "UTF-8"));
	    newnewUrlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "=" + URLEncoder.encode("12", "UTF-8"));
	    newnewUrlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("60", "UTF-8"));
	    newnewUrlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
	    newnewUrlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
	    newnewUrlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("IOS", "UTF-8"));
	    newnewUrlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8"));
	    
	    URL url02 = new URL(newnewUrlBuilder.toString());
	    HttpURLConnection conn02 = (HttpURLConnection) url02.openConnection();
	    conn02.setRequestMethod("GET");
	    conn02.setRequestProperty("Content-type", "application/json");

	    BufferedReader rd02 = null;
	    if (conn02.getResponseCode() >= 200 && conn02.getResponseCode() <= 300) {
	        rd02 = new BufferedReader(new InputStreamReader(conn02.getInputStream()));
	    } else {
	        rd02 = new BufferedReader(new InputStreamReader(conn02.getErrorStream()));
	    }
	    StringBuilder sb02 = new StringBuilder();
	    String line02;
	    while ((line02 = rd02.readLine()) != null) {
	        sb02.append(line02);
	    }
	    
		  // JSON 문자열로 바꾸기
        String result02 = sb02.toString();
        
        
        // JSON 문자열을 JsonNode 객체로 파싱하여 객체 모델로 변환
        ObjectMapper objectMapper02 = new ObjectMapper();
        JsonNode rootNode02 = objectMapper.readTree(result02);
	    
        tour.setTour_babycarry(rootNode02.get("response").get("body").get("items").get("item").get(0).get("chkbabycarriage").asText());
	    tour.setTour_animal_able(rootNode02.get("response").get("body").get("items").get("item").get(0).get("chkpet").asText());
	    tour.setTour_age_able(rootNode02.get("response").get("body").get("items").get("item").get(0).get("expagerange").asText());
	    tour.setTour_heritage1(rootNode02.get("response").get("body").get("items").get("item").get(0).get("heritage1").asText());
	    tour.setTour_heritage2(rootNode02.get("response").get("body").get("items").get("item").get(0).get("heritage2").asText());
	    tour.setTour_heritage3(rootNode02.get("response").get("body").get("items").get("item").get(0).get("heritage3").asText());
	    tour.setTour_infocenter(rootNode02.get("response").get("body").get("items").get("item").get(0).get("infocenter").asText());
	    tour.setTour_open_date(rootNode02.get("response").get("body").get("items").get("item").get(0).get("opendate").asText());
	    tour.setTour_parking(rootNode02.get("response").get("body").get("items").get("item").get(0).get("parking").asText());
	    tour.setTour_dayoff(rootNode02.get("response").get("body").get("items").get("item").get(0).get("restdate").asText());
	    tour.setTour_usetime(rootNode02.get("response").get("body").get("items").get("item").get(0).get("usetime").asText());
	    
	    
       
	    rd02.close();
	    conn02.disconnect();
	    
	    
	    
	    // 
	    StringBuilder newnewnewUrlBuilder = new StringBuilder("http://apis.data.go.kr/B551011/KorService1/detailImage1");
	    newnewnewUrlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
	            + serviceKey);
	    newnewnewUrlBuilder.append("&" + URLEncoder.encode("contentId", "UTF-8") + "=" + URLEncoder.encode(code, "UTF-8"));
	    newnewnewUrlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("60", "UTF-8"));
	    newnewnewUrlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
	    newnewnewUrlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
	    newnewnewUrlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("IOS", "UTF-8"));
	    newnewnewUrlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8"));
	    newnewnewUrlBuilder.append("&" + URLEncoder.encode("imageYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));
	    newnewnewUrlBuilder.append("&" + URLEncoder.encode("subImageYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8"));
	    
	    URL url03 = new URL(newnewnewUrlBuilder.toString());
	    HttpURLConnection conn03 = (HttpURLConnection) url03.openConnection();
	    conn03.setRequestMethod("GET");
	    conn03.setRequestProperty("Content-type", "application/json");
	    
	    BufferedReader rd03 = null;
	    if (conn03.getResponseCode() >= 200 && conn03.getResponseCode() <= 300) {
	        rd03 = new BufferedReader(new InputStreamReader(conn03.getInputStream()));
	    } else {
	        rd03 = new BufferedReader(new InputStreamReader(conn03.getErrorStream()));
	    }
	    StringBuilder sb03 = new StringBuilder();
	    String line03;
	    while ((line03 = rd03.readLine()) != null) {
	        sb03.append(line03);
	    }
	    
		// JSON 문자열로 바꾸기
        String result03 = sb03.toString();
        
        
        // JSON 문자열을 JsonNode 객체로 파싱하여 객체 모델로 변환
        ObjectMapper objectMapper03= new ObjectMapper();
        JsonNode rootNode03 = objectMapper.readTree(result03);
        
        String images = "";
	    JsonNode itemsNode = rootNode03.get("response").get("body").get("items").get("item");
	    if(itemsNode != null) {
	        for(int i = 0; i < itemsNode.size(); i++) {
	            String originimgurl = itemsNode.get(i).get("originimgurl").asText();
	            if(originimgurl != null && !originimgurl.isEmpty()) {
	                if(i == 0) {
	                    images += originimgurl;
	                } else {
	                    images += ("," + originimgurl);
	                }
	            }
	        }
        }
        //tour images 수정필요
	    tour.setTour_images(images);
        
        
	    rd03.close();
	    conn03.disconnect();
    
	    publicApiService.tourInsert(tour);


		}
	}
	
	//캠핑장 Insert API
	public String callApi(){
		
		StringBuilder result = new StringBuilder();
		
		String urlStr = "http://apis.data.go.kr/B551011/GoCamping/basedList?"+
				"serviceKey="+serviceKey+
				"&MobileOS=ETC"+
				"&MobileApp=AppTest"+
				"&_type=json"+
				"&pageNo=1"+
				"&numOfRows=3279";
		
		URL url= null;
		HttpURLConnection con =null;
		JSONObject result1 = null;
		StringBuilder sb = new StringBuilder();
		
		try {		
			url = new URL(urlStr);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-type","application/json");
			con.setDoOutput(true);
			
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
		while(br.ready()) {
			sb.append(br.readLine());
		}
			con.disconnect();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			result1 = (JSONObject) new JSONParser().parse(sb.toString());
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StringBuilder out = new StringBuilder();
		
		JSONObject response = (JSONObject) result1.get("response");
		JSONObject body = (JSONObject) response.get("body");
		JSONObject items = (JSONObject) body.get("items");
		JSONArray itmeList = (JSONArray) items.get("item");
		
		JSONArray itemList = (JSONArray) items.get("item");

		Iterator<?> iterator = itemList.iterator();
		List<CampApi> list = new ArrayList<>();
		
        while (iterator.hasNext()) {
            JSONObject item = (JSONObject)iterator.next();
            	
            CampApi dto = new CampApi();
            String facltNm = (String) item.get("facltNm");
            String lineIntro = (String) item.get("lineIntro");
            String intro = (String) item.get("intro");
            String allar = (String) item.get("allar");
            String insrncAt = (String) item.get("insrncAt");
            String facltDivNm = (String) item.get("facltDivNm");
            String hvofBgnde = (String) item.get("hvofBgnde");
            String hvofEnddle = (String) item.get("hvofEnddle");
            String featureNm = (String) item.get("featureNm");
            String induty = (String) item.get("induty");
            String lctCl = (String) item.get("lctCl");
            String doNm = (String) item.get("doNm");
            String sigunguNm = (String) item.get("sigunguNm");
            String zipcode = (String) item.get("zipcode");
            String addr1 = (String) item.get("addr1");
            String addr2 = (String) item.get("addr2");
            String mapX = (String) item.get("mapX");
            String mapY = (String) item.get("mapY");
            String tel = (String) item.get("tel");
            String homepage = (String) item.get("homepage");
            String resveUrl = (String) item.get("resveUrl");
            String resveCl = (String) item.get("resveCl");
            String gnrlSiteCo = (String) item.get("gnrlSiteCo");
            String autoSiteCo = (String) item.get("autoSiteCo");
            String glampSiteCo = (String) item.get("glampSiteCo");
            String caravSiteCo = (String) item.get("caravSiteCo");
            String indvdlCaravSiteCo = (String) item.get("indvdlCaravSiteCo");
            String caravInnerFclty = (String) item.get("caravInnerFclty");
            String operPdCl = (String) item.get("operPdCl");
            String operDeCl = (String) item.get("operDeCl");
            String toiletCo = (String) item.get("toiletCo");
            String swrmCo = (String) item.get("swrmCo");
            String wtrplCo = (String) item.get("wtrplCo");
            String sbrsCl = (String) item.get("sbrsCl");
            String posblFcltyCl = (String) item.get("posblFcltyCl");
            String eqpmnLendCl = (String) item.get("eqpmnLendCl");
            String animalCmgCl = (String) item.get("animalCmgCl");
            String firstImageUrl = (String) item.get("firstImageUrl");
            
            dto.setFacltNm(facltNm);
            dto.setLineIntro(lineIntro);
            dto.setIntro(intro);
            dto.setAllar(allar);
            dto.setInsrncAt(insrncAt);
            dto.setFacltDivNm(facltDivNm);
            dto.setHvofBgnde(hvofBgnde);
            dto.setHvofEnddle(hvofEnddle);
            dto.setFeatureNm(featureNm);
            dto.setInduty(induty);
            dto.setLctCl(lctCl);
            dto.setDoNm(doNm);
            dto.setSigunguNm(sigunguNm);
            dto.setZipcode(zipcode);
            dto.setAddr1(addr1);
            dto.setAddr2(addr2);
            dto.setMapX(mapX);
            dto.setMapY(mapY);
            dto.setTel(tel);
            dto.setHomepage(homepage);
            dto.setResveUrl(resveUrl);
            dto.setResveCl(resveCl);
            dto.setGnrlSiteCo(gnrlSiteCo);
            dto.setAutoSiteCo(autoSiteCo);
            dto.setGlampSiteCo(glampSiteCo);
            dto.setCaravSiteCo(indvdlCaravSiteCo);
            dto.setIndvdlCaravSiteCo(indvdlCaravSiteCo);
            dto.setCaravInnerFclty(caravInnerFclty);
            dto.setOperPdCl(operPdCl);
            dto.setOperDeCl(operDeCl);
            dto.setToiletCo(toiletCo);
            dto.setSwrmCo(swrmCo);
            dto.setWtrplCo(wtrplCo);
            dto.setSbrsCl(sbrsCl);
            dto.setPosblFcltyCl(posblFcltyCl);
            dto.setEqpmnLendCl(eqpmnLendCl);
            dto.setAnimalCmgCl(animalCmgCl);
            dto.setFirstImageUrl(firstImageUrl);
            
            list.add(dto);
            
    }
        
        publicApiService.campInsert(list);
        
        return out.toString();
	}
	
}
