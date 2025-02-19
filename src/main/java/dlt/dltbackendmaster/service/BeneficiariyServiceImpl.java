package dlt.dltbackendmaster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dlt.dltbackendmaster.domain.Beneficiaries;

/**
 * This class implements the Service interface
 * 
 * @author Francisco da Conceição Alberto Macuácua
 *
 */
@Service
public class BeneficiariyServiceImpl implements BeneficiariyService {
	@Autowired
	public DAOService daoService;

	@Override
	public void saveCompletionStatus(Integer beneficiaryId, Integer serviceCompletionLevel) {
		String queryName;

		switch (serviceCompletionLevel) {
		case 1:
			queryName = "Beneficiary.findByIdAndBeforeCompletedAServiceButNotFullPrimaryPackage";
			break;
		case 2:
			queryName = "Beneficiary.findByIdAndBeforeCompletedExactlyPrimaryPackage";
			break;
		default:
			queryName = "Beneficiary.findByIdAndBeforeCompletedPrimaryPackageAndAdditionalServices";
			break;
		}

		Beneficiaries beneficiary = daoService.GetUniqueEntityByNamedQuery(queryName, beneficiaryId,
				serviceCompletionLevel);
		if (beneficiary != null) {
			beneficiary.setCompletionStatus(serviceCompletionLevel);
			daoService.update(beneficiary);
		}
	}

}
