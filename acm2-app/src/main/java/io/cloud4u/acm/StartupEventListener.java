package io.cloud4u.acm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import io.cloud4u.acm.config.property.AcmConfigure;
import io.cloud4u.acm.service.AppService;
import io.cloud4u.acm.vo.GrantVO;
import io.cloud4u.common.spring.util.URLscanner;
import io.cloud4u.lib.acm.exception.UnauthorizedException;
import io.cloud4u.lib.acm.interceptor.AccessValidator;

@Component
public class StartupEventListener implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private AcmConfigure config;
	
	@Autowired
	private AppService service;

	@Autowired
	private URLscanner scanner;

	@Autowired
	private AccessValidator validator;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		scanner.loadtUris(event.getApplicationContext());
		try {
			GrantVO vo = service.grantApp(config.getToken(), config.getAid(), "127.0.0.1");
			validator.setKeystore(vo.getKeys());
		} catch (UnauthorizedException e) {
			e.printStackTrace();
		}
	}

}
