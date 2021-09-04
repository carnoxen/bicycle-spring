package bicycle.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import bicycle.entity.Member;
import bicycle.entity.Request;

public interface RequestRepository extends CrudRepository<Request, String> {
	public List<Request> findByMember(Member member);
	public Long countByCreatedAtAndMember(Date date, Member member);
}
