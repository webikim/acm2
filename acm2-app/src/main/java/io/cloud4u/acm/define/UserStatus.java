package io.cloud4u.acm.define;

import lombok.Getter;

@Getter
public enum UserStatus {
	DISABLED('D'), ENABLED('A'), PENDING('P'), LOCKED('L'), DELETED('R');

	final private char status;

	private UserStatus(char status) {
		this.status = status;
	}

}
