package io.cloud4u.acm.controller;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import io.cloud4u.common.lib.define.ResultStatus;
import io.cloud4u.common.lib.vo.ResultVO;
import io.cloud4u.lib.acm.service.ServiceService;

@Controller
@RequestMapping(value="/call/service", produces=MediaType.APPLICATION_JSON_VALUE)
public class ServiceController {

	@Autowired
	private ServiceService service;
	
	@RequestMapping(value="/scanned/list")
	public ResponseEntity<ResultVO<Set<String>>> serviceScanned(@RequestHeader Map<String, String> reqHeader) {
		HttpHeaders resHeader = new HttpHeaders();
		return new ResponseEntity<ResultVO<Set<String>>>(
				new ResultVO<Set<String>>()
					.setResultStatus(ResultStatus.OK)
					.setResult(service.getScannedUris()), resHeader, HttpStatus.OK);
	}
}
