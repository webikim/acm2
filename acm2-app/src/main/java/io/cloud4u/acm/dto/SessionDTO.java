package io.cloud4u.acm.dto;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Alias("sessionDto")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SessionDTO {
	String ses_id;
	String user_id;
	String ses_ip;
	String ses_detail;
	Date ses_expire;

	@Builder
	public SessionDTO(String ses_id, String user_id, String ses_ip, String ses_detail, Date ses_expire) {
		super();
		this.ses_id = ses_id;
		this.user_id = user_id;
		this.ses_ip = ses_ip;
		this.ses_detail = ses_detail;
		this.ses_expire = ses_expire;
	}

}
