package io.cloud4u.acm.config.property;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import io.cloud4u.common.lib.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
@ConfigurationProperties("cloud4u.acm")
@Getter
@Setter
@ToString
public class AcmConfigure {
	String basePackage;
	Integer keyLimit;
	String charSet;
	String aid;
	String token;
	String verify;
	Long resetExpire;
	Long signUpExpire;
	
	// default values
	@PostConstruct
	private void init() {
		if (keyLimit == null)
			keyLimit = 100;
		if (charSet == null)
			charSet = "UTF-8";
		if (resetExpire == null)
			resetExpire = Long.valueOf(DateUtil.HOUR);
		if (signUpExpire == null)
			signUpExpire = Long.valueOf(DateUtil.DAY);
	}
}
