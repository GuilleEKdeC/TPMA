/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Control;

import Control.exceptions.IllegalOrphanException;
import Control.exceptions.NonexistentEntityException;
import Entity.Clase;
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
public class ClaseJpaController implements Serializable {

    public ClaseJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clase clase) {
        if (clase.getTitularList() == null) {
            clase.setTitularList(new ArrayList<Titular>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Titular> attachedTitularList = new ArrayList<Titular>();
            for (Titular titularListTitularToAttach : clase.getTitularList()) {
                titularListTitularToAttach = em.getReference(titularListTitularToAttach.getClass(), titularListTitularToAttach.getIdTitular());
                attachedTitularList.add(titularListTitularToAttach);
            }
            clase.setTitularList(attachedTitularList);
            em.persist(clase);
            for (Titular titularListTitular : clase.getTitularList()) {
                Clase oldClaseSolicitadaOfTitularListTitular = titularListTitular.getClaseSolicitada();
                titularListTitular.setClaseSolicitada(clase);
                titularListTitular = em.merge(titularListTitular);
                if (oldClaseSolicitadaOfTitularListTitular != null) {
                    oldClaseSolicitadaOfTitularListTitular.getTitularList().remove(titularListTitular);
                    oldClaseSolicitadaOfTitularListTitular = em.merge(oldClaseSolicitadaOfTitularListTitular);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clase clase) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clase persistentClase = em.find(Clase.class, clase.getIdClaseSolicitada());
            List<Titular> titularListOld = persistentClase.getTitularList();
            List<Titular> titularListNew = clase.getTitularList();
            List<String> illegalOrphanMessages = null;
            for (Titular titularListOldTitular : titularListOld) {
                if (!titularListNew.contains(titularListOldTitular)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Titular " + titularListOldTitular + " since its claseSolicitada field is not nullable.");
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
            clase.setTitularList(titularListNew);
            clase = em.merge(clase);
            for (Titular titularListNewTitular : titularListNew) {
                if (!titularListOld.contains(titularListNewTitular)) {
                    Clase oldClaseSolicitadaOfTitularListNewTitular = titularListNewTitular.getClaseSolicitada();
                    titularListNewTitular.setClaseSolicitada(clase);
                    titularListNewTitular = em.merge(titularListNewTitular);
                    if (oldClaseSolicitadaOfTitularListNewTitular != null && !oldClaseSolicitadaOfTitularListNewTitular.equals(clase)) {
                        oldClaseSolicitadaOfTitularListNewTitular.getTitularList().remove(titularListNewTitular);
                        oldClaseSolicitadaOfTitularListNewTitular = em.merge(oldClaseSolicitadaOfTitularListNewTitular);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clase.getIdClaseSolicitada();
                if (findClase(id) == null) {
                    throw new NonexistentEntityException("The clase with id " + id + " no longer exists.");
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
            Clase clase;
            try {
                clase = em.getReference(Clase.class, id);
                clase.getIdClaseSolicitada();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clase with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Titular> titularListOrphanCheck = clase.getTitularList();
            for (Titular titularListOrphanCheckTitular : titularListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clase (" + clase + ") cannot be destroyed since the Titular " + titularListOrphanCheckTitular + " in its titularList field has a non-nullable claseSolicitada field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(clase);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clase> findClaseEntities() {
        return findClaseEntities(true, -1, -1);
    }

    public List<Clase> findClaseEntities(int maxResults, int firstResult) {
        return findClaseEntities(false, maxResults, firstResult);
    }

    private List<Clase> findClaseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clase.class));
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

    public Clase findClase(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clase.class, id);
        } finally {
            em.close();
        }
    }

    public int getClaseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clase> rt = cq.from(Clase.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
