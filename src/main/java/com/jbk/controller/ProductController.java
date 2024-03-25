package com.jbk.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.multipart.MultipartFile;

import com.jbk.model.ProductModel;
import com.jbk.service.ProductService;


@RestController
@RequestMapping("/product")
public class ProductController 
{
	@Autowired
	ProductService productService;
	
	@PostMapping("/add-product")
    public ResponseEntity<String> addProduct(@RequestBody @Valid ProductModel productModel) 
	{
		
		System.out.println(productModel);
		int status = productService.addProduct(productModel);

        if (status == 1)
        {
            //If category is added then this mes shown
        	return ResponseEntity.status(HttpStatus.CREATED).body("Product Successfully Added");
        }
        else
        	if (status == 2) 
        	{
        		//If category already exits then this mes shown
        		return ResponseEntity.status(HttpStatus.CONFLICT).body("Product Already Exists");
        	}
        	else 
        	{
        		//check for not null and unique constraints as we given it at time of table creation
        		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("ProductName must be Unique & not null");
        	}
	}
	
	@GetMapping("get-product-by-id/{id}")
	public ResponseEntity<?> getProductByID(@PathVariable long id) 
	{
		//ResponseEntity<?> we use ? because we make it generic for returning categoryModel object and String
		
		ProductModel productModel = productService.getProductById(id);
		
		if(productModel!=null)
		{
			 return ResponseEntity.ok(productModel);
		}
		
		else
		{
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid ProductId");
		}

	}
	
	@GetMapping("/get-all-product")
	public  ResponseEntity<?> getAllProduct() 
	{
		List<ProductModel> list = productService.getAllProduct();	
		
		if(!list.isEmpty())
		{
			 return ResponseEntity.ok(list);
		}
		
		else
		{
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Table is Empty");
		}
	}
	
	@DeleteMapping("/delete-product")
	public ResponseEntity<?> deleteProduct(@RequestParam long id) 
	{
		
		List<ProductModel> remainingProduct = productService.deleteProduct(id);
		
		if(remainingProduct!=null)
		{
			 return ResponseEntity.ok(remainingProduct);
		}
		
		else
		{
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid ProductId");
		}

	}
	
	@PutMapping("/update-product")
	public ResponseEntity<?> updateProduct(@RequestBody @Valid ProductModel productModel) 
	{
		ProductModel updatedProductModel = productService.updateProduct(productModel);
		
		
		if(updatedProductModel!=null)
		{
			 return ResponseEntity.ok(updatedProductModel);
		}
		
		else
		{
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Exists To Update");
		}
		
	}
	
	                   //Task2
	@GetMapping("/get-all-product-by-name-order")
	public ResponseEntity<?> getAllProductByNameByOrder(@RequestParam String propertyName,@RequestParam String orderType) 
	{
		List<ProductModel> ListOfallProductModel = productService.getAllProductsByOrder(orderType,propertyName);
		
		if(ListOfallProductModel!=null && !ListOfallProductModel.isEmpty())
		{
			 return ResponseEntity.ok(ListOfallProductModel);
		}
		
		else
		{
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Table is Empty or Invalid Order Type");
		}
		
	}
	
	                       //Task4
	@GetMapping("/get-all-products-higher-than-givenprice")
	public  ResponseEntity<?> getAllProductsHigherThanGivenPrice(@RequestParam Double productPrice) 
	{
		List<ProductModel> list = productService.getAllProductsHigherThanGivenPrice(productPrice);	
		
		if(list!=null && !list.isEmpty())
		{
			 return ResponseEntity.ok(list);
		}
		
		else
		{
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Table is Empty Or Invalid Amount");
		}
	}
	
                          //Task5
	@GetMapping("/get-all-products-starting-character-sequence")
	public  ResponseEntity<?> getAllProductsByStartingCharacterSequence(@RequestParam String searchCharacter) 
	{
		List<ProductModel> list = productService.getAllProductsByStartingCharacterSequence(searchCharacter);
	
		if(list!=null && !list.isEmpty())
		{
			 return ResponseEntity.ok(list);
		}
		
		else
		{
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Table is Empty Or Invalid Character");
		}

	}
	
                             //Task6
    @GetMapping("/get-all-products-of-specified-category")
     public  ResponseEntity<?> getAllProductsOfSpecifiedCategory(@RequestParam Long categoryId) 
    {
		
    	List<ProductModel> list = productService.getAllProductsOfSpecifiedCategory(categoryId);
    	
    	if(list!=null && !list.isEmpty())
		{
			 return ResponseEntity.ok(list);
		}
		
		else
		{
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Table is Empty Or Invalid categoryId");
		}
    	
    }
    
    
                                  //Task 7
    @GetMapping("highest-product-by-price")
    public ResponseEntity<?> highestProductByPrice() 
    {
    	ProductModel productModel = productService.highestProductByPrice();

    	if(productModel!=null)
    	{
    		return ResponseEntity.ok(productModel);
    	}

    	else
    	{
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Table Is Empty");
    	} 

    }   
    
                                   //Task8 (Projections)
    @GetMapping("/sum-of-product-prices")
    public ResponseEntity<Double> sumOfProductPrices() 
    {
    	double totalPrice  = productService.sumOfProductPrices();
    	
    	return ResponseEntity.ok(totalPrice );
    }
    
    
                                 //Task9 (Projections)
    @GetMapping("/get-total-count-of-products")
    public ResponseEntity<Integer> getTotalCountOfProducts() 
    {
    	int totalCountOfProducts  = productService.getTotalCountOfProducts();

    	return ResponseEntity.ok(totalCountOfProducts );
    }
    
    
                              //Task11
    @GetMapping("second-highest-product-by-price")
	public ResponseEntity<?> secondHighestProductByPrice() 
	{
		ProductModel productModel = productService.secondHighestProductByPrice();
		
		if(productModel!=null)
		{
			 return ResponseEntity.ok(productModel);
		}
		
		else
		{
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Table Is Empty");
		} 
		
	}
    
    @PostMapping("/upload-sheet")
	public Map<String, Object> uploadSheet(@RequestParam MultipartFile myFile) 
	{
    	Map<String,Object> map = productService.uploadSheet(myFile);
    	
    	return map;
    	
	}
	

}
