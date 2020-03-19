package com.ecommerece.services.impl;

import java.util.List;

import com.ecommerce.domain.RequestOrder;
import com.ecommerce.domain.RequestShippingEntity;

public interface ShippingServicesImpl {
	public RequestShippingEntity createShipment(RequestOrder demoOrder);

	public String getStatus(String orderID);

	public int updateStatus(String orderID, String status);

	public String getDeliveryDate(String orderID);

	public int updateDeliveryDate(String orderID, String deliveryDate);

	public int getDistance(String address);

	public List<RequestShippingEntity> getShipping();

}
