package com.ecommerce.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_shipping")
public class RequestShippingEntity {

	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long sid;

	@NotEmpty(message = "order id must not be empty")
	@Id
	private String orderID;

	@NotEmpty(message = "user id must not be empty")
	private String userId;

	@NotEmpty(message = "name must not be empty")
	private String name;

	@NotNull(message = "cost must not be empty")
	private float cost;

	@NotEmpty(message = "currency must not be empty")
	private String currency;

	@NotEmpty(message = "delivery status must not be empty")
	private String deliveryStatus;

	private LocalDateTime deliveryDate;

	@NotEmpty(message = "payment mode must not be empty")
	private String paymentMode;

	@NotEmpty(message = "address must not be empty")
	private String address;

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public LocalDateTime getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDateTime deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public RequestShippingEntity(Long sid, String orderID, String userId, String name, float cost, String currency,
			String deliveryStatus, LocalDateTime deliveryDate, String paymentMode, String address) {
		super();
		this.sid = sid;
		this.orderID = orderID;
		this.userId = userId;
		this.name = name;
		this.cost = cost;
		this.currency = currency;
		this.deliveryStatus = deliveryStatus;
		this.deliveryDate = deliveryDate;
		this.paymentMode = paymentMode;
		this.address = address;
	}

	public LocalDateTime getCurrentDate() {

		LocalDateTime now = LocalDateTime.now();

		return now;

	}

	public RequestShippingEntity() {
		super();
	}

}
