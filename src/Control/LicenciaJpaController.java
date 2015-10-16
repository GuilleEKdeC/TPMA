/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Control;

import Control.exceptions.NonexistentEntityException;
import Entity.Licencia;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author franc
 */
public class LicenciaJpaController implements Serializable {

    public LicenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Licencia licencia) {
        if (licencia.getUsuarioList() == null) {
            licencia.setUsuarioList(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : licencia.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getNombreUsuario());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            licencia.setUsuarioList(attachedUsuarioList);
            em.persist(licencia);
            for (Usuario usuarioListUsuario : licencia.getUsuarioList()) {
                usuarioListUsuario.getLicenciaList().add(licencia);
                usuarioListUsuario = em.merge(usuarioListUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Licencia licencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Licencia persistentLicencia = em.find(Licencia.class, licencia.getIdLicencia());
            List<Usuario> usuarioListOld = persistentLicencia.getUsuarioList();
            List<Usuario> usuarioListNew = licencia.getUsuarioList();
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getNombreUsuario());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            licencia.setUsuarioList(usuarioListNew);
            licencia = em.merge(licencia);
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    usuarioListOldUsuario.getLicenciaList().remove(licencia);
                    usuarioListOldUsuario = em.merge(usuarioListOldUsuario);
                }
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    usuarioListNewUsuario.getLicenciaList().add(licencia);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = licencia.getIdLicencia();
                if (findLicencia(id) == null) {
                    throw new NonexistentEntityException("The licencia with id " + id + " no longer exists.");
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
            Licencia licencia;
            try {
                licencia = em.getReference(Licencia.class, id);
                licencia.getIdLicencia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The licencia with id " + id + " no longer exists.", enfe);
            }
            List<Usuario> usuarioList = licencia.getUsuarioList();
            for (Usuario usuarioListUsuario : usuarioList) {
                usuarioListUsuario.getLicenciaList().remove(licencia);
                usuarioListUsuario = em.merge(usuarioListUsuario);
            }
            em.remove(licencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Licencia> findLicenciaEntities() {
        return findLicenciaEntities(true, -1, -1);
    }

    public List<Licencia> findLicenciaEntities(int maxResults, int firstResult) {
        return findLicenciaEntities(false, maxResults, firstResult);
    }

    private List<Licencia> findLicenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Licencia.class));
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

    public Licencia findLicencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Licencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getLicenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Licencia> rt = cq.from(Licencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
