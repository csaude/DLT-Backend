package dlt.dltbackendmaster.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import dlt.dltbackendmaster.domain.Users;

public class UsersSerializer extends JsonSerializer<Users> {

	@Override
	public void serialize(Users value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		Users user = new Users();
		user.setId(value.getId());
		user.setName(value.getName());
		user.setEmail(value.getEmail());
		user.setUsername(value.getUsername());
		user.setPhoneNumber(value.getPhoneNumber());
		user.setSurname(value.getSurname());
		user.setEntryPoint(value.getEntryPoint());
		user.setStatus(value.getStatus());
		user.setProvinces(value.getProvinces());
		user.setDistricts(value.getDistricts());
		user.setLocalities(value.getLocalities());
		user.setPartners(value.getPartners());
		user.setProfiles(value.getProfiles());
		user.setUs(value.getUs());
		gen.writeObject(user);
	}

}
