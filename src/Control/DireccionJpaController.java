/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Control;

import Control.exceptions.IllegalOrphanException;
import Control.exceptions.NonexistentEntityException;
import Entity.Direccion;
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
public class DireccionJpaController implements Serializable {

    public DireccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Direccion direccion) {
        if (direccion.getTitularList() == null) {
            direccion.setTitularList(new ArrayList<Titular>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Titular> attachedTitularList = new ArrayList<Titular>();
            for (Titular titularListTitularToAttach : direccion.getTitularList()) {
                titularListTitularToAttach = em.getReference(titularListTitularToAttach.getClass(), titularListTitularToAttach.getIdTitular());
                attachedTitularList.add(titularListTitularToAttach);
            }
            direccion.setTitularList(attachedTitularList);
            em.persist(direccion);
            for (Titular titularListTitular : direccion.getTitularList()) {
                Direccion oldDireccionFKOfTitularListTitular = titularListTitular.getDireccionFK();
                titularListTitular.setDireccionFK(direccion);
                titularListTitular = em.merge(titularListTitular);
                if (oldDireccionFKOfTitularListTitular != null) {
                    oldDireccionFKOfTitularListTitular.getTitularList().remove(titularListTitular);
                    oldDireccionFKOfTitularListTitular = em.merge(oldDireccionFKOfTitularListTitular);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Direccion direccion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Direccion persistentDireccion = em.find(Direccion.class, direccion.getIdDireccion());
            List<Titular> titularListOld = persistentDireccion.getTitularList();
            List<Titular> titularListNew = direccion.getTitularList();
            List<String> illegalOrphanMessages = null;
            for (Titular titularListOldTitular : titularListOld) {
                if (!titularListNew.contains(titularListOldTitular)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Titular " + titularListOldTitular + " since its direccionFK field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Titular> attachedTitularListNew = new ArrayList<Titular>();
            for (Titular titularListNewTitularToAttach : titularListNew) {
                titularListNewTitularToAttach = em.getReference(titularListNewTitularToAttach.getClass(), titularListNewTitularToAttach.getIdTitular());
                attachedTitularListNew.add(titularListNewTitularToAttach);
            }
            titularListNew = attachedTitularListNew;
            direccion.setTitularList(titularListNew);
            direccion = em.merge(direccion);
            for (Titular titularListNewTitular : titularListNew) {
                if (!titularListOld.contains(titularListNewTitular)) {
                    Direccion oldDireccionFKOfTitularListNewTitular = titularListNewTitular.getDireccionFK();
                    titularListNewTitular.setDireccionFK(direccion);
                    titularListNewTitular = em.merge(titularListNewTitular);
                    if (oldDireccionFKOfTitularListNewTitular != null && !oldDireccionFKOfTitularListNewTitular.equals(direccion)) {
                        oldDireccionFKOfTitularListNewTitular.getTitularList().remove(titularListNewTitular);
                        oldDireccionFKOfTitularListNewTitular = em.merge(oldDireccionFKOfTitularListNewTitular);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = direccion.getIdDireccion();
                if (findDireccion(id) == null) {
                    throw new NonexistentEntityException("The direccion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Direccion direccion;
            try {
                direccion = em.getReference(Direccion.class, id);
                direccion.getIdDireccion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The direccion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Titular> titularListOrphanCheck = direccion.getTitularList();
            for (Titular titularListOrphanCheckTitular : titularListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Direccion (" + direccion + ") cannot be destroyed since the Titular " + titularListOrphanCheckTitular + " in its titularList field has a non-nullable direccionFK field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(direccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Direccion> findDireccionEntities() {
        return findDireccionEntities(true, -1, -1);
    }

    public List<Direccion> findDireccionEntities(int maxResults, int firstResult) {
        return findDireccionEntities(false, maxResults, firstResult);
    }

    private List<Direccion> findDireccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Direccion.class));
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

    public Direccion findDireccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Direccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getDireccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Direccion> rt = cq.from(Direccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
