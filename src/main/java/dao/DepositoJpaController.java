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
import dto.Deposito;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ing. Andres Gomez
 */
public class DepositoJpaController implements Serializable {

    public DepositoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Deposito deposito) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta codigoCuenta = deposito.getCodigoCuenta();
            if (codigoCuenta != null) {
                codigoCuenta = em.getReference(codigoCuenta.getClass(), codigoCuenta.getCodigoCuenta());
                deposito.setCodigoCuenta(codigoCuenta);
            }
            em.persist(deposito);
            if (codigoCuenta != null) {
                codigoCuenta.getDepositoList().add(deposito);
                codigoCuenta = em.merge(codigoCuenta);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Deposito deposito) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Deposito persistentDeposito = em.find(Deposito.class, deposito.getCodigoDeposito());
            Cuenta codigoCuentaOld = persistentDeposito.getCodigoCuenta();
            Cuenta codigoCuentaNew = deposito.getCodigoCuenta();
            if (codigoCuentaNew != null) {
                codigoCuentaNew = em.getReference(codigoCuentaNew.getClass(), codigoCuentaNew.getCodigoCuenta());
                deposito.setCodigoCuenta(codigoCuentaNew);
            }
            deposito = em.merge(deposito);
            if (codigoCuentaOld != null && !codigoCuentaOld.equals(codigoCuentaNew)) {
                codigoCuentaOld.getDepositoList().remove(deposito);
                codigoCuentaOld = em.merge(codigoCuentaOld);
            }
            if (codigoCuentaNew != null && !codigoCuentaNew.equals(codigoCuentaOld)) {
                codigoCuentaNew.getDepositoList().add(deposito);
                codigoCuentaNew = em.merge(codigoCuentaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = deposito.getCodigoDeposito();
                if (findDeposito(id) == null) {
                    throw new NonexistentEntityException("The deposito with id " + id + " no longer exists.");
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
            Deposito deposito;
            try {
                deposito = em.getReference(Deposito.class, id);
                deposito.getCodigoDeposito();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The deposito with id " + id + " no longer exists.", enfe);
            }
            Cuenta codigoCuenta = deposito.getCodigoCuenta();
            if (codigoCuenta != null) {
                codigoCuenta.getDepositoList().remove(deposito);
                codigoCuenta = em.merge(codigoCuenta);
            }
            em.remove(deposito);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Deposito> findDepositoEntities() {
        return findDepositoEntities(true, -1, -1);
    }

    public List<Deposito> findDepositoEntities(int maxResults, int firstResult) {
        return findDepositoEntities(false, maxResults, firstResult);
    }

    private List<Deposito> findDepositoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Deposito.class));
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

    public Deposito findDeposito(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Deposito.class, id);
        } finally {
            em.close();
        }
    }

    public int getDepositoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Deposito> rt = cq.from(Deposito.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
