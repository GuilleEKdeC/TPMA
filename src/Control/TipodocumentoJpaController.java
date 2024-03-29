/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Control;

import Control.exceptions.NonexistentEntityException;
import Control.exceptions.PreexistingEntityException;
import Entity.Tipodocumento;
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
public class TipodocumentoJpaController implements Serializable {

    public TipodocumentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipodocumento tipodocumento) throws PreexistingEntityException, Exception {
        if (tipodocumento.getTitularList() == null) {
            tipodocumento.setTitularList(new ArrayList<Titular>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Titular> attachedTitularList = new ArrayList<Titular>();
            for (Titular titularListTitularToAttach : tipodocumento.getTitularList()) {
                titularListTitularToAttach = em.getReference(titularListTitularToAttach.getClass(), titularListTitularToAttach.getIdTitular());
                attachedTitularList.add(titularListTitularToAttach);
            }
            tipodocumento.setTitularList(attachedTitularList);
            em.persist(tipodocumento);
            for (Titular titularListTitular : tipodocumento.getTitularList()) {
                Tipodocumento oldTipoDocumentoFKOfTitularListTitular = titularListTitular.getTipoDocumentoFK();
                titularListTitular.setTipoDocumentoFK(tipodocumento);
                titularListTitular = em.merge(titularListTitular);
                if (oldTipoDocumentoFKOfTitularListTitular != null) {
                    oldTipoDocumentoFKOfTitularListTitular.getTitularList().remove(titularListTitular);
                    oldTipoDocumentoFKOfTitularListTitular = em.merge(oldTipoDocumentoFKOfTitularListTitular);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipodocumento(tipodocumento.getIdTipoDocumento()) != null) {
                throw new PreexistingEntityException("Tipodocumento " + tipodocumento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipodocumento tipodocumento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipodocumento persistentTipodocumento = em.find(Tipodocumento.class, tipodocumento.getIdTipoDocumento());
            List<Titular> titularListOld = persistentTipodocumento.getTitularList();
            List<Titular> titularListNew = tipodocumento.getTitularList();
            List<Titular> attachedTitularListNew = new ArrayList<Titular>();
            for (Titular titularListNewTitularToAttach : titularListNew) {
                titularListNewTitularToAttach = em.getReference(titularListNewTitularToAttach.getClass(), titularListNewTitularToAttach.getIdTitular());
                attachedTitularListNew.add(titularListNewTitularToAttach);
            }
            titularListNew = attachedTitularListNew;
            tipodocumento.setTitularList(titularListNew);
            tipodocumento = em.merge(tipodocumento);
            for (Titular titularListOldTitular : titularListOld) {
                if (!titularListNew.contains(titularListOldTitular)) {
                    titularListOldTitular.setTipoDocumentoFK(null);
                    titularListOldTitular = em.merge(titularListOldTitular);
                }
            }
            for (Titular titularListNewTitular : titularListNew) {
                if (!titularListOld.contains(titularListNewTitular)) {
                    Tipodocumento oldTipoDocumentoFKOfTitularListNewTitular = titularListNewTitular.getTipoDocumentoFK();
                    titularListNewTitular.setTipoDocumentoFK(tipodocumento);
                    titularListNewTitular = em.merge(titularListNewTitular);
                    if (oldTipoDocumentoFKOfTitularListNewTitular != null && !oldTipoDocumentoFKOfTitularListNewTitular.equals(tipodocumento)) {
                        oldTipoDocumentoFKOfTitularListNewTitular.getTitularList().remove(titularListNewTitular);
                        oldTipoDocumentoFKOfTitularListNewTitular = em.merge(oldTipoDocumentoFKOfTitularListNewTitular);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipodocumento.getIdTipoDocumento();
                if (findTipodocumento(id) == null) {
                    throw new NonexistentEntityException("The tipodocumento with id " + id + " no longer exists.");
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
            Tipodocumento tipodocumento;
            try {
                tipodocumento = em.getReference(Tipodocumento.class, id);
                tipodocumento.getIdTipoDocumento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipodocumento with id " + id + " no longer exists.", enfe);
            }
            List<Titular> titularList = tipodocumento.getTitularList();
            for (Titular titularListTitular : titularList) {
                titularListTitular.setTipoDocumentoFK(null);
                titularListTitular = em.merge(titularListTitular);
            }
            em.remove(tipodocumento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipodocumento> findTipodocumentoEntities() {
        return findTipodocumentoEntities(true, -1, -1);
    }

    public List<Tipodocumento> findTipodocumentoEntities(int maxResults, int firstResult) {
        return findTipodocumentoEntities(false, maxResults, firstResult);
    }

    private List<Tipodocumento> findTipodocumentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipodocumento.class));
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

    public Tipodocumento findTipodocumento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipodocumento.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipodocumentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipodocumento> rt = cq.from(Tipodocumento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
