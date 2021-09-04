package bicycle.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Immutable;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Immutable
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
@RequiredArgsConstructor
public class BicycleView {
	@Id
	private final String serial;
	
	private final Date registeredAt;
	private final Character kind;
	private final Boolean lost;
	
	private final String reqid;
	private final String username;
}
