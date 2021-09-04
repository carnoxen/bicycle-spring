package bicycle.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import bicycle.entity.BicycleView;

public interface BicycleViewRepository extends CrudRepository<BicycleView, String> {
	public List<BicycleView> findByUsername(String username);
}
