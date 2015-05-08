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
import Modelo.UML.Municipios;
import java.util.ArrayList;
import java.util.Collection;
import Modelo.UML.Centros;
import Modelo.UML.Provincias;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author 1glm02
 */
public class ProvinciasJpaController implements Serializable {

    public ProvinciasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Provincias provincias) throws PreexistingEntityException, Exception {
        if (provincias.getMunicipiosCollection() == null) {
            provincias.setMunicipiosCollection(new ArrayList<Municipios>());
        }
        if (provincias.getCentrosCollection() == null) {
            provincias.setCentrosCollection(new ArrayList<Centros>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Municipios> attachedMunicipiosCollection = new ArrayList<Municipios>();
            for (Municipios municipiosCollectionMunicipiosToAttach : provincias.getMunicipiosCollection()) {
                municipiosCollectionMunicipiosToAttach = em.getReference(municipiosCollectionMunicipiosToAttach.getClass(), municipiosCollectionMunicipiosToAttach.getIdmunicipio());
                attachedMunicipiosCollection.add(municipiosCollectionMunicipiosToAttach);
            }
            provincias.setMunicipiosCollection(attachedMunicipiosCollection);
            Collection<Centros> attachedCentrosCollection = new ArrayList<Centros>();
            for (Centros centrosCollectionCentrosToAttach : provincias.getCentrosCollection()) {
                centrosCollectionCentrosToAttach = em.getReference(centrosCollectionCentrosToAttach.getClass(), centrosCollectionCentrosToAttach.getIdcentro());
                attachedCentrosCollection.add(centrosCollectionCentrosToAttach);
            }
            provincias.setCentrosCollection(attachedCentrosCollection);
            em.persist(provincias);
            for (Municipios municipiosCollectionMunicipios : provincias.getMunicipiosCollection()) {
                Provincias oldIdprovinciaOfMunicipiosCollectionMunicipios = municipiosCollectionMunicipios.getIdprovincia();
                municipiosCollectionMunicipios.setIdprovincia(provincias);
                municipiosCollectionMunicipios = em.merge(municipiosCollectionMunicipios);
                if (oldIdprovinciaOfMunicipiosCollectionMunicipios != null) {
                    oldIdprovinciaOfMunicipiosCollectionMunicipios.getMunicipiosCollection().remove(municipiosCollectionMunicipios);
                    oldIdprovinciaOfMunicipiosCollectionMunicipios = em.merge(oldIdprovinciaOfMunicipiosCollectionMunicipios);
                }
            }
            for (Centros centrosCollectionCentros : provincias.getCentrosCollection()) {
                Provincias oldIdprovinciaOfCentrosCollectionCentros = centrosCollectionCentros.getIdprovincia();
                centrosCollectionCentros.setIdprovincia(provincias);
                centrosCollectionCentros = em.merge(centrosCollectionCentros);
                if (oldIdprovinciaOfCentrosCollectionCentros != null) {
                    oldIdprovinciaOfCentrosCollectionCentros.getCentrosCollection().remove(centrosCollectionCentros);
                    oldIdprovinciaOfCentrosCollectionCentros = em.merge(oldIdprovinciaOfCentrosCollectionCentros);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProvincias(provincias.getIdprovincia()) != null) {
                throw new PreexistingEntityException("Provincias " + provincias + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Provincias provincias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Provincias persistentProvincias = em.find(Provincias.class, provincias.getIdprovincia());
            Collection<Municipios> municipiosCollectionOld = persistentProvincias.getMunicipiosCollection();
            Collection<Municipios> municipiosCollectionNew = provincias.getMunicipiosCollection();
            Collection<Centros> centrosCollectionOld = persistentProvincias.getCentrosCollection();
            Collection<Centros> centrosCollectionNew = provincias.getCentrosCollection();
            Collection<Municipios> attachedMunicipiosCollectionNew = new ArrayList<Municipios>();
            for (Municipios municipiosCollectionNewMunicipiosToAttach : municipiosCollectionNew) {
                municipiosCollectionNewMunicipiosToAttach = em.getReference(municipiosCollectionNewMunicipiosToAttach.getClass(), municipiosCollectionNewMunicipiosToAttach.getIdmunicipio());
                attachedMunicipiosCollectionNew.add(municipiosCollectionNewMunicipiosToAttach);
            }
            municipiosCollectionNew = attachedMunicipiosCollectionNew;
            provincias.setMunicipiosCollection(municipiosCollectionNew);
            Collection<Centros> attachedCentrosCollectionNew = new ArrayList<Centros>();
            for (Centros centrosCollectionNewCentrosToAttach : centrosCollectionNew) {
                centrosCollectionNewCentrosToAttach = em.getReference(centrosCollectionNewCentrosToAttach.getClass(), centrosCollectionNewCentrosToAttach.getIdcentro());
                attachedCentrosCollectionNew.add(centrosCollectionNewCentrosToAttach);
            }
            centrosCollectionNew = attachedCentrosCollectionNew;
            provincias.setCentrosCollection(centrosCollectionNew);
            provincias = em.merge(provincias);
            for (Municipios municipiosCollectionOldMunicipios : municipiosCollectionOld) {
                if (!municipiosCollectionNew.contains(municipiosCollectionOldMunicipios)) {
                    municipiosCollectionOldMunicipios.setIdprovincia(null);
                    municipiosCollectionOldMunicipios = em.merge(municipiosCollectionOldMunicipios);
                }
            }
            for (Municipios municipiosCollectionNewMunicipios : municipiosCollectionNew) {
                if (!municipiosCollectionOld.contains(municipiosCollectionNewMunicipios)) {
                    Provincias oldIdprovinciaOfMunicipiosCollectionNewMunicipios = municipiosCollectionNewMunicipios.getIdprovincia();
                    municipiosCollectionNewMunicipios.setIdprovincia(provincias);
                    municipiosCollectionNewMunicipios = em.merge(municipiosCollectionNewMunicipios);
                    if (oldIdprovinciaOfMunicipiosCollectionNewMunicipios != null && !oldIdprovinciaOfMunicipiosCollectionNewMunicipios.equals(provincias)) {
                        oldIdprovinciaOfMunicipiosCollectionNewMunicipios.getMunicipiosCollection().remove(municipiosCollectionNewMunicipios);
                        oldIdprovinciaOfMunicipiosCollectionNewMunicipios = em.merge(oldIdprovinciaOfMunicipiosCollectionNewMunicipios);
                    }
                }
            }
            for (Centros centrosCollectionOldCentros : centrosCollectionOld) {
                if (!centrosCollectionNew.contains(centrosCollectionOldCentros)) {
                    centrosCollectionOldCentros.setIdprovincia(null);
                    centrosCollectionOldCentros = em.merge(centrosCollectionOldCentros);
                }
            }
            for (Centros centrosCollectionNewCentros : centrosCollectionNew) {
                if (!centrosCollectionOld.contains(centrosCollectionNewCentros)) {
                    Provincias oldIdprovinciaOfCentrosCollectionNewCentros = centrosCollectionNewCentros.getIdprovincia();
                    centrosCollectionNewCentros.setIdprovincia(provincias);
                    centrosCollectionNewCentros = em.merge(centrosCollectionNewCentros);
                    if (oldIdprovinciaOfCentrosCollectionNewCentros != null && !oldIdprovinciaOfCentrosCollectionNewCentros.equals(provincias)) {
                        oldIdprovinciaOfCentrosCollectionNewCentros.getCentrosCollection().remove(centrosCollectionNewCentros);
                        oldIdprovinciaOfCentrosCollectionNewCentros = em.merge(oldIdprovinciaOfCentrosCollectionNewCentros);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = provincias.getIdprovincia();
                if (findProvincias(id) == null) {
                    throw new NonexistentEntityException("The provincias with id " + id + " no longer exists.");
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
            Provincias provincias;
            try {
                provincias = em.getReference(Provincias.class, id);
                provincias.getIdprovincia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The provincias with id " + id + " no longer exists.", enfe);
            }
            Collection<Municipios> municipiosCollection = provincias.getMunicipiosCollection();
            for (Municipios municipiosCollectionMunicipios : municipiosCollection) {
                municipiosCollectionMunicipios.setIdprovincia(null);
                municipiosCollectionMunicipios = em.merge(municipiosCollectionMunicipios);
            }
            Collection<Centros> centrosCollection = provincias.getCentrosCollection();
            for (Centros centrosCollectionCentros : centrosCollection) {
                centrosCollectionCentros.setIdprovincia(null);
                centrosCollectionCentros = em.merge(centrosCollectionCentros);
            }
            em.remove(provincias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Provincias> findProvinciasEntities() {
        return findProvinciasEntities(true, -1, -1);
    }

    public List<Provincias> findProvinciasEntities(int maxResults, int firstResult) {
        return findProvinciasEntities(false, maxResults, firstResult);
    }

    private List<Provincias> findProvinciasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Provincias.class));
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

    public Provincias findProvincias(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Provincias.class, id);
        } finally {
            em.close();
        }
    }

    public int getProvinciasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Provincias> rt = cq.from(Provincias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
