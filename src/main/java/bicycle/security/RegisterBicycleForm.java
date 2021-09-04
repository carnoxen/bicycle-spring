package bicycle.security;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import bicycle.entity.Image;
import bicycle.entity.Request;
import lombok.Data;

// 입력을 받은 자전거 정보의 구조
@Data
public class RegisterBicycleForm {
	private String kind;
	private List<MultipartFile> images;
	
	public Request toRequest(String request_id) {
		return new Request(request_id, kind.charAt(0), 0);
	}
	
	public Image toImage(UUID uuid) {
		return new Image(uuid);
	}
}
