package bicycle.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import bicycle.entity.Member;

@Controller
public class HomeController {
	// 홈페이지로 넘어감
	@GetMapping("/")
	public String home(Model model, @AuthenticationPrincipal Member you) {
		model.addAttribute("you", you);
		return "home";
	}
	
	// 사용자를 찾을 수 없을 때 리다이렉트
	@GetMapping("/notfound")
	public String notfound(Model model, @AuthenticationPrincipal Member you) {
		model.addAttribute("you", you);
		return "404";
	}
}
