package io.cloud4u.lib.acm.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cloud4u.common.lib.define.ResultStatus;
import io.cloud4u.common.lib.vo.ResultVO;
import io.cloud4u.common.spring.util.BeanUtil;
import io.cloud4u.common.spring.util.NetUtil;
import io.cloud4u.lib.acm.define.ClaimName;
import io.cloud4u.lib.acm.define.HeaderName;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		AccessValidator validator = (AccessValidator) BeanUtil.getBean(AccessValidator.class);
		String headerToken = request.getHeader(HeaderName.AUTHORIZATION);
		Map<String, Object> claims = validator.validateToken(headerToken);
		log.debug("ip : {}, claims : {}", NetUtil.getIp(request), claims);

		if (claims != null) {
			if (NetUtil.getIp(request).equals((String) claims.get(ClaimName.IP))) {
				// TODO checking url has moved to node.js
//				String requestUri = request.getRequestURI().replaceFirst(request.getContextPath(), "");
//				System.out.println("......................................");
//				System.out.println("request uri = " + requestUri);
//				System.out.println("context path = " + request.getContextPath());
//				System.out.println("......................................");
				log.debug("...... passed auth interceptor ......");
				return HandlerInterceptor.super.preHandle(request, response, handler);
			}
		}
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.getWriter().write(new ObjectMapper().writeValueAsString(new ResultVO<Object>().setResultStatus(ResultStatus.UNAUTHORIZED)));
		response.getWriter().flush();
		response.getWriter().close();
		return false;
	}

}
