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
import dto.Transferencia;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Ing. Andres Gomez
 */
public class TransferenciaJpaController implements Serializable {

    public TransferenciaJpaController() {
    }

    public TransferenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_CriptoTheChaulisCW_war_1.0-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Transferencia transferencia) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta codigoCuentaOrigen = transferencia.getCodigoCuentaOrigen();
            if (codigoCuentaOrigen != null) {
                codigoCuentaOrigen = em.getReference(codigoCuentaOrigen.getClass(), codigoCuentaOrigen.getCodigoCuenta());
                transferencia.setCodigoCuentaOrigen(codigoCuentaOrigen);
            }
            Cuenta codigoCuentaDestino = transferencia.getCodigoCuentaDestino();
            if (codigoCuentaDestino != null) {
                codigoCuentaDestino = em.getReference(codigoCuentaDestino.getClass(), codigoCuentaDestino.getCodigoCuenta());
                transferencia.setCodigoCuentaDestino(codigoCuentaDestino);
            }
            em.persist(transferencia);
            if (codigoCuentaOrigen != null) {
                codigoCuentaOrigen.getTransferenciaList().add(transferencia);
                codigoCuentaOrigen = em.merge(codigoCuentaOrigen);
            }
            if (codigoCuentaDestino != null) {
                codigoCuentaDestino.getTransferenciaList().add(transferencia);
                codigoCuentaDestino = em.merge(codigoCuentaDestino);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Transferencia transferencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transferencia persistentTransferencia = em.find(Transferencia.class, transferencia.getCodigoTransferencia());
            Cuenta codigoCuentaOrigenOld = persistentTransferencia.getCodigoCuentaOrigen();
            Cuenta codigoCuentaOrigenNew = transferencia.getCodigoCuentaOrigen();
            Cuenta codigoCuentaDestinoOld = persistentTransferencia.getCodigoCuentaDestino();
            Cuenta codigoCuentaDestinoNew = transferencia.getCodigoCuentaDestino();
            if (codigoCuentaOrigenNew != null) {
                codigoCuentaOrigenNew = em.getReference(codigoCuentaOrigenNew.getClass(), codigoCuentaOrigenNew.getCodigoCuenta());
                transferencia.setCodigoCuentaOrigen(codigoCuentaOrigenNew);
            }
            if (codigoCuentaDestinoNew != null) {
                codigoCuentaDestinoNew = em.getReference(codigoCuentaDestinoNew.getClass(), codigoCuentaDestinoNew.getCodigoCuenta());
                transferencia.setCodigoCuentaDestino(codigoCuentaDestinoNew);
            }
            transferencia = em.merge(transferencia);
            if (codigoCuentaOrigenOld != null && !codigoCuentaOrigenOld.equals(codigoCuentaOrigenNew)) {
                codigoCuentaOrigenOld.getTransferenciaList().remove(transferencia);
                codigoCuentaOrigenOld = em.merge(codigoCuentaOrigenOld);
            }
            if (codigoCuentaOrigenNew != null && !codigoCuentaOrigenNew.equals(codigoCuentaOrigenOld)) {
                codigoCuentaOrigenNew.getTransferenciaList().add(transferencia);
                codigoCuentaOrigenNew = em.merge(codigoCuentaOrigenNew);
            }
            if (codigoCuentaDestinoOld != null && !codigoCuentaDestinoOld.equals(codigoCuentaDestinoNew)) {
                codigoCuentaDestinoOld.getTransferenciaList().remove(transferencia);
                codigoCuentaDestinoOld = em.merge(codigoCuentaDestinoOld);
            }
            if (codigoCuentaDestinoNew != null && !codigoCuentaDestinoNew.equals(codigoCuentaDestinoOld)) {
                codigoCuentaDestinoNew.getTransferenciaList().add(transferencia);
                codigoCuentaDestinoNew = em.merge(codigoCuentaDestinoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = transferencia.getCodigoTransferencia();
                if (findTransferencia(id) == null) {
                    throw new NonexistentEntityException("The transferencia with id " + id + " no longer exists.");
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
            Transferencia transferencia;
            try {
                transferencia = em.getReference(Transferencia.class, id);
                transferencia.getCodigoTransferencia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transferencia with id " + id + " no longer exists.", enfe);
            }
            Cuenta codigoCuentaOrigen = transferencia.getCodigoCuentaOrigen();
            if (codigoCuentaOrigen != null) {
                codigoCuentaOrigen.getTransferenciaList().remove(transferencia);
                codigoCuentaOrigen = em.merge(codigoCuentaOrigen);
            }
            Cuenta codigoCuentaDestino = transferencia.getCodigoCuentaDestino();
            if (codigoCuentaDestino != null) {
                codigoCuentaDestino.getTransferenciaList().remove(transferencia);
                codigoCuentaDestino = em.merge(codigoCuentaDestino);
            }
            em.remove(transferencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Transferencia> findTransferenciaEntities() {
        return findTransferenciaEntities(true, -1, -1);
    }

    public List<Transferencia> findTransferenciaEntities(int maxResults, int firstResult) {
        return findTransferenciaEntities(false, maxResults, firstResult);
    }

    private List<Transferencia> findTransferenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Transferencia.class));
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

    public Transferencia findTransferencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Transferencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransferenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Transferencia> rt = cq.from(Transferencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Object[]> listTransfer() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Transferencia.listar");
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public boolean insertTransfer(Transferencia transferencia) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(transferencia);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
