package io.cloud4u.acm.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.cloud4u.acm.exception.NotValidException;
import io.cloud4u.acm.service.ValidationService;

@Controller
@RequestMapping(value="/call/validate", produces=MediaType.APPLICATION_JSON_VALUE)
public class ValidationController {

	@Autowired
	private ValidationService service;
	
	@RequestMapping(value="/verify", method=RequestMethod.GET)
    public void verify(HttpServletRequest request, HttpServletResponse response, 
    		@RequestParam("c") char type, @RequestParam("v") String valid_id, @RequestParam("s") String signature) throws NotValidException {
		String forwardUrl = service.receiveValidation(type, valid_id, signature);
		System.out.println(forwardUrl);
		RequestDispatcher dispatcher = request.getRequestDispatcher(forwardUrl);
		try {
			dispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
