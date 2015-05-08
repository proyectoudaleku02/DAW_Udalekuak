/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.BD;

import Modelo.BD.exceptions.NonexistentEntityException;
import Modelo.BD.exceptions.PreexistingEntityException;
import Modelo.UML.Centros;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.UML.Provincias;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author 1glm02
 */
public class CentrosJpaController implements Serializable {

    public CentrosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Centros centros) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Provincias idprovincia = centros.getIdprovincia();
            if (idprovincia != null) {
                idprovincia = em.getReference(idprovincia.getClass(), idprovincia.getIdprovincia());
                centros.setIdprovincia(idprovincia);
            }
            em.persist(centros);
            if (idprovincia != null) {
                idprovincia.getCentrosCollection().add(centros);
                idprovincia = em.merge(idprovincia);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCentros(centros.getIdcentro()) != null) {
                throw new PreexistingEntityException("Centros " + centros + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Centros centros) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Centros persistentCentros = em.find(Centros.class, centros.getIdcentro());
            Provincias idprovinciaOld = persistentCentros.getIdprovincia();
            Provincias idprovinciaNew = centros.getIdprovincia();
            if (idprovinciaNew != null) {
                idprovinciaNew = em.getReference(idprovinciaNew.getClass(), idprovinciaNew.getIdprovincia());
                centros.setIdprovincia(idprovinciaNew);
            }
            centros = em.merge(centros);
            if (idprovinciaOld != null && !idprovinciaOld.equals(idprovinciaNew)) {
                idprovinciaOld.getCentrosCollection().remove(centros);
                idprovinciaOld = em.merge(idprovinciaOld);
            }
            if (idprovinciaNew != null && !idprovinciaNew.equals(idprovinciaOld)) {
                idprovinciaNew.getCentrosCollection().add(centros);
                idprovinciaNew = em.merge(idprovinciaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = centros.getIdcentro();
                if (findCentros(id) == null) {
                    throw new NonexistentEntityException("The centros with id " + id + " no longer exists.");
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
            Centros centros;
            try {
                centros = em.getReference(Centros.class, id);
                centros.getIdcentro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The centros with id " + id + " no longer exists.", enfe);
            }
            Provincias idprovincia = centros.getIdprovincia();
            if (idprovincia != null) {
                idprovincia.getCentrosCollection().remove(centros);
                idprovincia = em.merge(idprovincia);
            }
            em.remove(centros);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Centros> findCentrosEntities() {
        return findCentrosEntities(true, -1, -1);
    }

    public List<Centros> findCentrosEntities(int maxResults, int firstResult) {
        return findCentrosEntities(false, maxResults, firstResult);
    }

    private List<Centros> findCentrosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Centros.class));
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

    public Centros findCentros(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Centros.class, id);
        } finally {
            em.close();
        }
    }

    public int getCentrosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Centros> rt = cq.from(Centros.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
