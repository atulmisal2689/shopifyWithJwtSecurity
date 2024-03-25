package com.jbk.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jbk.model.CategoryModel;
import com.jbk.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController
{
	@Autowired
	CategoryService categoryService;
	
	@PostMapping("/add-category")
    public ResponseEntity<String> addCategory(@RequestBody @Valid CategoryModel categoryModel) 
	{
        int status = categoryService.addCategory(categoryModel);

        if (status == 1)
        {
            //If category is added then this mes shown
        	return ResponseEntity.status(HttpStatus.CREATED).body("Data Successfully Added");
        }
        else
        	if (status == 2) 
        	{
        		//If category already exits then this mes shown
        		return ResponseEntity.status(HttpStatus.CONFLICT).body("Category Already Exists");
        	}
        	else 
        	{
        		//check for not null and unique constraints as we given it at time of table creation
        		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("categoryName and Decription must be Unique & not null");
        	}
	}
	
	@GetMapping("get-category-by-id/{id}")
	public ResponseEntity<?> getCategoryByID(@PathVariable long id) 
	{
		//ResponseEntity<?> we use ? because we make it generic for returning categoryModel object and String
		
		CategoryModel categoryModel = categoryService.getCategoryById(id);
		
		if(categoryModel!=null)
		{
			//return new ResponseEntity<>(categoryModel,HttpStatus.CREATED);
			              //or//
			return ResponseEntity.ok(categoryModel);
			
		}
		
		else
		{
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid CategoryId");
		}

	}
	
	@GetMapping("/get-all-category")
	public  ResponseEntity<?> getAllCategory() 
	{
		List<CategoryModel> list = categoryService.getAllCategory();	
		
		if(!list.isEmpty())
		{
			 return ResponseEntity.ok(list);
		}
		
		else
		{
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categaory Table is Empty");
		}
	}
	
	@DeleteMapping("/delete-category")
	public ResponseEntity<?> deleteCategory(@RequestParam long id) 
	{
		
		List<CategoryModel> remainingCategory = categoryService.deleteCategory(id);
		
		if(remainingCategory!=null)
		{
			 return ResponseEntity.ok(remainingCategory);
		}
		
		else
		{
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid CategoryId");
		}

	}
	
	@PutMapping("/update-category")
	public ResponseEntity<?> updateCategory(@RequestBody @Valid CategoryModel categoryModel) 
	{
		CategoryModel categoryModel2 = categoryService.updateCategory(categoryModel);
		
		
		if(categoryModel2!=null)
		{
			 return ResponseEntity.ok(categoryModel2);
		}
		
		else
		{
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category Not Exists To Update");
		}
		
	}
	                    //Task1
	@GetMapping("/get-category-by-name/{categoryName}")
	public ResponseEntity<?>getCategoryByName(@PathVariable String categoryName)
	{
		
		CategoryModel categoryModel = categoryService.getCategoryByName(categoryName);
		if(categoryModel!=null)
		{
			 return ResponseEntity.ok(categoryModel);
		}
		
		else
		{
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CategoryName Not Exists");
		}
		
	}

}
