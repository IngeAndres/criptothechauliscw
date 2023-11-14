/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.Cuenta;
import dto.Retiro;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ing. Andres Gomez
 */
public class RetiroJpaController implements Serializable {

    public RetiroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Retiro retiro) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta codigoCuenta = retiro.getCodigoCuenta();
            if (codigoCuenta != null) {
                codigoCuenta = em.getReference(codigoCuenta.getClass(), codigoCuenta.getCodigoCuenta());
                retiro.setCodigoCuenta(codigoCuenta);
            }
            em.persist(retiro);
            if (codigoCuenta != null) {
                codigoCuenta.getRetiroList().add(retiro);
                codigoCuenta = em.merge(codigoCuenta);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Retiro retiro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Retiro persistentRetiro = em.find(Retiro.class, retiro.getCodigoRetiro());
            Cuenta codigoCuentaOld = persistentRetiro.getCodigoCuenta();
            Cuenta codigoCuentaNew = retiro.getCodigoCuenta();
            if (codigoCuentaNew != null) {
                codigoCuentaNew = em.getReference(codigoCuentaNew.getClass(), codigoCuentaNew.getCodigoCuenta());
                retiro.setCodigoCuenta(codigoCuentaNew);
            }
            retiro = em.merge(retiro);
            if (codigoCuentaOld != null && !codigoCuentaOld.equals(codigoCuentaNew)) {
                codigoCuentaOld.getRetiroList().remove(retiro);
                codigoCuentaOld = em.merge(codigoCuentaOld);
            }
            if (codigoCuentaNew != null && !codigoCuentaNew.equals(codigoCuentaOld)) {
                codigoCuentaNew.getRetiroList().add(retiro);
                codigoCuentaNew = em.merge(codigoCuentaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = retiro.getCodigoRetiro();
                if (findRetiro(id) == null) {
                    throw new NonexistentEntityException("The retiro with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Retiro retiro;
            try {
                retiro = em.getReference(Retiro.class, id);
                retiro.getCodigoRetiro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The retiro with id " + id + " no longer exists.", enfe);
            }
            Cuenta codigoCuenta = retiro.getCodigoCuenta();
            if (codigoCuenta != null) {
                codigoCuenta.getRetiroList().remove(retiro);
                codigoCuenta = em.merge(codigoCuenta);
            }
            em.remove(retiro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Retiro> findRetiroEntities() {
        return findRetiroEntities(true, -1, -1);
    }

    public List<Retiro> findRetiroEntities(int maxResults, int firstResult) {
        return findRetiroEntities(false, maxResults, firstResult);
    }

    private List<Retiro> findRetiroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Retiro.class));
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

    public Retiro findRetiro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Retiro.class, id);
        } finally {
            em.close();
        }
    }

    public int getRetiroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Retiro> rt = cq.from(Retiro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
