package bicycle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import bicycle.entity.Member;
import bicycle.repository.BicycleViewRepository;
import bicycle.repository.ImageRepository;
import bicycle.repository.MemberRepository;
import bicycle.repository.RequestRepository;

// 사용자 페이지를 보여준다. 여기서 사용자가 등록한 자전거의 정보까지 모두 보여진다.
@Controller
@RequestMapping("/member")
public class MemberController {
	private final MemberRepository memberRepo;
	private final BicycleViewRepository bicycleViewRepo;
	private final RequestRepository requestRepo;
	private final ImageRepository imageRepo;
	
	@Autowired
	public MemberController(MemberRepository memberRepo, BicycleViewRepository bicycleViewRepo, 
			RequestRepository requestRepo, ImageRepository imageRepo) {
		this.memberRepo = memberRepo;
		this.bicycleViewRepo = bicycleViewRepo;
		this.requestRepo = requestRepo;
		this.imageRepo = imageRepo;
	}
	
	@GetMapping("/{username}")
	public String member(Model model, @AuthenticationPrincipal Member you, @PathVariable String username) throws Exception {
		model.addAttribute("you", you);
		
		var userOptional = this.memberRepo.findById(username);
		
		if (userOptional.isPresent()) {
			model.addAttribute("username", username);
			
			var user = userOptional.get();
			var user_bicycle_views = this.bicycleViewRepo.findByUsername(user.getUsername());
			
			model.addAttribute("bicycles", user_bicycle_views);
			
			for (var user_bicycle_view : user_bicycle_views) {
				var found_request = this.requestRepo.findById(user_bicycle_view.getReqid()).get();
				var bicycle_images = this.imageRepo.findByRequest(found_request);
				
				model.addAttribute(user_bicycle_view.getSerial(), bicycle_images);
			}
		
			return "member";
		}
		
		// 사용자를 못 찾으면 notfound 페이지로
		return "redirect:/notfound";
	}
}
