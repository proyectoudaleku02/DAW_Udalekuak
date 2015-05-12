/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.BD;

import Excepciones.exceptions.NonexistentEntityException;
import Excepciones.exceptions.PreexistingEntityException;
import Modelo.UML.CentroHasModelo;
import Modelo.UML.CentroHasModeloPK;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author 1glm02
 */
public class CentroHasModeloJpaController implements Serializable {

    public CentroHasModeloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CentroHasModelo centroHasModelo) throws PreexistingEntityException, Exception {
        if (centroHasModelo.getCentroHasModeloPK() == null) {
            centroHasModelo.setCentroHasModeloPK(new CentroHasModeloPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(centroHasModelo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCentroHasModelo(centroHasModelo.getCentroHasModeloPK()) != null) {
                throw new PreexistingEntityException("CentroHasModelo " + centroHasModelo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CentroHasModelo centroHasModelo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            centroHasModelo = em.merge(centroHasModelo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CentroHasModeloPK id = centroHasModelo.getCentroHasModeloPK();
                if (findCentroHasModelo(id) == null) {
                    throw new NonexistentEntityException("The centroHasModelo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CentroHasModeloPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CentroHasModelo centroHasModelo;
            try {
                centroHasModelo = em.getReference(CentroHasModelo.class, id);
                centroHasModelo.getCentroHasModeloPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The centroHasModelo with id " + id + " no longer exists.", enfe);
            }
            em.remove(centroHasModelo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CentroHasModelo> findCentroHasModeloEntities() {
        return findCentroHasModeloEntities(true, -1, -1);
    }

    public List<CentroHasModelo> findCentroHasModeloEntities(int maxResults, int firstResult) {
        return findCentroHasModeloEntities(false, maxResults, firstResult);
    }

    private List<CentroHasModelo> findCentroHasModeloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CentroHasModelo.class));
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

    public CentroHasModelo findCentroHasModelo(CentroHasModeloPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CentroHasModelo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCentroHasModeloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CentroHasModelo> rt = cq.from(CentroHasModelo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
