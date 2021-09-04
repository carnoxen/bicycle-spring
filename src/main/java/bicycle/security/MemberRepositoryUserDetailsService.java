package bicycle.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import bicycle.repository.MemberRepository;

// username에 해당하는 사용자 검색 서비스
@Service
public class MemberRepositoryUserDetailsService implements UserDetailsService {
	private MemberRepository memberRepo;
	
	@Autowired
	public MemberRepositoryUserDetailsService(MemberRepository memberRepo) {
		this.memberRepo = memberRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		var member = memberRepo.findById(username);
		
		if (member.isPresent()) {
			return member.get();
		}
		
		throw new UsernameNotFoundException("User '" + username + "' not found");
	}

}
