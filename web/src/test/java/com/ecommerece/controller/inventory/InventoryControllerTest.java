package com.ecommerece.controller.inventory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ecommerce.domain.ApiResponse;
import com.ecommerce.domain.RequestInventoryEntity;
import com.ecommerce.services.InventoryServices;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class InventoryControllerTest {

	@InjectMocks
	InventoryController inventoryController;

	// This annotation is useful in integration test where a particular bean-an
	// external service-needs to be mock
	@Mock
	private InventoryServices inventoryService;

	@Test
	public void addItem() throws Exception {

		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		RequestInventoryEntity mockItem = new RequestInventoryEntity("101", 1);

		when(inventoryService.addItem(any(RequestInventoryEntity.class))).thenReturn(mockItem);

		// when
		MappingJacksonValue mappingJacksonValue = inventoryController.addItemInInventory(mockItem);

		// then
		ApiResponse apiResponse = (ApiResponse) mappingJacksonValue.getValue();
		System.out.println("===responseEntity" + apiResponse.getData().getClass());

		mockItem = (RequestInventoryEntity) apiResponse.getData();

		ResponseEntity<RequestInventoryEntity> responseEntity = ResponseEntity.ok().body(mockItem);

		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

	}

	@Test
	public void testGetInventory() {

		// given
		RequestInventoryEntity requestInventoryEntity1 = new RequestInventoryEntity("101", 2);
		RequestInventoryEntity requestInventoryEntity2 = new RequestInventoryEntity("102", 3);
		List<RequestInventoryEntity> requestInventoryEntities = new ArrayList<RequestInventoryEntity>();
		requestInventoryEntities.add(requestInventoryEntity1);
		requestInventoryEntities.add(requestInventoryEntity2);

		when(inventoryService.getInventorty()).thenReturn(requestInventoryEntities);

		// when
		MappingJacksonValue mappingJacksonValue = inventoryController.getInventory();

		// then
		ApiResponse apiResponse = (ApiResponse) mappingJacksonValue.getValue();

		assertThat(apiResponse.getdataArray().size()).isEqualTo(2);

		RequestInventoryEntity result = (RequestInventoryEntity) apiResponse.getdataArray().get(0);

		assertThat(result.getProductID().equalsIgnoreCase("101"));

	}

	@Test
	public void testAddQuantityOfItemInInventory() {
		// given
		int updatedId = 1;
		RequestInventoryEntity mockItem = new RequestInventoryEntity("102", 5);

//		when(inventoryService.getProduct(Mockito.anyString())).thenReturn(mockItem);

		lenient().when(inventoryService.updateInventorty(Mockito.any(RequestInventoryEntity.class)))
				.thenReturn(updatedId);

		// when
		MappingJacksonValue mappingJacksonValue = inventoryController
				.updateItemInInventory(any(RequestInventoryEntity.class));

		// then
		ApiResponse apiResponse = (ApiResponse) mappingJacksonValue.getValue();

		System.out.println("mappingJacksonValue" + apiResponse);

		assertThat(apiResponse.getMessage().equals("Inventory Updated successfully"));
	}

	@Test
	public void testGetItemInInventory() { // given

		RequestInventoryEntity mockItem = new RequestInventoryEntity("102", 5);

		Mockito.lenient().when(inventoryService.getProduct(Mockito.anyString())).thenReturn(mockItem);

		// when
		MappingJacksonValue mappingJacksonValue = inventoryController.getItem((Mockito.anyString()));

		// then
		ApiResponse apiResponse = (ApiResponse) mappingJacksonValue.getValue();

		assertThat(apiResponse.getMessage().equals("success"));

	}

	@Test
	public void testDeleteItemInInventory() { // given

		boolean isDeleted = true;
		Mockito.lenient().when(inventoryService.deleteProduct(Mockito.anyString())).thenReturn(isDeleted);

		// when
		MappingJacksonValue mappingJacksonValue = inventoryController.deleteItem((Mockito.anyString()));

		// then
		ApiResponse apiResponse = (ApiResponse) mappingJacksonValue.getValue();

		assertThat(apiResponse.getMessage().equals("successfully Deleted"));

	}

}
