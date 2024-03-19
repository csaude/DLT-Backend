package dlt.dltbackendmaster.repository;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Parameter;
import javax.transaction.Transactional;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This class implements the DAO Repository Interface
 * 
 * @author derciobucuane
 *
 */
@Transactional
@Repository
public class DAORepositoryImpl implements DAORepository {

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> getAll(Class<T> klass) {
		return getCurrentSession().createQuery("from " + klass.getName()).list();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> getAllQuery(String s) {
		return getCurrentSession().createQuery(s).list();
	}

	@SuppressWarnings("unchecked")
	public <T> T update(T klass) {
		T updatedKlass = (T) getCurrentSession().merge(klass);
		return updatedKlass;

	}

	public <T> boolean exist(T klass) {
		return getCurrentSession().contains(klass);
	}

	@SuppressWarnings("rawtypes")
	public <T> int updateQuery(String query, Object... params) {
		NativeQuery q = getCurrentSession().createNativeQuery(query);
		int i = 0;
		for (Object o : params) {
			q.setParameter(i, o);
			i++;
		}
		int r = q.executeUpdate();
		return r;
	}

	public <T> int count(Class<T> klass) {
		Long l = (Long) getCurrentSession().createQuery("select count(c) from " + klass.getName() + " c")
				.uniqueResult();
		return l.intValue();
	}

	public <T> Serializable Save(T klass) {
		Serializable savedKlassId = getCurrentSession().save(klass);
		return savedKlassId;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T GetUniqueEntityByNamedQuery(String query, Object... params) {
		Query q = getCurrentSession().getNamedQuery(query);
		
		int i = 0;
		for (Parameter param : q.getParameters()) {
			q.setParameter(param, params[i++]);
		}

		List<T> results = q.list();

		T foundentity = null;
		if (!results.isEmpty()) {
			// ignores multiple results
			foundentity = results.get(0);
		}
		return foundentity;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T GetUniqueEntityByNamedQuery(String query, String searchNui, String searchName, Integer searchUserCreator, Integer searchDistrict, Object... params) {
		Query q = getCurrentSession().getNamedQuery(query);
		
		int i = 0;
		for (Parameter param : q.getParameters()) {
			if(!"searchNui".equals(param.getName()) && !"searchName".equals(param.getName()) && !"searchUserCreator".equals(param.getName()) && !"searchDistrict".equals(param.getName())) {
				q.setParameter(param, params[i]);
				i++;
			}else {
				q.setParameter("searchNui", "%"+searchNui+"%");
				q.setParameter("searchName", "%"+searchName+"%");
				q.setParameter("searchUserCreator",searchUserCreator); 
				q.setParameter("searchDistrict", searchDistrict); 
			}	
		}

		List<T> results = q.list();

		T foundentity = null;
		if (!results.isEmpty()) {
			// ignores multiple results
			foundentity = results.get(0);
		}
		return foundentity;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T GetUniqueEntityByNamedQuery(String query, String searchNui, Integer searchUserCreator, Integer searchDistrict, Object... params) {
		Query q = getCurrentSession().getNamedQuery(query);
		
		int i = 0;
		for (Parameter param : q.getParameters()) {
			if(!"searchNui".equals(param.getName()) && !"searchUserCreator".equals(param.getName()) && !"searchDistrict".equals(param.getName())) {
				q.setParameter(param, params[i]);
				i++;
			}else {
				q.setParameter("searchNui", "%"+searchNui+"%");
				q.setParameter("searchUserCreator",searchUserCreator); 
				q.setParameter("searchDistrict", searchDistrict); 
			}	
		}

		List<T> results = q.list();

		T foundentity = null;
		if (!results.isEmpty()) {
			// ignores multiple results
			foundentity = results.get(0);
		}
		return foundentity;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> GetAllEntityByNamedQuery(String query, Object... params) {
		Query q = getCurrentSession().getNamedQuery(query);
		int i = 0;
		for (Parameter param : q.getParameters()) {
			q.setParameter(param, params[i++]);
		}

		List<T> results = q.getResultList();

		return results;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> GetEntityByNamedQuery(String query, int beneficiaryId,List<Integer> servicesIds) {
		Query q = getCurrentSession().getNamedQuery(query);
		
		q.setParameter("beneficiaryId", beneficiaryId);
		q.setParameter("servicesIds",servicesIds); 
				
		List<T> results = q.getResultList();

		return results;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> GetEntityByNamedQuery(String query, Integer beneficiaryId, Integer ageBand,
			Integer level) {
		Query q = getCurrentSession().getNamedQuery(query);
		
		q.setParameter("beneficiaryId", beneficiaryId);
		q.setParameter("ageBand",ageBand); 
		q.setParameter("level",level); 
				
		List<T> results = q.getResultList();

		return results;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> GetEntityByNamedQuery(String query, String name, Date dateOfBirth, int locality) {
		Query q = getCurrentSession().getNamedQuery(query);

		q.setParameter("name", name);
		q.setParameter("dateOfBirth", dateOfBirth);
		q.setParameter("locality", locality);

		List<T> results = q.getResultList();

		return results;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> GetAllPagedEntityByNamedQuery(String query, int pageIndex, int pageSize, String searchNui,Integer searchUserCreator, Integer searchDistrict, Object... params) {
		Query q = getCurrentSession().getNamedQuery(query);
		int i = 0;
		

		for (Parameter param : q.getParameters()) {
			if(!"searchNui".equals(param.getName()) && !"searchUserCreator".equals(param.getName()) && !"searchDistrict".equals(param.getName())) {
				q.setParameter(param, params[i]);
				i++;
			}else {
				q.setParameter("searchNui", "%"+searchNui+"%");
				q.setParameter("searchUserCreator",searchUserCreator); 
				q.setParameter("searchDistrict", searchDistrict); 
			}	
		}

		List<T> results = q
				.setFirstResult(pageIndex*pageSize)
				.setMaxResults(pageSize)
				.getResultList();

		return results;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> GetAllPagedEntityByNamedQuery(String query, int pageIndex, int pageSize, String searchNui, String searchName, Integer searchUserCreator, Integer searchDistrict, Object... params) {
		Query q = getCurrentSession().getNamedQuery(query);
		int i = 0;
		

		for (Parameter param : q.getParameters()) {
			if(!"searchNui".equals(param.getName()) && !"searchName".equals(param.getName()) && !"searchUserCreator".equals(param.getName()) && !"searchDistrict".equals(param.getName())) {
				q.setParameter(param, params[i]);
				i++;
			}else {
				q.setParameter("searchNui", "%"+searchNui+"%");
				q.setParameter("searchName", "%"+searchName+"%");
				q.setParameter("searchUserCreator",searchUserCreator); 
				q.setParameter("searchDistrict", searchDistrict); 
			}	
		}

		List<T> results = q
				.setFirstResult(pageIndex*pageSize)
				.setMaxResults(pageSize)
				.getResultList();

		return results;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> GetAllPagedUserEntityByNamedQuery(String query, int pageIndex, int pageSize, String searchUsername, Integer searchUserCreator, Integer searchDistrict, Object... params) {
		Query q = getCurrentSession().getNamedQuery(query);
		int i = 0;
		

		for (Parameter param : q.getParameters()) {
			if(!"searchUsername".equals(param.getName()) && !"searchUserCreator".equals(param.getName()) && !"searchDistrict".equals(param.getName())) {
				q.setParameter(param, params[i]);
				i++;
			}else {
				q.setParameter("searchUsername", "%"+searchUsername+"%");
				q.setParameter("searchUserCreator", searchUserCreator); 
				q.setParameter("searchDistrict", searchDistrict);  
			}	
		}

		List<T> results = q
				.setFirstResult(pageIndex*pageSize)
				.setMaxResults(pageSize)
				.getResultList();

		return results;
	}

    @Override
    public <T> List<T> GetAllPagedEntityByNamedQuery(String query, int pageIndex, int pageSize, Date searchStartDate,
            Date searchEndDate, Object... params) {
        Query q = getCurrentSession().getNamedQuery(query);
        int i = 0;
        
        for (Parameter param : q.getParameters()) {
            if(!"searchEndDate".equals(param.getName()) && !"searchStartDate".equals(param.getName())) {
                q.setParameter(param, params[i]);
                i++;
            }else {
                q.setParameter("searchStartDate", searchStartDate);
                q.setParameter("searchEndDate", searchEndDate); 
            }   
        }
        List<T> results = q
                .setFirstResult(pageIndex*pageSize)
                .setMaxResults(pageSize)
                .getResultList();

        return results;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T> List<T> GetAllEntityByNamedNativeQuery(String query, Object... params) {
        Query q = getCurrentSession().getNamedNativeQuery(query);
        int i = 0;
        for (Parameter param : q.getParameters()) {
            q.setParameter(param, params[i++]);
        }

        List<T> results = q.getResultList();

        return results;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T> List<T> GetByNamedNativeQuery(String query, Integer district, Date startDate, Date endDate, Object... params) {
        Query q = getCurrentSession().getNamedNativeQuery(query);
        int i = 0;
   
        for (Parameter param : q.getParameters()) {
			if(!"district".equals(param.getName()) && !"startDate".equals(param.getName()) && !"endDate".equals(param.getName())) {
				q.setParameter(param, params[i]);
				i++;
			}else {
				q.setParameter("district", district);
				q.setParameter("startDate", startDate);
				q.setParameter("endDate", endDate); 
			}	
		}

        List<T> results = q.getResultList();

        return results;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T> List<T> GetAllPagedEntityByNamedNativeQuery(String query, int pageIndex, int pageSize, Date startDate, Date endDate,  List<Integer>districts, Object... params) {
        Query q = getCurrentSession().getNamedNativeQuery(query);
        int i = 0;
      
        for (Parameter param : q.getParameters()) {
			if(!"districts".equals(param.getName()) && !"startDate".equals(param.getName()) && !"endDate".equals(param.getName())) {
				q.setParameter(param, params[i]);
				i++;
			}else {
				q.setParameter("districts", districts);
				q.setParameter("startDate", startDate);
				q.setParameter("endDate", endDate); 
			}	
		}

        List<T> results = q
                .setFirstResult(pageIndex*pageSize)
                .setMaxResults(pageSize)
                .getResultList();

        return results;
    }

	@Override
	public <T> T find(Class<T> klass, Object id) {

		return getCurrentSession().find(klass, id);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> findByQuery(String hql, Map<String, Object> entidade, Map<String, Object> namedParams) {
		SQLQuery query = getCurrentSession().createSQLQuery(hql);
		if (entidade != null) {
			Entry mapEntry;
			for (Iterator it = entidade.entrySet().iterator(); it.hasNext(); query.addEntity((String) mapEntry.getKey(),
					(Class) mapEntry.getValue())) {
				mapEntry = (Entry) it.next();
			}
		}
		if (namedParams != null) {
			Entry mapEntry;
			for (Iterator it = namedParams.entrySet().iterator(); it.hasNext(); query
					.setParameter((String) mapEntry.getKey(), mapEntry.getValue())) {
				mapEntry = (Entry) it.next();
			}
		}
		List returnList = query.list();

		return returnList;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> findByQueryFilter(String hql, Map<String, Object> entidade, Map<String, Object> namedParams,
			int f, int m) {

		SQLQuery query = getCurrentSession().createSQLQuery(hql);
		if (entidade != null) {
			Entry mapEntry;
			for (Iterator it = entidade.entrySet().iterator(); it.hasNext(); query.addEntity((String) mapEntry.getKey(),
					(Class) mapEntry.getValue())) {
				mapEntry = (Entry) it.next();
			}
		}
		if (namedParams != null) {
			Entry mapEntry;
			for (Iterator it = namedParams.entrySet().iterator(); it.hasNext(); query
					.setParameter((String) mapEntry.getKey(), mapEntry.getValue())) {
				mapEntry = (Entry) it.next();
			}
		}
		query.setFirstResult(f);
		query.setMaxResults(m);
		List returnList = query.list();

		return returnList;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> findByJPQuery(String hql, Map<String, Object> namedParams) {
		Query query = getCurrentSession().createQuery(hql);
		if (namedParams != null) {
			Entry mapEntry;
			for (Iterator it = namedParams.entrySet().iterator(); it.hasNext(); query
					.setParameter((String) mapEntry.getKey(), mapEntry.getValue())) {
				mapEntry = (Entry) it.next();
			}
		}
		List returnList = query.list();

		return returnList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> findByJPQueryFilter(String hql, Map<String, Object> namedParams, int f, int m) {
		Query query = getCurrentSession().createQuery(hql);
		if (namedParams != null) {
			Entry mapEntry;
			for (Iterator it = namedParams.entrySet().iterator(); it.hasNext(); query
					.setParameter((String) mapEntry.getKey(), mapEntry.getValue())) {
				mapEntry = (Entry) it.next();
			}
		}
		query.setFirstResult(f);
		query.setMaxResults(m);
		List returnList = query.list();

		return returnList;
	}

	public <T> void delete(T klass) {
		getCurrentSession().delete(klass);
	}

	public Class<? extends Annotation> annotationType() {
		// TODO Auto-generated method stub
		return null;
	}

	public String value() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> int UpdateEntitiesByNamedQuery(String query, Object... params) {
		// TODO Auto-generated method stub

		try {
			Query q = getCurrentSession().getNamedQuery(query);

			for (@SuppressWarnings("rawtypes")
			Parameter param : q.getParameters()) {
				q.setParameter(param, params[0]);
			}

			int r = q.executeUpdate();

			return r;
		} catch (Exception e) {
			throw e;
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> GetByParamsPagedEntityByNamedQuery(String query, int pageIndex, int pageSize, Object... params) {
		Query q = getCurrentSession().getNamedQuery(query);
		int i = 0;
		for (Parameter param : q.getParameters()) {
			q.setParameter(param, params[i++]);
		}
		List<T> results = q.setFirstResult(pageIndex * pageSize).setMaxResults(pageSize).getResultList();

		return results;
	}

}
