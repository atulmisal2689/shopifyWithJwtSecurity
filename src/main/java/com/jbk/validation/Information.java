package com.jbk.validation;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Negative;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.URL;

public class Information
{
	  // Validation Annotations for int Field
	  
	@Min(value = 1, message = "Value must be at least 1")
	@Max(value = 100, message = "Value must not exceed 100")
	private int number;

	@Positive(message = "Value must be positive")
	private int positiveNumber;

	@Negative(message = "Value must be negative")
	private int negativeNumber;

	@Digits(integer = 3, fraction = 0, message = "Value must have exactly 3 digits")
	private int numberWithExactDigits;
	
	
	
	               // Validation Annotations for String Field
	
	@Size(min = 2, max = 50, message = "String length must be between 2 and 50 characters")
	private String text1;
	
	@NotBlank(message = "Value cannot be blank or null")
	private String text2;
	
	
	@NotEmpty(message = "Value cannot be empty or null")
	private String text3;
	
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Invalid characters in the string")
	private String alphanumericText;
	
	@Email(message = "Invalid email address")
	private String email;
	
	
	@URL(message = "Invalid URL")
	private String url;







}
