package dlt.dltbackendmaster.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import dlt.dltbackendmaster.domain.District;

public class DistrictSerializer extends JsonSerializer<District>
{

    @Override
    public void serialize(District value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        District district = new District();
        district.setId(value.getId());
        district.setProvince(value.getProvince());
        district.setCode(value.getCode());
        district.setName(value.getName());
//        district.setCreatedBy(value.getCreatedBy());
//        district.setDateCreated(value.getDateCreated());
//        district.setUpdatedBy(value.getUpdatedBy());
//        district.setDateUpdated(value.getDateUpdated());
        gen.writeObject(district);
    }

}
