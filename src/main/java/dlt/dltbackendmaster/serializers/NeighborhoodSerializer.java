package dlt.dltbackendmaster.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import dlt.dltbackendmaster.domain.Neighborhood;


public class NeighborhoodSerializer extends JsonSerializer<Neighborhood>
{
    @Override
    public void serialize(Neighborhood value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        Neighborhood neighborhood = new Neighborhood();
        neighborhood.setId(value.getId());
        neighborhood.setName(value.getName());
        neighborhood.setDescription(value.getDescription());
        neighborhood.setLocality(value.getLocality());
        neighborhood.setStatus(value.getStatus());
        neighborhood.setCreatedBy(value.getCreatedBy());
        neighborhood.setDateCreated(value.getDateCreated());
        neighborhood.setUpdatedBy(value.getUpdatedBy());
        neighborhood.setDateUpdated(value.getDateUpdated());
        gen.writeObject(neighborhood);
    }
}
