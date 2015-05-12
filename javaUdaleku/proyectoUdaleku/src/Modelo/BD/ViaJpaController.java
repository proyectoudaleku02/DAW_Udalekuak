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
import Modelo.UML.Direccion;
import Modelo.UML.Via;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author 1glm02
 */
public class ViaJpaController implements Serializable {

    public ViaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Via via) throws PreexistingEntityException, Exception {
        if (via.getDireccionCollection() == null) {
            via.setDireccionCollection(new ArrayList<Direccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipio idmunicipio = via.getIdmunicipio();
            if (idmunicipio != null) {
                idmunicipio = em.getReference(idmunicipio.getClass(), idmunicipio.getIdmunicipio());
                via.setIdmunicipio(idmunicipio);
            }
            Collection<Direccion> attachedDireccionCollection = new ArrayList<Direccion>();
            for (Direccion direccionCollectionDireccionToAttach : via.getDireccionCollection()) {
                direccionCollectionDireccionToAttach = em.getReference(direccionCollectionDireccionToAttach.getClass(), direccionCollectionDireccionToAttach.getIddireccion());
                attachedDireccionCollection.add(direccionCollectionDireccionToAttach);
            }
            via.setDireccionCollection(attachedDireccionCollection);
            em.persist(via);
            if (idmunicipio != null) {
                idmunicipio.getViaCollection().add(via);
                idmunicipio = em.merge(idmunicipio);
            }
            for (Direccion direccionCollectionDireccion : via.getDireccionCollection()) {
                Via oldIdviaOfDireccionCollectionDireccion = direccionCollectionDireccion.getIdvia();
                direccionCollectionDireccion.setIdvia(via);
                direccionCollectionDireccion = em.merge(direccionCollectionDireccion);
                if (oldIdviaOfDireccionCollectionDireccion != null) {
                    oldIdviaOfDireccionCollectionDireccion.getDireccionCollection().remove(direccionCollectionDireccion);
                    oldIdviaOfDireccionCollectionDireccion = em.merge(oldIdviaOfDireccionCollectionDireccion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVia(via.getIdvia()) != null) {
                throw new PreexistingEntityException("Via " + via + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Via via) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Via persistentVia = em.find(Via.class, via.getIdvia());
            Municipio idmunicipioOld = persistentVia.getIdmunicipio();
            Municipio idmunicipioNew = via.getIdmunicipio();
            Collection<Direccion> direccionCollectionOld = persistentVia.getDireccionCollection();
            Collection<Direccion> direccionCollectionNew = via.getDireccionCollection();
            if (idmunicipioNew != null) {
                idmunicipioNew = em.getReference(idmunicipioNew.getClass(), idmunicipioNew.getIdmunicipio());
                via.setIdmunicipio(idmunicipioNew);
            }
            Collection<Direccion> attachedDireccionCollectionNew = new ArrayList<Direccion>();
            for (Direccion direccionCollectionNewDireccionToAttach : direccionCollectionNew) {
                direccionCollectionNewDireccionToAttach = em.getReference(direccionCollectionNewDireccionToAttach.getClass(), direccionCollectionNewDireccionToAttach.getIddireccion());
                attachedDireccionCollectionNew.add(direccionCollectionNewDireccionToAttach);
            }
            direccionCollectionNew = attachedDireccionCollectionNew;
            via.setDireccionCollection(direccionCollectionNew);
            via = em.merge(via);
            if (idmunicipioOld != null && !idmunicipioOld.equals(idmunicipioNew)) {
                idmunicipioOld.getViaCollection().remove(via);
                idmunicipioOld = em.merge(idmunicipioOld);
            }
            if (idmunicipioNew != null && !idmunicipioNew.equals(idmunicipioOld)) {
                idmunicipioNew.getViaCollection().add(via);
                idmunicipioNew = em.merge(idmunicipioNew);
            }
            for (Direccion direccionCollectionOldDireccion : direccionCollectionOld) {
                if (!direccionCollectionNew.contains(direccionCollectionOldDireccion)) {
                    direccionCollectionOldDireccion.setIdvia(null);
                    direccionCollectionOldDireccion = em.merge(direccionCollectionOldDireccion);
                }
            }
            for (Direccion direccionCollectionNewDireccion : direccionCollectionNew) {
                if (!direccionCollectionOld.contains(direccionCollectionNewDireccion)) {
                    Via oldIdviaOfDireccionCollectionNewDireccion = direccionCollectionNewDireccion.getIdvia();
                    direccionCollectionNewDireccion.setIdvia(via);
                    direccionCollectionNewDireccion = em.merge(direccionCollectionNewDireccion);
                    if (oldIdviaOfDireccionCollectionNewDireccion != null && !oldIdviaOfDireccionCollectionNewDireccion.equals(via)) {
                        oldIdviaOfDireccionCollectionNewDireccion.getDireccionCollection().remove(direccionCollectionNewDireccion);
                        oldIdviaOfDireccionCollectionNewDireccion = em.merge(oldIdviaOfDireccionCollectionNewDireccion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = via.getIdvia();
                if (findVia(id) == null) {
                    throw new NonexistentEntityException("The via with id " + id + " no longer exists.");
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
            Via via;
            try {
                via = em.getReference(Via.class, id);
                via.getIdvia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The via with id " + id + " no longer exists.", enfe);
            }
            Municipio idmunicipio = via.getIdmunicipio();
            if (idmunicipio != null) {
                idmunicipio.getViaCollection().remove(via);
                idmunicipio = em.merge(idmunicipio);
            }
            Collection<Direccion> direccionCollection = via.getDireccionCollection();
            for (Direccion direccionCollectionDireccion : direccionCollection) {
                direccionCollectionDireccion.setIdvia(null);
                direccionCollectionDireccion = em.merge(direccionCollectionDireccion);
            }
            em.remove(via);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Via> findViaEntities() {
        return findViaEntities(true, -1, -1);
    }

    public List<Via> findViaEntities(int maxResults, int firstResult) {
        return findViaEntities(false, maxResults, firstResult);
    }

    private List<Via> findViaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Via.class));
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

    public Via findVia(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Via.class, id);
        } finally {
            em.close();
        }
    }

    public int getViaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Via> rt = cq.from(Via.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
