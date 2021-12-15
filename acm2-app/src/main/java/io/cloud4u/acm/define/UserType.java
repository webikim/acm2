package io.cloud4u.acm.define;

public enum UserType {
	EMAIL('E'), SNS('s');
	
	private char type;

	private UserType(char type) {
		this.type = type;
	}

	public char getType() {
		return type;
	}
	
}
