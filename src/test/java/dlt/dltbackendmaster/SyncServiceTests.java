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
class SyncServiceTests {
	// Replace "YOUR_TOKEN_HERE" with the actual token
	private static String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmbWFjdWFjdWEifQ.iEAYoRSUL-7BNtYaZiJ1OS-IXthri-1znKLE2ipBpeY5JLfqPqBv448_vz3_-yvhyRIXN9vw8g6ywrtiplAdXw";

	@Autowired
	private MockMvc mockMvc;

	@Test
	void whenGetSynced_thenReturnsStatusOK() throws Exception {
		String username = "fmacuacua";
		mockMvc.perform(MockMvcRequestBuilders.get("/sync").param("username", username)
				.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void whenGetPagedUsersLastSync_thenReturnsStatusOK() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/sync/usersLastSync/paged").param("userId", "5324")
				.param("level", "CENTRAL").param("pageIndex", "0").param("pageSize", "100").param("searchUsername", "")
				.param("searchUserCreator", "1").param("searchDistrict", "1").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token)).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void whenGetCustomSynced_thenReturnsStatusOK() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/custom/sync/beneficiaries")
				.param("lastPulledAt", "2021-03-01T00:00:00Z").param("nui", "1111179938").param("userId", "5324")
				.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
