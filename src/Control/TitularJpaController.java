/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Control;

import Control.exceptions.IllegalOrphanException;
import Control.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Direccion;
import Entity.Factorrh;
import Entity.Gruposanguineo;
import Entity.Tipodocumento;
import Entity.Licencia;
import Entity.Titular;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author franc
 */
public class TitularJpaController implements Serializable {

    public TitularJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Titular titular) {
        if (titular.getLicenciaList() == null) {
            titular.setLicenciaList(new ArrayList<Licencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Direccion direccionFK = titular.getDireccionFK();
            if (direccionFK != null) {
                direccionFK = em.getReference(direccionFK.getClass(), direccionFK.getIdDireccion());
                titular.setDireccionFK(direccionFK);
            }
            Factorrh factorRHFK = titular.getFactorRHFK();
            if (factorRHFK != null) {
                factorRHFK = em.getReference(factorRHFK.getClass(), factorRHFK.getIdfactor());
                titular.setFactorRHFK(factorRHFK);
            }
            Gruposanguineo grupoSanguineoFK = titular.getGrupoSanguineoFK();
            if (grupoSanguineoFK != null) {
                grupoSanguineoFK = em.getReference(grupoSanguineoFK.getClass(), grupoSanguineoFK.getIdGrupo());
                titular.setGrupoSanguineoFK(grupoSanguineoFK);
            }
            Tipodocumento tipoDocumentoFK = titular.getTipoDocumentoFK();
            if (tipoDocumentoFK != null) {
                tipoDocumentoFK = em.getReference(tipoDocumentoFK.getClass(), tipoDocumentoFK.getIdTipoDocumento());
                titular.setTipoDocumentoFK(tipoDocumentoFK);
            }
            List<Licencia> attachedLicenciaList = new ArrayList<Licencia>();
            for (Licencia licenciaListLicenciaToAttach : titular.getLicenciaList()) {
                licenciaListLicenciaToAttach = em.getReference(licenciaListLicenciaToAttach.getClass(), licenciaListLicenciaToAttach.getIdLicencia());
                attachedLicenciaList.add(licenciaListLicenciaToAttach);
            }
            titular.setLicenciaList(attachedLicenciaList);
            em.persist(titular);
            if (direccionFK != null) {
                direccionFK.getTitularList().add(titular);
                direccionFK = em.merge(direccionFK);
            }
            if (factorRHFK != null) {
                factorRHFK.getTitularList().add(titular);
                factorRHFK = em.merge(factorRHFK);
            }
            if (grupoSanguineoFK != null) {
                grupoSanguineoFK.getTitularList().add(titular);
                grupoSanguineoFK = em.merge(grupoSanguineoFK);
            }
            if (tipoDocumentoFK != null) {
                tipoDocumentoFK.getTitularList().add(titular);
                tipoDocumentoFK = em.merge(tipoDocumentoFK);
            }
            for (Licencia licenciaListLicencia : titular.getLicenciaList()) {
                Titular oldIdtitularFKOfLicenciaListLicencia = licenciaListLicencia.getIdtitularFK();
                licenciaListLicencia.setIdtitularFK(titular);
                licenciaListLicencia = em.merge(licenciaListLicencia);
                if (oldIdtitularFKOfLicenciaListLicencia != null) {
                    oldIdtitularFKOfLicenciaListLicencia.getLicenciaList().remove(licenciaListLicencia);
                    oldIdtitularFKOfLicenciaListLicencia = em.merge(oldIdtitularFKOfLicenciaListLicencia);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Titular titular) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Titular persistentTitular = em.find(Titular.class, titular.getIdTitular());
            Direccion direccionFKOld = persistentTitular.getDireccionFK();
            Direccion direccionFKNew = titular.getDireccionFK();
            Factorrh factorRHFKOld = persistentTitular.getFactorRHFK();
            Factorrh factorRHFKNew = titular.getFactorRHFK();
            Gruposanguineo grupoSanguineoFKOld = persistentTitular.getGrupoSanguineoFK();
            Gruposanguineo grupoSanguineoFKNew = titular.getGrupoSanguineoFK();
            Tipodocumento tipoDocumentoFKOld = persistentTitular.getTipoDocumentoFK();
            Tipodocumento tipoDocumentoFKNew = titular.getTipoDocumentoFK();
            List<Licencia> licenciaListOld = persistentTitular.getLicenciaList();
            List<Licencia> licenciaListNew = titular.getLicenciaList();
            List<String> illegalOrphanMessages = null;
            for (Licencia licenciaListOldLicencia : licenciaListOld) {
                if (!licenciaListNew.contains(licenciaListOldLicencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Licencia " + licenciaListOldLicencia + " since its idtitularFK field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (direccionFKNew != null) {
                direccionFKNew = em.getReference(direccionFKNew.getClass(), direccionFKNew.getIdDireccion());
                titular.setDireccionFK(direccionFKNew);
            }
            if (factorRHFKNew != null) {
                factorRHFKNew = em.getReference(factorRHFKNew.getClass(), factorRHFKNew.getIdfactor());
                titular.setFactorRHFK(factorRHFKNew);
            }
            if (grupoSanguineoFKNew != null) {
                grupoSanguineoFKNew = em.getReference(grupoSanguineoFKNew.getClass(), grupoSanguineoFKNew.getIdGrupo());
                titular.setGrupoSanguineoFK(grupoSanguineoFKNew);
            }
            if (tipoDocumentoFKNew != null) {
                tipoDocumentoFKNew = em.getReference(tipoDocumentoFKNew.getClass(), tipoDocumentoFKNew.getIdTipoDocumento());
                titular.setTipoDocumentoFK(tipoDocumentoFKNew);
            }
            List<Licencia> attachedLicenciaListNew = new ArrayList<Licencia>();
            for (Licencia licenciaListNewLicenciaToAttach : licenciaListNew) {
                licenciaListNewLicenciaToAttach = em.getReference(licenciaListNewLicenciaToAttach.getClass(), licenciaListNewLicenciaToAttach.getIdLicencia());
                attachedLicenciaListNew.add(licenciaListNewLicenciaToAttach);
            }
            licenciaListNew = attachedLicenciaListNew;
            titular.setLicenciaList(licenciaListNew);
            titular = em.merge(titular);
            if (direccionFKOld != null && !direccionFKOld.equals(direccionFKNew)) {
                direccionFKOld.getTitularList().remove(titular);
                direccionFKOld = em.merge(direccionFKOld);
            }
            if (direccionFKNew != null && !direccionFKNew.equals(direccionFKOld)) {
                direccionFKNew.getTitularList().add(titular);
                direccionFKNew = em.merge(direccionFKNew);
            }
            if (factorRHFKOld != null && !factorRHFKOld.equals(factorRHFKNew)) {
                factorRHFKOld.getTitularList().remove(titular);
                factorRHFKOld = em.merge(factorRHFKOld);
            }
            if (factorRHFKNew != null && !factorRHFKNew.equals(factorRHFKOld)) {
                factorRHFKNew.getTitularList().add(titular);
                factorRHFKNew = em.merge(factorRHFKNew);
            }
            if (grupoSanguineoFKOld != null && !grupoSanguineoFKOld.equals(grupoSanguineoFKNew)) {
                grupoSanguineoFKOld.getTitularList().remove(titular);
                grupoSanguineoFKOld = em.merge(grupoSanguineoFKOld);
            }
            if (grupoSanguineoFKNew != null && !grupoSanguineoFKNew.equals(grupoSanguineoFKOld)) {
                grupoSanguineoFKNew.getTitularList().add(titular);
                grupoSanguineoFKNew = em.merge(grupoSanguineoFKNew);
            }
            if (tipoDocumentoFKOld != null && !tipoDocumentoFKOld.equals(tipoDocumentoFKNew)) {
                tipoDocumentoFKOld.getTitularList().remove(titular);
                tipoDocumentoFKOld = em.merge(tipoDocumentoFKOld);
            }
            if (tipoDocumentoFKNew != null && !tipoDocumentoFKNew.equals(tipoDocumentoFKOld)) {
                tipoDocumentoFKNew.getTitularList().add(titular);
                tipoDocumentoFKNew = em.merge(tipoDocumentoFKNew);
            }
            for (Licencia licenciaListNewLicencia : licenciaListNew) {
                if (!licenciaListOld.contains(licenciaListNewLicencia)) {
                    Titular oldIdtitularFKOfLicenciaListNewLicencia = licenciaListNewLicencia.getIdtitularFK();
                    licenciaListNewLicencia.setIdtitularFK(titular);
                    licenciaListNewLicencia = em.merge(licenciaListNewLicencia);
                    if (oldIdtitularFKOfLicenciaListNewLicencia != null && !oldIdtitularFKOfLicenciaListNewLicencia.equals(titular)) {
                        oldIdtitularFKOfLicenciaListNewLicencia.getLicenciaList().remove(licenciaListNewLicencia);
                        oldIdtitularFKOfLicenciaListNewLicencia = em.merge(oldIdtitularFKOfLicenciaListNewLicencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = titular.getIdTitular();
                if (findTitular(id) == null) {
                    throw new NonexistentEntityException("The titular with id " + id + " no longer exists.");
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
            Titular titular;
            try {
                titular = em.getReference(Titular.class, id);
                titular.getIdTitular();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The titular with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Licencia> licenciaListOrphanCheck = titular.getLicenciaList();
            for (Licencia licenciaListOrphanCheckLicencia : licenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Titular (" + titular + ") cannot be destroyed since the Licencia " + licenciaListOrphanCheckLicencia + " in its licenciaList field has a non-nullable idtitularFK field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Direccion direccionFK = titular.getDireccionFK();
            if (direccionFK != null) {
                direccionFK.getTitularList().remove(titular);
                direccionFK = em.merge(direccionFK);
            }
            Factorrh factorRHFK = titular.getFactorRHFK();
            if (factorRHFK != null) {
                factorRHFK.getTitularList().remove(titular);
                factorRHFK = em.merge(factorRHFK);
            }
            Gruposanguineo grupoSanguineoFK = titular.getGrupoSanguineoFK();
            if (grupoSanguineoFK != null) {
                grupoSanguineoFK.getTitularList().remove(titular);
                grupoSanguineoFK = em.merge(grupoSanguineoFK);
            }
            Tipodocumento tipoDocumentoFK = titular.getTipoDocumentoFK();
            if (tipoDocumentoFK != null) {
                tipoDocumentoFK.getTitularList().remove(titular);
                tipoDocumentoFK = em.merge(tipoDocumentoFK);
            }
            em.remove(titular);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Titular> findTitularEntities() {
        return findTitularEntities(true, -1, -1);
    }

    public List<Titular> findTitularEntities(int maxResults, int firstResult) {
        return findTitularEntities(false, maxResults, firstResult);
    }

    private List<Titular> findTitularEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Titular.class));
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

    public Titular findTitular(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Titular.class, id);
        } finally {
            em.close();
        }
    }

    public int getTitularCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Titular> rt = cq.from(Titular.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
