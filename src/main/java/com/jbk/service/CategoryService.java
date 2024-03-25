package com.jbk.service;

import java.util.List;

import com.jbk.model.CategoryModel;


public interface CategoryService 
{

	public int addCategory(CategoryModel category);
	public CategoryModel getCategoryById(long categoryId);
	public List<CategoryModel> getAllCategory();
	public List<CategoryModel> deleteCategory(long categoryId);
	public CategoryModel updateCategory(CategoryModel category);

	public CategoryModel getCategoryByName(String categoryName);
}
