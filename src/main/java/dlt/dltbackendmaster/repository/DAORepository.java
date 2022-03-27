package dlt.dltbackendmaster.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * This interface represents the integrated Repository for all domains 
 * @author derciobucuane
 *
 */
public interface DAORepository {
	<T> List<T> getAll(Class<T> klass);

	<T> List<T> getAllQuery(String s);

	<T> T update(T klass);

	<T> boolean exist(T klass);

	<T> int updateQuery(String query, Object... params);

	<T> int count(Class<T> klass);

	<T> Serializable Save(T klass);

	<T> T GetUniqueEntityByNamedQuery(String query, Object... params);

	<T> List<T> GetAllEntityByNamedQuery(String query, Object... params);

	<T> T find(Class<T> klass, Object id);

	<T> List<T> findByQuery(String hql, Map<String, Object> entidade, Map<String, Object> namedParams);

	<T> List<T> findByQueryFilter(String hql, Map<String, Object> entidade, Map<String, Object> namedParams, int f, int m);

	<T> List<T> findByJPQuery(String hql,Map<String, Object> namedParams);

	<T> List<T> findByJPQueryFilter(String hql, Map<String, Object> namedParams, int f, int m);

	<T> void delete(T klass);
}
