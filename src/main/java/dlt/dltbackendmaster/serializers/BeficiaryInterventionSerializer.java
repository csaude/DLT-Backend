package dlt.dltbackendmaster.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import dlt.dltbackendmaster.domain.BeneficiariesInterventions;

public class BeficiaryInterventionSerializer extends JsonSerializer<BeneficiariesInterventions>
{

    @Override
    public void serialize(BeneficiariesInterventions value, JsonGenerator gen, SerializerProvider serializers)
                    throws IOException {
    	BeneficiariesInterventions intervention = new BeneficiariesInterventions();
        intervention.setBeneficiaries(value.getBeneficiaries());
        intervention.setSubServices(value.getSubServices());
        //intervention.setDate(value.getId().getDate());
        intervention.setEntryPoint(value.getEntryPoint());
        intervention.setResult(value.getResult());
        intervention.setProvider(value.getProvider());
        intervention.setActivistId(value.getActivistId());
        intervention.setRemarks(value.getRemarks());
    //    intervention.setUs_id(String.valueOf(value.getUs().getId()));
        intervention.setStatus(value.getStatus());
        intervention.setCreatedBy(value.getCreatedBy());
        intervention.setDateCreated(value.getDateCreated());
        intervention.setUpdatedBy(value.getUpdatedBy());
        intervention.setDateUpdated(value.getDateUpdated());
    }}
