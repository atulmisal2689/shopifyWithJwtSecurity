package com.jbk.dao;

import java.util.List;

import com.jbk.entity.ProductEntity;

public interface ProductDao {

	public int addProduct(ProductEntity product);
	public ProductEntity getProductById(long productId);
	public List<ProductEntity> getAllProduct();
	public List<ProductEntity> deleteProduct(long productId);
	public ProductEntity updateProduct(ProductEntity productEntity);
	public List<ProductEntity> getAllProductsByOrder(String orderType, String propertyName);
	public List<ProductEntity> getAllProductsHigherThanGivenPrice(Double productPrice);
	public List<ProductEntity> getAllProductsByStartingCharacterSequence(String searchCharacter);
	public List<ProductEntity> getAllProductsOfSpecifiedCategory(Long categoryId);
	public double sumOfProductPrices();
	public int getTotalCountOfProducts();
	public ProductEntity secondHighestProductByPrice();
	public ProductEntity highestProductByPrice();
	
}
