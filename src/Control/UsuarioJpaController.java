/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Control;

import Control.exceptions.NonexistentEntityException;
import Control.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Licencia;
import Entity.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author franc
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getLicenciaList() == null) {
            usuario.setLicenciaList(new ArrayList<Licencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Licencia> attachedLicenciaList = new ArrayList<Licencia>();
            for (Licencia licenciaListLicenciaToAttach : usuario.getLicenciaList()) {
                licenciaListLicenciaToAttach = em.getReference(licenciaListLicenciaToAttach.getClass(), licenciaListLicenciaToAttach.getIdLicencia());
                attachedLicenciaList.add(licenciaListLicenciaToAttach);
            }
            usuario.setLicenciaList(attachedLicenciaList);
            em.persist(usuario);
            for (Licencia licenciaListLicencia : usuario.getLicenciaList()) {
                licenciaListLicencia.getUsuarioList().add(usuario);
                licenciaListLicencia = em.merge(licenciaListLicencia);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getNombreUsuario()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getNombreUsuario());
            List<Licencia> licenciaListOld = persistentUsuario.getLicenciaList();
            List<Licencia> licenciaListNew = usuario.getLicenciaList();
            List<Licencia> attachedLicenciaListNew = new ArrayList<Licencia>();
            for (Licencia licenciaListNewLicenciaToAttach : licenciaListNew) {
                licenciaListNewLicenciaToAttach = em.getReference(licenciaListNewLicenciaToAttach.getClass(), licenciaListNewLicenciaToAttach.getIdLicencia());
                attachedLicenciaListNew.add(licenciaListNewLicenciaToAttach);
            }
            licenciaListNew = attachedLicenciaListNew;
            usuario.setLicenciaList(licenciaListNew);
            usuario = em.merge(usuario);
            for (Licencia licenciaListOldLicencia : licenciaListOld) {
                if (!licenciaListNew.contains(licenciaListOldLicencia)) {
                    licenciaListOldLicencia.getUsuarioList().remove(usuario);
                    licenciaListOldLicencia = em.merge(licenciaListOldLicencia);
                }
            }
            for (Licencia licenciaListNewLicencia : licenciaListNew) {
                if (!licenciaListOld.contains(licenciaListNewLicencia)) {
                    licenciaListNewLicencia.getUsuarioList().add(usuario);
                    licenciaListNewLicencia = em.merge(licenciaListNewLicencia);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getNombreUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getNombreUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<Licencia> licenciaList = usuario.getLicenciaList();
            for (Licencia licenciaListLicencia : licenciaList) {
                licenciaListLicencia.getUsuarioList().remove(usuario);
                licenciaListLicencia = em.merge(licenciaListLicencia);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
