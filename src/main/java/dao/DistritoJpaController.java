package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.Datospersonales;
import dto.Distrito;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ing. Andres Gomez
 */
public class DistritoJpaController implements Serializable {

    public DistritoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Distrito distrito) {
        if (distrito.getDatospersonalesList() == null) {
            distrito.setDatospersonalesList(new ArrayList<Datospersonales>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Datospersonales> attachedDatospersonalesList = new ArrayList<Datospersonales>();
            for (Datospersonales datospersonalesListDatospersonalesToAttach : distrito.getDatospersonalesList()) {
                datospersonalesListDatospersonalesToAttach = em.getReference(datospersonalesListDatospersonalesToAttach.getClass(), datospersonalesListDatospersonalesToAttach.getIdPersona());
                attachedDatospersonalesList.add(datospersonalesListDatospersonalesToAttach);
            }
            distrito.setDatospersonalesList(attachedDatospersonalesList);
            em.persist(distrito);
            for (Datospersonales datospersonalesListDatospersonales : distrito.getDatospersonalesList()) {
                Distrito oldIdDistritoOfDatospersonalesListDatospersonales = datospersonalesListDatospersonales.getIdDistrito();
                datospersonalesListDatospersonales.setIdDistrito(distrito);
                datospersonalesListDatospersonales = em.merge(datospersonalesListDatospersonales);
                if (oldIdDistritoOfDatospersonalesListDatospersonales != null) {
                    oldIdDistritoOfDatospersonalesListDatospersonales.getDatospersonalesList().remove(datospersonalesListDatospersonales);
                    oldIdDistritoOfDatospersonalesListDatospersonales = em.merge(oldIdDistritoOfDatospersonalesListDatospersonales);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Distrito distrito) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Distrito persistentDistrito = em.find(Distrito.class, distrito.getIdDistrito());
            List<Datospersonales> datospersonalesListOld = persistentDistrito.getDatospersonalesList();
            List<Datospersonales> datospersonalesListNew = distrito.getDatospersonalesList();
            List<String> illegalOrphanMessages = null;
            for (Datospersonales datospersonalesListOldDatospersonales : datospersonalesListOld) {
                if (!datospersonalesListNew.contains(datospersonalesListOldDatospersonales)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Datospersonales " + datospersonalesListOldDatospersonales + " since its idDistrito field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Datospersonales> attachedDatospersonalesListNew = new ArrayList<Datospersonales>();
            for (Datospersonales datospersonalesListNewDatospersonalesToAttach : datospersonalesListNew) {
                datospersonalesListNewDatospersonalesToAttach = em.getReference(datospersonalesListNewDatospersonalesToAttach.getClass(), datospersonalesListNewDatospersonalesToAttach.getIdPersona());
                attachedDatospersonalesListNew.add(datospersonalesListNewDatospersonalesToAttach);
            }
            datospersonalesListNew = attachedDatospersonalesListNew;
            distrito.setDatospersonalesList(datospersonalesListNew);
            distrito = em.merge(distrito);
            for (Datospersonales datospersonalesListNewDatospersonales : datospersonalesListNew) {
                if (!datospersonalesListOld.contains(datospersonalesListNewDatospersonales)) {
                    Distrito oldIdDistritoOfDatospersonalesListNewDatospersonales = datospersonalesListNewDatospersonales.getIdDistrito();
                    datospersonalesListNewDatospersonales.setIdDistrito(distrito);
                    datospersonalesListNewDatospersonales = em.merge(datospersonalesListNewDatospersonales);
                    if (oldIdDistritoOfDatospersonalesListNewDatospersonales != null && !oldIdDistritoOfDatospersonalesListNewDatospersonales.equals(distrito)) {
                        oldIdDistritoOfDatospersonalesListNewDatospersonales.getDatospersonalesList().remove(datospersonalesListNewDatospersonales);
                        oldIdDistritoOfDatospersonalesListNewDatospersonales = em.merge(oldIdDistritoOfDatospersonalesListNewDatospersonales);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = distrito.getIdDistrito();
                if (findDistrito(id) == null) {
                    throw new NonexistentEntityException("The distrito with id " + id + " no longer exists.");
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
            Distrito distrito;
            try {
                distrito = em.getReference(Distrito.class, id);
                distrito.getIdDistrito();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The distrito with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Datospersonales> datospersonalesListOrphanCheck = distrito.getDatospersonalesList();
            for (Datospersonales datospersonalesListOrphanCheckDatospersonales : datospersonalesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Distrito (" + distrito + ") cannot be destroyed since the Datospersonales " + datospersonalesListOrphanCheckDatospersonales + " in its datospersonalesList field has a non-nullable idDistrito field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(distrito);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Distrito> findDistritoEntities() {
        return findDistritoEntities(true, -1, -1);
    }

    public List<Distrito> findDistritoEntities(int maxResults, int firstResult) {
        return findDistritoEntities(false, maxResults, firstResult);
    }

    private List<Distrito> findDistritoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Distrito.class));
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

    public Distrito findDistrito(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Distrito.class, id);
        } finally {
            em.close();
        }
    }

    public int getDistritoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Distrito> rt = cq.from(Distrito.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
