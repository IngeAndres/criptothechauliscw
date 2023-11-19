package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.Datospersonales;
import dto.Tipodocumento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ing. Andres Gomez
 */
public class TipodocumentoJpaController implements Serializable {

    public TipodocumentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipodocumento tipodocumento) {
        if (tipodocumento.getDatospersonalesList() == null) {
            tipodocumento.setDatospersonalesList(new ArrayList<Datospersonales>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Datospersonales> attachedDatospersonalesList = new ArrayList<Datospersonales>();
            for (Datospersonales datospersonalesListDatospersonalesToAttach : tipodocumento.getDatospersonalesList()) {
                datospersonalesListDatospersonalesToAttach = em.getReference(datospersonalesListDatospersonalesToAttach.getClass(), datospersonalesListDatospersonalesToAttach.getIdPersona());
                attachedDatospersonalesList.add(datospersonalesListDatospersonalesToAttach);
            }
            tipodocumento.setDatospersonalesList(attachedDatospersonalesList);
            em.persist(tipodocumento);
            for (Datospersonales datospersonalesListDatospersonales : tipodocumento.getDatospersonalesList()) {
                Tipodocumento oldIdTipoDocumentoOfDatospersonalesListDatospersonales = datospersonalesListDatospersonales.getIdTipoDocumento();
                datospersonalesListDatospersonales.setIdTipoDocumento(tipodocumento);
                datospersonalesListDatospersonales = em.merge(datospersonalesListDatospersonales);
                if (oldIdTipoDocumentoOfDatospersonalesListDatospersonales != null) {
                    oldIdTipoDocumentoOfDatospersonalesListDatospersonales.getDatospersonalesList().remove(datospersonalesListDatospersonales);
                    oldIdTipoDocumentoOfDatospersonalesListDatospersonales = em.merge(oldIdTipoDocumentoOfDatospersonalesListDatospersonales);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipodocumento tipodocumento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipodocumento persistentTipodocumento = em.find(Tipodocumento.class, tipodocumento.getIdTipoDocumento());
            List<Datospersonales> datospersonalesListOld = persistentTipodocumento.getDatospersonalesList();
            List<Datospersonales> datospersonalesListNew = tipodocumento.getDatospersonalesList();
            List<String> illegalOrphanMessages = null;
            for (Datospersonales datospersonalesListOldDatospersonales : datospersonalesListOld) {
                if (!datospersonalesListNew.contains(datospersonalesListOldDatospersonales)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Datospersonales " + datospersonalesListOldDatospersonales + " since its idTipoDocumento field is not nullable.");
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
            tipodocumento.setDatospersonalesList(datospersonalesListNew);
            tipodocumento = em.merge(tipodocumento);
            for (Datospersonales datospersonalesListNewDatospersonales : datospersonalesListNew) {
                if (!datospersonalesListOld.contains(datospersonalesListNewDatospersonales)) {
                    Tipodocumento oldIdTipoDocumentoOfDatospersonalesListNewDatospersonales = datospersonalesListNewDatospersonales.getIdTipoDocumento();
                    datospersonalesListNewDatospersonales.setIdTipoDocumento(tipodocumento);
                    datospersonalesListNewDatospersonales = em.merge(datospersonalesListNewDatospersonales);
                    if (oldIdTipoDocumentoOfDatospersonalesListNewDatospersonales != null && !oldIdTipoDocumentoOfDatospersonalesListNewDatospersonales.equals(tipodocumento)) {
                        oldIdTipoDocumentoOfDatospersonalesListNewDatospersonales.getDatospersonalesList().remove(datospersonalesListNewDatospersonales);
                        oldIdTipoDocumentoOfDatospersonalesListNewDatospersonales = em.merge(oldIdTipoDocumentoOfDatospersonalesListNewDatospersonales);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipodocumento.getIdTipoDocumento();
                if (findTipodocumento(id) == null) {
                    throw new NonexistentEntityException("The tipodocumento with id " + id + " no longer exists.");
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
            Tipodocumento tipodocumento;
            try {
                tipodocumento = em.getReference(Tipodocumento.class, id);
                tipodocumento.getIdTipoDocumento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipodocumento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Datospersonales> datospersonalesListOrphanCheck = tipodocumento.getDatospersonalesList();
            for (Datospersonales datospersonalesListOrphanCheckDatospersonales : datospersonalesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipodocumento (" + tipodocumento + ") cannot be destroyed since the Datospersonales " + datospersonalesListOrphanCheckDatospersonales + " in its datospersonalesList field has a non-nullable idTipoDocumento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipodocumento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipodocumento> findTipodocumentoEntities() {
        return findTipodocumentoEntities(true, -1, -1);
    }

    public List<Tipodocumento> findTipodocumentoEntities(int maxResults, int firstResult) {
        return findTipodocumentoEntities(false, maxResults, firstResult);
    }

    private List<Tipodocumento> findTipodocumentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipodocumento.class));
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

    public Tipodocumento findTipodocumento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipodocumento.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipodocumentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipodocumento> rt = cq.from(Tipodocumento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
