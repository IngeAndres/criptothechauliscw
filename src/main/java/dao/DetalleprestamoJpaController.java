package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dto.Detalleprestamo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.Tipoinformacionbien;
import dto.Prestamo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ing. Andres Gomez
 */
public class DetalleprestamoJpaController implements Serializable {

    public DetalleprestamoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Detalleprestamo detalleprestamo) {
        if (detalleprestamo.getPrestamoList() == null) {
            detalleprestamo.setPrestamoList(new ArrayList<Prestamo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipoinformacionbien idTipoInformacionBien = detalleprestamo.getIdTipoInformacionBien();
            if (idTipoInformacionBien != null) {
                idTipoInformacionBien = em.getReference(idTipoInformacionBien.getClass(), idTipoInformacionBien.getIdTipoInformacionBien());
                detalleprestamo.setIdTipoInformacionBien(idTipoInformacionBien);
            }
            List<Prestamo> attachedPrestamoList = new ArrayList<Prestamo>();
            for (Prestamo prestamoListPrestamoToAttach : detalleprestamo.getPrestamoList()) {
                prestamoListPrestamoToAttach = em.getReference(prestamoListPrestamoToAttach.getClass(), prestamoListPrestamoToAttach.getIdPrestamo());
                attachedPrestamoList.add(prestamoListPrestamoToAttach);
            }
            detalleprestamo.setPrestamoList(attachedPrestamoList);
            em.persist(detalleprestamo);
            if (idTipoInformacionBien != null) {
                idTipoInformacionBien.getDetalleprestamoList().add(detalleprestamo);
                idTipoInformacionBien = em.merge(idTipoInformacionBien);
            }
            for (Prestamo prestamoListPrestamo : detalleprestamo.getPrestamoList()) {
                Detalleprestamo oldIdDetallePrestamoOfPrestamoListPrestamo = prestamoListPrestamo.getIdDetallePrestamo();
                prestamoListPrestamo.setIdDetallePrestamo(detalleprestamo);
                prestamoListPrestamo = em.merge(prestamoListPrestamo);
                if (oldIdDetallePrestamoOfPrestamoListPrestamo != null) {
                    oldIdDetallePrestamoOfPrestamoListPrestamo.getPrestamoList().remove(prestamoListPrestamo);
                    oldIdDetallePrestamoOfPrestamoListPrestamo = em.merge(oldIdDetallePrestamoOfPrestamoListPrestamo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Detalleprestamo detalleprestamo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Detalleprestamo persistentDetalleprestamo = em.find(Detalleprestamo.class, detalleprestamo.getIdDetallePrestamo());
            Tipoinformacionbien idTipoInformacionBienOld = persistentDetalleprestamo.getIdTipoInformacionBien();
            Tipoinformacionbien idTipoInformacionBienNew = detalleprestamo.getIdTipoInformacionBien();
            List<Prestamo> prestamoListOld = persistentDetalleprestamo.getPrestamoList();
            List<Prestamo> prestamoListNew = detalleprestamo.getPrestamoList();
            List<String> illegalOrphanMessages = null;
            for (Prestamo prestamoListOldPrestamo : prestamoListOld) {
                if (!prestamoListNew.contains(prestamoListOldPrestamo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Prestamo " + prestamoListOldPrestamo + " since its idDetallePrestamo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idTipoInformacionBienNew != null) {
                idTipoInformacionBienNew = em.getReference(idTipoInformacionBienNew.getClass(), idTipoInformacionBienNew.getIdTipoInformacionBien());
                detalleprestamo.setIdTipoInformacionBien(idTipoInformacionBienNew);
            }
            List<Prestamo> attachedPrestamoListNew = new ArrayList<Prestamo>();
            for (Prestamo prestamoListNewPrestamoToAttach : prestamoListNew) {
                prestamoListNewPrestamoToAttach = em.getReference(prestamoListNewPrestamoToAttach.getClass(), prestamoListNewPrestamoToAttach.getIdPrestamo());
                attachedPrestamoListNew.add(prestamoListNewPrestamoToAttach);
            }
            prestamoListNew = attachedPrestamoListNew;
            detalleprestamo.setPrestamoList(prestamoListNew);
            detalleprestamo = em.merge(detalleprestamo);
            if (idTipoInformacionBienOld != null && !idTipoInformacionBienOld.equals(idTipoInformacionBienNew)) {
                idTipoInformacionBienOld.getDetalleprestamoList().remove(detalleprestamo);
                idTipoInformacionBienOld = em.merge(idTipoInformacionBienOld);
            }
            if (idTipoInformacionBienNew != null && !idTipoInformacionBienNew.equals(idTipoInformacionBienOld)) {
                idTipoInformacionBienNew.getDetalleprestamoList().add(detalleprestamo);
                idTipoInformacionBienNew = em.merge(idTipoInformacionBienNew);
            }
            for (Prestamo prestamoListNewPrestamo : prestamoListNew) {
                if (!prestamoListOld.contains(prestamoListNewPrestamo)) {
                    Detalleprestamo oldIdDetallePrestamoOfPrestamoListNewPrestamo = prestamoListNewPrestamo.getIdDetallePrestamo();
                    prestamoListNewPrestamo.setIdDetallePrestamo(detalleprestamo);
                    prestamoListNewPrestamo = em.merge(prestamoListNewPrestamo);
                    if (oldIdDetallePrestamoOfPrestamoListNewPrestamo != null && !oldIdDetallePrestamoOfPrestamoListNewPrestamo.equals(detalleprestamo)) {
                        oldIdDetallePrestamoOfPrestamoListNewPrestamo.getPrestamoList().remove(prestamoListNewPrestamo);
                        oldIdDetallePrestamoOfPrestamoListNewPrestamo = em.merge(oldIdDetallePrestamoOfPrestamoListNewPrestamo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleprestamo.getIdDetallePrestamo();
                if (findDetalleprestamo(id) == null) {
                    throw new NonexistentEntityException("The detalleprestamo with id " + id + " no longer exists.");
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
            Detalleprestamo detalleprestamo;
            try {
                detalleprestamo = em.getReference(Detalleprestamo.class, id);
                detalleprestamo.getIdDetallePrestamo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleprestamo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Prestamo> prestamoListOrphanCheck = detalleprestamo.getPrestamoList();
            for (Prestamo prestamoListOrphanCheckPrestamo : prestamoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Detalleprestamo (" + detalleprestamo + ") cannot be destroyed since the Prestamo " + prestamoListOrphanCheckPrestamo + " in its prestamoList field has a non-nullable idDetallePrestamo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Tipoinformacionbien idTipoInformacionBien = detalleprestamo.getIdTipoInformacionBien();
            if (idTipoInformacionBien != null) {
                idTipoInformacionBien.getDetalleprestamoList().remove(detalleprestamo);
                idTipoInformacionBien = em.merge(idTipoInformacionBien);
            }
            em.remove(detalleprestamo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Detalleprestamo> findDetalleprestamoEntities() {
        return findDetalleprestamoEntities(true, -1, -1);
    }

    public List<Detalleprestamo> findDetalleprestamoEntities(int maxResults, int firstResult) {
        return findDetalleprestamoEntities(false, maxResults, firstResult);
    }

    private List<Detalleprestamo> findDetalleprestamoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Detalleprestamo.class));
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

    public Detalleprestamo findDetalleprestamo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Detalleprestamo.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleprestamoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Detalleprestamo> rt = cq.from(Detalleprestamo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
