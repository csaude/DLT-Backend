package dlt.dltbackendmaster.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import dlt.dltbackendmaster.domain.Us;

public class UsSerializer extends JsonSerializer<Us>{

	@Override
	public void serialize(Us value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		Us us = new Us();
		us.setId(value.getId());
		us.setCode(value.getCode());
//		us.setDescription(value.getDescription());
		us.setName(value.getName());
//		us.setLatitude(value.getLatitude());
//		us.setLongitude(value.getLongitude());
//		us.setStatus(value.getStatus());
//		us.setDateCreated(value.getDateCreated());
//		us.setDateUpdated(value.getDateUpdated());
//		us.setUpdatedBy(value.getUpdatedBy());
//		us.setCreatedBy(value.getCreatedBy());
		gen.writeObject(us);
		
	}

}
