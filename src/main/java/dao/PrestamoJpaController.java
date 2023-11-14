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
import dto.Cliente;
import dto.Prestamo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Ing. Andres Gomez
 */
public class PrestamoJpaController implements Serializable {

    public PrestamoJpaController() {
    }

    public PrestamoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_CriptoTheChaulisCW_war_1.0-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Prestamo prestamo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente codigoCliente = prestamo.getCodigoCliente();
            if (codigoCliente != null) {
                codigoCliente = em.getReference(codigoCliente.getClass(), codigoCliente.getCodigoCliente());
                prestamo.setCodigoCliente(codigoCliente);
            }
            em.persist(prestamo);
            if (codigoCliente != null) {
                codigoCliente.getPrestamoList().add(prestamo);
                codigoCliente = em.merge(codigoCliente);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Prestamo prestamo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Prestamo persistentPrestamo = em.find(Prestamo.class, prestamo.getCodigoPrestamo());
            Cliente codigoClienteOld = persistentPrestamo.getCodigoCliente();
            Cliente codigoClienteNew = prestamo.getCodigoCliente();
            if (codigoClienteNew != null) {
                codigoClienteNew = em.getReference(codigoClienteNew.getClass(), codigoClienteNew.getCodigoCliente());
                prestamo.setCodigoCliente(codigoClienteNew);
            }
            prestamo = em.merge(prestamo);
            if (codigoClienteOld != null && !codigoClienteOld.equals(codigoClienteNew)) {
                codigoClienteOld.getPrestamoList().remove(prestamo);
                codigoClienteOld = em.merge(codigoClienteOld);
            }
            if (codigoClienteNew != null && !codigoClienteNew.equals(codigoClienteOld)) {
                codigoClienteNew.getPrestamoList().add(prestamo);
                codigoClienteNew = em.merge(codigoClienteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = prestamo.getCodigoPrestamo();
                if (findPrestamo(id) == null) {
                    throw new NonexistentEntityException("The prestamo with id " + id + " no longer exists.");
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
            Prestamo prestamo;
            try {
                prestamo = em.getReference(Prestamo.class, id);
                prestamo.getCodigoPrestamo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The prestamo with id " + id + " no longer exists.", enfe);
            }
            Cliente codigoCliente = prestamo.getCodigoCliente();
            if (codigoCliente != null) {
                codigoCliente.getPrestamoList().remove(prestamo);
                codigoCliente = em.merge(codigoCliente);
            }
            em.remove(prestamo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Prestamo> findPrestamoEntities() {
        return findPrestamoEntities(true, -1, -1);
    }

    public List<Prestamo> findPrestamoEntities(int maxResults, int firstResult) {
        return findPrestamoEntities(false, maxResults, firstResult);
    }

    private List<Prestamo> findPrestamoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Prestamo.class));
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

    public Prestamo findPrestamo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Prestamo.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrestamoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Prestamo> rt = cq.from(Prestamo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public boolean registrarPrestamos(Prestamo registrarPrest) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(registrarPrest);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
