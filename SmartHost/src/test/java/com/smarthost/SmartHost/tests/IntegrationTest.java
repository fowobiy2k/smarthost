package com.smarthost.SmartHost.tests;

//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.smarthost.SmartHost.controller.CoreController;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CoreController.class)
public class IntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void test1() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/income?premiumRooms=3&economyRooms=3");
		MvcResult result = mockMvc.perform(request).andReturn();
		assertEquals("<p>Usage premium: 3(EUR <b>738</b>)</p><br><p>Usage economy: 3(EUR <b>167.99</b>)</p>", result.getResponse().getContentAsString());
	}
	
	
	@Test
	public void test2() throws Exception {
		mockMvc.perform(get("/income?premiumRooms=7&economyRooms=5")).andExpect(content().string("<p>Usage premium: 6(EUR <b>1054</b>)</p><br><p>Usage economy: 4(EUR <b>189.99</b>)</p>"));
		
	}
	
	@Test
	public void test3() throws Exception {
		mockMvc.perform(get("/income?premiumRooms=2&economyRooms=7")).andExpect(content().string("<p>Usage premium: 2(EUR <b>583</b>)</p><br><p>Usage economy: 4(EUR <b>189.99</b>)</p>"));
		
	}
	
	@Test
	public void test4() throws Exception {
		mockMvc.perform(get("/income?premiumRooms=7&economyRooms=1")).andExpect(content().string("<p>Usage premium: 7(EUR <b>1153.99</b>)</p><br><p>Usage economy: 1(EUR <b>45</b>)</p>"));
		
	}

}
