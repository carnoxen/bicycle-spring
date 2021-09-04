package bicycle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import bicycle.repository.MemberRepository;
import bicycle.security.SignupForm;

// 사용자의 회원가입 처리
@Controller
@RequestMapping("/signup")
public class SignupController {
	private MemberRepository memberRepo;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public SignupController(MemberRepository memberRepo, PasswordEncoder passwordEncoder) {
		this.memberRepo = memberRepo;
		this.passwordEncoder = passwordEncoder;
	}
	
	@GetMapping
	public String signup() {
		return "signup";
	}
	
	// 비밀번호를 인코딩하고 저장
	@PostMapping
	public String processSignup(SignupForm signupform) {
		memberRepo.save(signupform.toMember(passwordEncoder));
		return "redirect:/login";
	}
}