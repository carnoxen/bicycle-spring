package bicycle.storage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

// 이미지 저장 서비스
// 파일 저장 장소: ./upload/<자전거 번호>/<uuid>.png
@Service
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;

	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	// 받은 이미지를 png로 저장
	@Override
	public void storeWithPath(MultipartFile file, Path path) {
		// TODO Auto-generated method stub
		
		try {
			
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file.");
			}
			
			var destinationFile = this.rootLocation
					.resolve(path).normalize().toAbsolutePath();
			Files.createDirectories(destinationFile.getParent());
			
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				// This is a security check
				throw new StorageException("Cannot store file outside current directory.");
			}
			
			var bufferedImage = ImageIO.read(file.getInputStream());
			ImageIO.write(bufferedImage, "png", destinationFile.toFile());
			
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
		
	}

	// 업로드된 파일 로드
	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	// 파일을 리소스로 변환
	@Override
	public Resource loadAsResource(String filename) {
		
		try {
			var file = load(filename);
			var resource = new UrlResource(file.toUri());
			
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			
			throw new MalformedURLException();
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
		
	}

	// upload 폴더 제거
	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	// upload 폴더 생성
	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
}
