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

import com.jbk.model.SupplierModel;
import com.jbk.service.SupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController 
{
	@Autowired
	SupplierService supplierService;
	
	@PostMapping("/add-supplier")
    public ResponseEntity<String> addSupplier(@RequestBody @Valid SupplierModel supplierModel) 
	{
        int status = supplierService.addSupplier(supplierModel);

        if (status == 1)
        {
            //If category is added then this mes shown
        	return ResponseEntity.status(HttpStatus.CREATED).body("Supplier Successfully Added");
        }
        else
        	if (status == 2) 
        	{
        		//If category already exits then this mes shown
        		return ResponseEntity.status(HttpStatus.CONFLICT).body("Supplier Already Exists");
        	}
        	else 
        	{
        		//check for not null and unique constraints as we given it at time of table creation
        		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("supplierName and mobileNo must be Unique & not null");
        	}
	}
	
	
	@GetMapping("get-supplier-by-id/{id}")
	public ResponseEntity<?> getSupplierByID(@PathVariable long id) 
	{
		//ResponseEntity<?> we use ? because we make it generic for returning categoryModel object and String
		
		SupplierModel supplierModel = supplierService.getSupplierById(id);
		
		if(supplierModel!=null)
		{
			 return ResponseEntity.ok(supplierModel);
		}
		
		else
		{
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid supplierId");
		}

	}
	
	
	@GetMapping("/get-all-supplier")
	public  ResponseEntity<?> getAllSupplier() 
	{
		List<SupplierModel> list = supplierService.getAllSupplier();	
		
		if(!list.isEmpty())
		{
			 return ResponseEntity.ok(list);
		}
		
		else
		{
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supplier Table is Empty");
		}
	}
	
	@DeleteMapping("/delete-supplier")
	public ResponseEntity<?> deleteSupplier(@RequestParam long id) 
	{
		
		List<SupplierModel> remainingSuppliers = supplierService.deleteSupplier(id);
		
		if(remainingSuppliers!=null)
		{
			 return ResponseEntity.ok(remainingSuppliers);
		}
		
		else
		{
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid SupplierId");
		}

	}
	
	@PutMapping("/update-supplier")
	public ResponseEntity<?> updateSupplier(@RequestBody SupplierModel supplierModel) 
	{
		SupplierModel supplierModel2 = supplierService.updateSupplier(supplierModel);
		
		
		if(supplierModel2!=null)
		{
			 return ResponseEntity.ok(supplierModel2);
		}
		
		else
		{
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("supplier Not Exists To Update");
		}
		
	}
	
	                     //Task3
	@GetMapping("/recently-added-two-suppliers")
	public ResponseEntity<?> recentlyAddedTwoSuppliers() 
	{
		List<SupplierModel> list = supplierService.recentlyAddedTwoSuppliers();
		
		if(list!=null &&!list.isEmpty())
		{
			 return ResponseEntity.ok(list);
		}
		
		else
		{
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supplier Table is Empty");
		}
		
	}
  
}
