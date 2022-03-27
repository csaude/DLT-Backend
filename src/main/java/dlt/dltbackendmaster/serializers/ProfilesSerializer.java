package dlt.dltbackendmaster.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import dlt.dltbackendmaster.domain.Profiles;

public class ProfilesSerializer extends JsonSerializer<Profiles> {

	@Override
	public void serialize(Profiles value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		Profiles profiles = new Profiles();
		profiles.setId(value.getId());
		profiles.setDescription(value.getDescription());
		profiles.setName(value.getName());
		profiles.setOfflineId(value.getOfflineId());
		gen.writeObject(profiles);
		
	}

}
