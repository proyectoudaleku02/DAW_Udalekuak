/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.BD;

import Modelo.BD.exceptions.NonexistentEntityException;
import Modelo.BD.exceptions.PreexistingEntityException;
import Modelo.UML.Municipios;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.UML.Provincias;
import Modelo.UML.Vias;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author 1glm02
 */
public class MunicipiosJpaController implements Serializable {

    public MunicipiosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Municipios municipios) throws PreexistingEntityException, Exception {
        if (municipios.getViasCollection() == null) {
            municipios.setViasCollection(new ArrayList<Vias>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Provincias idprovincia = municipios.getIdprovincia();
            if (idprovincia != null) {
                idprovincia = em.getReference(idprovincia.getClass(), idprovincia.getIdprovincia());
                municipios.setIdprovincia(idprovincia);
            }
            Collection<Vias> attachedViasCollection = new ArrayList<Vias>();
            for (Vias viasCollectionViasToAttach : municipios.getViasCollection()) {
                viasCollectionViasToAttach = em.getReference(viasCollectionViasToAttach.getClass(), viasCollectionViasToAttach.getIdvia());
                attachedViasCollection.add(viasCollectionViasToAttach);
            }
            municipios.setViasCollection(attachedViasCollection);
            em.persist(municipios);
            if (idprovincia != null) {
                idprovincia.getMunicipiosCollection().add(municipios);
                idprovincia = em.merge(idprovincia);
            }
            for (Vias viasCollectionVias : municipios.getViasCollection()) {
                Municipios oldIdmunicipioOfViasCollectionVias = viasCollectionVias.getIdmunicipio();
                viasCollectionVias.setIdmunicipio(municipios);
                viasCollectionVias = em.merge(viasCollectionVias);
                if (oldIdmunicipioOfViasCollectionVias != null) {
                    oldIdmunicipioOfViasCollectionVias.getViasCollection().remove(viasCollectionVias);
                    oldIdmunicipioOfViasCollectionVias = em.merge(oldIdmunicipioOfViasCollectionVias);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMunicipios(municipios.getIdmunicipio()) != null) {
                throw new PreexistingEntityException("Municipios " + municipios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Municipios municipios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipios persistentMunicipios = em.find(Municipios.class, municipios.getIdmunicipio());
            Provincias idprovinciaOld = persistentMunicipios.getIdprovincia();
            Provincias idprovinciaNew = municipios.getIdprovincia();
            Collection<Vias> viasCollectionOld = persistentMunicipios.getViasCollection();
            Collection<Vias> viasCollectionNew = municipios.getViasCollection();
            if (idprovinciaNew != null) {
                idprovinciaNew = em.getReference(idprovinciaNew.getClass(), idprovinciaNew.getIdprovincia());
                municipios.setIdprovincia(idprovinciaNew);
            }
            Collection<Vias> attachedViasCollectionNew = new ArrayList<Vias>();
            for (Vias viasCollectionNewViasToAttach : viasCollectionNew) {
                viasCollectionNewViasToAttach = em.getReference(viasCollectionNewViasToAttach.getClass(), viasCollectionNewViasToAttach.getIdvia());
                attachedViasCollectionNew.add(viasCollectionNewViasToAttach);
            }
            viasCollectionNew = attachedViasCollectionNew;
            municipios.setViasCollection(viasCollectionNew);
            municipios = em.merge(municipios);
            if (idprovinciaOld != null && !idprovinciaOld.equals(idprovinciaNew)) {
                idprovinciaOld.getMunicipiosCollection().remove(municipios);
                idprovinciaOld = em.merge(idprovinciaOld);
            }
            if (idprovinciaNew != null && !idprovinciaNew.equals(idprovinciaOld)) {
                idprovinciaNew.getMunicipiosCollection().add(municipios);
                idprovinciaNew = em.merge(idprovinciaNew);
            }
            for (Vias viasCollectionOldVias : viasCollectionOld) {
                if (!viasCollectionNew.contains(viasCollectionOldVias)) {
                    viasCollectionOldVias.setIdmunicipio(null);
                    viasCollectionOldVias = em.merge(viasCollectionOldVias);
                }
            }
            for (Vias viasCollectionNewVias : viasCollectionNew) {
                if (!viasCollectionOld.contains(viasCollectionNewVias)) {
                    Municipios oldIdmunicipioOfViasCollectionNewVias = viasCollectionNewVias.getIdmunicipio();
                    viasCollectionNewVias.setIdmunicipio(municipios);
                    viasCollectionNewVias = em.merge(viasCollectionNewVias);
                    if (oldIdmunicipioOfViasCollectionNewVias != null && !oldIdmunicipioOfViasCollectionNewVias.equals(municipios)) {
                        oldIdmunicipioOfViasCollectionNewVias.getViasCollection().remove(viasCollectionNewVias);
                        oldIdmunicipioOfViasCollectionNewVias = em.merge(oldIdmunicipioOfViasCollectionNewVias);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = municipios.getIdmunicipio();
                if (findMunicipios(id) == null) {
                    throw new NonexistentEntityException("The municipios with id " + id + " no longer exists.");
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
            Municipios municipios;
            try {
                municipios = em.getReference(Municipios.class, id);
                municipios.getIdmunicipio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The municipios with id " + id + " no longer exists.", enfe);
            }
            Provincias idprovincia = municipios.getIdprovincia();
            if (idprovincia != null) {
                idprovincia.getMunicipiosCollection().remove(municipios);
                idprovincia = em.merge(idprovincia);
            }
            Collection<Vias> viasCollection = municipios.getViasCollection();
            for (Vias viasCollectionVias : viasCollection) {
                viasCollectionVias.setIdmunicipio(null);
                viasCollectionVias = em.merge(viasCollectionVias);
            }
            em.remove(municipios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Municipios> findMunicipiosEntities() {
        return findMunicipiosEntities(true, -1, -1);
    }

    public List<Municipios> findMunicipiosEntities(int maxResults, int firstResult) {
        return findMunicipiosEntities(false, maxResults, firstResult);
    }

    private List<Municipios> findMunicipiosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Municipios.class));
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

    public Municipios findMunicipios(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Municipios.class, id);
        } finally {
            em.close();
        }
    }

    public int getMunicipiosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Municipios> rt = cq.from(Municipios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
