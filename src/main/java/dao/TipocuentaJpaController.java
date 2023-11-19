package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.Cuenta;
import dto.Tipocuenta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Ing. Andres Gomez
 */
public class TipocuentaJpaController implements Serializable {

    public TipocuentaJpaController() {
    }

    public TipocuentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_CriptoTheChaulisCW_war_1.0-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipocuenta tipocuenta) {
        if (tipocuenta.getCuentaList() == null) {
            tipocuenta.setCuentaList(new ArrayList<Cuenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Cuenta> attachedCuentaList = new ArrayList<Cuenta>();
            for (Cuenta cuentaListCuentaToAttach : tipocuenta.getCuentaList()) {
                cuentaListCuentaToAttach = em.getReference(cuentaListCuentaToAttach.getClass(), cuentaListCuentaToAttach.getIdCuenta());
                attachedCuentaList.add(cuentaListCuentaToAttach);
            }
            tipocuenta.setCuentaList(attachedCuentaList);
            em.persist(tipocuenta);
            for (Cuenta cuentaListCuenta : tipocuenta.getCuentaList()) {
                Tipocuenta oldIdTipoCuentaOfCuentaListCuenta = cuentaListCuenta.getIdTipoCuenta();
                cuentaListCuenta.setIdTipoCuenta(tipocuenta);
                cuentaListCuenta = em.merge(cuentaListCuenta);
                if (oldIdTipoCuentaOfCuentaListCuenta != null) {
                    oldIdTipoCuentaOfCuentaListCuenta.getCuentaList().remove(cuentaListCuenta);
                    oldIdTipoCuentaOfCuentaListCuenta = em.merge(oldIdTipoCuentaOfCuentaListCuenta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipocuenta tipocuenta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipocuenta persistentTipocuenta = em.find(Tipocuenta.class, tipocuenta.getIdTipoCuenta());
            List<Cuenta> cuentaListOld = persistentTipocuenta.getCuentaList();
            List<Cuenta> cuentaListNew = tipocuenta.getCuentaList();
            List<String> illegalOrphanMessages = null;
            for (Cuenta cuentaListOldCuenta : cuentaListOld) {
                if (!cuentaListNew.contains(cuentaListOldCuenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cuenta " + cuentaListOldCuenta + " since its idTipoCuenta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Cuenta> attachedCuentaListNew = new ArrayList<Cuenta>();
            for (Cuenta cuentaListNewCuentaToAttach : cuentaListNew) {
                cuentaListNewCuentaToAttach = em.getReference(cuentaListNewCuentaToAttach.getClass(), cuentaListNewCuentaToAttach.getIdCuenta());
                attachedCuentaListNew.add(cuentaListNewCuentaToAttach);
            }
            cuentaListNew = attachedCuentaListNew;
            tipocuenta.setCuentaList(cuentaListNew);
            tipocuenta = em.merge(tipocuenta);
            for (Cuenta cuentaListNewCuenta : cuentaListNew) {
                if (!cuentaListOld.contains(cuentaListNewCuenta)) {
                    Tipocuenta oldIdTipoCuentaOfCuentaListNewCuenta = cuentaListNewCuenta.getIdTipoCuenta();
                    cuentaListNewCuenta.setIdTipoCuenta(tipocuenta);
                    cuentaListNewCuenta = em.merge(cuentaListNewCuenta);
                    if (oldIdTipoCuentaOfCuentaListNewCuenta != null && !oldIdTipoCuentaOfCuentaListNewCuenta.equals(tipocuenta)) {
                        oldIdTipoCuentaOfCuentaListNewCuenta.getCuentaList().remove(cuentaListNewCuenta);
                        oldIdTipoCuentaOfCuentaListNewCuenta = em.merge(oldIdTipoCuentaOfCuentaListNewCuenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipocuenta.getIdTipoCuenta();
                if (findTipocuenta(id) == null) {
                    throw new NonexistentEntityException("The tipocuenta with id " + id + " no longer exists.");
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
            Tipocuenta tipocuenta;
            try {
                tipocuenta = em.getReference(Tipocuenta.class, id);
                tipocuenta.getIdTipoCuenta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipocuenta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cuenta> cuentaListOrphanCheck = tipocuenta.getCuentaList();
            for (Cuenta cuentaListOrphanCheckCuenta : cuentaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipocuenta (" + tipocuenta + ") cannot be destroyed since the Cuenta " + cuentaListOrphanCheckCuenta + " in its cuentaList field has a non-nullable idTipoCuenta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipocuenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipocuenta> findTipocuentaEntities() {
        return findTipocuentaEntities(true, -1, -1);
    }

    public List<Tipocuenta> findTipocuentaEntities(int maxResults, int firstResult) {
        return findTipocuentaEntities(false, maxResults, firstResult);
    }

    private List<Tipocuenta> findTipocuentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipocuenta.class));
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

    public Tipocuenta findTipocuenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipocuenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipocuentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipocuenta> rt = cq.from(Tipocuenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
