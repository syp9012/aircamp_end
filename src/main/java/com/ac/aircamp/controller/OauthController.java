package com.ac.aircamp.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.ac.aircamp.model.User;
import com.ac.aircamp.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/oauth")
public class OauthController {
	
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	
	
	//네이버 application.yaml 파일을 가져와 변수에 저장
	//네이버
	//네이버 clientid
	@Value("${spring.security.oauth2.client.registration.naver.clientId}")
	private String naverClientId;
	
	//네이버 clientSecret
	@Value("${spring.security.oauth2.client.registration.naver.clientSecret}")
	private String naverClientSecret;
	
	//네이버 clientAuthenticationMethod
	@Value("${spring.security.oauth2.client.registration.naver.clientAuthenticationMethod}")
	private String naverClientAuthenticationMethod;
	
	//네이버 authenticationGrantType
	@Value("${spring.security.oauth2.client.registration.naver.authorizationGrantType}")
	private String naverAuthorizationGrantType;
	
	//네이버 redirectUri
	@Value("${spring.security.oauth2.client.registration.naver.redirectUri}")
	private String naverRedirectUri;
	
	//네이버 clientName
	@Value("${spring.security.oauth2.client.registration.naver.clientName}")
	private String naverClientName;
	

	
	//카카오
	//카카오 clientid
	@Value("${spring.security.oauth2.client.registration.kakao.clientId}")
	private String kakaoClientId;
	
	//카카오 clientAuthenticationMethod
	@Value("${spring.security.oauth2.client.registration.kakao.clientAuthenticationMethod}")
	private String kakaoClientAuthenticationMethod;
	
	//카카오 authorizationGrantType
	@Value("${spring.security.oauth2.client.registration.kakao.authorizationGrantType}")
	private String kakaoAuthorizationGrantType;
	
	//카카오 redirectUri
	@Value("${spring.security.oauth2.client.registration.kakao.redirectUri}")
	private String kakaoRedirectUri;
	
	//카카오 clientName
	@Value("${spring.security.oauth2.client.registration.kakao.clientName}")
	private String kakaoClientName;
		



