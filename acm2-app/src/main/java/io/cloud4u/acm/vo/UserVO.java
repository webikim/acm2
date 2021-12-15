package io.cloud4u.acm.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserVO {
	String userid;
	String email;
	String password;
	char twopass;
	String secret;
	String ip;
	String session;

	@Builder
	public UserVO(String userid, String email, String password, char twopass, String secret, String ip,
			String session) {
		super();
		this.userid = userid;
		this.email = email;
		this.password = password;
		this.twopass = twopass;
		this.secret = secret;
		this.ip = ip;
		this.session = session;
	}

}
