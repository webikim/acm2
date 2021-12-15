package io.cloud4u.acm.config.property;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
@ConfigurationProperties("cloud4u.token")
@Getter
@Setter
@ToString
public class TokenConfigure {
	public final static String TOKEN_DAY = "day";
	public final static String TOKEN_HOUR = "hour";
	public final static String TOKEN_MIN = "min";

	Map<String, Integer> expire;
	String subject;
	String issuer;

	// default values
	@PostConstruct
	private void init() {
		if (expire == null) {
			expire = new HashMap<String, Integer>();
			expire.put(TOKEN_DAY, 1);
		}
	}
}
