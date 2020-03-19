package com.ecommerece.services.impl;

import java.util.List;

import com.ecommerce.domain.RequestInventoryEntity;

public interface InventortyServicesImpl {
	public RequestInventoryEntity addItem(RequestInventoryEntity requestInventoryEntity);

	public int updateInventorty(RequestInventoryEntity requestInventoryEntity);

	public List<RequestInventoryEntity> getInventorty();

	public RequestInventoryEntity getProduct(String productID);

}
