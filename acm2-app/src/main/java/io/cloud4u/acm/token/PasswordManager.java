package io.cloud4u.acm.token;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class PasswordManager {

	public String encryptPassword(String str) {
		return DigestUtils.sha1Hex(str);
	}
	
	public boolean comparePassword(String str1, String str2) {
		return str1.contentEquals(str2);
	}

}
