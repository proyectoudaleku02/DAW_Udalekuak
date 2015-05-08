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
import Modelo.UML.Direcciones;
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
public class ViasJpaController implements Serializable {

    public ViasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vias vias) throws PreexistingEntityException, Exception {
        if (vias.getDireccionesCollection() == null) {
            vias.setDireccionesCollection(new ArrayList<Direcciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipios idmunicipio = vias.getIdmunicipio();
            if (idmunicipio != null) {
                idmunicipio = em.getReference(idmunicipio.getClass(), idmunicipio.getIdmunicipio());
                vias.setIdmunicipio(idmunicipio);
            }
            Collection<Direcciones> attachedDireccionesCollection = new ArrayList<Direcciones>();
            for (Direcciones direccionesCollectionDireccionesToAttach : vias.getDireccionesCollection()) {
                direccionesCollectionDireccionesToAttach = em.getReference(direccionesCollectionDireccionesToAttach.getClass(), direccionesCollectionDireccionesToAttach.getIddireccion());
                attachedDireccionesCollection.add(direccionesCollectionDireccionesToAttach);
            }
            vias.setDireccionesCollection(attachedDireccionesCollection);
            em.persist(vias);
            if (idmunicipio != null) {
                idmunicipio.getViasCollection().add(vias);
                idmunicipio = em.merge(idmunicipio);
            }
            for (Direcciones direccionesCollectionDirecciones : vias.getDireccionesCollection()) {
                Vias oldIdviaOfDireccionesCollectionDirecciones = direccionesCollectionDirecciones.getIdvia();
                direccionesCollectionDirecciones.setIdvia(vias);
                direccionesCollectionDirecciones = em.merge(direccionesCollectionDirecciones);
                if (oldIdviaOfDireccionesCollectionDirecciones != null) {
                    oldIdviaOfDireccionesCollectionDirecciones.getDireccionesCollection().remove(direccionesCollectionDirecciones);
                    oldIdviaOfDireccionesCollectionDirecciones = em.merge(oldIdviaOfDireccionesCollectionDirecciones);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVias(vias.getIdvia()) != null) {
                throw new PreexistingEntityException("Vias " + vias + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vias vias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vias persistentVias = em.find(Vias.class, vias.getIdvia());
            Municipios idmunicipioOld = persistentVias.getIdmunicipio();
            Municipios idmunicipioNew = vias.getIdmunicipio();
            Collection<Direcciones> direccionesCollectionOld = persistentVias.getDireccionesCollection();
            Collection<Direcciones> direccionesCollectionNew = vias.getDireccionesCollection();
            if (idmunicipioNew != null) {
                idmunicipioNew = em.getReference(idmunicipioNew.getClass(), idmunicipioNew.getIdmunicipio());
                vias.setIdmunicipio(idmunicipioNew);
            }
            Collection<Direcciones> attachedDireccionesCollectionNew = new ArrayList<Direcciones>();
            for (Direcciones direccionesCollectionNewDireccionesToAttach : direccionesCollectionNew) {
                direccionesCollectionNewDireccionesToAttach = em.getReference(direccionesCollectionNewDireccionesToAttach.getClass(), direccionesCollectionNewDireccionesToAttach.getIddireccion());
                attachedDireccionesCollectionNew.add(direccionesCollectionNewDireccionesToAttach);
            }
            direccionesCollectionNew = attachedDireccionesCollectionNew;
            vias.setDireccionesCollection(direccionesCollectionNew);
            vias = em.merge(vias);
            if (idmunicipioOld != null && !idmunicipioOld.equals(idmunicipioNew)) {
                idmunicipioOld.getViasCollection().remove(vias);
                idmunicipioOld = em.merge(idmunicipioOld);
            }
            if (idmunicipioNew != null && !idmunicipioNew.equals(idmunicipioOld)) {
                idmunicipioNew.getViasCollection().add(vias);
                idmunicipioNew = em.merge(idmunicipioNew);
            }
            for (Direcciones direccionesCollectionOldDirecciones : direccionesCollectionOld) {
                if (!direccionesCollectionNew.contains(direccionesCollectionOldDirecciones)) {
                    direccionesCollectionOldDirecciones.setIdvia(null);
                    direccionesCollectionOldDirecciones = em.merge(direccionesCollectionOldDirecciones);
                }
            }
            for (Direcciones direccionesCollectionNewDirecciones : direccionesCollectionNew) {
                if (!direccionesCollectionOld.contains(direccionesCollectionNewDirecciones)) {
                    Vias oldIdviaOfDireccionesCollectionNewDirecciones = direccionesCollectionNewDirecciones.getIdvia();
                    direccionesCollectionNewDirecciones.setIdvia(vias);
                    direccionesCollectionNewDirecciones = em.merge(direccionesCollectionNewDirecciones);
                    if (oldIdviaOfDireccionesCollectionNewDirecciones != null && !oldIdviaOfDireccionesCollectionNewDirecciones.equals(vias)) {
                        oldIdviaOfDireccionesCollectionNewDirecciones.getDireccionesCollection().remove(direccionesCollectionNewDirecciones);
                        oldIdviaOfDireccionesCollectionNewDirecciones = em.merge(oldIdviaOfDireccionesCollectionNewDirecciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = vias.getIdvia();
                if (findVias(id) == null) {
                    throw new NonexistentEntityException("The vias with id " + id + " no longer exists.");
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
            Vias vias;
            try {
                vias = em.getReference(Vias.class, id);
                vias.getIdvia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vias with id " + id + " no longer exists.", enfe);
            }
            Municipios idmunicipio = vias.getIdmunicipio();
            if (idmunicipio != null) {
                idmunicipio.getViasCollection().remove(vias);
                idmunicipio = em.merge(idmunicipio);
            }
            Collection<Direcciones> direccionesCollection = vias.getDireccionesCollection();
            for (Direcciones direccionesCollectionDirecciones : direccionesCollection) {
                direccionesCollectionDirecciones.setIdvia(null);
                direccionesCollectionDirecciones = em.merge(direccionesCollectionDirecciones);
            }
            em.remove(vias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vias> findViasEntities() {
        return findViasEntities(true, -1, -1);
    }

    public List<Vias> findViasEntities(int maxResults, int firstResult) {
        return findViasEntities(false, maxResults, firstResult);
    }

    private List<Vias> findViasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vias.class));
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

    public Vias findVias(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vias.class, id);
        } finally {
            em.close();
        }
    }

    public int getViasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vias> rt = cq.from(Vias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
