/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Control;

import Control.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Tipodocumento;
import Entity.Direccion;
import Entity.Factorrh;
import Entity.Gruposanguineo;
import Entity.Clase;
import Entity.Titular;
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
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipodocumento tipoDocumentoFK = titular.getTipoDocumentoFK();
            if (tipoDocumentoFK != null) {
                tipoDocumentoFK = em.getReference(tipoDocumentoFK.getClass(), tipoDocumentoFK.getIdTipoDocumento());
                titular.setTipoDocumentoFK(tipoDocumentoFK);
            }
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
            Clase claseSolicitada = titular.getClaseSolicitada();
            if (claseSolicitada != null) {
                claseSolicitada = em.getReference(claseSolicitada.getClass(), claseSolicitada.getIdClaseSolicitada());
                titular.setClaseSolicitada(claseSolicitada);
            }
            em.persist(titular);
            if (tipoDocumentoFK != null) {
                tipoDocumentoFK.getTitularList().add(titular);
                tipoDocumentoFK = em.merge(tipoDocumentoFK);
            }
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
            if (claseSolicitada != null) {
                claseSolicitada.getTitularList().add(titular);
                claseSolicitada = em.merge(claseSolicitada);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Titular titular) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Titular persistentTitular = em.find(Titular.class, titular.getIdTitular());
            Tipodocumento tipoDocumentoFKOld = persistentTitular.getTipoDocumentoFK();
            Tipodocumento tipoDocumentoFKNew = titular.getTipoDocumentoFK();
            Direccion direccionFKOld = persistentTitular.getDireccionFK();
            Direccion direccionFKNew = titular.getDireccionFK();
            Factorrh factorRHFKOld = persistentTitular.getFactorRHFK();
            Factorrh factorRHFKNew = titular.getFactorRHFK();
            Gruposanguineo grupoSanguineoFKOld = persistentTitular.getGrupoSanguineoFK();
            Gruposanguineo grupoSanguineoFKNew = titular.getGrupoSanguineoFK();
            Clase claseSolicitadaOld = persistentTitular.getClaseSolicitada();
            Clase claseSolicitadaNew = titular.getClaseSolicitada();
            if (tipoDocumentoFKNew != null) {
                tipoDocumentoFKNew = em.getReference(tipoDocumentoFKNew.getClass(), tipoDocumentoFKNew.getIdTipoDocumento());
                titular.setTipoDocumentoFK(tipoDocumentoFKNew);
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
            if (claseSolicitadaNew != null) {
                claseSolicitadaNew = em.getReference(claseSolicitadaNew.getClass(), claseSolicitadaNew.getIdClaseSolicitada());
                titular.setClaseSolicitada(claseSolicitadaNew);
            }
            titular = em.merge(titular);
            if (tipoDocumentoFKOld != null && !tipoDocumentoFKOld.equals(tipoDocumentoFKNew)) {
                tipoDocumentoFKOld.getTitularList().remove(titular);
                tipoDocumentoFKOld = em.merge(tipoDocumentoFKOld);
            }
            if (tipoDocumentoFKNew != null && !tipoDocumentoFKNew.equals(tipoDocumentoFKOld)) {
                tipoDocumentoFKNew.getTitularList().add(titular);
                tipoDocumentoFKNew = em.merge(tipoDocumentoFKNew);
            }
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
            if (claseSolicitadaOld != null && !claseSolicitadaOld.equals(claseSolicitadaNew)) {
                claseSolicitadaOld.getTitularList().remove(titular);
                claseSolicitadaOld = em.merge(claseSolicitadaOld);
            }
            if (claseSolicitadaNew != null && !claseSolicitadaNew.equals(claseSolicitadaOld)) {
                claseSolicitadaNew.getTitularList().add(titular);
                claseSolicitadaNew = em.merge(claseSolicitadaNew);
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

    public void destroy(Integer id) throws NonexistentEntityException {
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
            Tipodocumento tipoDocumentoFK = titular.getTipoDocumentoFK();
            if (tipoDocumentoFK != null) {
                tipoDocumentoFK.getTitularList().remove(titular);
                tipoDocumentoFK = em.merge(tipoDocumentoFK);
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
            Clase claseSolicitada = titular.getClaseSolicitada();
            if (claseSolicitada != null) {
                claseSolicitada.getTitularList().remove(titular);
                claseSolicitada = em.merge(claseSolicitada);
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
