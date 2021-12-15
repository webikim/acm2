package io.cloud4u.acm.dto;

import org.apache.ibatis.type.Alias;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Alias("userDto")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDTO {
	String user_id;
	String iden_id;
	String user_name;
	String user_email;
	String user_password;
	char user_type;
	Integer user_loginfail;
	char user_twopass;
	String user_secret;
	char user_status;
	String key_id;
	String soc_id;
	String soc_name;

	@Builder
	public UserDTO(String user_id, String iden_id, String user_name, String user_email, String user_password,
			char user_type, Integer user_loginfail, char user_twopass, String user_secret, char user_status,
			String key_id, String soc_id, String soc_name) {
		super();
		this.user_id = user_id;
		this.iden_id = iden_id;
		this.user_name = user_name;
		this.user_email = user_email;
		this.user_password = user_password;
		this.user_type = user_type;
		this.user_loginfail = user_loginfail;
		this.user_twopass = user_twopass;
		this.user_secret = user_secret;
		this.user_status = user_status;
		this.key_id = key_id;
		this.soc_id = soc_id;
		this.soc_name = soc_name;
	}

}
