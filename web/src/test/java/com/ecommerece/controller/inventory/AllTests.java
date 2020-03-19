package com.ecommerece.controller.inventory;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ecommerce.services.InventoryServicesTest;
import com.ecommerce.services.ShippingServicesTest;
import com.ecommerece.controller.shipping.ShippingControllerIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({ InventoryControllerTest.class, InventoryControllerIntegrationTest.class, InventoryServicesTest.class,
		ShippingServicesTest.class, ShippingControllerIntegrationTest.class })
public class AllTests {

}
