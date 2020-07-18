/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author zhangshuting
 */
public class JpaController implements Serializable {

	public JpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(User user) throws PreexistingEntityException,Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
		} catch (Exception ex) {
			if (findUser(user.getUsername()) != null) {
				throw new PreexistingEntityException("User " + user.getUsername()+ " already exists.", ex);
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

    public void edit(User username) throws NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			username = em.merge(username);
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				String id = username.getUsername();
				if (findUser(id) == null) {
					throw new NonexistentEntityException("The user " + username + " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void destroy(String username) throws NonexistentEntityException {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			User user;
			try {
				user = em.getReference(User.class, username);
				user.getUsername();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The user " + username+ " no longer exists.", enfe);
			}
			em.remove(user);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<User> findEntities() {
		return findEntities(true, -1, -1);
	}

	public List<User> findEntities(int maxResults, int firstResult) {
		return findEntities(false, maxResults, firstResult);
	}

	private List<User> findEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(User.class));
			Query q = em.createQuery(cq);
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	public User findUser(String username) {
		EntityManager em = getEntityManager();
		try {
			return em.find(User.class, username);
		} finally {
			em.close();
		}
	}

	public int getCustomerCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<User> rt = cq.from(User.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
        public class PreexistingEntityException extends Exception {
            public PreexistingEntityException(String message, Throwable cause) {
                super(message, cause);
            }
            public PreexistingEntityException(String message) {
                super(message);
            }
        }
        public class NonexistentEntityException extends Exception {
            public NonexistentEntityException(String message, Throwable cause) {
                super(message, cause);
            }
            public NonexistentEntityException(String message) {
                super(message);
            }
        }
}
