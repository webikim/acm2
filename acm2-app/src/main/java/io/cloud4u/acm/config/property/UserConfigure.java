package io.cloud4u.acm.config.property;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import io.cloud4u.common.lib.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties("cloud4u.session")
@Getter
@Setter
public class UserConfigure {
	Boolean signup_verify;
	Boolean session_enable;
	Long session_time;
	
	// default values
	@PostConstruct
	private void init() {
		if (signup_verify == null)
			signup_verify = true;
		if (session_enable == null) 
			session_enable = true;
		if (session_time == null)
			session_time = DateUtil.HOUR;
	}
}
