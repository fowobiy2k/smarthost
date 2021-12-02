package com.smarthost.SmartHost.tests;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
		RequestBuilder request = MockMvcRequestBuilders.get("/income?premiunRooms=3&economyRooms=3");
		MvcResult result = mockMvc.perform(request).andReturn();
		assertEquals("Usage premium: 3(EUR 738)\nUsage economy: 3(EUR 167.99)", result.getResponse().getContentAsString());
	}
	
//	@Test
//	public void test1() throws Exception {
//		mockMvc.perform(get("/income?premiunRooms=3&economyRooms=3")).andDo(print()).andExpect(status().isOk());
////		assertEquals("Usage premium: 3(EUR 738)\nUsage economy: 3(EUR 167.99)", result.getResponse().getContentAsString());
//	}

}
