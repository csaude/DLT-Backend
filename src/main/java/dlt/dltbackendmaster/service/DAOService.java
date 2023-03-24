package dlt.dltbackendmaster.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import dlt.dltbackendmaster.domain.BeneficiariesInterventions;
import dlt.dltbackendmaster.domain.ReferencesServicesObject;

/**
 * This interface holds the service implementations called by the API controller 
 * @author derciobucuane
 *
 */
public interface DAOService {
	<T> List<T> getAll(Class<T> klass);

	<T> List<T> getAllQuery(String s);

	<T> Serializable Save(T klass);

	<T> void delete(T klass);
	
	<T> int UpdateEntitiesByNamedQuery(String query, Object... params);

	<T> T update(T klass);

	<T> int updateQuery(String query, Object... params);

	<T> int count(Class<T> klass);

	<T> boolean exist(T klass);

	<T> T GetUniqueEntityByNamedQuery(String query, Object... params);

	<T> List<T> GetAllEntityByNamedQuery(String query, Object... params);
	
	<T> List<T> GetAllPagedEntityByNamedQuery(String query, int pageIndex, int pageSize, String searchNui, Object... params);
	
    <T> List<T> GetAllEntityByNamedNativeQuery(String query, Object... params);
	
	<T> T find(Class<T> klass, Object id);

	<T> List<T> findByQuery(String hql, Map<String, Object> entidade, Map<String, Object> namedParams);

	<T> List<T> findByQueryFilter(String hql, Map<String, Object> entidade, Map<String, Object> namedParams, int f, int m);

	<T> List<T> findByJPQuery(String hql, Map<String, Object> namedParams);

	<T> List<T> findByJPQueryFilter(String hql, Map<String, Object> namedParams, int f, int m);
	
	<T> ReferencesServicesObject registerServiceCompletionStatus(BeneficiariesInterventions intervention);
}
