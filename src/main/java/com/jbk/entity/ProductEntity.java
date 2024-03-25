package com.jbk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
	
	@Id
	@Column(unique = true,nullable = false )
	private Long productId;
	
	@Column(unique = true,nullable = false)
	private String productName;
	
	@OneToOne
	@JoinColumn(name="supplierId")
	private SupplierEntity supplierEntity;
	
	@OneToOne
	@JoinColumn(name = "categoryId")
	private CategoryEntity categoryEntity;
	
	@Column(nullable = false)
	private Integer productQty;
	
	@Column(nullable = false)
	private Double productPrice;
	
	
}
