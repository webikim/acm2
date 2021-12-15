package io.cloud4u.acm.vo;

import io.cloud4u.acm.dto.AppDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AppVO {
	String aid;
	String kid;

	@Builder
	public AppVO(String aid, String kid) {
		super();
		this.aid = aid;
		this.kid = kid;
	}

	public AppDTO toDTO() {
		return AppDTO.builder().app_id(aid).key_id(kid).build();
	}
}
