package io.cloud4u.acm.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResetVO {
	String userId;
	String secret;
	String signature;

	@Builder
	public ResetVO(String userId, String secret, String signature) {
		super();
		this.userId = userId;
		this.secret = secret;
		this.signature = signature;
	}

}
