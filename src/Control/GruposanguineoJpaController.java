/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Control;

import Control.exceptions.NonexistentEntityException;
import Control.exceptions.PreexistingEntityException;
import Entity.Gruposanguineo;
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
public class GruposanguineoJpaController implements Serializable {

    public GruposanguineoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Gruposanguineo gruposanguineo) throws PreexistingEntityException, Exception {
        if (gruposanguineo.getTitularList() == null) {
            gruposanguineo.setTitularList(new ArrayList<Titular>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Titular> attachedTitularList = new ArrayList<Titular>();
            for (Titular titularListTitularToAttach : gruposanguineo.getTitularList()) {
                titularListTitularToAttach = em.getReference(titularListTitularToAttach.getClass(), titularListTitularToAttach.getIdTitular());
                attachedTitularList.add(titularListTitularToAttach);
            }
            gruposanguineo.setTitularList(attachedTitularList);
            em.persist(gruposanguineo);
            for (Titular titularListTitular : gruposanguineo.getTitularList()) {
                Gruposanguineo oldGrupoSanguineoFKOfTitularListTitular = titularListTitular.getGrupoSanguineoFK();
                titularListTitular.setGrupoSanguineoFK(gruposanguineo);
                titularListTitular = em.merge(titularListTitular);
                if (oldGrupoSanguineoFKOfTitularListTitular != null) {
                    oldGrupoSanguineoFKOfTitularListTitular.getTitularList().remove(titularListTitular);
                    oldGrupoSanguineoFKOfTitularListTitular = em.merge(oldGrupoSanguineoFKOfTitularListTitular);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGruposanguineo(gruposanguineo.getIdGrupo()) != null) {
                throw new PreexistingEntityException("Gruposanguineo " + gruposanguineo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Gruposanguineo gruposanguineo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gruposanguineo persistentGruposanguineo = em.find(Gruposanguineo.class, gruposanguineo.getIdGrupo());
            List<Titular> titularListOld = persistentGruposanguineo.getTitularList();
            List<Titular> titularListNew = gruposanguineo.getTitularList();
            List<Titular> attachedTitularListNew = new ArrayList<Titular>();
            for (Titular titularListNewTitularToAttach : titularListNew) {
                titularListNewTitularToAttach = em.getReference(titularListNewTitularToAttach.getClass(), titularListNewTitularToAttach.getIdTitular());
                attachedTitularListNew.add(titularListNewTitularToAttach);
            }
            titularListNew = attachedTitularListNew;
            gruposanguineo.setTitularList(titularListNew);
            gruposanguineo = em.merge(gruposanguineo);
            for (Titular titularListOldTitular : titularListOld) {
                if (!titularListNew.contains(titularListOldTitular)) {
                    titularListOldTitular.setGrupoSanguineoFK(null);
                    titularListOldTitular = em.merge(titularListOldTitular);
                }
            }
            for (Titular titularListNewTitular : titularListNew) {
                if (!titularListOld.contains(titularListNewTitular)) {
                    Gruposanguineo oldGrupoSanguineoFKOfTitularListNewTitular = titularListNewTitular.getGrupoSanguineoFK();
                    titularListNewTitular.setGrupoSanguineoFK(gruposanguineo);
                    titularListNewTitular = em.merge(titularListNewTitular);
                    if (oldGrupoSanguineoFKOfTitularListNewTitular != null && !oldGrupoSanguineoFKOfTitularListNewTitular.equals(gruposanguineo)) {
                        oldGrupoSanguineoFKOfTitularListNewTitular.getTitularList().remove(titularListNewTitular);
                        oldGrupoSanguineoFKOfTitularListNewTitular = em.merge(oldGrupoSanguineoFKOfTitularListNewTitular);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = gruposanguineo.getIdGrupo();
                if (findGruposanguineo(id) == null) {
                    throw new NonexistentEntityException("The gruposanguineo with id " + id + " no longer exists.");
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
            Gruposanguineo gruposanguineo;
            try {
                gruposanguineo = em.getReference(Gruposanguineo.class, id);
                gruposanguineo.getIdGrupo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gruposanguineo with id " + id + " no longer exists.", enfe);
            }
            List<Titular> titularList = gruposanguineo.getTitularList();
            for (Titular titularListTitular : titularList) {
                titularListTitular.setGrupoSanguineoFK(null);
                titularListTitular = em.merge(titularListTitular);
            }
            em.remove(gruposanguineo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Gruposanguineo> findGruposanguineoEntities() {
        return findGruposanguineoEntities(true, -1, -1);
    }

    public List<Gruposanguineo> findGruposanguineoEntities(int maxResults, int firstResult) {
        return findGruposanguineoEntities(false, maxResults, firstResult);
    }

    private List<Gruposanguineo> findGruposanguineoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Gruposanguineo.class));
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

    public Gruposanguineo findGruposanguineo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Gruposanguineo.class, id);
        } finally {
            em.close();
        }
    }

    public int getGruposanguineoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Gruposanguineo> rt = cq.from(Gruposanguineo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
