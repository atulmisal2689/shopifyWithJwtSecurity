package com.jbk.validation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.jbk.entity.CategoryEntity;


@Component
public class ObjectValidation 
{

	public  Map<String, String> validateCategory(CategoryEntity categoryEntity)
	{
		Long categoryId = categoryEntity.getCategoryId();
		String categoryName = categoryEntity.getCategoryName();
		String discription = categoryEntity.getDiscription();
		Integer discount = categoryEntity.getDiscount();
		Integer gst = categoryEntity.getGst();
		Float deliveryCharge = categoryEntity.getDeliveryCharge();
		
		Map<String, String> errorMap=new HashMap<>();
		
		if(categoryId<=0) 
		{
			errorMap.put("categoryId", " Invalid category Id");
		}
		
		
		if(categoryName==null || categoryName.trim().equals("")) 
		{
			errorMap.put("Category Name", "Category Name Is Required");
		}
		
		if(discription==null || discription.trim().equals("")) 
		{
			errorMap.put("Category discription", "Category discription Is Required");
		}
		
		if(discount<=0) 
		{
			errorMap.put("Category discount", "Invalid discount Percentage");
		}
		
		if(discount<=0) 
		{
			errorMap.put("Category gst", "Invalid gst Percentage");
		}
		
		if(deliveryCharge<=0) 
		{
			errorMap.put("Category deliveryCharge", "Invalid Category deliveryCharge");
		}
		
		return errorMap;
		
		
	}
	

}
