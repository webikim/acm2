package io.cloud4u.acm.exception;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(annotations = Controller.class)
public class ACMExceptionHandler {
	
	@ExceptionHandler(NotValidException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
//	public String handleError(Exception ex, HttpServletRequest request, Model model) {
	public String handleError(Exception ex, HttpServletRequest request) {
		System.out.println("..................");
//		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//		String statusMsg = status.toString();
//		HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(statusMsg));
//		model.addAttribute("msg", statusMsg + " " + httpStatus.getReasonPhrase());
		return "error/error";
	}

//	@Override
//	public String getErrorPath() {
//		return "/error";
//	}
	
}
