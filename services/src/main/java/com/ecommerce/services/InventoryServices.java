package com.ecommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.domain.RequestInventoryEntity;
import com.ecommerce.exceptionhandling.RecordNotFoundException;
import com.ecommerce.repository.InventoryRepository;
import com.ecommerece.services.impl.InventortyServicesImpl;

@Component
public class InventoryServices implements InventortyServicesImpl {

	@Autowired
	InventoryRepository inventoryRepository;

	public RequestInventoryEntity addItem(RequestInventoryEntity requestInventoryEntity) {
		// TODO Auto-generated method stub

		if (getProduct(requestInventoryEntity.getProductID()) == null)
			return inventoryRepository.save(requestInventoryEntity);
		else {
			return null;
		}
	}

	public int updateInventorty(RequestInventoryEntity requestInventoryEntity) {
		// TODO Auto-generated method stub

		RequestInventoryEntity getProduct = getProduct(requestInventoryEntity.getProductID());
		System.out.println("==getProduct" + getProduct);
		int updatedQuantity = 0;

		if (getProduct != null && getProduct.getQuantity() > 0) {

			updatedQuantity = getProduct.getQuantity() + requestInventoryEntity.getQuantity();

			if (updatedQuantity >= 0) {
				int updatedId = inventoryRepository.updateInventory(requestInventoryEntity.getProductID(),
						updatedQuantity);

				System.out.println("updatedId===" + updatedId);

			} else {
				throw new IllegalArgumentException("Quantity cannot be negative.");
			}

		} else {
			throw new RecordNotFoundException("invalid product id");
		}
		return updatedQuantity;

	}

	public List<RequestInventoryEntity> getInventorty() {
		// TODO Auto-generated method stub
		List<RequestInventoryEntity> inventoryList = inventoryRepository.findAll();

		if (inventoryList != null) {
			return inventoryList;
		} else
			throw new RecordNotFoundException("Inventory List is Empty");

	}

	public RequestInventoryEntity getProduct(String productID) {
		// TODO Auto-generated method stub
		boolean exists = inventoryRepository.existsByProductID(productID);

		if (exists)
			return inventoryRepository.findByProductID(productID);

		else {
			return null;

		}
	}

	public Boolean deleteProduct(String productID) {
		// TODO Auto-generated method stub

		if (getProduct(productID) != null) {
			boolean isDeleted = inventoryRepository.deleteProductByProductID(productID);
			return isDeleted;
		} else {
			throw new RecordNotFoundException("invalid product id");
		}

	}

}
