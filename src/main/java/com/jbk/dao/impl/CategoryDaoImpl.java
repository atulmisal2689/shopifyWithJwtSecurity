package com.jbk.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbk.dao.CategoryDao;
import com.jbk.entity.CategoryEntity;

@Repository
public class CategoryDaoImpl implements CategoryDao 
{

	@Autowired
	SessionFactory sessionFactory;
	
	@SuppressWarnings("deprecation")
	@Override
	public int addCategory(CategoryEntity categoryEntity) 
	{

		   Session session = sessionFactory.openSession();
     	   Transaction transaction = session.beginTransaction();
     	   
			try 
			{
				CategoryEntity updatedCategoryEntity = session.get(CategoryEntity.class, categoryEntity.getCategoryId());
				if(updatedCategoryEntity==null)  
				{
					//If id realated object is not present in database
					session.save(categoryEntity); 
					transaction.commit();
					return 1;  
				}
				else
				{
					//If id realated object is present in database
					transaction.rollback();
					return 2;
				}
			}
			catch (Exception e) 
			{
				//If id realated object is not present in database but conflict in not null and unique constraints 
				//chek for not null and unique constraints as we given it at time of table creation
				//Error while committing the transaction
				transaction.rollback();
	            return 0; 
			}
		}		
	

	@Override
	public CategoryEntity getCategoryById(long categoryId) 
	{
		Session session = sessionFactory.openSession();
		CategoryEntity updatedCategoryEntity = session.get(CategoryEntity.class, categoryId);
		
		if(updatedCategoryEntity!=null)
		{
			return updatedCategoryEntity;
		}
		
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<CategoryEntity> getAllCategory() 
	{
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(CategoryEntity.class);
		List<CategoryEntity> list = criteria.list();
			return list;
	}

	@Override
	public List<CategoryEntity> deleteCategory(long categoryId) 
	{
		Session session = sessionFactory.openSession();
		CategoryEntity categoryEntity = session.get(CategoryEntity.class, categoryId);
		
		if(categoryEntity!=null)
		{
              session.delete(categoryEntity);
              session.beginTransaction().commit();;
              List<CategoryEntity> allCategory = getAllCategory();
              return allCategory;
		}
		else
		{
			return null;
		}
	}

	@Override
	public CategoryEntity updateCategory(CategoryEntity categoryEntity)
	{
		Session session = sessionFactory.openSession();
		CategoryEntity existingCategoryEntity  = session.get(CategoryEntity.class, categoryEntity.getCategoryId());
		
		if(existingCategoryEntity !=null)
		{
			existingCategoryEntity.setCategoryName(categoryEntity.getCategoryName());
            existingCategoryEntity.setDiscription(categoryEntity.getDiscription());
            existingCategoryEntity.setDiscount(categoryEntity.getDiscount());
            existingCategoryEntity.setGst(categoryEntity.getGst());
            existingCategoryEntity.setDeliveryCharge(categoryEntity.getDeliveryCharge());
			
			session.update(existingCategoryEntity);
			session.beginTransaction().commit();;
			return existingCategoryEntity;
		}
		
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public CategoryEntity getCategoryByName(String categoryName) 
	{
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(CategoryEntity.class);
		criteria.add(Restrictions.eq("categoryName", categoryName));
		CategoryEntity categoryEntity = (CategoryEntity) criteria.uniqueResult();
		
		if(categoryEntity!=null)
		{
              return categoryEntity;
		}
		else
		{
			return null;
		}
	}

}
