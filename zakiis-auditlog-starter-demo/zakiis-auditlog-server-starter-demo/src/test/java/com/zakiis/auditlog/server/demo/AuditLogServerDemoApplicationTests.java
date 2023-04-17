package com.zakiis.auditlog.server.demo;

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

import com.zakiis.auditlog.domain.dto.AuditLogQueryReq;
import com.zakiis.core.util.JsonUtil;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class AuditLogServerDemoApplicationTests {
	
	@Value("${server.port}")
	private String port;
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void contextLoads() throws Exception {
		Thread.sleep(3000L);
		AuditLogQueryReq req = new AuditLogQueryReq()
			.setOperateGroup("System Management")
			.setOperateTargetId("1");
		mockMvc.perform(
			MockMvcRequestBuilders.get("/zakiis/audit-log/list")
				.accept(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(req))
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
	}
}
