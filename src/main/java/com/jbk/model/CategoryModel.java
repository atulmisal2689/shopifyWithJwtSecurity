package com.jbk.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryModel 
{
	@Min(value = 1, message = "Category ID must be at least 1")
	private Long categoryId;
	
	@NotBlank(message = "Category Name is required")
	@NotEmpty(message = "Category Name is required")
	@Pattern(regexp = "^[a-zA-Z ]+[a-zA-Z0-9]*$", message = "CategoryName Invalid")
	private String categoryName;
	
	@NotBlank(message = "Category description is required")
	private String discription;
	
	@PositiveOrZero(message = "Discount must be a positive number or zero")
	@Max(value = 100, message = "Discount must be less than or equal to 100")
	private int discount;
	
	@PositiveOrZero(message = "Discount must be a positive number or zero")
	@Max(value = 100, message = "Discount must be less than or equal to 100")  
	private int gst;
	
	@PositiveOrZero(message = "Delivery Charge must be a positive number or zero")
	private float deliveryCharge;
	

}
