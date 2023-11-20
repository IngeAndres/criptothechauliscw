package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.Prestamo;
import dto.Tipocomprobante;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Ing. Andres Gomez
 */
public class TipocomprobanteJpaController implements Serializable {

    public TipocomprobanteJpaController() {
    }

    public TipocomprobanteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_CriptoTheChaulisCW_war_1.0-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipocomprobante tipocomprobante) {
        if (tipocomprobante.getPrestamoList() == null) {
            tipocomprobante.setPrestamoList(new ArrayList<Prestamo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Prestamo> attachedPrestamoList = new ArrayList<Prestamo>();
            for (Prestamo prestamoListPrestamoToAttach : tipocomprobante.getPrestamoList()) {
                prestamoListPrestamoToAttach = em.getReference(prestamoListPrestamoToAttach.getClass(), prestamoListPrestamoToAttach.getIdPrestamo());
                attachedPrestamoList.add(prestamoListPrestamoToAttach);
            }
            tipocomprobante.setPrestamoList(attachedPrestamoList);
            em.persist(tipocomprobante);
            for (Prestamo prestamoListPrestamo : tipocomprobante.getPrestamoList()) {
                Tipocomprobante oldIdTipoComprobanteOfPrestamoListPrestamo = prestamoListPrestamo.getIdTipoComprobante();
                prestamoListPrestamo.setIdTipoComprobante(tipocomprobante);
                prestamoListPrestamo = em.merge(prestamoListPrestamo);
                if (oldIdTipoComprobanteOfPrestamoListPrestamo != null) {
                    oldIdTipoComprobanteOfPrestamoListPrestamo.getPrestamoList().remove(prestamoListPrestamo);
                    oldIdTipoComprobanteOfPrestamoListPrestamo = em.merge(oldIdTipoComprobanteOfPrestamoListPrestamo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipocomprobante tipocomprobante) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipocomprobante persistentTipocomprobante = em.find(Tipocomprobante.class, tipocomprobante.getIdTipoComprobante());
            List<Prestamo> prestamoListOld = persistentTipocomprobante.getPrestamoList();
            List<Prestamo> prestamoListNew = tipocomprobante.getPrestamoList();
            List<String> illegalOrphanMessages = null;
            for (Prestamo prestamoListOldPrestamo : prestamoListOld) {
                if (!prestamoListNew.contains(prestamoListOldPrestamo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Prestamo " + prestamoListOldPrestamo + " since its idTipoComprobante field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Prestamo> attachedPrestamoListNew = new ArrayList<Prestamo>();
            for (Prestamo prestamoListNewPrestamoToAttach : prestamoListNew) {
                prestamoListNewPrestamoToAttach = em.getReference(prestamoListNewPrestamoToAttach.getClass(), prestamoListNewPrestamoToAttach.getIdPrestamo());
                attachedPrestamoListNew.add(prestamoListNewPrestamoToAttach);
            }
            prestamoListNew = attachedPrestamoListNew;
            tipocomprobante.setPrestamoList(prestamoListNew);
            tipocomprobante = em.merge(tipocomprobante);
            for (Prestamo prestamoListNewPrestamo : prestamoListNew) {
                if (!prestamoListOld.contains(prestamoListNewPrestamo)) {
                    Tipocomprobante oldIdTipoComprobanteOfPrestamoListNewPrestamo = prestamoListNewPrestamo.getIdTipoComprobante();
                    prestamoListNewPrestamo.setIdTipoComprobante(tipocomprobante);
                    prestamoListNewPrestamo = em.merge(prestamoListNewPrestamo);
                    if (oldIdTipoComprobanteOfPrestamoListNewPrestamo != null && !oldIdTipoComprobanteOfPrestamoListNewPrestamo.equals(tipocomprobante)) {
                        oldIdTipoComprobanteOfPrestamoListNewPrestamo.getPrestamoList().remove(prestamoListNewPrestamo);
                        oldIdTipoComprobanteOfPrestamoListNewPrestamo = em.merge(oldIdTipoComprobanteOfPrestamoListNewPrestamo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipocomprobante.getIdTipoComprobante();
                if (findTipocomprobante(id) == null) {
                    throw new NonexistentEntityException("The tipocomprobante with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipocomprobante tipocomprobante;
            try {
                tipocomprobante = em.getReference(Tipocomprobante.class, id);
                tipocomprobante.getIdTipoComprobante();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipocomprobante with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Prestamo> prestamoListOrphanCheck = tipocomprobante.getPrestamoList();
            for (Prestamo prestamoListOrphanCheckPrestamo : prestamoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipocomprobante (" + tipocomprobante + ") cannot be destroyed since the Prestamo " + prestamoListOrphanCheckPrestamo + " in its prestamoList field has a non-nullable idTipoComprobante field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipocomprobante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipocomprobante> findTipocomprobanteEntities() {
        return findTipocomprobanteEntities(true, -1, -1);
    }

    public List<Tipocomprobante> findTipocomprobanteEntities(int maxResults, int firstResult) {
        return findTipocomprobanteEntities(false, maxResults, firstResult);
    }

    private List<Tipocomprobante> findTipocomprobanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipocomprobante.class));
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

    public Tipocomprobante findTipocomprobante(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipocomprobante.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipocomprobanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipocomprobante> rt = cq.from(Tipocomprobante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Tipocomprobante> listarTipocomprobante() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Tipocomprobante.findAll");
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
}
