package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.Detalleprestamo;
import dto.Tipoinformacionbien;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Ing. Andres Gomez
 */
public class TipoinformacionbienJpaController implements Serializable {

    public TipoinformacionbienJpaController() {
    }

    public TipoinformacionbienJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_CriptoTheChaulisCW_war_1.0-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipoinformacionbien tipoinformacionbien) {
        if (tipoinformacionbien.getDetalleprestamoList() == null) {
            tipoinformacionbien.setDetalleprestamoList(new ArrayList<Detalleprestamo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Detalleprestamo> attachedDetalleprestamoList = new ArrayList<Detalleprestamo>();
            for (Detalleprestamo detalleprestamoListDetalleprestamoToAttach : tipoinformacionbien.getDetalleprestamoList()) {
                detalleprestamoListDetalleprestamoToAttach = em.getReference(detalleprestamoListDetalleprestamoToAttach.getClass(), detalleprestamoListDetalleprestamoToAttach.getIdDetallePrestamo());
                attachedDetalleprestamoList.add(detalleprestamoListDetalleprestamoToAttach);
            }
            tipoinformacionbien.setDetalleprestamoList(attachedDetalleprestamoList);
            em.persist(tipoinformacionbien);
            for (Detalleprestamo detalleprestamoListDetalleprestamo : tipoinformacionbien.getDetalleprestamoList()) {
                Tipoinformacionbien oldIdTipoInformacionBienOfDetalleprestamoListDetalleprestamo = detalleprestamoListDetalleprestamo.getIdTipoInformacionBien();
                detalleprestamoListDetalleprestamo.setIdTipoInformacionBien(tipoinformacionbien);
                detalleprestamoListDetalleprestamo = em.merge(detalleprestamoListDetalleprestamo);
                if (oldIdTipoInformacionBienOfDetalleprestamoListDetalleprestamo != null) {
                    oldIdTipoInformacionBienOfDetalleprestamoListDetalleprestamo.getDetalleprestamoList().remove(detalleprestamoListDetalleprestamo);
                    oldIdTipoInformacionBienOfDetalleprestamoListDetalleprestamo = em.merge(oldIdTipoInformacionBienOfDetalleprestamoListDetalleprestamo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipoinformacionbien tipoinformacionbien) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipoinformacionbien persistentTipoinformacionbien = em.find(Tipoinformacionbien.class, tipoinformacionbien.getIdTipoInformacionBien());
            List<Detalleprestamo> detalleprestamoListOld = persistentTipoinformacionbien.getDetalleprestamoList();
            List<Detalleprestamo> detalleprestamoListNew = tipoinformacionbien.getDetalleprestamoList();
            List<String> illegalOrphanMessages = null;
            for (Detalleprestamo detalleprestamoListOldDetalleprestamo : detalleprestamoListOld) {
                if (!detalleprestamoListNew.contains(detalleprestamoListOldDetalleprestamo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Detalleprestamo " + detalleprestamoListOldDetalleprestamo + " since its idTipoInformacionBien field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Detalleprestamo> attachedDetalleprestamoListNew = new ArrayList<Detalleprestamo>();
            for (Detalleprestamo detalleprestamoListNewDetalleprestamoToAttach : detalleprestamoListNew) {
                detalleprestamoListNewDetalleprestamoToAttach = em.getReference(detalleprestamoListNewDetalleprestamoToAttach.getClass(), detalleprestamoListNewDetalleprestamoToAttach.getIdDetallePrestamo());
                attachedDetalleprestamoListNew.add(detalleprestamoListNewDetalleprestamoToAttach);
            }
            detalleprestamoListNew = attachedDetalleprestamoListNew;
            tipoinformacionbien.setDetalleprestamoList(detalleprestamoListNew);
            tipoinformacionbien = em.merge(tipoinformacionbien);
            for (Detalleprestamo detalleprestamoListNewDetalleprestamo : detalleprestamoListNew) {
                if (!detalleprestamoListOld.contains(detalleprestamoListNewDetalleprestamo)) {
                    Tipoinformacionbien oldIdTipoInformacionBienOfDetalleprestamoListNewDetalleprestamo = detalleprestamoListNewDetalleprestamo.getIdTipoInformacionBien();
                    detalleprestamoListNewDetalleprestamo.setIdTipoInformacionBien(tipoinformacionbien);
                    detalleprestamoListNewDetalleprestamo = em.merge(detalleprestamoListNewDetalleprestamo);
                    if (oldIdTipoInformacionBienOfDetalleprestamoListNewDetalleprestamo != null && !oldIdTipoInformacionBienOfDetalleprestamoListNewDetalleprestamo.equals(tipoinformacionbien)) {
                        oldIdTipoInformacionBienOfDetalleprestamoListNewDetalleprestamo.getDetalleprestamoList().remove(detalleprestamoListNewDetalleprestamo);
                        oldIdTipoInformacionBienOfDetalleprestamoListNewDetalleprestamo = em.merge(oldIdTipoInformacionBienOfDetalleprestamoListNewDetalleprestamo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoinformacionbien.getIdTipoInformacionBien();
                if (findTipoinformacionbien(id) == null) {
                    throw new NonexistentEntityException("The tipoinformacionbien with id " + id + " no longer exists.");
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
            Tipoinformacionbien tipoinformacionbien;
            try {
                tipoinformacionbien = em.getReference(Tipoinformacionbien.class, id);
                tipoinformacionbien.getIdTipoInformacionBien();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoinformacionbien with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Detalleprestamo> detalleprestamoListOrphanCheck = tipoinformacionbien.getDetalleprestamoList();
            for (Detalleprestamo detalleprestamoListOrphanCheckDetalleprestamo : detalleprestamoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipoinformacionbien (" + tipoinformacionbien + ") cannot be destroyed since the Detalleprestamo " + detalleprestamoListOrphanCheckDetalleprestamo + " in its detalleprestamoList field has a non-nullable idTipoInformacionBien field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoinformacionbien);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipoinformacionbien> findTipoinformacionbienEntities() {
        return findTipoinformacionbienEntities(true, -1, -1);
    }

    public List<Tipoinformacionbien> findTipoinformacionbienEntities(int maxResults, int firstResult) {
        return findTipoinformacionbienEntities(false, maxResults, firstResult);
    }

    private List<Tipoinformacionbien> findTipoinformacionbienEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipoinformacionbien.class));
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

    public Tipoinformacionbien findTipoinformacionbien(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipoinformacionbien.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoinformacionbienCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipoinformacionbien> rt = cq.from(Tipoinformacionbien.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
