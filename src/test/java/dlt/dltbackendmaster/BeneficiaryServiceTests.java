package dlt.dltbackendmaster;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * This class implements the Service interface
 * 
 * @author Francisco da Conceicao Alberto Macuacua
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
class BeneficiaryServiceTests {
	// Replace "YOUR_TOKEN_HERE" with the actual token
	private static String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmbWFjdWFjdWEifQ.iEAYoRSUL-7BNtYaZiJ1OS-IXthri-1znKLE2ipBpeY5JLfqPqBv448_vz3_-yvhyRIXN9vw8g6ywrtiplAdXw";

	@Autowired
	private MockMvc mockMvc;

	@Test
	void whenGetBeneficiaries_thenReturnsStatusOK() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/beneficiaries").param("userId", "5324")
				.param("level", "CENTRAL").param("pageIndex", "0").param("pageSize", "100").param("searchName", "")
				.param("searchUserCreator", "1").param("searchDistrict", "1").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token)).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void whenGetBeneficiaryById_thenReturnsStatusOK() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/beneficiaries/400").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token)).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void whenGetCountByFilters_thenReturnsStatusOK() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/beneficiaries/countByFilters").param("userId", "5324")
				.param("level", "CENTRAL").param("pageIndex", "0").param("pageSize", "100").param("searchName", "")
				.param("searchUserCreator", "1").param("searchDistrict", "1").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token)).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void whenGetByNui_thenReturnsStatusOK() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/beneficiaries/findByNui").param("userId", "5324")
				.param("nui", "0000486").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token)).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void whenGetByPartnerId_thenReturnsStatusOK() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/beneficiaries/findByPartnerId").param("partnerId", "767655")
				.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
