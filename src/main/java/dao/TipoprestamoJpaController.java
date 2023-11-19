package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.Prestamo;
import dto.Tipoprestamo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Ing. Andres Gomez
 */
public class TipoprestamoJpaController implements Serializable {

    public TipoprestamoJpaController() {
    }

    public TipoprestamoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_CriptoTheChaulisCW_war_1.0-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipoprestamo tipoprestamo) {
        if (tipoprestamo.getPrestamoList() == null) {
            tipoprestamo.setPrestamoList(new ArrayList<Prestamo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Prestamo> attachedPrestamoList = new ArrayList<Prestamo>();
            for (Prestamo prestamoListPrestamoToAttach : tipoprestamo.getPrestamoList()) {
                prestamoListPrestamoToAttach = em.getReference(prestamoListPrestamoToAttach.getClass(), prestamoListPrestamoToAttach.getIdPrestamo());
                attachedPrestamoList.add(prestamoListPrestamoToAttach);
            }
            tipoprestamo.setPrestamoList(attachedPrestamoList);
            em.persist(tipoprestamo);
            for (Prestamo prestamoListPrestamo : tipoprestamo.getPrestamoList()) {
                Tipoprestamo oldIdTipoPrestamoOfPrestamoListPrestamo = prestamoListPrestamo.getIdTipoPrestamo();
                prestamoListPrestamo.setIdTipoPrestamo(tipoprestamo);
                prestamoListPrestamo = em.merge(prestamoListPrestamo);
                if (oldIdTipoPrestamoOfPrestamoListPrestamo != null) {
                    oldIdTipoPrestamoOfPrestamoListPrestamo.getPrestamoList().remove(prestamoListPrestamo);
                    oldIdTipoPrestamoOfPrestamoListPrestamo = em.merge(oldIdTipoPrestamoOfPrestamoListPrestamo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipoprestamo tipoprestamo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipoprestamo persistentTipoprestamo = em.find(Tipoprestamo.class, tipoprestamo.getIdTipoPrestamo());
            List<Prestamo> prestamoListOld = persistentTipoprestamo.getPrestamoList();
            List<Prestamo> prestamoListNew = tipoprestamo.getPrestamoList();
            List<String> illegalOrphanMessages = null;
            for (Prestamo prestamoListOldPrestamo : prestamoListOld) {
                if (!prestamoListNew.contains(prestamoListOldPrestamo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Prestamo " + prestamoListOldPrestamo + " since its idTipoPrestamo field is not nullable.");
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
            tipoprestamo.setPrestamoList(prestamoListNew);
            tipoprestamo = em.merge(tipoprestamo);
            for (Prestamo prestamoListNewPrestamo : prestamoListNew) {
                if (!prestamoListOld.contains(prestamoListNewPrestamo)) {
                    Tipoprestamo oldIdTipoPrestamoOfPrestamoListNewPrestamo = prestamoListNewPrestamo.getIdTipoPrestamo();
                    prestamoListNewPrestamo.setIdTipoPrestamo(tipoprestamo);
                    prestamoListNewPrestamo = em.merge(prestamoListNewPrestamo);
                    if (oldIdTipoPrestamoOfPrestamoListNewPrestamo != null && !oldIdTipoPrestamoOfPrestamoListNewPrestamo.equals(tipoprestamo)) {
                        oldIdTipoPrestamoOfPrestamoListNewPrestamo.getPrestamoList().remove(prestamoListNewPrestamo);
                        oldIdTipoPrestamoOfPrestamoListNewPrestamo = em.merge(oldIdTipoPrestamoOfPrestamoListNewPrestamo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoprestamo.getIdTipoPrestamo();
                if (findTipoprestamo(id) == null) {
                    throw new NonexistentEntityException("The tipoprestamo with id " + id + " no longer exists.");
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
            Tipoprestamo tipoprestamo;
            try {
                tipoprestamo = em.getReference(Tipoprestamo.class, id);
                tipoprestamo.getIdTipoPrestamo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoprestamo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Prestamo> prestamoListOrphanCheck = tipoprestamo.getPrestamoList();
            for (Prestamo prestamoListOrphanCheckPrestamo : prestamoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipoprestamo (" + tipoprestamo + ") cannot be destroyed since the Prestamo " + prestamoListOrphanCheckPrestamo + " in its prestamoList field has a non-nullable idTipoPrestamo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoprestamo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipoprestamo> findTipoprestamoEntities() {
        return findTipoprestamoEntities(true, -1, -1);
    }

    public List<Tipoprestamo> findTipoprestamoEntities(int maxResults, int firstResult) {
        return findTipoprestamoEntities(false, maxResults, firstResult);
    }

    private List<Tipoprestamo> findTipoprestamoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipoprestamo.class));
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

    public Tipoprestamo findTipoprestamo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipoprestamo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoprestamoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipoprestamo> rt = cq.from(Tipoprestamo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
