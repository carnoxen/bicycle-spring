package bicycle.controller;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bicycle.storage.StorageFileNotFoundException;
import bicycle.storage.StorageService;

// 이미지 표시 페이지
// url 형식: 루트 주소/upload/A01000001/123456(...)234.png
@RestController
@RequestMapping("/upload")
public class UploadController {
	private final StorageService storageService;

	@Autowired
	public UploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	// 해당 url로 들어가면 이미지 표시
	@GetMapping("/{filename:.+}")
	public ResponseEntity<Resource> loadImage(@PathVariable String filename) throws Exception {
		
		var uploadedPath = Paths.get(filename).toString();
		var file = storageService.loadAsResource(uploadedPath);
		
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_PNG)
				.body(file);
		
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}
}
