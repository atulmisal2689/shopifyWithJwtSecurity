package com.jbk.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.jbk.model.ProductModel;

public interface ProductService {
	
	public int addProduct(ProductModel product);
	public List<ProductModel> deleteProduct(long productId);
	public ProductModel getProductById(long productId);
	public List<ProductModel> getAllProduct();
	public ProductModel updateProduct(ProductModel product);
	
	public List<ProductModel>  getAllProductsByOrder(String orderType,String propertyName);
	
	public Map<String, Object> uploadSheet(MultipartFile file);
	public List<ProductModel> getAllProductsHigherThanGivenPrice(Double productPrice);
	public List<ProductModel> getAllProductsByStartingCharacterSequence(String searchCharacter);
	public List<ProductModel> getAllProductsOfSpecifiedCategory(Long categoryId);
	public double sumOfProductPrices();
	public int getTotalCountOfProducts();
	public ProductModel secondHighestProductByPrice();
	public ProductModel highestProductByPrice();

}
