package com.jbk.service;

import java.util.List;

import com.jbk.model.SupplierModel;

public interface SupplierService {

	public int addSupplier(SupplierModel supplier);

	public SupplierModel getSupplierById(long supplierId);

	public List<SupplierModel> getAllSupplier();

	public List<SupplierModel> deleteSupplier(long supplierId);

	public SupplierModel updateSupplier(SupplierModel supplier);
	
	public List<SupplierModel> recentlyAddedTwoSuppliers();

}
