package dlt.dltbackendmaster.repository;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * This class implements the DAO Repository Interface
 * 
 * @author derciobucuane
 *
 */
@Repository
public class DAORepositoryImpl implements DAORepository {

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> getAll(Class<T> klass) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("from " + klass.getName());
			List<T> returnList = query.list();

			return returnList;
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			if (tx != null)
				tx.commit();
			session.close();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> getAllQuery(String s) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(s);
			List<T> returnList = query.list();

			return returnList;
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T update(T klass) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			T updatedKlass = (T) session.merge(klass);
			tx.commit();
			return updatedKlass;
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	public <T> boolean exist(T klass) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			boolean result = session.contains(klass);

			return result;
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("rawtypes")
	public <T> int updateQuery(String query, Object... params) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			NativeQuery q = session.createNativeQuery(query);
			int i = 0;
			for (Object o : params) {
				q.setParameter(i, o);
				i++;
			}
			int r = q.executeUpdate();
			tx.commit();

			return r;
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	public <T> int count(Class<T> klass) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			Long l = (Long) session.createQuery("select count(c) from " + klass.getName() + " c").uniqueResult();

			return l.intValue();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
		}

	}

	public <T> Serializable Save(T klass) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Serializable savedKlassId = session.save(klass);
			tx.commit();
			return savedKlassId;
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T GetUniqueEntityByNamedQuery(String query, Object... params) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			Query q = session.getNamedQuery(query);
			int i = 0;

			for (Object o : params) {
				q.setParameter(i, o);
				i++;// new
			}

			List<T> results = q.list();

			T foundentity = null;
			if (!results.isEmpty()) {
				// ignores multiple results
				foundentity = results.get(0);
			}
			return foundentity;
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> GetAllEntityByNamedQuery(String query, Object... params) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Query q = session.getNamedQuery(query);
			int i = 0;

			for (Object o : params) {
				q.setParameter(i, o);
				i++;// new
			}

			List<T> results = q.list();

			return results;
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
		}

	}

	@Override
	public <T> T find(Class<T> klass, Object id) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			return session.find(klass, id);
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> findByQuery(String hql, Map<String, Object> entidade, Map<String, Object> namedParams) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			NativeQuery query = session.createSQLQuery(hql);
			if (entidade != null) {
				Entry mapEntry;
				for (Iterator it = entidade.entrySet().iterator(); it.hasNext(); query
						.addEntity((String) mapEntry.getKey(), (Class) mapEntry.getValue())) {
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
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> findByQueryFilter(String hql, Map<String, Object> entidade, Map<String, Object> namedParams,
			int f, int m) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			NativeQuery query = session.createSQLQuery(hql);
			if (entidade != null) {
				Entry mapEntry;
				for (Iterator it = entidade.entrySet().iterator(); it.hasNext(); query
						.addEntity((String) mapEntry.getKey(), (Class) mapEntry.getValue())) {
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
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			if (tx != null)
				tx.commit();
			session.close();
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> findByJPQuery(String hql, Map<String, Object> namedParams) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			if (namedParams != null) {
				Entry mapEntry;
				for (Iterator it = namedParams.entrySet().iterator(); it.hasNext(); query
						.setParameter((String) mapEntry.getKey(), mapEntry.getValue())) {
					mapEntry = (Entry) it.next();
				}
			}
			List returnList = query.list();

			return returnList;
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			if (tx != null)
				tx.commit();
			session.close();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> findByJPQueryFilter(String hql, Map<String, Object> namedParams, int f, int m) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(hql);
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
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	public <T> void delete(T klass) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.delete(klass);
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	public Class<? extends Annotation> annotationType() {
		// TODO Auto-generated method stub
		return null;
	}

	public String value() {
		// TODO Auto-generated method stub
		return null;
	}

}
