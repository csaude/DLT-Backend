package dlt.dltbackendmaster.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import dlt.dltbackendmaster.domain.Locality;

public class LocalitySerializer extends JsonSerializer<Locality>{

	@Override
	public void serialize(Locality value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		Locality locality = new Locality();
		locality.setId(value.getId());
		locality.setName(value.getName());
//		locality.setDescription(value.getDescription());
//		locality.setCreatedBy(value.getCreatedBy());
//		locality.setDateCreated(value.getDateCreated());
//		locality.setDateUpdated(value.getDateUpdated());
		locality.setDistrict(value.getDistrict());
//		locality.setStatus(value.getStatus());
		gen.writeObject(locality);
		
		
	}

}
