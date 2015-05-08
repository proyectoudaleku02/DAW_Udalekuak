/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.BD;

import Modelo.BD.exceptions.NonexistentEntityException;
import Modelo.BD.exceptions.PreexistingEntityException;
import Modelo.UML.Direcciones;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.UML.Vias;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author 1glm02
 */
public class DireccionesJpaController implements Serializable {

    public DireccionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Direcciones direcciones) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vias idvia = direcciones.getIdvia();
            if (idvia != null) {
                idvia = em.getReference(idvia.getClass(), idvia.getIdvia());
                direcciones.setIdvia(idvia);
            }
            em.persist(direcciones);
            if (idvia != null) {
                idvia.getDireccionesCollection().add(direcciones);
                idvia = em.merge(idvia);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDirecciones(direcciones.getIddireccion()) != null) {
                throw new PreexistingEntityException("Direcciones " + direcciones + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Direcciones direcciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Direcciones persistentDirecciones = em.find(Direcciones.class, direcciones.getIddireccion());
            Vias idviaOld = persistentDirecciones.getIdvia();
            Vias idviaNew = direcciones.getIdvia();
            if (idviaNew != null) {
                idviaNew = em.getReference(idviaNew.getClass(), idviaNew.getIdvia());
                direcciones.setIdvia(idviaNew);
            }
            direcciones = em.merge(direcciones);
            if (idviaOld != null && !idviaOld.equals(idviaNew)) {
                idviaOld.getDireccionesCollection().remove(direcciones);
                idviaOld = em.merge(idviaOld);
            }
            if (idviaNew != null && !idviaNew.equals(idviaOld)) {
                idviaNew.getDireccionesCollection().add(direcciones);
                idviaNew = em.merge(idviaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = direcciones.getIddireccion();
                if (findDirecciones(id) == null) {
                    throw new NonexistentEntityException("The direcciones with id " + id + " no longer exists.");
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
            Direcciones direcciones;
            try {
                direcciones = em.getReference(Direcciones.class, id);
                direcciones.getIddireccion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The direcciones with id " + id + " no longer exists.", enfe);
            }
            Vias idvia = direcciones.getIdvia();
            if (idvia != null) {
                idvia.getDireccionesCollection().remove(direcciones);
                idvia = em.merge(idvia);
            }
            em.remove(direcciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Direcciones> findDireccionesEntities() {
        return findDireccionesEntities(true, -1, -1);
    }

    public List<Direcciones> findDireccionesEntities(int maxResults, int firstResult) {
        return findDireccionesEntities(false, maxResults, firstResult);
    }

    private List<Direcciones> findDireccionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Direcciones.class));
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

    public Direcciones findDirecciones(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Direcciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getDireccionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Direcciones> rt = cq.from(Direcciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
