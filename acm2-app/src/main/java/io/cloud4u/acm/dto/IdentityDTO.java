package io.cloud4u.acm.dto;

import org.apache.ibatis.type.Alias;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Alias("identityDto")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class IdentityDTO {
	String iden_id;
	String key_id;
	String iden_status;
	String auth_id;

	@Builder
	public IdentityDTO(String iden_id, String key_id, String iden_status, String auth_id) {
		super();
		this.iden_id = iden_id;
		this.key_id = key_id;
		this.iden_status = iden_status;
		this.auth_id = auth_id;
	}

}
