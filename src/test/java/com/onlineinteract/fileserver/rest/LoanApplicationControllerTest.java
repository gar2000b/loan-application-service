package com.onlineinteract.fileserver.rest;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class LoanApplicationControllerTest {

	private static LoanApplicationController loanApplicationController;

	@BeforeClass
	public static void setup() {
		loanApplicationController = new LoanApplicationController();
	}
	
	@Test
	public void logCustomerTest() {
		Integer result = loanApplicationController.logCustomer("SIN Verified for customer: 1");
		assertEquals(Integer.valueOf(1), result);
	}
	
	@Test
	public void logCustomerFailTest() {
		Integer result = loanApplicationController.logCustomer("SIN Verified for customer 1");
		assertEquals(Integer.valueOf(-1), result);
	}

}
