package io.cloud4u.acm.dto;

import org.apache.ibatis.type.Alias;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Alias("authEndpointDto")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AuthEndpointDTO {
	String auth_id;
	Integer endpoint_ord;

	@Builder
	public AuthEndpointDTO(String auth_id, Integer endpoint_ord) {
		super();
		this.auth_id = auth_id;
		this.endpoint_ord = endpoint_ord;
	}

}
