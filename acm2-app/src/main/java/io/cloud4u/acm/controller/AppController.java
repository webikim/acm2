package io.cloud4u.acm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.cloud4u.acm.service.AppService;
import io.cloud4u.acm.vo.GrantVO;
import io.cloud4u.common.lib.define.ResultStatus;
import io.cloud4u.common.lib.vo.ResultVO;
import io.cloud4u.common.spring.util.NetUtil;
import io.cloud4u.lib.acm.define.HeaderName;
import io.cloud4u.lib.acm.exception.UnauthorizedException;

@Controller
@RequestMapping(value = "/call", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppController {

	@Autowired
	private AppService service;

	@RequestMapping(value = "/app/grant", method = RequestMethod.GET)
	public ResponseEntity<ResultVO<GrantVO>> grantApp(@RequestHeader Map<String, String> reqHeader, String aid) {
		HttpHeaders resHeader = new HttpHeaders();
		try {
			return new ResponseEntity<ResultVO<GrantVO>>(
					new ResultVO<GrantVO>().setResultStatus(ResultStatus.OK)
							.setResult(service.grantApp(reqHeader.get(HeaderName.AUTHORIZATION), aid, NetUtil.getIp())),
					resHeader, HttpStatus.OK);
		} catch (UnauthorizedException e) {
			return new ResponseEntity<ResultVO<GrantVO>>(
					new ResultVO<GrantVO>().setResultStatus(ResultStatus.UNAUTHORIZED).setResult(null), resHeader,
					HttpStatus.UNAUTHORIZED);
		}
	}
}
