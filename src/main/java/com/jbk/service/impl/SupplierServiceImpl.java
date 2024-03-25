package com.jbk.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbk.dao.SupplierDao;
import com.jbk.entity.SupplierEntity;
import com.jbk.model.SupplierModel;
import com.jbk.service.SupplierService;

@Service
public class SupplierServiceImpl implements SupplierService 
{

	@Autowired
	SupplierDao supplierDao;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public int addSupplier(SupplierModel supplierModel) 
	{
		SupplierEntity supplierEntity = modelMapper.map(supplierModel, SupplierEntity.class);
		return supplierDao.addSupplier(supplierEntity);
	}

	@Override
	public SupplierModel getSupplierById(long supplierId) 
	{
		SupplierEntity supplierEntity = supplierDao.getSupplierById(supplierId);
		
		if(supplierEntity!=null)
		{
			SupplierModel supplierModel = modelMapper.map(supplierEntity, SupplierModel.class);
			return supplierModel;
		}

		return null;
	}

	@Override
	public List<SupplierModel> getAllSupplier() 
	{
        List<SupplierEntity> list = supplierDao.getAllSupplier();
		
		List<SupplierModel> supplierModel = new ArrayList<>();

	        for (SupplierEntity ele : list)
	        {
	        	SupplierModel supplierModel1 = modelMapper.map(ele, SupplierModel.class);
	            supplierModel.add(supplierModel1);
	        }
	        return supplierModel;
	}

	@Override
	public List<SupplierModel> deleteSupplier(long supplierId)
	{
		List<SupplierEntity> remainingSupplier = supplierDao.deleteSupplier(supplierId);
		
		List<SupplierModel> supplierModels = new ArrayList<>();
		
		if(remainingSupplier!=null)
		{
			for (SupplierEntity ele : remainingSupplier)
	        {
				SupplierModel supplierModel = modelMapper.map(ele, SupplierModel.class);
				supplierModels.add(supplierModel);
	        }
	        return supplierModels;
		}
		
		return null;
	}

	@Override
	public SupplierModel updateSupplier(SupplierModel supplierModel)
	{
		SupplierEntity supplierEntity = modelMapper.map(supplierModel, SupplierEntity.class);

		SupplierEntity supplierEntity2 = supplierDao.updateSupplier(supplierEntity);
		
		if(supplierEntity2!=null)
		{
			SupplierModel supplierModel2 = modelMapper.map(supplierEntity2, SupplierModel.class);
			return supplierModel2;
		}

		return null;
	}

	@Override
	public List<SupplierModel> recentlyAddedTwoSuppliers()
	{
		List<SupplierEntity> list = supplierDao.recentlyAddedTwoSuppliers();

		List<SupplierModel> supplierModel = new ArrayList<>();

		if(!list.isEmpty())
		{
			for (SupplierEntity ele : list)
			{
				SupplierModel supplierModel1 = modelMapper.map(ele, SupplierModel.class);
				supplierModel.add(supplierModel1);
			}
			return supplierModel;
		}
		return null;
	}

}
