package com.jbk.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler 
{
	
	    // @Vaild madil checking ethe hote ahe.
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
	public Map<String, Object> methodArgumentNotValid(MethodArgumentNotValidException ex) 
	{
		Map<String, Object> map = new HashMap<>();
		
		List<FieldError> list = ex.getFieldErrors();
		
		for(FieldError ele:list)
		{
			map.put(ele.getField(), ele.getDefaultMessage());
		}
		
		return map;
	}
	
}
