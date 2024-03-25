package com.jbk.dao;

import java.util.List;

import com.jbk.entity.SupplierEntity;

public interface SupplierDao {
	
	public int addSupplier(SupplierEntity supplier);
	public SupplierEntity getSupplierById(long supplierId);
	public List<SupplierEntity> getAllSupplier();
	public List<SupplierEntity> deleteSupplier(long supplierId);
	public SupplierEntity updateSupplier(SupplierEntity supplier);
	public List<SupplierEntity> recentlyAddedTwoSuppliers();
	

}
