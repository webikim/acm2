package io.cloud4u.acm.dto;

import org.apache.ibatis.type.Alias;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Alias("identityAuthDto")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class IdentityAuthDTO {
	String iden_id;
	String auth_name;

	@Builder
	public IdentityAuthDTO(String iden_id, String auth_name) {
		super();
		this.iden_id = iden_id;
		this.auth_name = auth_name;
	}

}
