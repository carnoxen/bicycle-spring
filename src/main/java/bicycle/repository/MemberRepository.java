package bicycle.repository;

import org.springframework.data.repository.CrudRepository;

import bicycle.entity.Member;

public interface MemberRepository extends CrudRepository<Member, String> {}
