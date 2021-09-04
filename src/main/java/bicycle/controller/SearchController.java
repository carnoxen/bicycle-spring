package bicycle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bicycle.repository.BicycleRepository;

// 검색어 처리 시스템
@Controller
@RequestMapping("/search")
public class SearchController {
	private final BicycleRepository bicycleRepo;
	
	@Autowired
	public SearchController(BicycleRepository bicycleRepo) {
		this.bicycleRepo = bicycleRepo;
	}
	
	@GetMapping
	public String search(@RequestParam String serial) {
		var bicycle = this.bicycleRepo.findById(serial);
		if (bicycle.isPresent()) {
			return "redirect:/member/" + bicycle.get()
				.getRequest()
				.getMember()
				.getUsername();
		}

		return "redirect:/notfound";
	}
}
