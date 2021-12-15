package io.cloud4u.acm.dto;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Alias("validateDto")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ValidateDTO {
	String valid_id;
	String user_id;
	char valid_type;
	String valid_secret;
	String valid_route;
	Date valid_expire;
	
	@Builder
	public ValidateDTO(String valid_id, String user_id, char valid_type, String valid_secret, String valid_route,
			Date valid_expire) {
		super();
		this.valid_id = valid_id;
		this.user_id = user_id;
		this.valid_type = valid_type;
		this.valid_secret = valid_secret;
		this.valid_route = valid_route;
		this.valid_expire = valid_expire;
	}

}
