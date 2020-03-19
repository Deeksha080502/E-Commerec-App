package com.ecommerce.aop;

import org.springframework.aop.ThrowsAdvice;

public class ThrowsAdvisor implements ThrowsAdvice {

	public void logError(Exception ex) {
		System.out.println("additional concern if exception occurs");
		ex.printStackTrace();
	}
}
