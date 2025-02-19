package dlt.dltbackendmaster.service;

import java.util.List;

import dlt.dltbackendmaster.domain.CountIntervention;
import dlt.dltbackendmaster.domain.References;

public interface ReferenceService {
	List<References> findByStatus(Integer status);
	List<CountIntervention> countByBeneficiary(Integer beneficiaryId);
}
