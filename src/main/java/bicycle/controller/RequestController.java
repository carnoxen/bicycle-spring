package bicycle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import bicycle.entity.Member;
import bicycle.repository.ImageRepository;
import bicycle.repository.RequestRepository;

@Controller
@RequestMapping("/request")
public class RequestController {

	private final RequestRepository requestRepo;
	private final ImageRepository imageRepo;
	
	@Autowired
	public RequestController(RequestRepository requestRepo, ImageRepository imageRepo) {
		this.requestRepo = requestRepo;
		this.imageRepo = imageRepo;
	}
	
	@GetMapping
	public String member(Model model, @AuthenticationPrincipal Member you) throws Exception {
		model.addAttribute("you", you);
		
		var your_requests = this.requestRepo.findByMember(you);
		
		model.addAttribute("requests", your_requests);
		
		for (var request : your_requests) {
			var bicycle_images = this.imageRepo.findByRequest(request);
			
			model.addAttribute(request.getReqid(), bicycle_images);
		}
	
		return "request";
	}
}
