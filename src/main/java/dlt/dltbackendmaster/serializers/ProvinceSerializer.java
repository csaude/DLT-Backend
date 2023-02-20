package dlt.dltbackendmaster.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import dlt.dltbackendmaster.domain.Province;

public class ProvinceSerializer extends JsonSerializer<Province>
{

    @Override
    public void serialize(Province value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        Province province = new Province();
        province.setId(value.getId());
        province.setCode(value.getCode());
        province.setName(value.getName());
//        province.setCreatedBy(value.getCreatedBy());
//        province.setCreateDate(value.getCreateDate());
//        province.setUpdatedBy(value.getUpdatedBy());
//        province.setUpdateDate(value.getUpdateDate());
        gen.writeObject(province);
    }

}
