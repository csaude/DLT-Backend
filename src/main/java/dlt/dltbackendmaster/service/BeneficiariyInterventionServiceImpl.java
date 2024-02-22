package dlt.dltbackendmaster.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dlt.dltbackendmaster.domain.Beneficiaries;
import dlt.dltbackendmaster.domain.BeneficiariesInterventions;
import dlt.dltbackendmaster.domain.CountIntervention;
import dlt.dltbackendmaster.domain.References;
import dlt.dltbackendmaster.domain.ReferencesServices;
import dlt.dltbackendmaster.domain.ReferencesServicesObject;
import dlt.dltbackendmaster.domain.SubServices;
import dlt.dltbackendmaster.util.ServiceCompletionRules;

/**
 * This class implements the Service interface
 * 
 * @author Francisco da Conceição Alberto Macuácua
 *
 */
@Service
public class BeneficiariyInterventionServiceImpl implements BeneficiariyInterventionService {
	@Autowired
	public DAOService daoService;

	@Override
	public <T> ReferencesServicesObject registerServiceCompletionStatus(BeneficiariesInterventions intervention) {

		SubServices subService = daoService.find(SubServices.class, intervention.getId().getSubServiceId());
		intervention.setSubServices(subService);

		// Actualizar o status dos serviços solicitados
		Beneficiaries beneficiary = daoService.find(Beneficiaries.class, intervention.getBeneficiaries().getId());
		Integer serviceId = subService.getServices().getId();
		List<ReferencesServices> referencesServices = daoService.GetAllEntityByNamedQuery(
				"ReferencesServices.findByBeneficiaryAndService", beneficiary.getId(), serviceId);

		List<References> updatedReferences = new ArrayList<>();

		for (ReferencesServices referenceServices : referencesServices) {

			Set<BeneficiariesInterventions> interventions = beneficiary.getBeneficiariesInterventionses();
			Integer referenceServiceStatus = ServiceCompletionRules.getReferenceServiceStatus(interventions, serviceId);

			if (referenceServiceStatus.intValue() != (referenceServices.getStatus())) {
				referenceServices.setStatus(referenceServiceStatus);
				referenceServices.setDateUpdated(new Date());
				referenceServices.setUpdatedBy(subService.getCreatedBy());
				referenceServices = daoService.update(referenceServices);

				// Actualizar o status da referências
				References reference = referenceServices.getReferences();
				Integer referenceStatus = ServiceCompletionRules.getReferenceStatus(reference, interventions);

				if (referenceStatus.intValue() != reference.getStatus()) {
					reference.setStatus(referenceStatus);
					reference.setDateUpdated(new Date());
					reference.setUpdatedBy(subService.getCreatedBy());
					reference = daoService.update(reference);
				}
				updatedReferences.add(reference);
			}
		}
		ReferencesServicesObject referenceServiceObject = new ReferencesServicesObject(intervention, updatedReferences);

		return referenceServiceObject;

	}

	@Override
	public List<BeneficiariesInterventions> findByBeneficiaryId(Integer beneficiaryId) {

		List<BeneficiariesInterventions> beneficiariesInterventions = daoService
				.GetAllEntityByNamedQuery("BeneficiaryIntervention.findByBeneficiaryId", beneficiaryId);

		return beneficiariesInterventions;
	}

	@Override
	public List<CountIntervention> countAllInterventionsAndbByServiceType() {

		List<CountIntervention> beneficiariesInterventions = daoService
				.GetAllEntityByNamedQuery("BeneficiaryIntervention.countAllInterventionsAndbByServiceType");

		return beneficiariesInterventions;
	}
	
	@Override
	public List<BeneficiariesInterventions> findByBeneficiariesIds(Integer[] params) {	
        List<BeneficiariesInterventions> beneficiariesInterventions = daoService.GetAllEntityByNamedQuery("BeneficiaryIntervention.findInterventionsByBeneficiariesIds", Arrays.asList(params));

		return beneficiariesInterventions;
	}

	@Override
	public List<CountIntervention> countInterventionsByBeneficiaryAndServiceType(Integer beneficiaryId) {
		List<CountIntervention> beneficiariesInterventions = daoService
				.GetAllEntityByNamedQuery("BeneficiaryIntervention.countInterventionsByBeneficiaryAndServiceType", beneficiaryId);

		return beneficiariesInterventions;
	}

	@Override
	public List<CountIntervention> countInterventionsByBeneficiaryAndAgeBandAndLevel(Integer beneficiaryId, Integer ageBand, Integer level) {
		List<CountIntervention> beneficiariesInterventions = daoService
				.GetEntityByNamedQuery("BeneficiaryIntervention.countInterventionsByBeneficiaryAndAgeBandAndLevel", beneficiaryId, ageBand, level);

		return beneficiariesInterventions;
	}
}
