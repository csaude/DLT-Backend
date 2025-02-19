package dlt.dltbackendmaster.service;

import java.util.List;

import dlt.dltbackendmaster.domain.BeneficiariesInterventions;
import dlt.dltbackendmaster.domain.ReferencesServicesObject;
import dlt.dltbackendmaster.domain.CountIntervention;

/**
 * This interface holds the service implementations called by the API controller
 * 
 * @author Francisco da Conceição Alberto Macuácua
 *
 */
public interface BeneficiariyInterventionService {

	<T> ReferencesServicesObject registerServiceCompletionStatus(BeneficiariesInterventions intervention);

	List<BeneficiariesInterventions> findByBeneficiaryId(Integer beneficiaryId);

	List<CountIntervention> countAllInterventionsAndbByServiceType();
	
	List<BeneficiariesInterventions> findByBeneficiariesIds(Integer[] params);
	
	List<CountIntervention> countInterventionsByBeneficiaryAndServiceType(Integer beneficiaryId);
	
	List<CountIntervention> countInterventionsByBeneficiaryAndAgeBandAndLevel(Integer beneficiaryId, Integer ageBand,
			Integer level);
}
