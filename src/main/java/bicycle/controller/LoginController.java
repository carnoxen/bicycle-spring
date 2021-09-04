package bicycle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// 로그인 페이지로 넘어간다
@Controller
@RequestMapping("/login")
public class LoginController {
	@GetMapping
	public String login() {
		return "login";
	}
}
