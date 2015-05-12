/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.BD;

import Excepciones.exceptions.NonexistentEntityException;
import Excepciones.exceptions.PreexistingEntityException;
import Modelo.UML.Direccione;
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
public class DireccioneJpaController implements Serializable {

    public DireccioneJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Direccione direccione) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Via idvia = direccione.getIdvia();
            if (idvia != null) {
                idvia = em.getReference(idvia.getClass(), idvia.getIdvia());
                direccione.setIdvia(idvia);
            }
            em.persist(direccione);
            if (idvia != null) {
                idvia.getDireccioneCollection().add(direccione);
                idvia = em.merge(idvia);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDireccione(direccione.getIddireccion()) != null) {
                throw new PreexistingEntityException("Direccione " + direccione + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Direccione direccione) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Direccione persistentDireccione = em.find(Direccione.class, direccione.getIddireccion());
            Via idviaOld = persistentDireccione.getIdvia();
            Via idviaNew = direccione.getIdvia();
            if (idviaNew != null) {
                idviaNew = em.getReference(idviaNew.getClass(), idviaNew.getIdvia());
                direccione.setIdvia(idviaNew);
            }
            direccione = em.merge(direccione);
            if (idviaOld != null && !idviaOld.equals(idviaNew)) {
                idviaOld.getDireccioneCollection().remove(direccione);
                idviaOld = em.merge(idviaOld);
            }
            if (idviaNew != null && !idviaNew.equals(idviaOld)) {
                idviaNew.getDireccioneCollection().add(direccione);
                idviaNew = em.merge(idviaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = direccione.getIddireccion();
                if (findDireccione(id) == null) {
                    throw new NonexistentEntityException("The direccione with id " + id + " no longer exists.");
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
            Direccione direccione;
            try {
                direccione = em.getReference(Direccione.class, id);
                direccione.getIddireccion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The direccione with id " + id + " no longer exists.", enfe);
            }
            Via idvia = direccione.getIdvia();
            if (idvia != null) {
                idvia.getDireccioneCollection().remove(direccione);
                idvia = em.merge(idvia);
            }
            em.remove(direccione);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Direccione> findDireccioneEntities() {
        return findDireccioneEntities(true, -1, -1);
    }

    public List<Direccione> findDireccioneEntities(int maxResults, int firstResult) {
        return findDireccioneEntities(false, maxResults, firstResult);
    }

    private List<Direccione> findDireccioneEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Direccione.class));
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

    public Direccione findDireccione(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Direccione.class, id);
        } finally {
            em.close();
        }
    }

    public int getDireccioneCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Direccione> rt = cq.from(Direccione.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
