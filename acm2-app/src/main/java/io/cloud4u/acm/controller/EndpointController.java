package io.cloud4u.acm.controller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
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

import io.cloud4u.acm.dto.MethodDTO;
import io.cloud4u.acm.service.EndpointService;
import io.cloud4u.acm.vo.EndpointVO;
import io.cloud4u.common.lib.define.ResultStatus;
import io.cloud4u.common.lib.vo.ResultVO;

@Controller
@RequestMapping(value="/call", produces=MediaType.APPLICATION_JSON_VALUE)
public class EndpointController {
	
	@Autowired
	private EndpointService service;

	@RequestMapping(value="/endpoint/list", method=RequestMethod.GET)
	public ResponseEntity<ResultVO<List<EndpointVO>>> rollingKeys(@RequestHeader Map<String, String> reqHeader) {
		HttpHeaders resHeader = new HttpHeaders();
		return new ResponseEntity<ResultVO<List<EndpointVO>>>(
				new ResultVO<List<EndpointVO>>()
					.setResultStatus(ResultStatus.OK)
					.setResult(service.getEndpoints()), resHeader, HttpStatus.OK);
	}
	
	@RequestMapping(value="/method/save", method=RequestMethod.POST)
	public ResponseEntity<ResultVO<Integer>> saveMethod(@RequestHeader Map<String, String> reqHeader, @RequestBody MethodDTO method) {
		HttpHeaders resHeader = new HttpHeaders();
		ResultStatus status = ResultStatus.OK;
		Integer count = null;
		try {
			count = service.saveMethod(method);
		} catch (PersistenceException e) {
			status = ResultStatus.DUPLICATE;
		}
		return new ResponseEntity<ResultVO<Integer>>(
				new ResultVO<Integer>()
					.setResultStatus(status)
					.setResult(count), resHeader, HttpStatus.OK);
	}

}
