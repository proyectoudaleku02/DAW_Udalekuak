/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.BD;

import Modelo.BD.exceptions.NonexistentEntityException;
import Modelo.BD.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.UML.Municipio;
import java.util.ArrayList;
import java.util.Collection;
import Modelo.UML.Centro;
import Modelo.UML.Provincia;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author 1glm02
 */
public class ProvinciaJpaController implements Serializable {

    public ProvinciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Provincia provincia) throws PreexistingEntityException, Exception {
        if (provincia.getMunicipioCollection() == null) {
            provincia.setMunicipioCollection(new ArrayList<Municipio>());
        }
        if (provincia.getCentroCollection() == null) {
            provincia.setCentroCollection(new ArrayList<Centro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Municipio> attachedMunicipioCollection = new ArrayList<Municipio>();
            for (Municipio municipioCollectionMunicipioToAttach : provincia.getMunicipioCollection()) {
                municipioCollectionMunicipioToAttach = em.getReference(municipioCollectionMunicipioToAttach.getClass(), municipioCollectionMunicipioToAttach.getIdmunicipio());
                attachedMunicipioCollection.add(municipioCollectionMunicipioToAttach);
            }
            provincia.setMunicipioCollection(attachedMunicipioCollection);
            Collection<Centro> attachedCentroCollection = new ArrayList<Centro>();
            for (Centro centroCollectionCentroToAttach : provincia.getCentroCollection()) {
                centroCollectionCentroToAttach = em.getReference(centroCollectionCentroToAttach.getClass(), centroCollectionCentroToAttach.getIdcentro());
                attachedCentroCollection.add(centroCollectionCentroToAttach);
            }
            provincia.setCentroCollection(attachedCentroCollection);
            em.persist(provincia);
            for (Municipio municipioCollectionMunicipio : provincia.getMunicipioCollection()) {
                Provincia oldIdprovinciaOfMunicipioCollectionMunicipio = municipioCollectionMunicipio.getIdprovincia();
                municipioCollectionMunicipio.setIdprovincia(provincia);
                municipioCollectionMunicipio = em.merge(municipioCollectionMunicipio);
                if (oldIdprovinciaOfMunicipioCollectionMunicipio != null) {
                    oldIdprovinciaOfMunicipioCollectionMunicipio.getMunicipioCollection().remove(municipioCollectionMunicipio);
                    oldIdprovinciaOfMunicipioCollectionMunicipio = em.merge(oldIdprovinciaOfMunicipioCollectionMunicipio);
                }
            }
            for (Centro centroCollectionCentro : provincia.getCentroCollection()) {
                Provincia oldIdprovinciaOfCentroCollectionCentro = centroCollectionCentro.getIdprovincia();
                centroCollectionCentro.setIdprovincia(provincia);
                centroCollectionCentro = em.merge(centroCollectionCentro);
                if (oldIdprovinciaOfCentroCollectionCentro != null) {
                    oldIdprovinciaOfCentroCollectionCentro.getCentroCollection().remove(centroCollectionCentro);
                    oldIdprovinciaOfCentroCollectionCentro = em.merge(oldIdprovinciaOfCentroCollectionCentro);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProvincia(provincia.getIdprovincia()) != null) {
                throw new PreexistingEntityException("Provincia " + provincia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Provincia provincia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Provincia persistentProvincia = em.find(Provincia.class, provincia.getIdprovincia());
            Collection<Municipio> municipioCollectionOld = persistentProvincia.getMunicipioCollection();
            Collection<Municipio> municipioCollectionNew = provincia.getMunicipioCollection();
            Collection<Centro> centroCollectionOld = persistentProvincia.getCentroCollection();
            Collection<Centro> centroCollectionNew = provincia.getCentroCollection();
            Collection<Municipio> attachedMunicipioCollectionNew = new ArrayList<Municipio>();
            for (Municipio municipioCollectionNewMunicipioToAttach : municipioCollectionNew) {
                municipioCollectionNewMunicipioToAttach = em.getReference(municipioCollectionNewMunicipioToAttach.getClass(), municipioCollectionNewMunicipioToAttach.getIdmunicipio());
                attachedMunicipioCollectionNew.add(municipioCollectionNewMunicipioToAttach);
            }
            municipioCollectionNew = attachedMunicipioCollectionNew;
            provincia.setMunicipioCollection(municipioCollectionNew);
            Collection<Centro> attachedCentroCollectionNew = new ArrayList<Centro>();
            for (Centro centroCollectionNewCentroToAttach : centroCollectionNew) {
                centroCollectionNewCentroToAttach = em.getReference(centroCollectionNewCentroToAttach.getClass(), centroCollectionNewCentroToAttach.getIdcentro());
                attachedCentroCollectionNew.add(centroCollectionNewCentroToAttach);
            }
            centroCollectionNew = attachedCentroCollectionNew;
            provincia.setCentroCollection(centroCollectionNew);
            provincia = em.merge(provincia);
            for (Municipio municipioCollectionOldMunicipio : municipioCollectionOld) {
                if (!municipioCollectionNew.contains(municipioCollectionOldMunicipio)) {
                    municipioCollectionOldMunicipio.setIdprovincia(null);
                    municipioCollectionOldMunicipio = em.merge(municipioCollectionOldMunicipio);
                }
            }
            for (Municipio municipioCollectionNewMunicipio : municipioCollectionNew) {
                if (!municipioCollectionOld.contains(municipioCollectionNewMunicipio)) {
                    Provincia oldIdprovinciaOfMunicipioCollectionNewMunicipio = municipioCollectionNewMunicipio.getIdprovincia();
                    municipioCollectionNewMunicipio.setIdprovincia(provincia);
                    municipioCollectionNewMunicipio = em.merge(municipioCollectionNewMunicipio);
                    if (oldIdprovinciaOfMunicipioCollectionNewMunicipio != null && !oldIdprovinciaOfMunicipioCollectionNewMunicipio.equals(provincia)) {
                        oldIdprovinciaOfMunicipioCollectionNewMunicipio.getMunicipioCollection().remove(municipioCollectionNewMunicipio);
                        oldIdprovinciaOfMunicipioCollectionNewMunicipio = em.merge(oldIdprovinciaOfMunicipioCollectionNewMunicipio);
                    }
                }
            }
            for (Centro centroCollectionOldCentro : centroCollectionOld) {
                if (!centroCollectionNew.contains(centroCollectionOldCentro)) {
                    centroCollectionOldCentro.setIdprovincia(null);
                    centroCollectionOldCentro = em.merge(centroCollectionOldCentro);
                }
            }
            for (Centro centroCollectionNewCentro : centroCollectionNew) {
                if (!centroCollectionOld.contains(centroCollectionNewCentro)) {
                    Provincia oldIdprovinciaOfCentroCollectionNewCentro = centroCollectionNewCentro.getIdprovincia();
                    centroCollectionNewCentro.setIdprovincia(provincia);
                    centroCollectionNewCentro = em.merge(centroCollectionNewCentro);
                    if (oldIdprovinciaOfCentroCollectionNewCentro != null && !oldIdprovinciaOfCentroCollectionNewCentro.equals(provincia)) {
                        oldIdprovinciaOfCentroCollectionNewCentro.getCentroCollection().remove(centroCollectionNewCentro);
                        oldIdprovinciaOfCentroCollectionNewCentro = em.merge(oldIdprovinciaOfCentroCollectionNewCentro);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = provincia.getIdprovincia();
                if (findProvincia(id) == null) {
                    throw new NonexistentEntityException("The provincia with id " + id + " no longer exists.");
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
            Provincia provincia;
            try {
                provincia = em.getReference(Provincia.class, id);
                provincia.getIdprovincia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The provincia with id " + id + " no longer exists.", enfe);
            }
            Collection<Municipio> municipioCollection = provincia.getMunicipioCollection();
            for (Municipio municipioCollectionMunicipio : municipioCollection) {
                municipioCollectionMunicipio.setIdprovincia(null);
                municipioCollectionMunicipio = em.merge(municipioCollectionMunicipio);
            }
            Collection<Centro> centroCollection = provincia.getCentroCollection();
            for (Centro centroCollectionCentro : centroCollection) {
                centroCollectionCentro.setIdprovincia(null);
                centroCollectionCentro = em.merge(centroCollectionCentro);
            }
            em.remove(provincia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Provincia> findProvinciaEntities() {
        return findProvinciaEntities(true, -1, -1);
    }

    public List<Provincia> findProvinciaEntities(int maxResults, int firstResult) {
        return findProvinciaEntities(false, maxResults, firstResult);
    }

    private List<Provincia> findProvinciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Provincia.class));
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

    public Provincia findProvincia(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Provincia.class, id);
        } finally {
            em.close();
        }
    }

    public int getProvinciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Provincia> rt = cq.from(Provincia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
