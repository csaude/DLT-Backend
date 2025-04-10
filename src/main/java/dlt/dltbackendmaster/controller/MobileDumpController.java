package dlt.dltbackendmaster.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.gemfire.config.annotation.EnableCompression;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dlt.dltbackendmaster.domain.ShareFileResponse;
import dlt.dltbackendmaster.domain.Users;
import dlt.dltbackendmaster.security.UserDetailsServiceImpl;
import dlt.dltbackendmaster.service.DAOService;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@RestController
@EnableCompression
@RequestMapping("/mobile-dump")
public class MobileDumpController {
	private static final int SUPERVISOR_PROFILE_ID = 18;
	Logger logger = LoggerFactory.getLogger(MobileDumpController.class);
	private final String API_HOME= "http://sharedev.csaude.org.mz";
	private final String App_NAME = "My Library";
	private final String PATH = "DREAMS/mobile_dumps/testes1";
	private final DAOService service;
	
	@Autowired
	public MobileDumpController(DAOService service) {
		this.service = service;
	}

	private String getSeafileAuthToken(String username, String password) throws IOException{
		OkHttpClient client = new OkHttpClient();

		  String jsonBody = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, jsonBody);


		Request request = new Request.Builder()
		  .url(API_HOME+"/api2/auth-token/")
		  .post(body)
		  .addHeader("accept", "application/json")
		  .addHeader("content-type", "application/json")
		  .build();

		Response response = client.newCall(request).execute();
		
		String responseBody = response.body().string();
        JSONObject jsonResponse = new JSONObject(responseBody);
        return jsonResponse.getString("token");
	}
	
	private String getSeafileRepo(String authToken) throws IOException {			
			OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
		  .url(API_HOME+"/api/v2.1/repos/")
		  .get()
		  .addHeader("accept", "application/json")
		  .addHeader("content-type", "application/json")
		  .addHeader("authorization", "Bearer "+authToken)
		  .build();

		Response response = client.newCall(request).execute();

		ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = response.body().string();;
		JsonNode rootNode = objectMapper.readTree(jsonResponse );

        String repoId = rootNode.get("repos").get(0).get("repo_id").asText();

		return repoId;
	}
	
	private String genarateRepoApiToken(String repoId, String authToken) throws IOException {
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		String jsonBody="{\n"
				+ "    \"app_name\": \""+App_NAME+"\",\n"
				+ "    \"permission\": \"rw\"\n"
				+ "}";
		
		okhttp3.RequestBody body = RequestBody.create(mediaType, jsonBody);
		try {
			Request request = new Request.Builder()
			  .url(API_HOME+"/api/v2.1/repos/"+repoId+"/repo-api-tokens/")
			  .post(body)
			  .addHeader("accept", "application/json")
			  .addHeader("content-type", "application/json")
			  .addHeader("authorization", "Bearer "+authToken)
			  .build();

			Response response = client.newCall(request).execute();
			return response.body().string();
		} catch (IOException e) {
			Request request = new Request.Builder()
					  .url(API_HOME+"/api/v2.1/repos/"+repoId+"/repo-api-tokens/"+App_NAME)
					  .put(body)
					  .addHeader("accept", "application/json")
					  .addHeader("content-type", "application/json")
					  .addHeader("authorization", "Bearer "+authToken)
					  .build();

					Response response = client.newCall(request).execute();
					return response.body().string();
		}
	}
	
	public String createShareLink(String repoId, String authToken) throws IOException {
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		String jsonBody="{\n"
				+ "    \"repo_id\": \""+repoId+"\",\n"
				+ "    \"path\": \"/"+PATH+"\",\n"
				+ "    \"expire_days\": \"1\",\n"
				+ "    \"permissions\": {\n"
				+ "        \"can_edit\": true,\n"
				+ "        \"can_download\": true,\n"
				+ "        \"can_upload\": true\n"
				+ "    }\n"
				+ "}";
		RequestBody body = RequestBody.create(mediaType, jsonBody);
		Request request = new Request.Builder()
		  .url(API_HOME+"/api/v2.1/repos/"+repoId+"/repo-api-tokens/")
		  .post(body)
		  .addHeader("accept", "application/json")
		  .addHeader("content-type", "application/json")
		  .addHeader("authorization", "Bearer "+authToken)
		  .build();

		Response response = client.newCall(request).execute();
		return response.body().string();
	}
	
	@GetMapping(path = "/generate-upload-link",  produces = "application/json")
	public ShareFileResponse genarateShareLink(
			@RequestParam("username") String username, 
			@RequestParam("password") String password
	) throws IOException{
		
		String authToken = getSeafileAuthToken(username, password);
		String repoId = getSeafileRepo(authToken);
		
		genarateRepoApiToken(repoId, authToken);
		createShareLink(repoId, authToken);
		
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
		  .url(API_HOME+"/api2/repos/"+repoId+"/upload-link/?p=/")
		  .get()
		  .addHeader("accept", "application/json")
		  .addHeader("authorization", "Bearer "+authToken)
		  .build();

		Response response = client.newCall(request).execute();
		
		String responseBody = response.body().string();
        String uploadLink = responseBody.replace("\"", "");
        
		ShareFileResponse shareFileResponse = new ShareFileResponse(uploadLink,authToken);
	
		return shareFileResponse;
	}

	@GetMapping(path = "/is-supervisor-authorized")
	public boolean isMSupervisor(
			@RequestParam("username") String username, 
			@RequestParam("localities") List<Integer> localities) 
	{
		List<Users> supervisors = service.GetAllEntityByNamedQuery("Users.findByProfileAndLocalities", SUPERVISOR_PROFILE_ID, localities);
		
		List <String> usernames = supervisors.stream()
				.map(Users::getUsername)
				.map(String::trim)
				.collect(Collectors.toList());
		
		return usernames.contains(username.trim());		
	}
}
