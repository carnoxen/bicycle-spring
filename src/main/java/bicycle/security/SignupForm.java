package bicycle.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import bicycle.entity.Member;
import lombok.Data;

// 입력을 받은 사용자 정보의 구조
@Data
public class SignupForm {
	private String username;
	private String password; // 인코딩된 채로 저장
	private String email;
	private String state;
	private String county;
	
	public Member toMember(PasswordEncoder passwordEncoder) {
		return new Member(username, passwordEncoder.encode(password), email, state.charAt(0), Integer.parseInt(county));
	}
}
