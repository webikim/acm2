package io.cloud4u.acm.define;

import lombok.Getter;

@Getter
public enum ValidType {
	SIGNUP('S', "signup", "/call/user/signup/verified"), RESET('R', "passwordreset", "/call/password/reset/verified");
	
	private char type;
	private String template;
	private String forward;

	ValidType(char type, String template, String forward) {
		this.type = type;
		this.template = template;
		this.forward = forward;
	}

	public final static ValidType getValidType(char type) {
		switch (type) {
		case 'S':
			return SIGNUP;
		case 'R':
			return RESET;
		default:
			return null;
		}
	}
}
