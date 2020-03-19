package com.ecommerece.controller.shipping;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.lenient;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;

import com.ecommerce.domain.ApiResponse;
import com.ecommerce.domain.RequestOrder;
import com.ecommerce.domain.RequestShippingEntity;
import com.ecommerce.services.ShippingServices;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class ShippingControllerTest {

	@InjectMocks
	ShippingController shippingController;

	// This annotation is useful in integration test where a particular bean-an
	// external service-needs to be mock
	@Mock
	private ShippingServices shippingServices;

	@Test
	void testCreateShipment() {
		RequestOrder requestOrder = new RequestOrder();

		requestOrder.setOrderId("232323");
		requestOrder.setUid("u100");
		requestOrder.setCurrency("INR");
		requestOrder.setAmount(200);
		requestOrder.setPaymentMode("NetBanking");
		requestOrder.setCustomerName("Hcl");
		requestOrder.setAddreess("lucknow");

		RequestShippingEntity mockItem = shippingServices.createShiping(requestOrder);

		lenient().when(shippingServices.createShiping(any(RequestOrder.class))).thenReturn(mockItem);

		// when
		MappingJacksonValue mappingJacksonValue = shippingController.createShipment(requestOrder);

		// then
		ApiResponse apiResponse = (ApiResponse) mappingJacksonValue.getValue();

		mockItem = (RequestShippingEntity) apiResponse.getData();

		ResponseEntity<RequestShippingEntity> responseEntity = ResponseEntity.ok().body(mockItem);

		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}

	@Test
	void testGetStatus() {

		String mockStatus = "initiated";
		lenient().when(shippingServices.getStatus(Mockito.anyString())).thenReturn(mockStatus);

		// when
		MappingJacksonValue mappingJacksonValue = shippingController.getStatus("232323");
		// then
		ApiResponse apiResponse = (ApiResponse) mappingJacksonValue.getValue();
		String Status = (String) apiResponse.getData();

		assertThat(Status).isEqualTo(mockStatus);

	}

	@Test
	void testUpdateDeliveryStatus() {
		Map<String, String> json = new HashMap<String, String>();
		json.put("orderId", "232323");
		json.put("status", "Ready for Shipping");

		lenient().when(shippingServices.updateStatus(Mockito.anyString(), Mockito.anyString())).thenReturn(1);
		// when
		MappingJacksonValue mappingJacksonValue = shippingController.updateDeliveryStatus(json);

		// then
		ApiResponse apiResponse = (ApiResponse) mappingJacksonValue.getValue();
		String message = (String) apiResponse.getMessage();
		assertThat(message).isEqualTo("Delivery status Updated successfully");
	}

	@Test
	void testGetDeliveryDate() {
		String mockDeliverydate = "2020-03-10";
		lenient().when(shippingServices.getDeliveryDate(Mockito.anyString())).thenReturn(mockDeliverydate);

		// when
		MappingJacksonValue mappingJacksonValue = shippingController.getDeliveryDate("232323");
		// then
		ApiResponse apiResponse = (ApiResponse) mappingJacksonValue.getValue();
		String deliveryDate = (String) apiResponse.getData();

		assertThat(deliveryDate).isEqualTo(mockDeliverydate);

	}

	@Test
	void testUpdateDeliveryDate() {
		Map<String, String> json = new HashMap<String, String>();
		json.put("orderId", "232323");
		json.put("deliveryDate", "2020-03-20");

		lenient().when(shippingServices.updateDeliveryDate(Mockito.anyString(), Mockito.anyString())).thenReturn(1);
		// when
		MappingJacksonValue mappingJacksonValue = shippingController.updategetDeliveryDate(json);

		// then
		ApiResponse apiResponse = (ApiResponse) mappingJacksonValue.getValue();
		String message = (String) apiResponse.getMessage();
		assertThat(message).isEqualTo("Delivery date Updated successfully");
	}

	@Test
	void testGetShipping() {
		RequestShippingEntity item1 = new RequestShippingEntity();
		RequestShippingEntity item2 = new RequestShippingEntity();

		List<RequestShippingEntity> mockShippingList = new ArrayList<RequestShippingEntity>();
		mockShippingList.add(item1);
		mockShippingList.add(item2);

		lenient().when(shippingServices.getShipping()).thenReturn(mockShippingList);

		// when
		MappingJacksonValue mappingJacksonValue = shippingController.getShipping();
		// then
		ApiResponse apiResponse = (ApiResponse) mappingJacksonValue.getValue();
		List<RequestShippingEntity> shippingList = apiResponse.getdataArray();
		assertThat(shippingList.size()).isEqualTo(2);
	}

	@Test
	void deleteShipping() {

		lenient().when(shippingServices.deleteShipping(Mockito.anyString())).thenReturn(1);

		// when
		MappingJacksonValue mappingJacksonValue = shippingController.deleteShipping("232323");
		// then
		ApiResponse apiResponse = (ApiResponse) mappingJacksonValue.getValue();
		String message = (String) apiResponse.getMessage();
		assertThat(message).isEqualTo("successfully Deleted");
	}

}
