package io.cloud4u.acm.vo;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AuthVO {
	String auth_id;
	String auth_name;
	List<Integer> ords;

	@Builder
	public AuthVO(String auth_id, String auth_name, List<Integer> ords) {
		super();
		this.auth_id = auth_id;
		this.auth_name = auth_name;
		this.ords = ords;
	}

}
