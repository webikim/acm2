package io.cloud4u.acm.define;

public enum KeyType {
	IDENTITY_KEY('I'), OTHER_KEY('O');
	
	char keyType;
	
	private KeyType(char type) {
		this.keyType = type;
	}
	
	public char value() {
		return this.keyType;
	}
}
