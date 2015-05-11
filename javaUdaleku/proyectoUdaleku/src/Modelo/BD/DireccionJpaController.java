/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.BD;

import Modelo.BD.exceptions.NonexistentEntityException;
import Modelo.BD.exceptions.PreexistingEntityException;
import Modelo.UML.Direccion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.UML.Via;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author 1glm02
 */
public class DireccionJpaController implements Serializable {

    public DireccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Direccion direccion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Via idvia = direccion.getIdvia();
            if (idvia != null) {
                idvia = em.getReference(idvia.getClass(), idvia.getIdvia());
                direccion.setIdvia(idvia);
            }
            em.persist(direccion);
            if (idvia != null) {
                idvia.getDireccionCollection().add(direccion);
                idvia = em.merge(idvia);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDireccion(direccion.getIddireccion()) != null) {
                throw new PreexistingEntityException("Direccion " + direccion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Direccion direccion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Direccion persistentDireccion = em.find(Direccion.class, direccion.getIddireccion());
            Via idviaOld = persistentDireccion.getIdvia();
            Via idviaNew = direccion.getIdvia();
            if (idviaNew != null) {
                idviaNew = em.getReference(idviaNew.getClass(), idviaNew.getIdvia());
                direccion.setIdvia(idviaNew);
            }
            direccion = em.merge(direccion);
            if (idviaOld != null && !idviaOld.equals(idviaNew)) {
                idviaOld.getDireccionCollection().remove(direccion);
                idviaOld = em.merge(idviaOld);
            }
            if (idviaNew != null && !idviaNew.equals(idviaOld)) {
                idviaNew.getDireccionCollection().add(direccion);
                idviaNew = em.merge(idviaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = direccion.getIddireccion();
                if (findDireccion(id) == null) {
                    throw new NonexistentEntityException("The direccion with id " + id + " no longer exists.");
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
            Direccion direccion;
            try {
                direccion = em.getReference(Direccion.class, id);
                direccion.getIddireccion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The direccion with id " + id + " no longer exists.", enfe);
            }
            Via idvia = direccion.getIdvia();
            if (idvia != null) {
                idvia.getDireccionCollection().remove(direccion);
                idvia = em.merge(idvia);
            }
            em.remove(direccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Direccion> findDireccionEntities() {
        return findDireccionEntities(true, -1, -1);
    }

    public List<Direccion> findDireccionEntities(int maxResults, int firstResult) {
        return findDireccionEntities(false, maxResults, firstResult);
    }

    private List<Direccion> findDireccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Direccion.class));
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

    public Direccion findDireccion(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Direccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getDireccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Direccion> rt = cq.from(Direccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
