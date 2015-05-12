/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.BD;

import Excepciones.exceptions.NonexistentEntityException;
import Excepciones.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.UML.Provincia;
import Modelo.UML.Localidad;
import Modelo.UML.Municipio;
import java.util.ArrayList;
import java.util.Collection;
import Modelo.UML.Via;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author 1glm02
 */
public class MunicipioJpaController implements Serializable {

    public MunicipioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Municipio municipio) throws PreexistingEntityException, Exception {
        if (municipio.getLocalidadCollection() == null) {
            municipio.setLocalidadCollection(new ArrayList<Localidad>());
        }
        if (municipio.getViaCollection() == null) {
            municipio.setViaCollection(new ArrayList<Via>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Provincia idprovincia = municipio.getIdprovincia();
            if (idprovincia != null) {
                idprovincia = em.getReference(idprovincia.getClass(), idprovincia.getIdprovincia());
                municipio.setIdprovincia(idprovincia);
            }
            Collection<Localidad> attachedLocalidadCollection = new ArrayList<Localidad>();
            for (Localidad localidadCollectionLocalidadToAttach : municipio.getLocalidadCollection()) {
                localidadCollectionLocalidadToAttach = em.getReference(localidadCollectionLocalidadToAttach.getClass(), localidadCollectionLocalidadToAttach.getIdlocalidad());
                attachedLocalidadCollection.add(localidadCollectionLocalidadToAttach);
            }
            municipio.setLocalidadCollection(attachedLocalidadCollection);
            Collection<Via> attachedViaCollection = new ArrayList<Via>();
            for (Via viaCollectionViaToAttach : municipio.getViaCollection()) {
                viaCollectionViaToAttach = em.getReference(viaCollectionViaToAttach.getClass(), viaCollectionViaToAttach.getIdvia());
                attachedViaCollection.add(viaCollectionViaToAttach);
            }
            municipio.setViaCollection(attachedViaCollection);
            em.persist(municipio);
            if (idprovincia != null) {
                idprovincia.getMunicipioCollection().add(municipio);
                idprovincia = em.merge(idprovincia);
            }
            for (Localidad localidadCollectionLocalidad : municipio.getLocalidadCollection()) {
                Municipio oldIdmunicipioOfLocalidadCollectionLocalidad = localidadCollectionLocalidad.getIdmunicipio();
                localidadCollectionLocalidad.setIdmunicipio(municipio);
                localidadCollectionLocalidad = em.merge(localidadCollectionLocalidad);
                if (oldIdmunicipioOfLocalidadCollectionLocalidad != null) {
                    oldIdmunicipioOfLocalidadCollectionLocalidad.getLocalidadCollection().remove(localidadCollectionLocalidad);
                    oldIdmunicipioOfLocalidadCollectionLocalidad = em.merge(oldIdmunicipioOfLocalidadCollectionLocalidad);
                }
            }
            for (Via viaCollectionVia : municipio.getViaCollection()) {
                Municipio oldIdmunicipioOfViaCollectionVia = viaCollectionVia.getIdmunicipio();
                viaCollectionVia.setIdmunicipio(municipio);
                viaCollectionVia = em.merge(viaCollectionVia);
                if (oldIdmunicipioOfViaCollectionVia != null) {
                    oldIdmunicipioOfViaCollectionVia.getViaCollection().remove(viaCollectionVia);
                    oldIdmunicipioOfViaCollectionVia = em.merge(oldIdmunicipioOfViaCollectionVia);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMunicipio(municipio.getIdmunicipio()) != null) {
                throw new PreexistingEntityException("Municipio " + municipio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Municipio municipio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipio persistentMunicipio = em.find(Municipio.class, municipio.getIdmunicipio());
            Provincia idprovinciaOld = persistentMunicipio.getIdprovincia();
            Provincia idprovinciaNew = municipio.getIdprovincia();
            Collection<Localidad> localidadCollectionOld = persistentMunicipio.getLocalidadCollection();
            Collection<Localidad> localidadCollectionNew = municipio.getLocalidadCollection();
            Collection<Via> viaCollectionOld = persistentMunicipio.getViaCollection();
            Collection<Via> viaCollectionNew = municipio.getViaCollection();
            if (idprovinciaNew != null) {
                idprovinciaNew = em.getReference(idprovinciaNew.getClass(), idprovinciaNew.getIdprovincia());
                municipio.setIdprovincia(idprovinciaNew);
            }
            Collection<Localidad> attachedLocalidadCollectionNew = new ArrayList<Localidad>();
            for (Localidad localidadCollectionNewLocalidadToAttach : localidadCollectionNew) {
                localidadCollectionNewLocalidadToAttach = em.getReference(localidadCollectionNewLocalidadToAttach.getClass(), localidadCollectionNewLocalidadToAttach.getIdlocalidad());
                attachedLocalidadCollectionNew.add(localidadCollectionNewLocalidadToAttach);
            }
            localidadCollectionNew = attachedLocalidadCollectionNew;
            municipio.setLocalidadCollection(localidadCollectionNew);
            Collection<Via> attachedViaCollectionNew = new ArrayList<Via>();
            for (Via viaCollectionNewViaToAttach : viaCollectionNew) {
                viaCollectionNewViaToAttach = em.getReference(viaCollectionNewViaToAttach.getClass(), viaCollectionNewViaToAttach.getIdvia());
                attachedViaCollectionNew.add(viaCollectionNewViaToAttach);
            }
            viaCollectionNew = attachedViaCollectionNew;
            municipio.setViaCollection(viaCollectionNew);
            municipio = em.merge(municipio);
            if (idprovinciaOld != null && !idprovinciaOld.equals(idprovinciaNew)) {
                idprovinciaOld.getMunicipioCollection().remove(municipio);
                idprovinciaOld = em.merge(idprovinciaOld);
            }
            if (idprovinciaNew != null && !idprovinciaNew.equals(idprovinciaOld)) {
                idprovinciaNew.getMunicipioCollection().add(municipio);
                idprovinciaNew = em.merge(idprovinciaNew);
            }
            for (Localidad localidadCollectionOldLocalidad : localidadCollectionOld) {
                if (!localidadCollectionNew.contains(localidadCollectionOldLocalidad)) {
                    localidadCollectionOldLocalidad.setIdmunicipio(null);
                    localidadCollectionOldLocalidad = em.merge(localidadCollectionOldLocalidad);
                }
            }
            for (Localidad localidadCollectionNewLocalidad : localidadCollectionNew) {
                if (!localidadCollectionOld.contains(localidadCollectionNewLocalidad)) {
                    Municipio oldIdmunicipioOfLocalidadCollectionNewLocalidad = localidadCollectionNewLocalidad.getIdmunicipio();
                    localidadCollectionNewLocalidad.setIdmunicipio(municipio);
                    localidadCollectionNewLocalidad = em.merge(localidadCollectionNewLocalidad);
                    if (oldIdmunicipioOfLocalidadCollectionNewLocalidad != null && !oldIdmunicipioOfLocalidadCollectionNewLocalidad.equals(municipio)) {
                        oldIdmunicipioOfLocalidadCollectionNewLocalidad.getLocalidadCollection().remove(localidadCollectionNewLocalidad);
                        oldIdmunicipioOfLocalidadCollectionNewLocalidad = em.merge(oldIdmunicipioOfLocalidadCollectionNewLocalidad);
                    }
                }
            }
            for (Via viaCollectionOldVia : viaCollectionOld) {
                if (!viaCollectionNew.contains(viaCollectionOldVia)) {
                    viaCollectionOldVia.setIdmunicipio(null);
                    viaCollectionOldVia = em.merge(viaCollectionOldVia);
                }
            }
            for (Via viaCollectionNewVia : viaCollectionNew) {
                if (!viaCollectionOld.contains(viaCollectionNewVia)) {
                    Municipio oldIdmunicipioOfViaCollectionNewVia = viaCollectionNewVia.getIdmunicipio();
                    viaCollectionNewVia.setIdmunicipio(municipio);
                    viaCollectionNewVia = em.merge(viaCollectionNewVia);
                    if (oldIdmunicipioOfViaCollectionNewVia != null && !oldIdmunicipioOfViaCollectionNewVia.equals(municipio)) {
                        oldIdmunicipioOfViaCollectionNewVia.getViaCollection().remove(viaCollectionNewVia);
                        oldIdmunicipioOfViaCollectionNewVia = em.merge(oldIdmunicipioOfViaCollectionNewVia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = municipio.getIdmunicipio();
                if (findMunicipio(id) == null) {
                    throw new NonexistentEntityException("The municipio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipio municipio;
            try {
                municipio = em.getReference(Municipio.class, id);
                municipio.getIdmunicipio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The municipio with id " + id + " no longer exists.", enfe);
            }
            Provincia idprovincia = municipio.getIdprovincia();
            if (idprovincia != null) {
                idprovincia.getMunicipioCollection().remove(municipio);
                idprovincia = em.merge(idprovincia);
            }
            Collection<Localidad> localidadCollection = municipio.getLocalidadCollection();
            for (Localidad localidadCollectionLocalidad : localidadCollection) {
                localidadCollectionLocalidad.setIdmunicipio(null);
                localidadCollectionLocalidad = em.merge(localidadCollectionLocalidad);
            }
            Collection<Via> viaCollection = municipio.getViaCollection();
            for (Via viaCollectionVia : viaCollection) {
                viaCollectionVia.setIdmunicipio(null);
                viaCollectionVia = em.merge(viaCollectionVia);
            }
            em.remove(municipio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Municipio> findMunicipioEntities() {
        return findMunicipioEntities(true, -1, -1);
    }

    public List<Municipio> findMunicipioEntities(int maxResults, int firstResult) {
        return findMunicipioEntities(false, maxResults, firstResult);
    }

    private List<Municipio> findMunicipioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Municipio.class));
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

    public Municipio findMunicipio(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Municipio.class, id);
        } finally {
            em.close();
        }
    }

    public int getMunicipioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Municipio> rt = cq.from(Municipio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
