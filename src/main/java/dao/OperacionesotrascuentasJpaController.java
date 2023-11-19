package dao;

import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.Tipooperacion;
import dto.Cuenta;
import dto.Operacionesotrascuentas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Ing. Andres Gomez
 */
public class OperacionesotrascuentasJpaController implements Serializable {

    public OperacionesotrascuentasJpaController() {
    }

    public OperacionesotrascuentasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_CriptoTheChaulisCW_war_1.0-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Operacionesotrascuentas operacionesotrascuentas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipooperacion idTipoOperacion = operacionesotrascuentas.getIdTipoOperacion();
            if (idTipoOperacion != null) {
                idTipoOperacion = em.getReference(idTipoOperacion.getClass(), idTipoOperacion.getIdTipoOperacion());
                operacionesotrascuentas.setIdTipoOperacion(idTipoOperacion);
            }
            Cuenta idCuentaOrigen = operacionesotrascuentas.getIdCuentaOrigen();
            if (idCuentaOrigen != null) {
                idCuentaOrigen = em.getReference(idCuentaOrigen.getClass(), idCuentaOrigen.getIdCuenta());
                operacionesotrascuentas.setIdCuentaOrigen(idCuentaOrigen);
            }
            Cuenta idCuentaDestino = operacionesotrascuentas.getIdCuentaDestino();
            if (idCuentaDestino != null) {
                idCuentaDestino = em.getReference(idCuentaDestino.getClass(), idCuentaDestino.getIdCuenta());
                operacionesotrascuentas.setIdCuentaDestino(idCuentaDestino);
            }
            em.persist(operacionesotrascuentas);
            if (idTipoOperacion != null) {
                idTipoOperacion.getOperacionesotrascuentasList().add(operacionesotrascuentas);
                idTipoOperacion = em.merge(idTipoOperacion);
            }
            if (idCuentaOrigen != null) {
                idCuentaOrigen.getOperacionesotrascuentasList().add(operacionesotrascuentas);
                idCuentaOrigen = em.merge(idCuentaOrigen);
            }
            if (idCuentaDestino != null) {
                idCuentaDestino.getOperacionesotrascuentasList().add(operacionesotrascuentas);
                idCuentaDestino = em.merge(idCuentaDestino);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Operacionesotrascuentas operacionesotrascuentas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Operacionesotrascuentas persistentOperacionesotrascuentas = em.find(Operacionesotrascuentas.class, operacionesotrascuentas.getIdOperacionOC());
            Tipooperacion idTipoOperacionOld = persistentOperacionesotrascuentas.getIdTipoOperacion();
            Tipooperacion idTipoOperacionNew = operacionesotrascuentas.getIdTipoOperacion();
            Cuenta idCuentaOrigenOld = persistentOperacionesotrascuentas.getIdCuentaOrigen();
            Cuenta idCuentaOrigenNew = operacionesotrascuentas.getIdCuentaOrigen();
            Cuenta idCuentaDestinoOld = persistentOperacionesotrascuentas.getIdCuentaDestino();
            Cuenta idCuentaDestinoNew = operacionesotrascuentas.getIdCuentaDestino();
            if (idTipoOperacionNew != null) {
                idTipoOperacionNew = em.getReference(idTipoOperacionNew.getClass(), idTipoOperacionNew.getIdTipoOperacion());
                operacionesotrascuentas.setIdTipoOperacion(idTipoOperacionNew);
            }
            if (idCuentaOrigenNew != null) {
                idCuentaOrigenNew = em.getReference(idCuentaOrigenNew.getClass(), idCuentaOrigenNew.getIdCuenta());
                operacionesotrascuentas.setIdCuentaOrigen(idCuentaOrigenNew);
            }
            if (idCuentaDestinoNew != null) {
                idCuentaDestinoNew = em.getReference(idCuentaDestinoNew.getClass(), idCuentaDestinoNew.getIdCuenta());
                operacionesotrascuentas.setIdCuentaDestino(idCuentaDestinoNew);
            }
            operacionesotrascuentas = em.merge(operacionesotrascuentas);
            if (idTipoOperacionOld != null && !idTipoOperacionOld.equals(idTipoOperacionNew)) {
                idTipoOperacionOld.getOperacionesotrascuentasList().remove(operacionesotrascuentas);
                idTipoOperacionOld = em.merge(idTipoOperacionOld);
            }
            if (idTipoOperacionNew != null && !idTipoOperacionNew.equals(idTipoOperacionOld)) {
                idTipoOperacionNew.getOperacionesotrascuentasList().add(operacionesotrascuentas);
                idTipoOperacionNew = em.merge(idTipoOperacionNew);
            }
            if (idCuentaOrigenOld != null && !idCuentaOrigenOld.equals(idCuentaOrigenNew)) {
                idCuentaOrigenOld.getOperacionesotrascuentasList().remove(operacionesotrascuentas);
                idCuentaOrigenOld = em.merge(idCuentaOrigenOld);
            }
            if (idCuentaOrigenNew != null && !idCuentaOrigenNew.equals(idCuentaOrigenOld)) {
                idCuentaOrigenNew.getOperacionesotrascuentasList().add(operacionesotrascuentas);
                idCuentaOrigenNew = em.merge(idCuentaOrigenNew);
            }
            if (idCuentaDestinoOld != null && !idCuentaDestinoOld.equals(idCuentaDestinoNew)) {
                idCuentaDestinoOld.getOperacionesotrascuentasList().remove(operacionesotrascuentas);
                idCuentaDestinoOld = em.merge(idCuentaDestinoOld);
            }
            if (idCuentaDestinoNew != null && !idCuentaDestinoNew.equals(idCuentaDestinoOld)) {
                idCuentaDestinoNew.getOperacionesotrascuentasList().add(operacionesotrascuentas);
                idCuentaDestinoNew = em.merge(idCuentaDestinoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = operacionesotrascuentas.getIdOperacionOC();
                if (findOperacionesotrascuentas(id) == null) {
                    throw new NonexistentEntityException("The operacionesotrascuentas with id " + id + " no longer exists.");
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
            Operacionesotrascuentas operacionesotrascuentas;
            try {
                operacionesotrascuentas = em.getReference(Operacionesotrascuentas.class, id);
                operacionesotrascuentas.getIdOperacionOC();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The operacionesotrascuentas with id " + id + " no longer exists.", enfe);
            }
            Tipooperacion idTipoOperacion = operacionesotrascuentas.getIdTipoOperacion();
            if (idTipoOperacion != null) {
                idTipoOperacion.getOperacionesotrascuentasList().remove(operacionesotrascuentas);
                idTipoOperacion = em.merge(idTipoOperacion);
            }
            Cuenta idCuentaOrigen = operacionesotrascuentas.getIdCuentaOrigen();
            if (idCuentaOrigen != null) {
                idCuentaOrigen.getOperacionesotrascuentasList().remove(operacionesotrascuentas);
                idCuentaOrigen = em.merge(idCuentaOrigen);
            }
            Cuenta idCuentaDestino = operacionesotrascuentas.getIdCuentaDestino();
            if (idCuentaDestino != null) {
                idCuentaDestino.getOperacionesotrascuentasList().remove(operacionesotrascuentas);
                idCuentaDestino = em.merge(idCuentaDestino);
            }
            em.remove(operacionesotrascuentas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Operacionesotrascuentas> findOperacionesotrascuentasEntities() {
        return findOperacionesotrascuentasEntities(true, -1, -1);
    }

    public List<Operacionesotrascuentas> findOperacionesotrascuentasEntities(int maxResults, int firstResult) {
        return findOperacionesotrascuentasEntities(false, maxResults, firstResult);
    }

    private List<Operacionesotrascuentas> findOperacionesotrascuentasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Operacionesotrascuentas.class));
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

    public Operacionesotrascuentas findOperacionesotrascuentas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Operacionesotrascuentas.class, id);
        } finally {
            em.close();
        }
    }

    public int getOperacionesotrascuentasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Operacionesotrascuentas> rt = cq.from(Operacionesotrascuentas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
