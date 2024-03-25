package com.jbk.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbk.dao.ProductDao;
import com.jbk.entity.CategoryEntity;
import com.jbk.entity.ProductEntity;
import com.jbk.entity.SupplierEntity;

@Repository
public class ProdcutDaoImpl implements ProductDao 
{
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public int addProduct(ProductEntity productEntity)
	{
		Session session = sessionFactory.openSession();
  	   Transaction transaction = session.beginTransaction();
  	   
			try 
			{				
				ProductEntity updatedProductEntity = session.get(ProductEntity.class, productEntity.getProductId());			
				
				if(updatedProductEntity==null)  
				{
					//If id realated object is not present in database
					session.save(productEntity); 
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
				System.out.println("1111");
				e.printStackTrace();
				//If id realated object is not present in database but conflict in not null and unique constraints 
				//chek for not null and unique constraints as we given it at time of table creation
				//Error while committing the transaction
				transaction.rollback();
	            return 0; 
			}
	}

	@Override
	public ProductEntity getProductById(long productId) 
	{
		Session session = sessionFactory.openSession();
		ProductEntity updatedProductEntity = session.get(ProductEntity.class, productId);
		
		if(updatedProductEntity!=null)
		{
			return updatedProductEntity;
		}
		
		return null;
	}

	@Override
	public List<ProductEntity> getAllProduct() 
	{
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(ProductEntity.class);
		List<ProductEntity> productEntitylist = criteria.list();
			return productEntitylist;
	}

	@Override
	public List<ProductEntity> deleteProduct(long productId) 
	{
		Session session = sessionFactory.openSession();
		
		ProductEntity productEntity = session.get(ProductEntity.class, productId);
		
		if(productEntity!=null)
		{
              session.delete(productEntity);
              session.beginTransaction().commit();;
              
              List<ProductEntity> remainingProductList = getAllProduct();
              return remainingProductList;
		}
		else
		{
			return null;
		}
	}

	@Override
	public ProductEntity updateProduct(ProductEntity productEntity) 
	{

	    Session session = sessionFactory.openSession();
	    Transaction transaction = session.beginTransaction(); // Start a new transaction

	    ProductEntity existingProductEntity = session.get(ProductEntity.class, productEntity.getProductId());

	    if (existingProductEntity != null) 
	    {
	        // Update only the fields that should be updated
	        existingProductEntity.setProductName(productEntity.getProductName());
	        existingProductEntity.setProductPrice(productEntity.getProductPrice());
	        existingProductEntity.setProductQty(productEntity.getProductQty());

	        // Load the CategoryEntity and SupplierEntity by their IDs
	        CategoryEntity categoryEntity = session.get(CategoryEntity.class, existingProductEntity.getCategoryEntity().getCategoryId());
	        SupplierEntity supplierEntity = session.get(SupplierEntity.class, existingProductEntity.getSupplierEntity().getSupplierId());

	        // Check if the loaded CategoryEntity and SupplierEntity are not null
	        if (categoryEntity != null && supplierEntity != null) 
	        {
	            existingProductEntity.setCategoryEntity(categoryEntity);
	            existingProductEntity.setSupplierEntity(supplierEntity);

	            session.update(existingProductEntity);
	            transaction.commit(); // Commit the transaction
	            session.close(); // Close the session
	            return existingProductEntity;
	        }
	    }

	    transaction.rollback(); // Roll back the transaction if no existing product is found or if the CategoryEntity or SupplierEntity is not found
	    session.close(); // Close the session
	    return null;
	}



	@SuppressWarnings("deprecation")
	@Override
	public List<ProductEntity> getAllProductsByOrder(String orderType, String propertyName) 
	{
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(ProductEntity.class);
		
		if("asc".equals(orderType))
		{
			criteria.addOrder(Order.asc(propertyName));
		}
		else
		{
			if("desc".equals(orderType))
			{
				criteria.addOrder(Order.desc(propertyName));
			}
			else
			{
				return null;
			}
		}
		List<ProductEntity> list = criteria.list();
		
		return list;

	}

	@Override
	public List<ProductEntity> getAllProductsHigherThanGivenPrice(Double productPrice) 
	{
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(ProductEntity.class);
		criteria.add(Restrictions.gt("productPrice", productPrice));
		List<ProductEntity> list = criteria.list();
		return list;
	}

	@Override
	public List<ProductEntity> getAllProductsByStartingCharacterSequence(String searchCharacter)
	{
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(ProductEntity.class);
		criteria.add(Restrictions.like("productName", searchCharacter+"%"));
		List<ProductEntity> list = criteria.list();
		return list;
	}

	@Override
	public List<ProductEntity> getAllProductsOfSpecifiedCategory(Long categoryId) 
	{
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(ProductEntity.class);
		criteria.add(Restrictions.eq("categoryEntity.categoryId", categoryId));
		List<ProductEntity> list = criteria.list();
		return list;
	}

	@Override
	public ProductEntity highestProductByPrice() 
	{
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(ProductEntity.class);
		
		criteria.addOrder(Order.desc("productPrice"));
        criteria.setMaxResults(1); // Retrieve the Highest result

        ProductEntity productEntity = (ProductEntity) criteria.uniqueResult();

        if (productEntity != null) 
        {
            return productEntity;
        }
        else 
        {
            return null; // No highest Product found
        }
	}
	
	
	@Override
	public double sumOfProductPrices() 
	{
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(ProductEntity.class);
		
		   ProjectionList projections = Projections.projectionList();
	       // projections.add(Projections.sum("productPrice")); 
	        criteria.setProjection(Projections.sum("productPrice"));
	        Double total = (Double) criteria.uniqueResult();
	        session.close();

	        return total != null ? total : 0.0;
	}

	@Override
	public int getTotalCountOfProducts() 
	{
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(ProductEntity.class);
		
		criteria.setProjection(Projections.rowCount()); 
        int count = ((Long) criteria.uniqueResult()).intValue();
        session.close();
        return count;
	}

	@Override
	public ProductEntity secondHighestProductByPrice() 
	{
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(ProductEntity.class);
		criteria.addOrder(Order.desc("productPrice"));
		
		criteria.setFirstResult(1); // Skip the first result (highest price)
        criteria.setMaxResults(1); // Retrieve the second result

        ProductEntity productEntity = (ProductEntity) criteria.uniqueResult();

        if (productEntity != null) 
        {
            return productEntity;
        }
        else 
        {
            return null; // No second-highest Product found
        }
	}

}
