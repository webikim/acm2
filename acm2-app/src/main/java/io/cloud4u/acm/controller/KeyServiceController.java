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

import io.cloud4u.acm.service.KeyService;
import io.cloud4u.common.lib.define.ResultStatus;
import io.cloud4u.common.lib.vo.ResultVO;
import net.minidev.json.JSONObject;

@Controller
@RequestMapping(value="/call/key", produces=MediaType.APPLICATION_JSON_VALUE)
public class KeyServiceController {
	
	@Autowired
	private KeyService service;

	@RequestMapping(value="/rollingkeys", method=RequestMethod.GET)
	public ResponseEntity<ResultVO<Map<String, JSONObject>>> rollingKeys(@RequestHeader Map<String, String> reqHeader) {
		HttpHeaders resHeader = new HttpHeaders();
		return new ResponseEntity<ResultVO<Map<String, JSONObject>>>(
				new ResultVO<Map<String, JSONObject>>()
					.setResult(service.rollingKeys())
					.setResultStatus(ResultStatus.OK), resHeader, HttpStatus.OK);
	}
}
