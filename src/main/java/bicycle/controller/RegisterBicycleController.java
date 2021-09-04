package bicycle.controller;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import bicycle.entity.Member;
import bicycle.repository.ImageRepository;
import bicycle.repository.RequestRepository;
import bicycle.security.RegisterBicycleForm;
import bicycle.storage.StorageService;

// 자전거 등록 페이지
@Controller
@RequestMapping("/register_bicycle")
public class RegisterBicycleController {
	private final RequestRepository requestRepo;
	private final ImageRepository imageRepo;
	private final StorageService storageService;
	
	@Autowired
	public RegisterBicycleController(RequestRepository requestRepo, 
			ImageRepository imageRepo, StorageService storageService) {
		this.requestRepo = requestRepo;
		this.imageRepo = imageRepo;
		this.storageService = storageService;
	}
	
	@GetMapping
	public String register_bicycle(Model model, @AuthenticationPrincipal Member you) {
		model.addAttribute("you", you);
		
		return "register_bicycle";
	}
	
	// 번호 등록 체계: https://www.law.go.kr/법령/자전거이용활성화에관한법률시행규칙 참조
	// 입력된 정보를 토대로 데이터베이스에 저장
	// 이미지는 파일 이름만 데이터베이스에 저장, 파일 자체는 시스템으로 보관.
	@PostMapping
	public String processRegisterBicycle(@AuthenticationPrincipal Member you, 
			RegisterBicycleForm registerBicycleForm, SessionStatus sessionStatus) throws Exception {
		var date_format = new SimpleDateFormat("yyyyMMdd");
		var today = new Date();
		
		var your_request_count_on_a_day = this.requestRepo.countByCreatedAtAndMember(today, you);
		
		var prefix = date_format.format(today); // 광역 + 기초 접두사
		var suffix = your_request_count_on_a_day + 1; // 해당 지역에 등록된 자전거 수 + 1
		
		if (suffix > 3) {
			// TODO
		}
		
		var new_request = registerBicycleForm.toRequest(you.getUsername() + prefix + suffix);
		new_request.setMember(you);
		this.requestRepo.save(new_request);
		
		for (var image : registerBicycleForm.getImages()) {
			var new_uuid = UUID.randomUUID(); // 각 이미지마다 무작위의 uuid 부여
			
			while(this.imageRepo.findById(new_uuid).isPresent()) {
				new_uuid = UUID.randomUUID();
			}
			
			this.storageService.storeWithPath(image, Paths.get(new_uuid + ".png"));
			
			var new_image = registerBicycleForm.toImage(new_uuid);
			new_image.setRequest(new_request);
			this.imageRepo.save(new_image);
		}
		
		sessionStatus.setComplete();
		
		return "redirect:/request";
	}
}
