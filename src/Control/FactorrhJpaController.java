/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Control;

import Control.exceptions.NonexistentEntityException;
import Control.exceptions.PreexistingEntityException;
import Entity.Factorrh;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Titular;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author franc
 */
public class FactorrhJpaController implements Serializable {

    public FactorrhJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Factorrh factorrh) throws PreexistingEntityException, Exception {
        if (factorrh.getTitularList() == null) {
            factorrh.setTitularList(new ArrayList<Titular>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Titular> attachedTitularList = new ArrayList<Titular>();
            for (Titular titularListTitularToAttach : factorrh.getTitularList()) {
                titularListTitularToAttach = em.getReference(titularListTitularToAttach.getClass(), titularListTitularToAttach.getIdTitular());
                attachedTitularList.add(titularListTitularToAttach);
            }
            factorrh.setTitularList(attachedTitularList);
            em.persist(factorrh);
            for (Titular titularListTitular : factorrh.getTitularList()) {
                Factorrh oldFactorRHFKOfTitularListTitular = titularListTitular.getFactorRHFK();
                titularListTitular.setFactorRHFK(factorrh);
                titularListTitular = em.merge(titularListTitular);
                if (oldFactorRHFKOfTitularListTitular != null) {
                    oldFactorRHFKOfTitularListTitular.getTitularList().remove(titularListTitular);
                    oldFactorRHFKOfTitularListTitular = em.merge(oldFactorRHFKOfTitularListTitular);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFactorrh(factorrh.getIdfactor()) != null) {
                throw new PreexistingEntityException("Factorrh " + factorrh + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Factorrh factorrh) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factorrh persistentFactorrh = em.find(Factorrh.class, factorrh.getIdfactor());
            List<Titular> titularListOld = persistentFactorrh.getTitularList();
            List<Titular> titularListNew = factorrh.getTitularList();
            List<Titular> attachedTitularListNew = new ArrayList<Titular>();
            for (Titular titularListNewTitularToAttach : titularListNew) {
                titularListNewTitularToAttach = em.getReference(titularListNewTitularToAttach.getClass(), titularListNewTitularToAttach.getIdTitular());
                attachedTitularListNew.add(titularListNewTitularToAttach);
            }
            titularListNew = attachedTitularListNew;
            factorrh.setTitularList(titularListNew);
            factorrh = em.merge(factorrh);
            for (Titular titularListOldTitular : titularListOld) {
                if (!titularListNew.contains(titularListOldTitular)) {
                    titularListOldTitular.setFactorRHFK(null);
                    titularListOldTitular = em.merge(titularListOldTitular);
                }
            }
            for (Titular titularListNewTitular : titularListNew) {
                if (!titularListOld.contains(titularListNewTitular)) {
                    Factorrh oldFactorRHFKOfTitularListNewTitular = titularListNewTitular.getFactorRHFK();
                    titularListNewTitular.setFactorRHFK(factorrh);
                    titularListNewTitular = em.merge(titularListNewTitular);
                    if (oldFactorRHFKOfTitularListNewTitular != null && !oldFactorRHFKOfTitularListNewTitular.equals(factorrh)) {
                        oldFactorRHFKOfTitularListNewTitular.getTitularList().remove(titularListNewTitular);
                        oldFactorRHFKOfTitularListNewTitular = em.merge(oldFactorRHFKOfTitularListNewTitular);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = factorrh.getIdfactor();
                if (findFactorrh(id) == null) {
                    throw new NonexistentEntityException("The factorrh with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factorrh factorrh;
            try {
                factorrh = em.getReference(Factorrh.class, id);
                factorrh.getIdfactor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The factorrh with id " + id + " no longer exists.", enfe);
            }
            List<Titular> titularList = factorrh.getTitularList();
            for (Titular titularListTitular : titularList) {
                titularListTitular.setFactorRHFK(null);
                titularListTitular = em.merge(titularListTitular);
            }
            em.remove(factorrh);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Factorrh> findFactorrhEntities() {
        return findFactorrhEntities(true, -1, -1);
    }

    public List<Factorrh> findFactorrhEntities(int maxResults, int firstResult) {
        return findFactorrhEntities(false, maxResults, firstResult);
    }

    private List<Factorrh> findFactorrhEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Factorrh.class));
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

    public Factorrh findFactorrh(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Factorrh.class, id);
        } finally {
            em.close();
        }
    }

    public int getFactorrhCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Factorrh> rt = cq.from(Factorrh.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
