package com.zakiis.auditlog.client.demo;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.zakiis.auditlog.client.demo.domain.dto.UserInfo;
import com.zakiis.core.util.JsonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class AuditLogClientDemoApplicationTests {
	
	@Value("${server.port}")
	private String port;
	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() throws Exception {
		log.info("query user detail");
		mockMvc.perform(
			MockMvcRequestBuilders.get("/user/detail/1")
				.accept(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
		
		log.info("modify user info");
		UserInfo userInfo = new UserInfo().setRealName("李四");
		mockMvc.perform(
			MockMvcRequestBuilders.put("/user/1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(userInfo))
				
		).andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
		
		log.info("modify not exists user info");
		mockMvc.perform(
			MockMvcRequestBuilders.put("/user/2")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(userInfo))
				
		).andDo(MockMvcResultHandlers.print());
		
		log.info("batch delete user");
		mockMvc.perform(
			MockMvcRequestBuilders.delete("/user/batch")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("[1,2,3]")
		).andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
	}

	
}
