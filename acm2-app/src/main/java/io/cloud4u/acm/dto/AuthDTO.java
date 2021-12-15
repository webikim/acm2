package io.cloud4u.acm.dto;

import org.apache.ibatis.type.Alias;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Alias("authDto")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AuthDTO {
	String auth_id;
	String auth_name;
	String auth_status;
	Integer endpoint_ord;

	@Builder
	public AuthDTO(String auth_id, String auth_name, String auth_status, Integer endpoint_ord) {
		super();
		this.auth_id = auth_id;
		this.auth_name = auth_name;
		this.auth_status = auth_status;
		this.endpoint_ord = endpoint_ord;
	}

}
