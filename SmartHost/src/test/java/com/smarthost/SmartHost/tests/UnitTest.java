package com.smarthost.SmartHost.tests;

//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.smarthost.SmartHost.controller.CoreController;

public class UnitTest {
	
	@Test
	public void test1() {
		CoreController controller = new CoreController();
		assertEquals("<p>Usage premium: 3(EUR <b>738</b>)</p><br><p>Usage economy: 3(EUR <b>167.99</b>)</p>", controller.getIncome(3, 3));
	}
	
	@Test
	public void test2() {
		CoreController controller = new CoreController();
		assertEquals("<p>Usage premium: 6(EUR <b>1054</b>)</p><br><p>Usage economy: 4(EUR <b>189.99</b>)</p>", controller.getIncome(7, 5));
	}
	
	@Test
	public void test3() {
		CoreController controller = new CoreController();
		assertEquals("<p>Usage premium: 2(EUR <b>583</b>)</p><br><p>Usage economy: 4(EUR <b>189.99</b>)</p>", controller.getIncome(2, 7));
	}
	
	@Test
	public void test4() {
		CoreController controller = new CoreController();
		assertEquals("<p>Usage premium: 7(EUR <b>1153.99</b>)</p><br><p>Usage economy: 1(EUR <b>45</b>)</p>", controller.getIncome(7, 1));
	}
	
}
