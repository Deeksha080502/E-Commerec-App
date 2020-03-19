package com.ecommerce.domain;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFilter;

import java.lang.Boolean;
import java.util.List;

@SuppressWarnings("hiding")
@JsonFilter("ApiResponseFilter")
@Component
public class ApiResponse<String, Boolean, Integer, T> {

	private T data;
	private List<T> dataArray;
	private String message;
	private Boolean status;
	private Integer statusCode;

	public T get() {
		return this.data;
	}

	public void set(T data) {
		this.data = data;
	}

	public ApiResponse(String message, Boolean status, Integer statusCode, T data) {
		this.message = message;
		this.status = status;
		this.statusCode = statusCode;
		this.data = data;
	}

	public ApiResponse() {
		// TODO Auto-generated constructor stub
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<T> getdataArray() {
		return dataArray;
	}

	public void setT1(List<T> dataArray) {
		this.dataArray = dataArray;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getStatus() {
		return status;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public void setStatus(java.lang.Boolean boolean1) {
		// TODO Auto-generated method stub
		this.status = (Boolean) boolean1;
	}

}
