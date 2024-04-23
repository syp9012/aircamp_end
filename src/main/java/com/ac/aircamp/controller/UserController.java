package com.ac.aircamp.controller;

import java.io.File;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ac.aircamp.model.User;
import com.ac.aircamp.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	
	// 메인 폼으로 이동
	@GetMapping("/main")
	public String main() {
		return "main";
	}
	// 로그인 폼으로 이동
	@GetMapping("/login")
	public String login() {
		
		return "user/loginForm";
	}


	@PostMapping("/login")
	public String login(@RequestParam("u_id") String u_id, @RequestParam("u_pwd") String u_pwd, HttpSession session,
			Model model) throws Exception {
		System.out.println("로그인 이동");
		int result = 0;
		User dbUser = userService.checkUser(u_id);
		
		if (dbUser == null) { // 등록되지 않은 회원일때
			result = 1;
			model.addAttribute("result", result);
			System.out.println("result1");
			return "user/loginResult";

		} else if (dbUser.getU_withdraw().equals("1")) { // 등록된 회원일때
			if (passwordEncoder.matches(u_pwd, dbUser.getU_pwd()))  { // 비번이 같을때
				session.setAttribute("u_id", u_id); // 세션 설정

				// 닉네임을 메인페이지로 전달( 메인에서 세션을 구할시 삭제가능)
				String u_nickname = dbUser.getU_nickname();

				model.addAttribute("u_nickname", u_nickname);
				System.out.println("result2");
				return "redirect:/main";
				
			} else { // 비번이 다를때
				result = -1;
				model.addAttribute("result", result);
				System.out.println("매치 결과"+passwordEncoder.matches(u_pwd, dbUser.getU_pwd()));
				System.out.println("입력한 패스워드"+u_pwd);
				System.out.println("디비 패스워드"+dbUser.getU_pwd());
				System.out.println(dbUser.getU_pwd());
				System.out.println("result3");
			}
		}
		System.out.println("result4");
		return "user/loginResult";

	}
	
	// ID중복검사
	@PostMapping("/idCheck")
	@ResponseBody
	public int idcheck(@RequestParam("u_id") String u_id) {
		int result = 0;

		System.out.println(u_id);

		User user = userService.checkUser(u_id);
		if (user != null) { // 중복 ID
			result = 1;
		} else { // 사용가능 ID
			result = -1;
		}

		return result;
	}

	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();

		return "user/logout";
	}

	// 회원가입 폼
	@GetMapping("/join")
	public String join() {
		return "user/join";
	}

	// 회원가입
	@PostMapping("/join")
	public String join(User user, Model model) throws Exception {
		String rawPassword=user.getU_pwd();
		String encPassword=passwordEncoder.encode(rawPassword);
		user.setU_pwd(encPassword);
		
		userService.insert(user);
		
		return "redirect:login";
	}
	
	// 비번찾기 폼 
	@GetMapping("/pwdFind")
	public String pwd_find() {
		return "user/pwdFind";		
	}
	
	//비밀번호 변경
	@GetMapping("pwdUpdate")
	public String pwdUpdate() {
		return "user/pwdUpdateForm";
	}
	@PostMapping("pwdUpdate")
	public String pwdUpdate(User user, @RequestParam("u_newpwd") String u_newpwd, Model model) {
		System.out.println("메서드진입");
		int result = 0;
		User dbUser = userService.checkUser(user.getU_id());
		System.out.println("GetUser"+dbUser);
		
		if(passwordEncoder.matches(user.getU_pwd(), dbUser.getU_pwd())) {
			User newUser = new User();
			newUser.setU_id(user.getU_id());
			String encpassword = passwordEncoder.encode(u_newpwd);
			System.out.println();
			newUser.setU_pwd(encpassword);
			result = userService.pwdUpdate(newUser);
		}else {
			result = -1;
		}
		System.out.println(result);
		model.addAttribute("result", result);
		return "user/pwdUpdate";
	}
	
	@Value("${spring.mail.hostSMTPid}")
	String hostSMTPid;
	@Value("${spring.mail.hostSMTPpwd}")
	String hostSMTPpwd;
	
	
	// 비번찾기 : email로 전송
	@PostMapping("/pwdFind")
	public String pwd_find_ok(@RequestParam("u_id") String u_id, Model model) throws Exception {
		int result = 0;
		User dbUser = userService.checkUser(u_id);
		if (dbUser == null) {
			model.addAttribute("result",result);
			return "user/pwdResult";
			
		} else {
		User user = new User();
		Random random = new Random();
		String newPwd = String.valueOf(random.nextInt(999999));
		user.setU_id(u_id);
		user.setU_pwd(passwordEncoder.encode(newPwd));
		userService.updatePwd(user);
		System.out.println(newPwd);
		System.out.println(u_id);

			// Mail Server 설정
			String charSet = "utf-8";
			String hostSMTP = "smtp.naver.com";
			

			// 보내는 사람 EMail, 제목, 내용
			String fromEmail = "aircamp03@naver.com";
			String fromName = "관리자";
			String subject = "비밀번호 찾기";

			// 받는 사람 E-Mail 주소 : 원래는 받는 사람도 직접 작성했지만, 여기서는 DB에 저장된 정보가 존재 해서 그걸 불러서 사용한다.
			String mail = dbUser.getU_email()+"@"+dbUser.getU_domain();

			try {
				HtmlEmail email = new HtmlEmail();
				email.setDebug(true);
				email.setCharset(charSet);
				email.setSSL(true);
				email.setHostName(hostSMTP);
				email.setSmtpPort(587);

				email.setAuthentication(hostSMTPid, hostSMTPpwd);
				email.setTLS(true);
				email.addTo(mail, charSet);
				email.setFrom(fromEmail, fromName, charSet);
				email.setSubject(subject);
				email.setHtmlMsg("<p align = 'center'>비밀번호 찾기</p><br>" + 
								 "<div align='center'> 임시 비밀번호:"+newPwd+"</div>");
				email.send();
			} catch (Exception e) {
				System.out.println(e);
			}
			
			result = 1;
			model.addAttribute("result",result);
			
			return "user/pwdResult";
			
		}
	}
	
	// 회원 탈퇴
	@GetMapping("/withdraw")
	public String withdrawform(HttpSession session, Model model) {
		
		String u_id = (String) session.getAttribute("u_id");
		User user = userService.checkUser(u_id);

		model.addAttribute("u_id", u_id);
		model.addAttribute("u_nickname", user.getU_nickname());
		return "user/withdraw";
	}

	// 회원 탈퇴
	// 회원탈퇴 요청
	@PostMapping("/withdraw")
	public String withdraw(@RequestParam("u_pwd") String u_pwd, HttpSession session) {
		String u_id = (String) session.getAttribute("u_id");

		User user = userService.checkUser(u_id);

		if (user.getU_pwd().equals(u_pwd)) { // 비밀번호 일치시
			userService.withdraw(u_id);
			session.invalidate(); // 세션 삭제 후 main.jsp로 이동

			return "redirect:/main";
		} else { // 비밀번호 불일치시
			return "user/withdrawResult";
		}

	}

	// 회원정보 수정폼
	@GetMapping("/update")
	public String update(HttpSession session, Model model) {
		String id = (String) session.getAttribute("u_id");
		User user = userService.checkUser(id);
		
		if(user.getU_social().equals("normal")) {
			model.addAttribute("user", user);
			return "user/update";
		}else {
			model.addAttribute("user", user);
			return "user/updateSocial";
		}

	}

	// 회원정보 수정
	@PostMapping("/update")
	public String update(@RequestParam("u_profile1") MultipartFile mf, @ModelAttribute User user, HttpSession session,
			HttpServletRequest request, Model model) throws Exception {
		System.out.println("user 아이디" + user.getU_id());

		String id = (String) session.getAttribute("u_id");
		user.setU_id(id);

		String filename = mf.getOriginalFilename();
		int size = (int) mf.getSize();

		String path = session.getServletContext().getRealPath("upload");
		System.out.println("path:" + path);

		int result = 0;
		String newfilename = "";

		if (size > 0) { // 첨부파일이 전송된 경우

			// 파일 중복문제 해결
			String extension = filename.substring(filename.lastIndexOf("."), filename.length());
			System.out.println("extension:" + extension);

			UUID uuid = UUID.randomUUID();

			newfilename = uuid.toString() + extension;
			System.out.println("newfilename:" + newfilename);

			if (size > 100000) { // 100KB
				result = 1;
				model.addAttribute("result", result);

				return "user/uploadResult";

			} else if (!extension.equals(".jpg") && !extension.equals(".jpeg") && !extension.equals(".gif")
					&& !extension.equals(".png")) {

				result = 2;
				model.addAttribute("result", result);

				return "user/uploadResult";
			}
		}

		if (size > 0) { // 첨부파일 전송
			mf.transferTo(new File(path + "/" + newfilename));
		}

		User db = this.userService.checkUser(id);

		if (size > 0) { // 첨부 파일이 수정되면
			user.setU_profile(newfilename);
		} else { // 첨부파일이 수정되지 않으면
			user.setU_profile(db.getU_profile());
		}
		// update SQL문
		if(user.getU_social().equals("normal")) {	
			if (passwordEncoder.matches(user.getU_pwd(), db.getU_pwd())) { // 비밀번호 일치시
				userService.update(user);
				System.out.println("수정완료");
				return "redirect:/user/myPage";
			} else { // 비밀번호 불일치시
				return "user/updateResult";
			}
		}else{
			userService.update(user);
			return "redirect:/user/myPage";
			}

	}

	
	
	
	// myPage 이동
	@GetMapping("myPage")
	public String myPage(HttpSession session, Model model) {
		String id = (String) session.getAttribute("u_id");
		User user = userService.checkUser(id);
		
		model.addAttribute("u_nickname", user.getU_nickname());
		model.addAttribute("u_profile", user.getU_profile());
		model.addAttribute("u_phone", user.getU_phone());

		return "user/myPage";
	}

}
