package com.smarthost.SmartHost.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.smarthost.SmartHost.controller.CoreController;

public class UnitTest {
	
	@Test
	public void test1() {
		CoreController controller = new CoreController();
		assertEquals("Usage premium: 3(EUR 738)\nUsage economy: 3(EUR 167.99)", controller.getIncome(3, 3));
	}
	
	@Test
	public void test2() {
		CoreController controller = new CoreController();
		assertEquals("Usage premium: 6(EUR 1054)\nUsage economy: 4(EUR 189.99)", controller.getIncome(7, 5));
	}
	
	@Test
	public void test3() {
		CoreController controller = new CoreController();
		assertEquals("Usage premium: 2(EUR 583)\nUsage economy: 4(EUR 189.99)", controller.getIncome(2, 7));
	}
	
	@Test
	public void test4() {
		CoreController controller = new CoreController();
		assertEquals("Usage premium: 7(EUR 1153)\nUsage economy: 1(EUR 45)", controller.getIncome(7, 1));
	}
}
