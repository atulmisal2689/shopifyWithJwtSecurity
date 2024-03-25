package com.jbk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "supplier")
@Data
public class SupplierEntity 
{
	
	@Id
	@Column(unique = true,nullable = false)
	private Long supplierId;
	
	@Column(unique = true,nullable = false)
	private String supplierName;
	
	@Column(nullable = false)
	private String city;
	
	@Column(nullable = false)
	private int postalCode;
	
	@Column(nullable = false)
	private String countryName;
	
	@Column(unique = true,nullable = false)
	private String mobileNo;

}
