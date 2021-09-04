package bicycle.storage;

import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	void init();
	
	public void storeWithPath(MultipartFile file, Path path);

	Path load(String filename);

	Resource loadAsResource(String filename);

	void deleteAll();

}
