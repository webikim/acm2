package io.cloud4u.acm.define;

import lombok.Getter;

@Getter
public enum ResetStatus {
	ACTIVE('A'), EXPIRED('E');

	final private char status;

	private ResetStatus(char status) {
		this.status = status;
	}

}
