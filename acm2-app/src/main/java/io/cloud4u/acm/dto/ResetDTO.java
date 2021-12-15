package io.cloud4u.acm.dto;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Alias("resetDto")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResetDTO {
	String user_id;
	String reset_secret;
	String reset_halftoken;
	Date reset_expireDate;
	String reset_ip;
	char reset_status;

	@Builder
	public ResetDTO(String user_id, String reset_secret, String reset_halftoken, Date reset_expireDate, String reset_ip,
			char reset_status) {
		super();
		this.user_id = user_id;
		this.reset_secret = reset_secret;
		this.reset_halftoken = reset_halftoken;
		this.reset_expireDate = reset_expireDate;
		this.reset_ip = reset_ip;
		this.reset_status = reset_status;
	}

}
