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
public class PasswordVO {
	String v;
	String s;
	String oldPassword;
	String newPassword;
	
	@Builder
	public PasswordVO(String v, String s, String oldPassword, String newPassword) {
		super();
		this.v = v;
		this.s = s;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}
	
}
