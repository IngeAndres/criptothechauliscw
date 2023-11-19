package dao;

import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.Tipooperacion;
import dto.Cuenta;
import dto.Operacionescuentaspropias;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Ing. Andres Gomez
 */
public class OperacionescuentaspropiasJpaController implements Serializable {

    public OperacionescuentaspropiasJpaController() {
    }

    public OperacionescuentaspropiasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_CriptoTheChaulisCW_war_1.0-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Operacionescuentaspropias operacionescuentaspropias) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipooperacion idTipoOperacion = operacionescuentaspropias.getIdTipoOperacion();
            if (idTipoOperacion != null) {
                idTipoOperacion = em.getReference(idTipoOperacion.getClass(), idTipoOperacion.getIdTipoOperacion());
                operacionescuentaspropias.setIdTipoOperacion(idTipoOperacion);
            }
            Cuenta idCuenta = operacionescuentaspropias.getIdCuenta();
            if (idCuenta != null) {
                idCuenta = em.getReference(idCuenta.getClass(), idCuenta.getIdCuenta());
                operacionescuentaspropias.setIdCuenta(idCuenta);
            }
            em.persist(operacionescuentaspropias);
            if (idTipoOperacion != null) {
                idTipoOperacion.getOperacionescuentaspropiasList().add(operacionescuentaspropias);
                idTipoOperacion = em.merge(idTipoOperacion);
            }
            if (idCuenta != null) {
                idCuenta.getOperacionescuentaspropiasList().add(operacionescuentaspropias);
                idCuenta = em.merge(idCuenta);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Operacionescuentaspropias operacionescuentaspropias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Operacionescuentaspropias persistentOperacionescuentaspropias = em.find(Operacionescuentaspropias.class, operacionescuentaspropias.getIdOperacionCP());
            Tipooperacion idTipoOperacionOld = persistentOperacionescuentaspropias.getIdTipoOperacion();
            Tipooperacion idTipoOperacionNew = operacionescuentaspropias.getIdTipoOperacion();
            Cuenta idCuentaOld = persistentOperacionescuentaspropias.getIdCuenta();
            Cuenta idCuentaNew = operacionescuentaspropias.getIdCuenta();
            if (idTipoOperacionNew != null) {
                idTipoOperacionNew = em.getReference(idTipoOperacionNew.getClass(), idTipoOperacionNew.getIdTipoOperacion());
                operacionescuentaspropias.setIdTipoOperacion(idTipoOperacionNew);
            }
            if (idCuentaNew != null) {
                idCuentaNew = em.getReference(idCuentaNew.getClass(), idCuentaNew.getIdCuenta());
                operacionescuentaspropias.setIdCuenta(idCuentaNew);
            }
            operacionescuentaspropias = em.merge(operacionescuentaspropias);
            if (idTipoOperacionOld != null && !idTipoOperacionOld.equals(idTipoOperacionNew)) {
                idTipoOperacionOld.getOperacionescuentaspropiasList().remove(operacionescuentaspropias);
                idTipoOperacionOld = em.merge(idTipoOperacionOld);
            }
            if (idTipoOperacionNew != null && !idTipoOperacionNew.equals(idTipoOperacionOld)) {
                idTipoOperacionNew.getOperacionescuentaspropiasList().add(operacionescuentaspropias);
                idTipoOperacionNew = em.merge(idTipoOperacionNew);
            }
            if (idCuentaOld != null && !idCuentaOld.equals(idCuentaNew)) {
                idCuentaOld.getOperacionescuentaspropiasList().remove(operacionescuentaspropias);
                idCuentaOld = em.merge(idCuentaOld);
            }
            if (idCuentaNew != null && !idCuentaNew.equals(idCuentaOld)) {
                idCuentaNew.getOperacionescuentaspropiasList().add(operacionescuentaspropias);
                idCuentaNew = em.merge(idCuentaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = operacionescuentaspropias.getIdOperacionCP();
                if (findOperacionescuentaspropias(id) == null) {
                    throw new NonexistentEntityException("The operacionescuentaspropias with id " + id + " no longer exists.");
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
            Operacionescuentaspropias operacionescuentaspropias;
            try {
                operacionescuentaspropias = em.getReference(Operacionescuentaspropias.class, id);
                operacionescuentaspropias.getIdOperacionCP();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The operacionescuentaspropias with id " + id + " no longer exists.", enfe);
            }
            Tipooperacion idTipoOperacion = operacionescuentaspropias.getIdTipoOperacion();
            if (idTipoOperacion != null) {
                idTipoOperacion.getOperacionescuentaspropiasList().remove(operacionescuentaspropias);
                idTipoOperacion = em.merge(idTipoOperacion);
            }
            Cuenta idCuenta = operacionescuentaspropias.getIdCuenta();
            if (idCuenta != null) {
                idCuenta.getOperacionescuentaspropiasList().remove(operacionescuentaspropias);
                idCuenta = em.merge(idCuenta);
            }
            em.remove(operacionescuentaspropias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Operacionescuentaspropias> findOperacionescuentaspropiasEntities() {
        return findOperacionescuentaspropiasEntities(true, -1, -1);
    }

    public List<Operacionescuentaspropias> findOperacionescuentaspropiasEntities(int maxResults, int firstResult) {
        return findOperacionescuentaspropiasEntities(false, maxResults, firstResult);
    }

    private List<Operacionescuentaspropias> findOperacionescuentaspropiasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Operacionescuentaspropias.class));
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

    public Operacionescuentaspropias findOperacionescuentaspropias(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Operacionescuentaspropias.class, id);
        } finally {
            em.close();
        }
    }

    public int getOperacionescuentaspropiasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Operacionescuentaspropias> rt = cq.from(Operacionescuentaspropias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
