package dlt.dltbackendmaster.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import dlt.dltbackendmaster.domain.Services;

public class ServiceSerializer extends JsonSerializer<Services>
{
    @Override
    public void serialize(Services value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        Services service = new Services();
        service.setId(value.getId());
        service.setName(value.getName());
        service.setDescription(value.getDescription());
        service.setHidden(value.getHidden());
        service.setCoreService(service.getCoreService());
        service.setStatus(service.getStatus());
        service.setCreatedBy(value.getCreatedBy());
        service.setDateCreated(value.getDateCreated());
        service.setUpdatedBy(value.getUpdatedBy());
        service.setDateUpdated(value.getDateUpdated());
        gen.writeObject(service);
    }
}
