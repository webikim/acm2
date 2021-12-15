package io.cloud4u.lib.acm.define;
//
//import lombok.Getter;
//
//@Getter
//public enum HeaderKey {
//	AUTH_TOKEN_HEADER_KEY("X-VISCAt-Auth-Token"),
//	ACCESS_TOKEN_HEADER_KEY("X-VISCAt-Access-Token"),
//	SYSTEM_TOKEN_HEADER_KEY("X-VISCAt-System-Token"),
//	APP_ID_HEADER_KEY("X-VISCAt-Client-Id"),
//	APP_SECRET_HEADER_KEY("X-VISCAt-Client-Secret"),
//	AUTHORIZATION_KEY("authorization");
//	
//	private final String key;
//
//	HeaderKey(String key) {
//		this.key = key;
//	}
//}

public class HeaderName {
	public final static String AUTHORIZATION = "authorization";
	public final static String BEARER = "Bearer";
}