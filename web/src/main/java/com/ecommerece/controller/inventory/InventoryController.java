package com.ecommerece.controller.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.domain.ApiResponse;
import com.ecommerce.domain.RequestInventoryEntity;
import com.ecommerce.exceptionhandling.RecordNotFoundException;
import com.ecommerce.services.InventoryServices;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
@RequestMapping("/inventory")
@Validated
public class InventoryController {

	InventoryServices inventoryServices;

	@Autowired
	public void setInventoryService(InventoryServices inventoryServices) {
		this.inventoryServices = inventoryServices;
	}

	@PostMapping(path = "/addItem", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public MappingJacksonValue addItemInInventory(@RequestBody RequestInventoryEntity inventoryEntity) {
		ApiResponse<String, Boolean, Integer, RequestInventoryEntity> apiResponse = new ApiResponse<String, Boolean, Integer, RequestInventoryEntity>();

		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("data", "message", "status",
				"statusCode");

		RequestInventoryEntity requestInventoryEntity = inventoryServices.addItem(inventoryEntity);

		if (requestInventoryEntity != null) {

			apiResponse.setMessage("Item Added Successfully in Inventorty");
			apiResponse.setStatus(true);
			apiResponse.setStatusCode(200);
			apiResponse.set(requestInventoryEntity);

		} else {
			filter = SimpleBeanPropertyFilter.filterOutAllExcept("message", "status", "statusCode");

			apiResponse.setMessage("Item already exist in Inventorty");
			apiResponse.setStatus(false);
			apiResponse.setStatusCode(200);
		}

		FilterProvider filters = new SimpleFilterProvider().addFilter("ApiResponseFilter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(apiResponse);

		mapping.setFilters(filters);

		return mapping;

	}

	@PutMapping(path = "/updateItem", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public MappingJacksonValue updateItemInInventory(@RequestBody RequestInventoryEntity inventoryEntity) {
		ApiResponse<String, Boolean, Integer, RequestInventoryEntity> apiResponse = new ApiResponse<String, Boolean, Integer, RequestInventoryEntity>();

		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("message", "status",
				"statusCode");

		int updatedId = inventoryServices.updateInventorty(inventoryEntity);

		apiResponse.setMessage("Inventory Updated successfully");
		apiResponse.setStatus(true);
		apiResponse.setStatusCode(200);

		FilterProvider filters = new SimpleFilterProvider().addFilter("ApiResponseFilter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(apiResponse);

		mapping.setFilters(filters);

		return mapping;

	}

	@GetMapping(path = "/Item/{productId}", produces = "application/json")
	@ResponseBody
	public MappingJacksonValue getItem(@PathVariable String productId) {
		ApiResponse<String, Boolean, Integer, RequestInventoryEntity> apiResponse = new ApiResponse<String, Boolean, Integer, RequestInventoryEntity>();
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("data", "message", "status",
				"statusCode");

		RequestInventoryEntity requestInventoryEntity = inventoryServices.getProduct(productId);

		if (requestInventoryEntity != null) {
			apiResponse.setMessage("success");
			apiResponse.setStatus(true);
			apiResponse.setStatusCode(200);
			apiResponse.set(requestInventoryEntity);

			FilterProvider filters = new SimpleFilterProvider().addFilter("ApiResponseFilter", filter);

			MappingJacksonValue mapping = new MappingJacksonValue(apiResponse);

			mapping.setFilters(filters);

			return mapping;
		} else
			throw new RecordNotFoundException("invalid item id");

	}

	@GetMapping(path = "/", produces = "application/json")
	@ResponseBody
	public MappingJacksonValue getInventory() {
		ApiResponse<String, Boolean, Integer, RequestInventoryEntity> apiResponse = new ApiResponse<String, Boolean, Integer, RequestInventoryEntity>();
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("dataArray", "message", "status",
				"statusCode");

		apiResponse.setMessage("Success");
		apiResponse.setStatus(true);
		apiResponse.setStatusCode(200);
		apiResponse.setT1(inventoryServices.getInventorty());

		FilterProvider filters = new SimpleFilterProvider().addFilter("ApiResponseFilter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(apiResponse);

		mapping.setFilters(filters);

		return mapping;
	}

	@DeleteMapping(path = "/deleteItem/{productId}", produces = "application/json")
	@ResponseBody
	public MappingJacksonValue deleteItem(@PathVariable String productId) {
		ApiResponse<String, Boolean, Integer, RequestInventoryEntity> apiResponse = new ApiResponse<String, Boolean, Integer, RequestInventoryEntity>();
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("message", "status",
				"statusCode");
		boolean isDeleted = inventoryServices.deleteProduct(productId);
		if (isDeleted) {
			apiResponse.setMessage("successfully Deleted");
			apiResponse.setStatus(true);
			apiResponse.setStatusCode(200);

		}

		FilterProvider filters = new SimpleFilterProvider().addFilter("ApiResponseFilter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(apiResponse);

		mapping.setFilters(filters);

		return mapping;

	}

}
