package bicycle.repository;

import org.springframework.data.repository.CrudRepository;

import bicycle.entity.Bicycle;

public interface BicycleRepository extends CrudRepository<Bicycle, String>{
	public Long countBySerialStartsWith(String prefix);
}
