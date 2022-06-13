package dlt.dltbackendmaster.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import dlt.dltbackendmaster.domain.SubServices;

public class SubServiceSerializer extends JsonSerializer<SubServices>
{
    @Override
    public void serialize(SubServices value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        SubServices subService = new SubServices();
        subService.setId(value.getId());
        subService.setServices(value.getServices());
        subService.setName(value.getName());
        subService.setRemarks(value.getRemarks());
        subService.setHidden(value.getHidden());
        subService.setMandatory(value.getMandatory());
        subService.setSortOrder(value.getSortOrder());
        subService.setStatus(value.getStatus());
        subService.setCreatedBy(value.getCreatedBy());
        subService.setDateCreated(value.getDateCreated());
        subService.setUpdatedBy(value.getUpdatedBy());
        subService.setDateUpdated(value.getDateUpdated());
        gen.writeObject(subService);
    }
}
