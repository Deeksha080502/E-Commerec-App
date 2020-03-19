package com.ecommerce.domain;

public class RequestOrder {


	private String orderId;
	 private String uid;
	 private String currency;
	 private float amount;
	 private String paymentMode;
	 private String customerName;
	 private String addreess;


	 // Getter Methods 

	 public String getOrderId() {
	  return orderId;
	 }

	 public String getUid() {
	  return uid;
	 }

	 public String getCurrency() {
	  return currency;
	 }

	 public float getAmount() {
	  return amount;
	 }

	 public String getPaymentMode() {
	  return paymentMode;
	 }

	 public String getCustomerName() {
	  return customerName;
	 }

	 public String getAddreess() {
	  return addreess;
	 }

	 // Setter Methods 

	 public void setOrderId(String orderId) {
	  this.orderId = orderId;
	 }

	 public void setUid(String uid) {
	  this.uid = uid;
	 }

	 public void setCurrency(String currency) {
	  this.currency = currency;
	 }

	 public void setAmount(float amount) {
	  this.amount = amount;
	 }

	 public void setPaymentMode(String paymentMode) {
	  this.paymentMode = paymentMode;
	 }

	 public void setCustomerName(String customerName) {
	  this.customerName = customerName;
	 }

	 public void setAddreess(String addreess) {
	  this.addreess = addreess;
	 }
}
