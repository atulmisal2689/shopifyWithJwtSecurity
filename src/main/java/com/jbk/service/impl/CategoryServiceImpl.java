package com.jbk.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbk.dao.CategoryDao;
import com.jbk.entity.CategoryEntity;
import com.jbk.model.CategoryModel;
import com.jbk.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService 
{
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	CategoryDao categoryDao;

	@Override
	public int addCategory(CategoryModel categoryModel) 
	{
		CategoryEntity categoryEntity = modelMapper.map(categoryModel, CategoryEntity.class);

		return categoryDao.addCategory(categoryEntity);
	}

	@Override
	public CategoryModel getCategoryById(long categoryId) 
	{
		CategoryEntity categoryEntity = categoryDao.getCategoryById(categoryId);
		
		if(categoryEntity!=null)
		{
			CategoryModel categoryModel = modelMapper.map(categoryEntity, CategoryModel.class);
			return categoryModel;
		}

		return null;
	}

	@Override
	public List<CategoryModel> getAllCategory() 
	{
		List<CategoryEntity> list = categoryDao.getAllCategory();
		
		List<CategoryModel> categoryModels = new ArrayList<>();

	        for (CategoryEntity categoryEntity : list)
	        {
	            CategoryModel categoryModel = modelMapper.map(categoryEntity, CategoryModel.class);
	            categoryModels.add(categoryModel);
	        }
	        return categoryModels;
	   
	}

	@Override
	public List<CategoryModel> deleteCategory(long categoryId) 
	{
		List<CategoryEntity> remainingCategory = categoryDao.deleteCategory(categoryId);
		List<CategoryModel> categoryModels = new ArrayList<>();
		
		if(remainingCategory!=null)
		{
			for (CategoryEntity categoryEntity : remainingCategory)
	        {
	            CategoryModel categoryModel = modelMapper.map(categoryEntity, CategoryModel.class);
	            categoryModels.add(categoryModel);
	        }
	        return categoryModels;
		}
		
		return null;
	}

	@Override
	public CategoryModel updateCategory(CategoryModel categoryModel)
	{
		CategoryEntity categoryEntity = modelMapper.map(categoryModel, CategoryEntity.class);

		CategoryEntity categoryEntity2 = categoryDao.updateCategory(categoryEntity);
		
		if(categoryEntity2!=null)
		{
			CategoryModel categoryModel2 = modelMapper.map(categoryEntity2, CategoryModel.class);
			return categoryModel2;
		}

		return null;
	}

	@Override
	public CategoryModel getCategoryByName(String categoryName) 
	{
		CategoryEntity categoryEntity = categoryDao.getCategoryByName(categoryName);
		
		if(categoryEntity!=null)
		{
			CategoryModel categoryModel = modelMapper.map(categoryEntity, CategoryModel.class);
			return categoryModel;
		}

		return null;
	}

}
