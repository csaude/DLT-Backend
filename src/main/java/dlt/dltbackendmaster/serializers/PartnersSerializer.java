package dlt.dltbackendmaster.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import dlt.dltbackendmaster.domain.Partners;


public class PartnersSerializer extends JsonSerializer<Partners>{

	@Override
	public void serialize(Partners value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		Partners partner = new Partners();
		partner.setId(value.getId());
		partner.setName(value.getName());
		partner.setDescription(value.getDescription());
		partner.setAbbreviation(value.getAbbreviation());
		gen.writeObject(partner);
		
	}

}
