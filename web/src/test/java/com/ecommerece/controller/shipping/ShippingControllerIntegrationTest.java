package com.ecommerece.controller.shipping;

import static org.hamcrest.CoreMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
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
import com.ecommerce.domain.RequestOrder;
import com.ecommerce.domain.RequestShippingEntity;
import com.ecommerce.exceptionhandling.RecordNotFoundException;
import com.ecommerce.services.ShippingServices;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ShippingController.class, ShippingServices.class })
@WebMvcTest(ShippingController.class)
public class ShippingControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	// This annotation is useful in integration test where a particular bean-an
	// external service-needs to be mock
	@MockBean
	private ShippingServices shippingServices;

	@Test
	public void givenAddItemInInventory_whenMockMVC_thenResponseOK() throws Exception {

		RequestShippingEntity mockShipping = new RequestShippingEntity();

		String mockItem = "{\"orderId\": \"232323\", \"uid\":\"u100\", \"currency\": \"INR\", \"amount\": 200,\"paymentMode\": \"NetBanking\", \"customerName\": \"HCL\", \"addreess\": \"lucknow\" }";

		Mockito.when(shippingServices.createShipment(Mockito.any(RequestOrder.class))).thenReturn(mockShipping);

		mockMvc.perform(MockMvcRequestBuilders.post("/shipping/createshipment")

				.content(mockItem).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Create Shipment Successfully"));

	}

	@Test
	public void givenAddItemInInventory_whenMockMVC_thenResponseAlreadyExist() throws Exception {

		String mockItem = "{\"orderId\": \"232323\", \"uid\":\"u100\", \"currency\": \"INR\", \"amount\": 200,\"paymentMode\": \"NetBanking\", \"customerName\": \"HCL\", \"addreess\": \"lucknow\" }";

		Mockito.when(shippingServices.createShipment(Mockito.any(RequestOrder.class)))
				.thenReturn(Mockito.any(RequestShippingEntity.class));

		mockMvc.perform(MockMvcRequestBuilders.post("/shipping/createshipment")

				.content(mockItem).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Shipment already created"));

	}

	@Test
	public void givenGetShippingStatusUri_whenMockMVC_thenReturnsStatus() throws Exception {

		String status = "initiated shipping";

		Mockito.when(shippingServices.getStatus(Mockito.anyString())).thenReturn(status);

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/shipping/status/{orderId}", "232323")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data").value("initiated shipping"));

	}

	@Test
	public void givenUpdateShippingStatusUri_whenMockMVC_thenReturnsOk() throws Exception {

		String mockItem = "{\"orderId\": \"232323\",\"status\": \"ready for shpping\"}";

		int updatedId = 1;
		Mockito.when(shippingServices.updateStatus(Mockito.anyString(), Mockito.anyString())).thenReturn(updatedId);

		this.mockMvc
				.perform(MockMvcRequestBuilders.put("/shipping/updatedeliverystatus").content(mockItem)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Delivery status Updated successfully"));

	}

	@Test
	public void givenGetShippingUri_whenMockMVC_thenReturnsError() throws RecordNotFoundException {

		Mockito.when(shippingServices.getOneShipping(Mockito.anyString()))
				.thenReturn(Mockito.any((RequestShippingEntity.class)));

		try {
			this.mockMvc
					.perform(MockMvcRequestBuilders.get("/shipping/shipment/{orderId}", "232323")
							.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
					.andDo(print()).andExpect(status().isNotFound());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void givenGetShippingUri_whenMockMVC_thenReturnsOK() throws Exception {

//		String status = "initiated shipping";

//		Mockito.when(shippingServices.getStatus((Mockito.anyString()).thenReturn(false);

		RequestShippingEntity response = new RequestShippingEntity(1l, "232323", "u101", "Deeksha", 100f, "INR",
				"intiated", LocalDateTime.now(), "Netbanking", "Hcl");

		Mockito.when(shippingServices.getOneShipping(Mockito.anyString())).thenReturn(response);

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/shipping/shipment/{orderId}", "232323")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void givenGetShippingDateUri_whenMockMVC_thenReturnsDate() throws Exception {

		String deliveryDate = "2020-03-10";

		Mockito.when(shippingServices.getDeliveryDate(Mockito.anyString())).thenReturn(deliveryDate);

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/shipping/deliveryDate/{orderId}", "232323")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(deliveryDate));

	}

	@Test
	public void givenUpdateShippingDateUri_whenMockMVC_thenReturnsOk() throws Exception {

		String mockItem = "{\"orderId\": \"232323\",\"deliveryDate\": \"2020-03-10\"}";

		int updatedId = 1;
		Mockito.when(shippingServices.updateDeliveryDate(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(updatedId);

		this.mockMvc
				.perform(MockMvcRequestBuilders.put("/shipping/updatedeliverydate").content(mockItem)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Delivery date Updated successfully"));

	}

	@Test
	public void givenGetShippingUri_whenMockMVC_thenReturnsOk() throws Exception {

		RequestShippingEntity mockShippingItem = new RequestShippingEntity();
		RequestShippingEntity mockShippingItem1 = new RequestShippingEntity();

		List<RequestShippingEntity> shippingList = Arrays.asList(mockShippingItem, mockShippingItem1);

		Mockito.when(shippingServices.getShipping()).thenReturn(shippingList);

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/shipping/").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"));

	}

	@Test
	public void givenDeleteShipping_whenMockMVC_thenReturnsOk() throws Exception {

		int isDeleted = 1;
		Mockito.when(shippingServices.deleteShipping(Mockito.anyString())).thenReturn(isDeleted);

		this.mockMvc
				.perform(MockMvcRequestBuilders.delete("/shipping/deleteShipping/{orderId}", "232323")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("successfully Deleted"));

	}

}
