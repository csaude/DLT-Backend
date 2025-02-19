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
class ProvinceServiceTests {
	// Replace "YOUR_TOKEN_HERE" with the actual token
	private static String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmbWFjdWFjdWEifQ.iEAYoRSUL-7BNtYaZiJ1OS-IXthri-1znKLE2ipBpeY5JLfqPqBv448_vz3_-yvhyRIXN9vw8g6ywrtiplAdXw";

	@Autowired
	private MockMvc mockMvc;

	@Test
	void whenGetAllProvinces_thenReturnsStatusOK() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/provinces").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token)).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void whenGetProvinceDisctricts_thenReturnsStatusOK() throws Exception {
		String[] provincesIds = { "1", "2" };

		mockMvc.perform(MockMvcRequestBuilders.get("/api/provdisctricts").param("provinces", provincesIds)
				.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void whenGetDistictLocalities_thenReturnsStatusOK() throws Exception {
		String[] districtsIds = { "1", "2" };
		mockMvc.perform(MockMvcRequestBuilders.get("/api/distlocalities").param("districts", districtsIds)
				.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void whenGetLocalNeighborhoods_thenReturnsStatusOK() throws Exception {
		String[] localitiesIds = { "1", "2" };
		mockMvc.perform(MockMvcRequestBuilders.get("/api/localneighborhoods").param("localities", localitiesIds)
				.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void whenGetLocalUss_thenReturnsStatusOK() throws Exception {
		String[] localitiesIds = { "1", "2" };
		mockMvc.perform(MockMvcRequestBuilders.get("/api/localus").param("localities", localitiesIds)
				.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void whenGetProvincesNames_thenReturnsStatusOK() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/provinces/get-provinces")
				.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void whenGetByActiveProvinces_thenReturnsStatusOK() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/getprovinces").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token)).andExpect(MockMvcResultMatchers.status().isOk());
	}

//	@Test
//	void givenValidJson_whenPostProvince_thenReturnsStatusOK() throws Exception {
//		// Corrected JSON format with escaped backslashes for special characters
//		String json = "{\"id\":2,\"code\":\"GZ\",\"name\":\"Gaza\",\"status\":1,\"updatedBy\":1,\"createdBy\":1,\"createDate\":\"2021-03-01T00:00:00Z\"}";
//
//		mockMvc.perform(MockMvcRequestBuilders.post("/api/provinces").content(json)
//				.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
//				.andExpect(MockMvcResultMatchers.status().isOk());
//	}
//
//	@Test
//	void givenValidJson_whenPutProvince_thenReturnsStatusOK() throws Exception {
//		String json = "{\"id\":2,\"code\":\"GZ\",\"name\":\"Gaza\",\"status\":1,\"updatedBy\":\\1,\"createdBy\":1,\"createDate\":\"2021-03-01T00:00:00Z\"}";
//		
//		mockMvc.perform(MockMvcRequestBuilders.put("/api/provinces").content(json)
//				.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
//				.andExpect(MockMvcResultMatchers.status().isOk());
//	}

}
