package dlt.dltbackendmaster.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import dlt.dltbackendmaster.domain.Beneficiaries;

public class BeneficiarySerializer extends JsonSerializer<Beneficiaries>
{
    @Override
    public void serialize(Beneficiaries value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        Beneficiaries beneficiary = new Beneficiaries();
        beneficiary.setId(value.getId());
        beneficiary.setNui(value.getNui());
        beneficiary.setSurname(value.getSurname());
        beneficiary.setName(value.getName());
        beneficiary.setNickName(value.getNickName());
      //  beneficiary.setOrganization(value.getOrganization());
        beneficiary.setGender(value.getGender());
        beneficiary.setDateOfBirth(value.getDateOfBirth());
        beneficiary.setAddress(value.getAddress());
        beneficiary.setEMail(value.getEMail());
        beneficiary.setEntryPoint(value.getEntryPoint());
        beneficiary.setNeighborhood(value.getNeighborhood());
        beneficiary.setPartnerId(value.getPartnerId());
        beneficiary.setLocality(value.getLocality());
        beneficiary.setPhoneNumber(value.getPhoneNumber());
        beneficiary.setVia(value.getVia());
       // beneficiary.setUsId(value.getUsId());
        beneficiary.setStatus(value.getStatus());
        beneficiary.setVbltLivesWith(value.getVbltLivesWith());
        beneficiary.setVbltIsOrphan(value.getVbltIsOrphan());
        beneficiary.setVbltIsDeficient(value.getVbltIsDeficient());
        beneficiary.setVbltDeficiencyType(value.getVbltDeficiencyType());
        beneficiary.setVbltIsStudent(value.getVbltIsStudent());
        beneficiary.setVbltSchoolGrade(value.getVbltSchoolGrade());
        beneficiary.setVbltSchoolName(value.getVbltSchoolName());
        beneficiary.setVbltMarriedBefore(value.getVbltMarriedBefore());
        beneficiary.setVbltChildren(value.getVbltChildren());
        beneficiary.setVbltPregnantOrBreastfeeding(value.getVbltPregnantOrBreastfeeding());
        beneficiary.setVbltIsEmployed(value.getVbltIsEmployed());
        beneficiary.setVbltTestedHiv(value.getVbltTestedHiv());
        beneficiary.setVbltSexuallyActive(value.getVbltSexuallyActive());
        beneficiary.setVbltPregnantBefore(value.getVbltPregnantBefore());
        beneficiary.setVbltMultiplePartners(value.getVbltMultiplePartners());
        beneficiary.setVbltTraffickingVictim(value.getVbltTraffickingVictim());
        beneficiary.setVbltAlcoholDrugsUse(value.getVbltAlcoholDrugsUse());
        beneficiary.setVbltIsMigrant(value.getVbltIsMigrant());
        beneficiary.setVbltSexualExploitation(value.getVbltSexualExploitation());
        beneficiary.setVbltSexploitationTime(value.getVbltSexploitationTime());
        beneficiary.setVbltVbgVictim(value.getVbltVbgVictim());
        beneficiary.setVbltVbgType(value.getVbltVbgType());
        beneficiary.setVbltVbgTime(value.getVbltVbgTime());
        beneficiary.setVbltStiHistory(value.getVbltStiHistory());
        beneficiary.setVbltSexWorker(value.getVbltSexWorker());
        beneficiary.setVbltHouseSustainer(value.getVbltHouseSustainer());
        beneficiary.setCreatedBy(value.getCreatedBy());
        beneficiary.setDateCreated(value.getDateCreated());
        beneficiary.setUpdatedBy(value.getUpdatedBy());
        beneficiary.setDateUpdated(value.getDateUpdated());
        beneficiary.setOfflineId(value.getOfflineId());
        gen.writeObject(beneficiary);

    }
}
