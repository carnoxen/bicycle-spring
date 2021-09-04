package bicycle;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import bicycle.storage.StorageProperties;
import bicycle.storage.StorageService;

// 스프링 어플리케이션 시작점
@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class BicycleApplication {

	public static void main(String[] args) {
		SpringApplication.run(BicycleApplication.class, args);
	}

	// 실행하면서 이전에 저장된 모든 이미지 삭제
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

}
