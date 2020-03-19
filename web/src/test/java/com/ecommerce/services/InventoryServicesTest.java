package com.ecommerce.services;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.junit4.SpringRunner;

import com.ecommerce.domain.RequestInventoryEntity;
import com.ecommerce.repository.InventoryRepository;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = InventoryServices.class)

@RunWith(SpringRunner.class)
@SuiteClasses({ InventoryServices.class })
public class InventoryServicesTest {

	@InjectMocks
	InventoryServices inventortyService;

	@Mock
	InventoryRepository inventoryRespositoy;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void addItem_whenInvoked_thenReturnsItem() {

		RequestInventoryEntity mockItem = new RequestInventoryEntity("102", 2);

		when(inventoryRespositoy.existsByProductID(Mockito.anyString())).thenReturn(false);

		when(inventoryRespositoy.findByProductID(Mockito.anyString())).thenReturn(mockItem);

		when(inventoryRespositoy.save(Mockito.any(RequestInventoryEntity.class))).thenReturn(mockItem);
		RequestInventoryEntity requestInventoryEntity = inventortyService.addItem(mockItem);

		assertEquals(mockItem.getProductID(), requestInventoryEntity.getProductID());

	}

	@Test
	public void updateInventorty_whenInvoked_thenReturnUpdatedId() {

		int updatedID = 1;

		RequestInventoryEntity mockItem = new RequestInventoryEntity("102", 3);

		when(inventoryRespositoy.existsByProductID(Mockito.anyString())).thenReturn(true);
		when(inventoryRespositoy.findByProductID(Mockito.anyString())).thenReturn(mockItem);

		when(inventoryRespositoy.updateInventory(Mockito.anyString(), Mockito.anyInt())).thenReturn(updatedID);

		int updatedID1 = inventortyService.updateInventorty(mockItem);

		assertTrue(updatedID1 > 0);
	}

	@Test
	public void getInventorty_whenInvoked_thenReturnList() {
		RequestInventoryEntity requestInventoryEntity1 = new RequestInventoryEntity("101", 2);
		RequestInventoryEntity requestInventoryEntity2 = new RequestInventoryEntity("102", 3);

		when(inventoryRespositoy.findAll()).thenReturn(Arrays.asList(requestInventoryEntity1, requestInventoryEntity2));
		List<RequestInventoryEntity> inventoryList = inventortyService.getInventorty();

		assertEquals(inventoryList.size(), 2);
	}

	@Test
	public void getProduct_whenInvoked_thenReturnItem() {
		RequestInventoryEntity mockItem = new RequestInventoryEntity("102", 3);

		when(inventoryRespositoy.existsByProductID(Mockito.anyString())).thenReturn(true);
		when(inventoryRespositoy.findByProductID(Mockito.anyString())).thenReturn(mockItem);

		RequestInventoryEntity requestInventoryEntity = inventortyService.getProduct(mockItem.getProductID());

		assertEquals(requestInventoryEntity.getProductID(), mockItem.getProductID());

	}

	@Test
	public void deleteProduct_whenInvoked_thenReturnItem() {
		RequestInventoryEntity mockItem = new RequestInventoryEntity("102", 3);

		when(inventoryRespositoy.existsByProductID(Mockito.anyString())).thenReturn(true);
		when(inventoryRespositoy.findByProductID(Mockito.anyString())).thenReturn(mockItem);

		when(inventoryRespositoy.deleteProductByProductID(Mockito.anyString())).thenReturn(true);

		boolean isDeleted = inventortyService.deleteProduct(mockItem.getProductID());
		assertTrue(isDeleted);
	}

}
