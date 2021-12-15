package io.cloud4u.acm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.cloud4u.lib.acm.interceptor.AuthInterceptor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class WebConfigure implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		WebMvcConfigurer.super.addInterceptors(registry);
		registry.addInterceptor(new AuthInterceptor())
				.addPathPatterns("/**")
				// TODO move exclude permission to nodejs. all access come from edge (nodejs)
				.excludePathPatterns(
						"/call/app/grant",
						"/call/user/signup",
						"/call/user/signup/verified",
						"/call/user/signin",
						"/call/validate/verify",
						"/call/password/reset",
						"/call/password/reset/verified",
						"/call/password/change");
		log.info("auth interceptor registered !");
	}

}
