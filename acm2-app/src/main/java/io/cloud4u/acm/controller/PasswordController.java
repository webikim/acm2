package io.cloud4u.acm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.cloud4u.acm.service.PasswordService;
import io.cloud4u.acm.vo.PasswordVO;
import io.cloud4u.acm.vo.UserVO;
import io.cloud4u.common.lib.define.ResultStatus;
import io.cloud4u.common.lib.vo.ResultVO;

@Controller
@RequestMapping(value="/call/password", produces=MediaType.APPLICATION_JSON_VALUE)
public class PasswordController {

	@Autowired
	private PasswordService service;
	
	@RequestMapping(value="/change", method=RequestMethod.POST)
//	public ResponseEntity<ResultVO<Boolean>> changePassword(@RequestHeader Map<String, String> reqHeader, @RequestBody PasswordVO passwordVo) {		// application/json
	public ResponseEntity<ResultVO<Boolean>> changePassword(@RequestHeader Map<String, String> reqHeader, @RequestParam Map<String, String> param) {	// application/x-www-form-urlencoded
		PasswordVO passwordVo = PasswordVO.builder()
				.v(param.get("v"))
				.s(param.get("s"))
				.oldPassword(param.get("oldPassword"))
				.newPassword(param.get("newPassword")).build();
		HttpHeaders resHeader = new HttpHeaders();
		return new ResponseEntity<ResultVO<Boolean>>(
				new ResultVO<Boolean>()
				.setResultStatus(service.changePassword(passwordVo) ? ResultStatus.OK : ResultStatus.ERROR)
				.setResult(true), resHeader, HttpStatus.OK);
	}
	
	@RequestMapping(value="/reset", method=RequestMethod.POST)
	public ResponseEntity<ResultVO<Boolean>> resetRequest(@RequestHeader Map<String, String> reqHeader, @RequestBody UserVO user) {
		HttpHeaders resHeader = new HttpHeaders();
		boolean result = service.resetRequest(user.getUserid());
		return new ResponseEntity<ResultVO<Boolean>>(
				new ResultVO<Boolean>()
				.setResultStatus(result ? ResultStatus.OK : ResultStatus.ERROR)
				.setResult(true), resHeader, HttpStatus.OK);
	}
	
	@RequestMapping(value="/reset/verified")
	public String resetVerified(@RequestParam("c") char type, @RequestParam("v") String valid_id, @RequestParam("s") String signature, Model model) {
		model.addAttribute("link", "../password/change");
		model.addAttribute("v", valid_id);
		model.addAttribute("s", signature);
		return "validated/passwordreset";
	}
	
}
