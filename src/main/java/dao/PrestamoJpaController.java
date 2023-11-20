package dao;

import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.Tipoprestamo;
import dto.Cuenta;
import dto.Tipocomprobante;
import dto.Detalleprestamo;
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
            Tipoprestamo idTipoPrestamo = prestamo.getIdTipoPrestamo();
            if (idTipoPrestamo != null) {
                idTipoPrestamo = em.getReference(idTipoPrestamo.getClass(), idTipoPrestamo.getIdTipoPrestamo());
                prestamo.setIdTipoPrestamo(idTipoPrestamo);
            }
            Cuenta idCuenta = prestamo.getIdCuenta();
            if (idCuenta != null) {
                idCuenta = em.getReference(idCuenta.getClass(), idCuenta.getIdCuenta());
                prestamo.setIdCuenta(idCuenta);
            }
            Tipocomprobante idTipoComprobante = prestamo.getIdTipoComprobante();
            if (idTipoComprobante != null) {
                idTipoComprobante = em.getReference(idTipoComprobante.getClass(), idTipoComprobante.getIdTipoComprobante());
                prestamo.setIdTipoComprobante(idTipoComprobante);
            }
            Detalleprestamo idDetallePrestamo = prestamo.getIdDetallePrestamo();
            if (idDetallePrestamo != null) {
                idDetallePrestamo = em.getReference(idDetallePrestamo.getClass(), idDetallePrestamo.getIdDetallePrestamo());
                prestamo.setIdDetallePrestamo(idDetallePrestamo);
            }
            em.persist(prestamo);
            if (idTipoPrestamo != null) {
                idTipoPrestamo.getPrestamoList().add(prestamo);
                idTipoPrestamo = em.merge(idTipoPrestamo);
            }
            if (idCuenta != null) {
                idCuenta.getPrestamoList().add(prestamo);
                idCuenta = em.merge(idCuenta);
            }
            if (idTipoComprobante != null) {
                idTipoComprobante.getPrestamoList().add(prestamo);
                idTipoComprobante = em.merge(idTipoComprobante);
            }
            if (idDetallePrestamo != null) {
                idDetallePrestamo.getPrestamoList().add(prestamo);
                idDetallePrestamo = em.merge(idDetallePrestamo);
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
            Prestamo persistentPrestamo = em.find(Prestamo.class, prestamo.getIdPrestamo());
            Tipoprestamo idTipoPrestamoOld = persistentPrestamo.getIdTipoPrestamo();
            Tipoprestamo idTipoPrestamoNew = prestamo.getIdTipoPrestamo();
            Cuenta idCuentaOld = persistentPrestamo.getIdCuenta();
            Cuenta idCuentaNew = prestamo.getIdCuenta();
            Tipocomprobante idTipoComprobanteOld = persistentPrestamo.getIdTipoComprobante();
            Tipocomprobante idTipoComprobanteNew = prestamo.getIdTipoComprobante();
            Detalleprestamo idDetallePrestamoOld = persistentPrestamo.getIdDetallePrestamo();
            Detalleprestamo idDetallePrestamoNew = prestamo.getIdDetallePrestamo();
            if (idTipoPrestamoNew != null) {
                idTipoPrestamoNew = em.getReference(idTipoPrestamoNew.getClass(), idTipoPrestamoNew.getIdTipoPrestamo());
                prestamo.setIdTipoPrestamo(idTipoPrestamoNew);
            }
            if (idCuentaNew != null) {
                idCuentaNew = em.getReference(idCuentaNew.getClass(), idCuentaNew.getIdCuenta());
                prestamo.setIdCuenta(idCuentaNew);
            }
            if (idTipoComprobanteNew != null) {
                idTipoComprobanteNew = em.getReference(idTipoComprobanteNew.getClass(), idTipoComprobanteNew.getIdTipoComprobante());
                prestamo.setIdTipoComprobante(idTipoComprobanteNew);
            }
            if (idDetallePrestamoNew != null) {
                idDetallePrestamoNew = em.getReference(idDetallePrestamoNew.getClass(), idDetallePrestamoNew.getIdDetallePrestamo());
                prestamo.setIdDetallePrestamo(idDetallePrestamoNew);
            }
            prestamo = em.merge(prestamo);
            if (idTipoPrestamoOld != null && !idTipoPrestamoOld.equals(idTipoPrestamoNew)) {
                idTipoPrestamoOld.getPrestamoList().remove(prestamo);
                idTipoPrestamoOld = em.merge(idTipoPrestamoOld);
            }
            if (idTipoPrestamoNew != null && !idTipoPrestamoNew.equals(idTipoPrestamoOld)) {
                idTipoPrestamoNew.getPrestamoList().add(prestamo);
                idTipoPrestamoNew = em.merge(idTipoPrestamoNew);
            }
            if (idCuentaOld != null && !idCuentaOld.equals(idCuentaNew)) {
                idCuentaOld.getPrestamoList().remove(prestamo);
                idCuentaOld = em.merge(idCuentaOld);
            }
            if (idCuentaNew != null && !idCuentaNew.equals(idCuentaOld)) {
                idCuentaNew.getPrestamoList().add(prestamo);
                idCuentaNew = em.merge(idCuentaNew);
            }
            if (idTipoComprobanteOld != null && !idTipoComprobanteOld.equals(idTipoComprobanteNew)) {
                idTipoComprobanteOld.getPrestamoList().remove(prestamo);
                idTipoComprobanteOld = em.merge(idTipoComprobanteOld);
            }
            if (idTipoComprobanteNew != null && !idTipoComprobanteNew.equals(idTipoComprobanteOld)) {
                idTipoComprobanteNew.getPrestamoList().add(prestamo);
                idTipoComprobanteNew = em.merge(idTipoComprobanteNew);
            }
            if (idDetallePrestamoOld != null && !idDetallePrestamoOld.equals(idDetallePrestamoNew)) {
                idDetallePrestamoOld.getPrestamoList().remove(prestamo);
                idDetallePrestamoOld = em.merge(idDetallePrestamoOld);
            }
            if (idDetallePrestamoNew != null && !idDetallePrestamoNew.equals(idDetallePrestamoOld)) {
                idDetallePrestamoNew.getPrestamoList().add(prestamo);
                idDetallePrestamoNew = em.merge(idDetallePrestamoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = prestamo.getIdPrestamo();
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
                prestamo.getIdPrestamo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The prestamo with id " + id + " no longer exists.", enfe);
            }
            Tipoprestamo idTipoPrestamo = prestamo.getIdTipoPrestamo();
            if (idTipoPrestamo != null) {
                idTipoPrestamo.getPrestamoList().remove(prestamo);
                idTipoPrestamo = em.merge(idTipoPrestamo);
            }
            Cuenta idCuenta = prestamo.getIdCuenta();
            if (idCuenta != null) {
                idCuenta.getPrestamoList().remove(prestamo);
                idCuenta = em.merge(idCuenta);
            }
            Tipocomprobante idTipoComprobante = prestamo.getIdTipoComprobante();
            if (idTipoComprobante != null) {
                idTipoComprobante.getPrestamoList().remove(prestamo);
                idTipoComprobante = em.merge(idTipoComprobante);
            }
            Detalleprestamo idDetallePrestamo = prestamo.getIdDetallePrestamo();
            if (idDetallePrestamo != null) {
                idDetallePrestamo.getPrestamoList().remove(prestamo);
                idDetallePrestamo = em.merge(idDetallePrestamo);
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
