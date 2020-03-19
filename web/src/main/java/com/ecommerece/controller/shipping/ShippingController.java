package com.ecommerece.controller.shipping;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.domain.ApiResponse;
import com.ecommerce.domain.RequestInventoryEntity;
import com.ecommerce.domain.RequestOrder;
import com.ecommerce.domain.RequestShippingEntity;
import com.ecommerce.exceptionhandling.RecordNotFoundException;
import com.ecommerce.services.ShippingServices;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
@RequestMapping("/shipping")
@Validated
public class ShippingController {

	@Autowired
	ShippingServices shippingServices;

	@PostMapping(path = "/createshipment", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public MappingJacksonValue createShipment(@RequestBody RequestOrder requestOrder) {

		ApiResponse<String, Boolean, Integer, RequestShippingEntity> apiResponse = new ApiResponse<String, Boolean, Integer, RequestShippingEntity>();

		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("data", "message", "status",
				"statusCode");

		RequestShippingEntity requestShippingEntity = shippingServices.createShipment(requestOrder);

		if (requestShippingEntity != null) {
			apiResponse.setMessage("Create Shipment Successfully");
			apiResponse.setStatus(true);
			apiResponse.setStatusCode(200);
			apiResponse.set(requestShippingEntity);

		} else {
			apiResponse.setMessage("Shipment already created");
			apiResponse.setStatus(true);
			apiResponse.setStatusCode(200);
		}

		FilterProvider filters = new SimpleFilterProvider().addFilter("ApiResponseFilter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(apiResponse);

		mapping.setFilters(filters);

		return mapping;

	}

	@GetMapping(path = "/status/{orderId}", produces = "application/json")
	@ResponseBody
	public MappingJacksonValue getStatus(@PathVariable String orderId) {

		ApiResponse<String, Boolean, Integer, String> apiResponse = new ApiResponse<String, Boolean, Integer, String>();

		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("data", "message", "status",
				"statusCode");

		String status = shippingServices.getStatus(orderId);

		apiResponse.setMessage("Success");
		apiResponse.setStatus(true);
		apiResponse.setStatusCode(200);
		apiResponse.set(status);

		FilterProvider filters = new SimpleFilterProvider().addFilter("ApiResponseFilter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(apiResponse);

		mapping.setFilters(filters);

		return mapping;

	}

	@PutMapping(path = "/updatedeliverystatus", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public MappingJacksonValue updateDeliveryStatus(@RequestBody Map<String, String> json) {
		ApiResponse<String, Boolean, Integer, Integer> apiResponse = new ApiResponse<String, Boolean, Integer, Integer>();

		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("message", "status",
				"statusCode");

		System.out.println("orderID==" + json.get("orderId") + "==" + json.get("orderId"));

		int updatedId = shippingServices.updateStatus(json.get("orderId"), json.get("status"));

		if (updatedId > 0) {
			apiResponse.setMessage("Delivery status Updated successfully");
			apiResponse.setData(updatedId);
			apiResponse.setStatus(true);
			apiResponse.setStatusCode(200);
		}

		FilterProvider filters = new SimpleFilterProvider().addFilter("ApiResponseFilter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(apiResponse);

		mapping.setFilters(filters);

		return mapping;

	}

	@GetMapping(path = "/deliveryDate/{orderId}", produces = "application/json")
	@ResponseBody
	public MappingJacksonValue getDeliveryDate(@PathVariable String orderId) {

		ApiResponse<String, Boolean, Integer, String> apiResponse = new ApiResponse<String, Boolean, Integer, String>();

		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("data", "message", "status",
				"statusCode");

		String deliveryDate = shippingServices.getDeliveryDate(orderId);

		apiResponse.setMessage("Success");
		apiResponse.setStatus(true);
		apiResponse.setStatusCode(200);
		apiResponse.set(deliveryDate);

		FilterProvider filters = new SimpleFilterProvider().addFilter("ApiResponseFilter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(apiResponse);

		mapping.setFilters(filters);

		return mapping;

	}

	@PutMapping(path = "/updatedeliverydate", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public MappingJacksonValue updategetDeliveryDate(@RequestBody Map<String, String> json) {
		ApiResponse<String, Boolean, Integer, Integer> apiResponse = new ApiResponse<String, Boolean, Integer, Integer>();

		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("message", "status",
				"statusCode");

		System.out.println("orderID==" + json.get("orderId") + "==" + json.get("deliveryDate"));

		int updatedId = shippingServices.updateDeliveryDate(json.get("orderId"), json.get("deliveryDate"));

		if (updatedId > 0) {
			apiResponse.setMessage("Delivery date Updated successfully");
			apiResponse.setData(updatedId);
			apiResponse.setStatus(true);
			apiResponse.setStatusCode(200);
		}

		FilterProvider filters = new SimpleFilterProvider().addFilter("ApiResponseFilter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(apiResponse);

		mapping.setFilters(filters);

		return mapping;

	}

	@GetMapping(path = "/", produces = "application/json")
	@ResponseBody
	public MappingJacksonValue getShipping() {
		ApiResponse<String, Boolean, Integer, RequestShippingEntity> apiResponse = new ApiResponse<String, Boolean, Integer, RequestShippingEntity>();

		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("dataArray", "message", "status",
				"statusCode");

		List<RequestShippingEntity> shippingList = shippingServices.getShipping();

		apiResponse.setMessage("success");
		apiResponse.setT1(shippingList);
		apiResponse.setStatus(true);
		apiResponse.setStatusCode(200);

		FilterProvider filters = new SimpleFilterProvider().addFilter("ApiResponseFilter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(apiResponse);

		mapping.setFilters(filters);

		return mapping;

	}

	@GetMapping(path = "/shipment/{orderId}", produces = "application/json")
	@ResponseBody
	public MappingJacksonValue getOneShipping(@PathVariable String orderId) {
		ApiResponse<String, Boolean, Integer, RequestShippingEntity> apiResponse = new ApiResponse<String, Boolean, Integer, RequestShippingEntity>();

		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("data", "message", "status",
				"statusCode");

		RequestShippingEntity shippment = shippingServices.getOneShipping(orderId);

		if (shippment != null) {
			apiResponse.setMessage("success");
			apiResponse.setData(shippment);
			apiResponse.setStatus(true);
			apiResponse.setStatusCode(200);

			FilterProvider filters = new SimpleFilterProvider().addFilter("ApiResponseFilter", filter);

			MappingJacksonValue mapping = new MappingJacksonValue(apiResponse);

			mapping.setFilters(filters);

			return mapping;
		} else
			throw new RecordNotFoundException("invalid item id");

	}

	@DeleteMapping(path = "/deleteShipping/{orderId}", produces = "application/json")
	@ResponseBody
	public MappingJacksonValue deleteShipping(@PathVariable String orderId) {
		ApiResponse<String, Boolean, Integer, RequestInventoryEntity> apiResponse = new ApiResponse<String, Boolean, Integer, RequestInventoryEntity>();
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("message", "status",
				"statusCode");
		int isDeleted = shippingServices.deleteShipping(orderId);
		if (isDeleted > 0) {
			apiResponse.setMessage("successfully Deleted");
			apiResponse.setStatus(true);
			apiResponse.setStatusCode(200);

		}

		FilterProvider filters = new SimpleFilterProvider().addFilter("ApiResponseFilter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(apiResponse);

		mapping.setFilters(filters);

		return mapping;

	}

	/*
	 * { "orderId": "232323", "uid":"u100", "currency": "INR", "amount": 200,
	 * "paymentMode": "NetBanking", "customerName": "HCL", "addreess": "lucknow" }
	 */

	/*
	 * { "orderId": "232323", "status" : "ready for shipping" } { "orderId":
	 * "232323", "delivery_date" : "ready for shipping" }
	 */
}
