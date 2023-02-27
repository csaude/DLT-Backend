package dlt.dltbackendmaster.service;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dlt.dltbackendmaster.domain.Beneficiaries;
import dlt.dltbackendmaster.domain.BeneficiariesInterventions;
import dlt.dltbackendmaster.domain.References;
import dlt.dltbackendmaster.domain.ReferencesServices;
import dlt.dltbackendmaster.domain.ReferencesServicesObject;
import dlt.dltbackendmaster.domain.SubServices;
import dlt.dltbackendmaster.repository.DAORepository;
import dlt.dltbackendmaster.util.ServiceCompletionRules;

/**
 * This class implements the Service interface
 * @author derciobucuane
 *
 */
@Service
public class DAOServiceImpl implements DAOService{

	@Autowired
	private DAORepository repository;

	@Transactional(readOnly = true)
	public <T> List<T> getAll(Class<T> klass) {
		return repository.getAll(klass);
	}

	@Transactional(readOnly = true)
	public <T> List<T> getAllQuery(String s) {
		return repository.getAllQuery(s);
	}

	@Transactional
	public <T> Serializable Save(T klass) {
		return repository.Save(klass);
	}

	@Transactional
	public <T> void delete(T klass) {
		repository.delete(klass);
	}

	@Transactional
	public <T> T update(T klass) {
		return repository.update(klass);
	}

	@Transactional
	public <T> int updateQuery(String query, Object... params) {
		return repository.updateQuery(query, params);
	}

	@Transactional
	public <T> int count(Class<T> klass) {
		return repository.count(klass);
	}

	@Transactional
	public <T> boolean exist(T klass) {
		return repository.exist(klass);
	}

	@Transactional
	public <T> T GetUniqueEntityByNamedQuery(String query, Object... params) {
		return repository.GetUniqueEntityByNamedQuery(query, params);
	}

	@Transactional
	public <T> List<T> GetAllEntityByNamedQuery(String query, Object... params) {
		return repository.GetAllEntityByNamedQuery(query, params);
	}
	
	@Transactional
	public <T> List<T> GetAllPagedEntityByNamedQuery(String query, int pageIndex, int pageSize, Object... params) {
		return repository.GetAllPagedEntityByNamedQuery(query,pageIndex,pageSize, params);
	}
	
	@Transactional
    public <T> List<T> GetAllEntityByNamedNativeQuery(String query, Object... params) {
        return repository.GetAllEntityByNamedNativeQuery(query, params);
    }

	@Override
	public <T> T find(Class<T> klass, Object id) {
		return repository.find(klass, id);
	}

	@Transactional
	public <T> List<T> findByQuery(String hql, Map<String, Object> entidade, Map<String, Object> namedParams) {
		return repository.findByQuery(hql, entidade, namedParams);
	}

	@Transactional
	public <T> List<T> findByQueryFilter(String hql, Map<String, Object> entidade, Map<String, Object> namedParams,
			int f, int m) {
		return repository.findByQueryFilter(hql, entidade, namedParams, f, m);
	}

	@Transactional
	public <T> List<T> findByJPQuery(String hql, Map<String, Object> namedParams) {
		return repository.findByJPQuery(hql, namedParams);
	}

	@Transactional
	public <T> List<T> findByJPQueryFilter(String hql, Map<String, Object> namedParams, int f, int m) {
		return repository.findByJPQueryFilter(hql, namedParams, f, m);
	}

	
	public Class<? extends Annotation> annotationType() {
		// TODO Auto-generated method stub
		return null;
	}


	public String value() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> int UpdateEntitiesByNamedQuery(String query, Object... params) {
		// TODO Auto-generated method stub
		return repository.UpdateEntitiesByNamedQuery(query, params);
	}

	@Override
	public <T> ReferencesServicesObject registerServiceCompletionStatus(BeneficiariesInterventions intervention) {
		
        
        SubServices subService = find(SubServices.class, intervention.getId().getSubServiceId());
        intervention.setSubServices(subService);
        
        // Actualizar o status dos serviços solicitados
        Beneficiaries beneficiary = find(Beneficiaries.class, intervention.getBeneficiaries().getId());
        Integer serviceId = subService.getServices().getId();
        List<ReferencesServices> referencesServices = GetAllEntityByNamedQuery("ReferencesServices.findByBeneficiaryAndService",
                                                                                       beneficiary.getId(),
                                                                                       serviceId);
        
        List<References> updatedReferences = new ArrayList<>();
        
        for (ReferencesServices referenceServices : referencesServices) {

            Integer referenceServiceStatus = ServiceCompletionRules.getReferenceServiceStatus(beneficiary,
                                                                                              serviceId);

            if (referenceServiceStatus.intValue() != (referenceServices.getStatus())) {
                referenceServices.setStatus(referenceServiceStatus);
                referenceServices.setDateUpdated(new Date());
                referenceServices.setUpdatedBy(subService.getCreatedBy());
                referenceServices = update(referenceServices);

                // Actualizar o status da referências
                References reference = referenceServices.getReferences();
                Integer referenceStatus = ServiceCompletionRules.getReferenceStatus(reference);

                if (referenceStatus.intValue() != reference.getStatus()) {
                    reference.setStatus(referenceStatus);
                    reference.setDateUpdated(new Date());
                    reference.setUpdatedBy(subService.getCreatedBy());
                    reference = update(reference);
                }
                updatedReferences.add(reference);
            }
        }
        ReferencesServicesObject referenceServiceObject = new ReferencesServicesObject(intervention,
                updatedReferences);
		
		return referenceServiceObject;
		
	}
}
