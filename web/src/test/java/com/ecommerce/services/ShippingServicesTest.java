package com.ecommerce.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.ecommerce.domain.RequestInventoryEntity;
import com.ecommerce.domain.RequestOrder;
import com.ecommerce.domain.RequestShippingEntity;
import com.ecommerce.repository.ShippingRepository;

@RunWith(SpringRunner.class)
@SuiteClasses({ ShippingServices.class })
public class ShippingServicesTest {

	@InjectMocks
	ShippingServices shippingServices;

	@Mock
	ShippingRepository shippingRepository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

//	{ "orderId": "232323", "uid":"u100", "currency": "INR", "amount": 200,
//		 * "paymentMode": "NetBanking", "customerName": "HCL", "addreess": "lucknow" }

	@Test
	public void testCreateShipment() {

		RequestOrder requestOrder = new RequestOrder();

		requestOrder.setOrderId("232323");
		requestOrder.setUid("u100");
		requestOrder.setCurrency("INR");
		requestOrder.setAmount(200);
		requestOrder.setPaymentMode("NetBanking");
		requestOrder.setCustomerName("Hcl");
		requestOrder.setAddreess("lucknow");

		RequestShippingEntity mockItem = shippingServices.createShiping(requestOrder);

		when(shippingRepository.existsByOrderID(Mockito.anyString())).thenReturn(false);

		when(shippingRepository.saveAndFlush(Mockito.any(RequestShippingEntity.class))).thenReturn(mockItem);
		RequestShippingEntity shipping = shippingServices.createShipment(requestOrder);

		System.out.println("shipping.getOrderID()" + shipping.getOrderID());
		System.out.println("mockItem.getOrderID()" + mockItem.getOrderID());

		assertEquals(mockItem.getOrderID(), shipping.getOrderID());
	}

	@Test
	public void testGetStatus() {
		String orderID = "232323";
		boolean isExist = true;

		RequestOrder requestOrder = new RequestOrder();

		requestOrder.setOrderId("232323");
		requestOrder.setUid("u100");
		requestOrder.setCurrency("INR");
		requestOrder.setAmount(200);
		requestOrder.setPaymentMode("NetBanking");
		requestOrder.setCustomerName("Hcl");
		requestOrder.setAddreess("lucknow");

		RequestShippingEntity mockItem = shippingServices.createShiping(requestOrder);

		when(shippingRepository.existsByOrderID(Mockito.anyString())).thenReturn(isExist);

		when(shippingRepository.findByOrderID(Mockito.anyString())).thenReturn(mockItem);

		orderID = requestOrder.getOrderId();
		String deliveryStatus = shippingServices.getStatus(orderID);
		assertEquals(deliveryStatus, mockItem.getDeliveryStatus());
	}

	@Test
	public void testUpdateStatus() {
		RequestOrder requestOrder = new RequestOrder();

		requestOrder.setOrderId("232323");
		requestOrder.setUid("u100");
		requestOrder.setCurrency("INR");
		requestOrder.setAmount(200);
		requestOrder.setPaymentMode("NetBanking");
		requestOrder.setCustomerName("Hcl");
		requestOrder.setAddreess("lucknow");

		RequestShippingEntity mockItem = shippingServices.createShiping(requestOrder);

		when(shippingRepository.existsByOrderID(Mockito.anyString())).thenReturn(true);

		when(shippingRepository.findByOrderID(Mockito.anyString())).thenReturn(mockItem);

		when(shippingRepository.updateDeliveryStatus(Mockito.anyString(), Mockito.anyString())).thenReturn(1);

		int updatedId = shippingServices.updateStatus("232323", "Ready for shipping");
		assertEquals(updatedId, 1);
	}

	@Test
	public void testGetDeliveryDate() {
		String orderID = "232323";
		boolean isExist = true;

		RequestOrder requestOrder = new RequestOrder();

		requestOrder.setOrderId("232323");
		requestOrder.setUid("u100");
		requestOrder.setCurrency("INR");
		requestOrder.setAmount(200);
		requestOrder.setPaymentMode("NetBanking");
		requestOrder.setCustomerName("Hcl");
		requestOrder.setAddreess("lucknow");

		RequestShippingEntity mockItem = shippingServices.createShiping(requestOrder);

		when(shippingRepository.existsByOrderID(Mockito.anyString())).thenReturn(isExist);

		when(shippingRepository.findByOrderID(Mockito.anyString())).thenReturn(mockItem);

		orderID = requestOrder.getOrderId();
		String deliveryDate = shippingServices.getDeliveryDate(orderID);
		assertEquals(deliveryDate, mockItem.getDeliveryDate().toString());
	}

	@Test
	public void testUpdateDeliveryDate() {
		RequestOrder requestOrder = new RequestOrder();

		requestOrder.setOrderId("232323");
		requestOrder.setUid("u100");
		requestOrder.setCurrency("INR");
		requestOrder.setAmount(200);
		requestOrder.setPaymentMode("NetBanking");
		requestOrder.setCustomerName("Hcl");
		requestOrder.setAddreess("lucknow");

		RequestShippingEntity mockItem = shippingServices.createShiping(requestOrder);

		when(shippingRepository.existsByOrderID(Mockito.anyString())).thenReturn(true);

		when(shippingRepository.findByOrderID(Mockito.anyString())).thenReturn(mockItem);

		when(shippingRepository.updateDeliveryDate(Mockito.anyString(), Mockito.anyString())).thenReturn(1);

		int updatedId = shippingServices.updateDeliveryDate("232323", "2020-03-10");
		assertEquals(updatedId, 1);
	}

	@Test
	public void testGetShipping() {
		RequestShippingEntity shipping = new RequestShippingEntity();
		RequestShippingEntity shipping1 = new RequestShippingEntity();
		List<RequestShippingEntity> shippingList = Arrays.asList(shipping, shipping1);

		when(shippingRepository.findAll()).thenReturn(shippingList);

		List<RequestShippingEntity> newshippingList = shippingServices.getShipping();

		assertEquals(newshippingList.size(), 2);
	}

	@Test
	public void testDeleteShipping() {
		String orderID = "232323";
		boolean isExist = true;

		when(shippingRepository.existsByOrderID(Mockito.anyString())).thenReturn(isExist);

		when(shippingRepository.deleteProductByOrderId(Mockito.anyString())).thenReturn(1);

		int updatedID = shippingServices.deleteShipping(orderID);
		assertEquals(updatedID, 1);
	}

	@Test
	public void testGetDistance() {

		int distance = shippingServices.getDistance("lucknow");
		assertEquals(distance, 200);
	}

}
