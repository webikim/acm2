package io.cloud4u.acm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import io.cloud4u.acm.exception.AlreadyExistException;
import io.cloud4u.acm.exception.InvalidUserException;
import io.cloud4u.acm.exception.NotEnabledException;
import io.cloud4u.acm.service.SignService;
import io.cloud4u.acm.vo.UserVO;
import io.cloud4u.common.lib.define.ResultStatus;
import io.cloud4u.common.lib.vo.ResultVO;
import io.cloud4u.common.spring.util.NetUtil;

@RestController
@RequestMapping(value="/call/user", produces=MediaType.APPLICATION_JSON_VALUE)
public class SignController {

	@Autowired
	private SignService service;
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public ResponseEntity<ResultVO<Boolean>> singup(@RequestHeader Map<String, String> reqHeader, @RequestBody UserVO user) {
		HttpHeaders resHeader = new HttpHeaders();
//		UserVO updatedUser;
		ResultStatus status;
		try {
//			updatedUser = service.signup(user);
			service.signup(user);
			status = ResultStatus.OK;
		} catch (AlreadyExistException e) {
//			updatedUser = user;
			status = ResultStatus.DUPLICATE;
		} catch (InvalidUserException e) {
//			updatedUser = user;
			status = ResultStatus.ERROR;
		}
		return new ResponseEntity<ResultVO<Boolean>>(
				new ResultVO<Boolean>()
				.setResultStatus(status)
				.setResult(status.equals(ResultStatus.OK)), resHeader, HttpStatus.OK);
	}
	
	@RequestMapping(value="/signup/verified")
	public RedirectView signupVerified(@RequestParam("c") char type, @RequestParam("v") String valid_id, @RequestParam("s") String signature) {
		boolean result = service.signupVerified(valid_id, signature);
		System.out.println("....... signup verified ......");
		// TODO error redirect
		return new RedirectView("/");
	}
	
	@RequestMapping(value="/signin")
	public ResponseEntity<ResultVO<String>> signin(@RequestHeader Map<String, String> reqHeader, UserVO user) {
		HttpHeaders resHeader = new HttpHeaders();
		ResultStatus status;
//		resHeader.setLocation(new URI(""));
//		resHeader.set(headerName, headerValue);
//		System.out.println(Arrays.deepToString(reqHeader.keySet().toArray()));
		user.setIp(NetUtil.getIp());
		try {
			return new ResponseEntity<ResultVO<String>>(
				new ResultVO<String>()
				.setResultStatus(ResultStatus.OK)
				.setResult(service.signin(user)), resHeader, HttpStatus.OK);
		} catch (NotEnabledException e) {
			status = ResultStatus.NOT_ENABLED;
		} catch (InvalidUserException e) {
			status = ResultStatus.INVALID_DATA;
		} catch (AlreadyExistException e) {
			status = ResultStatus.ANOTHER_LOGIN;
		}
		return new ResponseEntity<ResultVO<String>>(
				new ResultVO<String>()
				.setResultStatus(status)
				.setResult(""), resHeader, HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ResponseEntity<String> test() {
		return new ResponseEntity<String>("test", HttpStatus.OK);
	}
}
