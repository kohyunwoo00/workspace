package com.kh.spring.exception.controller;

import java.security.InvalidParameterException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHandlingController {
	private ModelAndView createErrorResponse(String errorMsg, Exception e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", errorMsg).setViewName("include/error_page");
		log.info("예외발생 : {} ", errorMsg, e);
		return mv;
	}
/*	
	@ExceptionHandler(TooLargeValueException.class)
	protected ModelAndView tooLargeValueError(InvalidParameterException e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", "아이디가 너무 길어").setViewName("include/error_page");
		return mv;
	}

	@ExceptionHandler(TooLargeValueException.class)
	protected ModelAndView tooLargeValueError(TooLargeValueException e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("message",e.getMessage()).setViewName("include/error_page");
		return mv;
	}
	*/
}
