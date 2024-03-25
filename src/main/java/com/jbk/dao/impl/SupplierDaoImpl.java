package com.jbk.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbk.dao.SupplierDao;
import com.jbk.entity.SupplierEntity;

@Repository
public class SupplierDaoImpl implements SupplierDao 
{
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public int addSupplier(SupplierEntity supplierEntity) 
	{
		Session session = sessionFactory.openSession();
  	    Transaction transaction = session.beginTransaction();
  	   
			try 
			{
				SupplierEntity updatedSupplierEntity = session.get(SupplierEntity.class, supplierEntity.getSupplierId());
				if(updatedSupplierEntity==null)  
				{
					//If id realated object is not present in database
					session.save(supplierEntity); 
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
	public SupplierEntity getSupplierById(long supplierId) 
	{
		Session session = sessionFactory.openSession();
		SupplierEntity updatedSupplierEntity = session.get(SupplierEntity.class, supplierId);
		
		if(updatedSupplierEntity!=null)
		{
			return updatedSupplierEntity;
		}
		
		return null;
	}

	@Override
	public List<SupplierEntity> getAllSupplier() 
	{
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(SupplierEntity.class);
		List<SupplierEntity> list = criteria.list();
			return list;
	}

	@Override
	public List<SupplierEntity> deleteSupplier(long supplierId) 
	{
		Session session = sessionFactory.openSession();
		
		SupplierEntity supplierEntity = session.get(SupplierEntity.class, supplierId);
		
		if(supplierEntity!=null)
		{
              session.delete(supplierEntity);
              session.beginTransaction().commit();;
              List<SupplierEntity> allSupplier = getAllSupplier();
              return allSupplier;
		}
		else
		{
			return null;
		}
	}

	@Override
	public SupplierEntity updateSupplier(SupplierEntity supplierEntity) 
	{
		Session session = sessionFactory.openSession();
		
		SupplierEntity existingSupplierEntity  = session.get(SupplierEntity.class, supplierEntity.getSupplierId());
		
		if(existingSupplierEntity !=null)
		{
			existingSupplierEntity.setSupplierName(supplierEntity.getSupplierName());
			existingSupplierEntity.setCity(supplierEntity.getCity());
			existingSupplierEntity.setPostalCode(supplierEntity.getPostalCode());
			existingSupplierEntity.setCountryName(supplierEntity.getCountryName());
			existingSupplierEntity.setMobileNo(supplierEntity.getMobileNo());
			
			session.update(existingSupplierEntity);
			session.beginTransaction().commit();;
			return existingSupplierEntity;
		}
		
		return null;
	}

	@Override
	public List<SupplierEntity> recentlyAddedTwoSuppliers()
	{
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(SupplierEntity.class);
		criteria.addOrder(Order.desc("supplierId"));  
		criteria.setMaxResults(2);
		List<SupplierEntity> supplierEntity = criteria.list();
		return supplierEntity;
	}

}
