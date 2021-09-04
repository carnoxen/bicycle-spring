package bicycle.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import bicycle.entity.Image;
import bicycle.entity.Request;

public interface ImageRepository extends CrudRepository<Image, UUID> {
	public List<Image> findByRequest(Request request);
}
