package com.ecommerce.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.domain.RequestOrder;
import com.ecommerce.domain.RequestShippingEntity;
import com.ecommerce.exceptionhandling.RecordNotFoundException;
import com.ecommerce.repository.ShippingRepository;
import com.ecommerece.services.impl.ShippingServicesImpl;

@Service
public class ShippingServices implements ShippingServicesImpl {

	@Autowired
	ShippingRepository shippingRepository;

	public RequestShippingEntity createShipment(RequestOrder requestOrder) {
		// TODO Auto-generated method stub

		if (getOrder(requestOrder.getOrderId()) == false) {

			RequestShippingEntity requestShippingEntity = createShiping(requestOrder);

			return shippingRepository.saveAndFlush(requestShippingEntity);
		} else
			return null;
//		

	}

	public RequestShippingEntity createShiping(RequestOrder requestOrder) {
		int numberofDays = 1; // 1 day per 100 km.

		int distance = getDistance(requestOrder.getAddreess()); // km
		Long estimatedDaye = (long) ((numberofDays * distance) / 100);

		RequestShippingEntity requestShippingEntity = new RequestShippingEntity();

		requestShippingEntity.setUserId(requestOrder.getUid());
		requestShippingEntity.setOrderID(requestOrder.getOrderId());
		requestShippingEntity.setCost(requestOrder.getAmount());
		requestShippingEntity.setName(requestOrder.getCustomerName());
		requestShippingEntity.setAddress(requestOrder.getAddreess());
		requestShippingEntity.setPaymentMode(requestOrder.getPaymentMode());
		requestShippingEntity.setCurrency(requestOrder.getCurrency());
		requestShippingEntity.setDeliveryStatus("Intiated");

		LocalDateTime localDate = requestShippingEntity.getCurrentDate();

		requestShippingEntity.setDeliveryDate(localDate.plusDays(estimatedDaye));

		return requestShippingEntity;
	}

	public String getStatus(String orderID) {
		// TODO Auto-generated method stub

		// check valid order

		if (getOneShipping(orderID) != null) {
			RequestShippingEntity requestShippingEntity = getOneShipping(orderID);
			System.out.println("======getDeliveryStatus" + requestShippingEntity.getDeliveryStatus());

			return requestShippingEntity.getDeliveryStatus();
		} else
			throw new RecordNotFoundException("invalid order id");

	}

	public int updateStatus(String orderID, String status) {
		// TODO Auto-generated method stub

		// check valid order

		if (getOrder(orderID)) {
			int updatedID = shippingRepository.updateDeliveryStatus(orderID, status);

			return updatedID;

		} else
			throw new RecordNotFoundException("invalid order id");

	}

	private boolean getOrder(String orderID) {

		if (shippingRepository.existsByOrderID(orderID)) {

			return true;
		}

		else {
			return false;

		}

	}

	public RequestShippingEntity getOneShipping(String orderID) {
		if (getOrder(orderID)) {
			RequestShippingEntity requestShippingEntity = shippingRepository.findByOrderID(orderID);
			return requestShippingEntity;
		} else {
			return null;
		}

	}

	public String getDeliveryDate(String orderID) {
		// TODO Auto-generated method stub
		// check valid order

		if (getOneShipping(orderID) != null) {
			RequestShippingEntity requestShippingEntity = getOneShipping(orderID);
			return requestShippingEntity.getDeliveryDate().toString();
		} else
			throw new RecordNotFoundException("invalid order id");

	}

	public int updateDeliveryDate(String orderID, String deliveryDate) {
		// TODO Auto-generated method stub
		// check valid order

		if (getOrder(orderID)) {
			boolean isValiddate = validateJavaDate(deliveryDate);
			if (isValiddate) {
				int updatedID = shippingRepository.updateDeliveryDate(orderID, deliveryDate);

				return updatedID;
			} else
				throw new RecordNotFoundException("invalid date format");

		} else
			throw new RecordNotFoundException("invalid order id");
	}

	private static boolean validateJavaDate(String strDate) {
		/* Check if date is 'null' */
		if (strDate.trim().equals("")) {
			return true;
		}
		/* Date is not 'null' */
		else {
			/*
			 * Set preferred date format, For example MM-dd-yyyy, MM.dd.yyyy,dd.MM.yyyy etc.
			 */
			SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
			sdfrmt.setLenient(false);
			/*
			 * Create Date object parse the string into date
			 */
			try {
				Date javaDate = sdfrmt.parse(strDate);
				System.out.println(strDate + " is valid date format");
			}
			/* Date format is invalid */
			catch (ParseException e) {
				System.out.println(strDate + " is Invalid Date format");
				return false;
			}
			/* Return true if date format is valid */
			return true;
		}
	}

	public List<RequestShippingEntity> getShipping() {
		// TODO Auto-generated method stub
		List<RequestShippingEntity> shipping = shippingRepository.findAll();
		if (shipping != null && shipping.size() > 0) {

			System.out.println("shipping===" + shipping);
			return shipping;
		} else
			throw new RecordNotFoundException("Shipping List is Empty");
	}

	public int deleteShipping(String OrderId) {

		int isDeleted = 0;

		if (getOrder(OrderId)) {
			isDeleted = shippingRepository.deleteProductByOrderId(OrderId);
			return isDeleted;
		} else
			throw new RecordNotFoundException("invalid order id");

	}

	public int getDistance(String address) {
		return 200;
		// TODO Auto-generated method stub

	}

}