	//카카오톡 회원가입 로그인
	@GetMapping("/kakao/callback")
		public String kakaoCallback(@RequestParam("code") String code, HttpSession session, Model model) { // Data를 리턴해주는 컨트롤러 

		//post 방식으로 key=value  데이터를 요청
		//RestTemplate
		
		//HttpHeader 오브젝트 생성
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
		
		//HttpBody 오브젝트 생성
		MultiValueMap<String, String> params =new LinkedMultiValueMap<>();
		params.add("grant_type", kakaoAuthorizationGrantType);
		params.add("client_id", kakaoClientId);
		params.add("redirect_uri", kakaoRedirectUri);
		params.add("code", code);
		
		//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest=new HttpEntity<>(params,headers);
		
		// Http 요청하기 - Post방식 - 그리고 response 변수의 응답받음
		ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token?", HttpMethod.POST, kakaoTokenRequest, String.class);	
		
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> tokenMap = new HashMap();
		
		// JSON String -> Map으로 파싱
		try {
			tokenMap = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String accessToken= tokenMap.get("access_token").toString();
	
		
		//HttpHeader 오브젝트 생성
		RestTemplate rt2 = new RestTemplate();
		HttpHeaders headers2 = new HttpHeaders();
		
		headers2.add("Authorization","Bearer "+accessToken);
		headers2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
		
		//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest=
				new HttpEntity<>(headers2);
		
		// Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답받음
		ResponseEntity<String> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, kakaoProfileRequest, String.class);	
		
		//Gson,
		ObjectMapper objectMapper2 = new ObjectMapper();
		//ObjectMapper를 사용하여 JSON 데이터를 DTO 객체로 변환할 때 FAIL_ON_UNKNOWN_PROPERTIES 기능을 비활성화하는 것은 유효한 접근 방법입니다. 
		//이렇게 하면 DTO 클래스에 정의되지 않은 속성이 JSON 데이터에 포함되어 있더라도 예외가 발생하지 않고 객체 변환이 계속됩니다.
		objectMapper2.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		Map<String, Object> kakaoProfileMap = new HashMap();
		
		//JSON 형식의 값을 맵에 저장
		//id, properties(nickname, profile_image), kakaoaccount(email)을 맵으로 파싱합니다.
		try {
			kakaoProfileMap = objectMapper2.readValue(response2.getBody(), new TypeReference<Map<String, Object>>() {});
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		//매개변수로 전달할 user객체를 생성
		User user = new User();
		
		//id를 kakaoProfileMap 에서 가져옴
		String id = kakaoProfileMap.get("id").toString();

		// 닉네임이 담겨있는 Map을 변수에 저장.
		Map<String, Object> propertiesMap= (Map<String, Object>) kakaoProfileMap.get("properties");
		String nickname=propertiesMap.get("nickname").toString();
		
		
		//email이 담겨있는 Map을 변수에 담습니다.
		Map<String, Object> kakaoAccountMap= (Map<String, Object>) kakaoProfileMap.get("kakao_account");
		String fullEmail=kakaoAccountMap.get("email").toString();
		
		//이메일주소를 @기준 나눠 email, domain으로 나누어 변수에 저장
		String[] emailParts=fullEmail.split("@");
		
		//비밀번호 임의로 난수 생성하여 저장
		String pwd=UUID.randomUUID().toString();
		
		//휴대폰번호 임의 번호 등록
		
		user.setU_id(id);
		user.setU_nickname(nickname);
		user.setU_email(emailParts[0]);
		user.setU_domain(emailParts[1]);
		user.setU_nickname(nickname);
		user.setU_phone("01000000000");
		user.setU_pwd(pwd);

		if(userService.checkUser(user.getU_id())==null) { 
			userService.insertSocial(user);
			session.setAttribute("u_id", user.getU_id());
			model.addAttribute("u_profile",user.getU_profile());
			model.addAttribute("u_nickname",user.getU_nickname()); 
		} else { 
		user =userService.checkUser(user.getU_id()); session.setAttribute("u_id", user.getU_id());
		model.addAttribute("u_profile",user.getU_profile());
		model.addAttribute("u_nickname",user.getU_nickname());
		} 
	    // utf8로 인코딩

		return "redirect:/main" ;
	}
	
	//네이버 회원가입 로그인
		@GetMapping("/naver/callback")
			public String naverCallback(@RequestParam("code") String code, HttpSession session, Model model) { // Data를 리턴해주는 컨트롤러 
			
			//post 방식으로 key=value  데이터를 요청(카카오쪽으로
			//Retrofit2
			//OkHttp
			//RestTemplate
			
			//HttpHeader 오브젝트 생성
			RestTemplate rt = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("X-Naver-Client-Id",naverClientId);
			headers.add("X-Naver-Client-Secret", naverClientSecret);
			
			//HttpBody 오브젝트 생성
			MultiValueMap<String, String> params =new LinkedMultiValueMap<>();
			params.add("grant_type", naverAuthorizationGrantType);
			params.add("client_id", naverClientId);
			params.add("client_secret", naverClientSecret);
			params.add("redirect_uri", naverRedirectUri);
			params.add("code", code);
			params.add("state", "1234");
			
			
			//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
			HttpEntity<MultiValueMap<String,String>> naverTokenRequest=
					new HttpEntity<>(params,headers);
			
			// Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답받음
			ResponseEntity<String> response = rt.exchange("https://nid.naver.com/oauth2.0/token?", HttpMethod.POST, naverTokenRequest, String.class);	
			
			//Gson,
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> oauthToken = new HashMap();
			
			try {
				oauthToken = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

			//액세스 토큰을 파싱
			String accessToken = oauthToken.get("access_token").toString();
			
			//HttpHeader 오브젝트 생성 RestTemplate
			RestTemplate rt2 = new RestTemplate();
			HttpHeaders headers2 = new HttpHeaders();
			
			headers2.add("Authorization","Bearer "+accessToken);
			
			
			//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
			HttpEntity<MultiValueMap<String,String>> naverProfileRequest2= new HttpEntity<>(headers2);
			  
			 // Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답받음 ResponseEntity<String>
			ResponseEntity<String>  response2 = rt2.exchange("https://openapi.naver.com/v1/nid/me", HttpMethod.POST, naverProfileRequest2, String.class);
			  
			//Gson, 
			ObjectMapper objectMapper2 = new ObjectMapper(); 
			//ObjectMapper를 사용하여 JSON 데이터를 DTO 객체로 변환할 때 FAIL_ON_UNKNOWN_PROPERTIES 기능을 비활성화하는 것은 유효한 접근 방법입니다. 
			//이렇게 하면 DTO 클래스에 정의되지 않은 속성이 JSON 데이터에 포함되어 있더라도 예외가 발생하지 않고 객체 변환이 계속됩니다.
			  
			objectMapper2.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			
			Map<Object, Object>  naverProfileMap = new HashMap();
			

			//JSON 형식의 값을 맵에 저장
			//Response(id, nickname, profile_image,email, mobile)를 맵으로 파싱합니다.
			try { 
				naverProfileMap = objectMapper2.readValue(response2.getBody(),new TypeReference<Map<Object, Object>>() {}); 
			} catch(JsonMappingException e) { 
				e.printStackTrace(); 
			} catch(JsonProcessingException e) { 
				e.printStackTrace(); }
			
			
			Map<String, Object>  naverResponseMap= (Map<String, Object>) naverProfileMap.get("response");
			
			User user = new User(); 
			
			// 아이디 구해옴
			String [] id = naverResponseMap.get("id").toString().split("(?<=\\G.{10})", -1);
			String id2 = id[0];
	
			//닉네임 구해옴
			String nickname = naverResponseMap.get("nickname").toString();
			
			// 이메일 구해와 도메인과 메일 아이디로 구분
			String fullEmail=naverResponseMap.get("email").toString();
			String[] emailParts=fullEmail.split("@");
			
			//휴대폰 번호 구해옴
			String phone = naverResponseMap.get("mobile").toString().replace("-", "");
			
			//비밀번호 난수화 임의 생성
			String pwd=UUID.randomUUID().toString(); 
			
			user.setU_id(id2);
			user.setU_nickname(nickname);
			user.setU_email(emailParts[0]);
			user.setU_domain(emailParts[1]);
			user.setU_phone(phone);
			user.setU_pwd(pwd);
	
			// 가입자 혹은 비 가입자 체크 해서 처리 //userService.checkUser(user.getU_id())
			if(userService.checkUser(user.getU_id())==null) { 
				userService.insertSocial(user);
				session.setAttribute("u_id", user.getU_id());
			} else { 
			user =userService.checkUser(user.getU_id()); 
			session.setAttribute("u_id", user.getU_id());
			} 
			
			
			return "redirect:/main";
		}
		

	
	
}