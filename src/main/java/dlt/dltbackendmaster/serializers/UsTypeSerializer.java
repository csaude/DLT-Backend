package dlt.dltbackendmaster.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import dlt.dltbackendmaster.domain.UsType;

public class UsTypeSerializer extends JsonSerializer<UsType> {

	@Override
	public void serialize(UsType value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		UsType usType = new UsType();
		usType.setId(value.getId());
		usType.setName(value.getName());
		usType.setDescription(value.getDescription());
		usType.setLevel(value.getLevel());
		usType.setType(value.getType());
		usType.setStatus(value.getStatus());
		/*usType.setDateCreated(value.getDateCreated());
		usType.setDateUpdated(value.getDateUpdated());
		usType.setCreatedBy(value.getCreatedBy());
		usType.setUpdatedBy(value.getUpdatedBy());*/
		gen.writeObject(usType);
	}

}
