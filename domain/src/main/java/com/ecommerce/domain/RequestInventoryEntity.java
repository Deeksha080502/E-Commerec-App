package com.ecommerce.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import  javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_inventory")
public class RequestInventoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	

    @NotEmpty(message = "product id must not be empty")
	private String productID;

    @NotNull(message = "quantity must not be empty")
	private Integer quantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public RequestInventoryEntity(String productID, Integer quantity) {
		super();

		this.productID = productID;
		this.quantity = quantity;
	}

	public RequestInventoryEntity() {
		// TODO Auto-generated constructor stub
	}

}
