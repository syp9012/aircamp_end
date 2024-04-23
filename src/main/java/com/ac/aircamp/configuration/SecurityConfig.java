package com.ac.aircamp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer{
	
	// PasswordEncoder 인터페이스로 생성 다형성
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	// HttpSecurity 설정
	// 로그인시 시큐리티 로그인 창으로 넘어가는것을 막습니다.
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
         .formLogin().disable()  // FormLogin 사용 X
         .httpBasic().disable()  // httpBasic 사용 X
         .csrf().disable()       // csrf 보안 사용 X
         .headers().frameOptions().disable();
	     return http.build();
	     
	}
   // 카카오맵 api 접근 허용
   @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로에 대해 CORS 설정 적용
        .allowedOrigins("*") // 모든 출처 허용
        .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드 지정
        .allowedHeaders("*"); // 모든 헤더 허용
   }

	
	

}