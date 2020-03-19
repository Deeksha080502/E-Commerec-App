package com.ecommerece.controller.inventory;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ecommerce.domain.RequestInventoryEntity;
import com.ecommerce.exceptionhandling.RecordNotFoundException;
import com.ecommerce.services.InventoryServices;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { InventoryController.class, InventoryServices.class })
@WebMvcTest(InventoryController.class)
public class InventoryControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	// This annotation is useful in integration test where a particular bean-an
	// external service-needs to be mock
	@MockBean
	private InventoryServices inventoryService;

	@Test
	public void givenAddItemInInventory_whenMockMVC_thenResponseOK() throws Exception {

		RequestInventoryEntity mockInventoryItem = new RequestInventoryEntity("1", 9);

		String mockItem = "{\"productID\": \"1\",\"quantity\": 9}";

		Mockito.when(inventoryService.addItem(Mockito.any(RequestInventoryEntity.class))).thenReturn(mockInventoryItem);

		mockMvc.perform(MockMvcRequestBuilders.post("/inventory/addItem")

				.content(mockItem).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Item Added Successfully in Inventorty"));

	}

	@Test
	public void givenAddItemInInventory_whenMockMVC_thenResponseAlreadyExist() throws Exception {

		String mockItem = "{\"productID\": \"1\",\"quantity\": 9}";

		Mockito.when(inventoryService.addItem(Mockito.any(RequestInventoryEntity.class)))
				.thenReturn(Mockito.any(RequestInventoryEntity.class));

		mockMvc.perform(MockMvcRequestBuilders.post("/inventory/addItem")

				.content(mockItem).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Item already exist in Inventorty"));

	}

	@Test
	public void givenGetItemUri_whenMockMVC_thenReturnsItem() throws Exception {

		RequestInventoryEntity mockInventoryItem = new RequestInventoryEntity("1", 9);

		Mockito.when(inventoryService.getProduct(Mockito.anyString())).thenReturn(mockInventoryItem);

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/inventory/Item/{productId}", "1")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"));

	}

	@Test
	public void givenGetItemUri_whenMockMVC_thenReturnsError() throws RecordNotFoundException {

		Mockito.when(inventoryService.getProduct(Mockito.anyString()))
				.thenReturn(Mockito.any(RequestInventoryEntity.class));

		try {
			this.mockMvc
					.perform(MockMvcRequestBuilders.get("/inventory/Item/{productId}", "1")
							.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andDo(print()).andExpect(status().isNotFound());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void givenUpdateUri_whenMockMVC_thenReturnsItem() throws Exception {

		String mockItem = "{\"productID\": \"1\",\"quantity\": 9}";

		int updatedId = 1;
		Mockito.when(inventoryService.updateInventorty(Mockito.any(RequestInventoryEntity.class)))
				.thenReturn(updatedId);

		this.mockMvc
				.perform(MockMvcRequestBuilders.put("/inventory/updateItem").content(mockItem)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Inventory Updated successfully"));

	}

	@Test
	public void givenDeleteItem_whenMockMVC_thenReturnsItem() throws Exception {

		boolean isDeleted = true;
		Mockito.when(inventoryService.deleteProduct(Mockito.anyString())).thenReturn(isDeleted);

		this.mockMvc
				.perform(MockMvcRequestBuilders.delete("/inventory/deleteItem/{productId}", "107")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("successfully Deleted"));

	}

	@Test
	public void givenGetAllItemUri_whenMockMVC_thenReturnsItems() throws Exception {

		RequestInventoryEntity mockInventoryItem = new RequestInventoryEntity("1", 9);
		RequestInventoryEntity mockInventoryItem1 = new RequestInventoryEntity("2", 4);

		List<RequestInventoryEntity> inventoryList = Arrays.asList(mockInventoryItem, mockInventoryItem1);

		Mockito.when(inventoryService.getInventorty()).thenReturn(inventoryList);

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/inventory/").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Success"));

	}

}
