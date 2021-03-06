package com.flightinportugal;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FipApplicationTests {
	@Autowired
	private MockMvc mvc;

	private ResultActions invokeGet() throws Exception {
		return mvc.perform(get("/flight/avg?date_from=01/08/2020&source_airport=LIS&target_airport=OPO&partner=RYR")
				.accept(MediaType.APPLICATION_JSON));
	}

	@Test
	public void shouldReturnStatusOk() throws Exception {
		MvcResult result = invokeGet()
				.andExpect(status().isOk())
				.andReturn();
	}

}
