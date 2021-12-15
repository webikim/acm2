package io.cloud4u.acm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.cloud4u.acm.service.ResourceService;
import io.cloud4u.acm.vo.ResourceVO;
import io.cloud4u.common.lib.define.ResultStatus;
import io.cloud4u.common.lib.vo.ResultVO;
import lombok.NonNull;

@Controller
@RequestMapping(value="/call/permission", produces=MediaType.APPLICATION_JSON_VALUE)
public class ResourceController {
	
	@Autowired
	private ResourceService service;
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public ResponseEntity<ResultVO<Boolean>> registerPermission(@RequestHeader Map<String, String> reqHeader, @RequestBody ResourceVO resource) {
		Boolean result = service.registerPermission(resource);
		HttpHeaders resHeader = new HttpHeaders();
		return new ResponseEntity<ResultVO<Boolean>>(
				new ResultVO<Boolean>()
				.setResultStatus(result ? ResultStatus.OK : ResultStatus.ERROR)
				.setResult(result), resHeader, HttpStatus.OK);
	}

	@RequestMapping(value="/get", method=RequestMethod.GET)
	public ResponseEntity<ResultVO<String>> getPermission(@NonNull ResourceVO resource) {
		String result = service.getPermission(resource);
		HttpHeaders resHeader = new HttpHeaders();
		return new ResponseEntity<ResultVO<String>>(
				new ResultVO<String>()
				.setResultStatus(ResultStatus.OK)
				.setResult(result), resHeader, HttpStatus.OK);
	}

	@RequestMapping(value="/update", method=RequestMethod.POST )
	public ResponseEntity<ResultVO<Boolean>> updatePermission(@NonNull ResourceVO resource) {
		Boolean result = service.updatePermission(resource);
		HttpHeaders resHeader = new HttpHeaders();
		return new ResponseEntity<ResultVO<Boolean>>(
				new ResultVO<Boolean>()
				.setResultStatus(result ? ResultStatus.OK : ResultStatus.ERROR)
				.setResult(result), resHeader, HttpStatus.OK);
	}
	
	@RequestMapping(value="/remove", method=RequestMethod.GET)
	public ResponseEntity<ResultVO<Boolean>> removePermission(@NonNull ResourceVO resource) {
		service.removePermission(resource);
		HttpHeaders resHeader = new HttpHeaders();
		return new ResponseEntity<ResultVO<Boolean>>(
				new ResultVO<Boolean>()
				.setResultStatus(ResultStatus.OK)
				.setResult(true), resHeader, HttpStatus.OK);
	}

}
