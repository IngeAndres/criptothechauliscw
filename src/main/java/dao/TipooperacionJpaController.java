package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.Operacionesotrascuentas;
import java.util.ArrayList;
import java.util.List;
import dto.Operacionescuentaspropias;
import dto.Tipooperacion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Ing. Andres Gomez
 */
public class TipooperacionJpaController implements Serializable {

    public TipooperacionJpaController() {
    }

    public TipooperacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_CriptoTheChaulisCW_war_1.0-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipooperacion tipooperacion) {
        if (tipooperacion.getOperacionesotrascuentasList() == null) {
            tipooperacion.setOperacionesotrascuentasList(new ArrayList<Operacionesotrascuentas>());
        }
        if (tipooperacion.getOperacionescuentaspropiasList() == null) {
            tipooperacion.setOperacionescuentaspropiasList(new ArrayList<Operacionescuentaspropias>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Operacionesotrascuentas> attachedOperacionesotrascuentasList = new ArrayList<Operacionesotrascuentas>();
            for (Operacionesotrascuentas operacionesotrascuentasListOperacionesotrascuentasToAttach : tipooperacion.getOperacionesotrascuentasList()) {
                operacionesotrascuentasListOperacionesotrascuentasToAttach = em.getReference(operacionesotrascuentasListOperacionesotrascuentasToAttach.getClass(), operacionesotrascuentasListOperacionesotrascuentasToAttach.getIdOperacionOC());
                attachedOperacionesotrascuentasList.add(operacionesotrascuentasListOperacionesotrascuentasToAttach);
            }
            tipooperacion.setOperacionesotrascuentasList(attachedOperacionesotrascuentasList);
            List<Operacionescuentaspropias> attachedOperacionescuentaspropiasList = new ArrayList<Operacionescuentaspropias>();
            for (Operacionescuentaspropias operacionescuentaspropiasListOperacionescuentaspropiasToAttach : tipooperacion.getOperacionescuentaspropiasList()) {
                operacionescuentaspropiasListOperacionescuentaspropiasToAttach = em.getReference(operacionescuentaspropiasListOperacionescuentaspropiasToAttach.getClass(), operacionescuentaspropiasListOperacionescuentaspropiasToAttach.getIdOperacionCP());
                attachedOperacionescuentaspropiasList.add(operacionescuentaspropiasListOperacionescuentaspropiasToAttach);
            }
            tipooperacion.setOperacionescuentaspropiasList(attachedOperacionescuentaspropiasList);
            em.persist(tipooperacion);
            for (Operacionesotrascuentas operacionesotrascuentasListOperacionesotrascuentas : tipooperacion.getOperacionesotrascuentasList()) {
                Tipooperacion oldIdTipoOperacionOfOperacionesotrascuentasListOperacionesotrascuentas = operacionesotrascuentasListOperacionesotrascuentas.getIdTipoOperacion();
                operacionesotrascuentasListOperacionesotrascuentas.setIdTipoOperacion(tipooperacion);
                operacionesotrascuentasListOperacionesotrascuentas = em.merge(operacionesotrascuentasListOperacionesotrascuentas);
                if (oldIdTipoOperacionOfOperacionesotrascuentasListOperacionesotrascuentas != null) {
                    oldIdTipoOperacionOfOperacionesotrascuentasListOperacionesotrascuentas.getOperacionesotrascuentasList().remove(operacionesotrascuentasListOperacionesotrascuentas);
                    oldIdTipoOperacionOfOperacionesotrascuentasListOperacionesotrascuentas = em.merge(oldIdTipoOperacionOfOperacionesotrascuentasListOperacionesotrascuentas);
                }
            }
            for (Operacionescuentaspropias operacionescuentaspropiasListOperacionescuentaspropias : tipooperacion.getOperacionescuentaspropiasList()) {
                Tipooperacion oldIdTipoOperacionOfOperacionescuentaspropiasListOperacionescuentaspropias = operacionescuentaspropiasListOperacionescuentaspropias.getIdTipoOperacion();
                operacionescuentaspropiasListOperacionescuentaspropias.setIdTipoOperacion(tipooperacion);
                operacionescuentaspropiasListOperacionescuentaspropias = em.merge(operacionescuentaspropiasListOperacionescuentaspropias);
                if (oldIdTipoOperacionOfOperacionescuentaspropiasListOperacionescuentaspropias != null) {
                    oldIdTipoOperacionOfOperacionescuentaspropiasListOperacionescuentaspropias.getOperacionescuentaspropiasList().remove(operacionescuentaspropiasListOperacionescuentaspropias);
                    oldIdTipoOperacionOfOperacionescuentaspropiasListOperacionescuentaspropias = em.merge(oldIdTipoOperacionOfOperacionescuentaspropiasListOperacionescuentaspropias);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipooperacion tipooperacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipooperacion persistentTipooperacion = em.find(Tipooperacion.class, tipooperacion.getIdTipoOperacion());
            List<Operacionesotrascuentas> operacionesotrascuentasListOld = persistentTipooperacion.getOperacionesotrascuentasList();
            List<Operacionesotrascuentas> operacionesotrascuentasListNew = tipooperacion.getOperacionesotrascuentasList();
            List<Operacionescuentaspropias> operacionescuentaspropiasListOld = persistentTipooperacion.getOperacionescuentaspropiasList();
            List<Operacionescuentaspropias> operacionescuentaspropiasListNew = tipooperacion.getOperacionescuentaspropiasList();
            List<String> illegalOrphanMessages = null;
            for (Operacionesotrascuentas operacionesotrascuentasListOldOperacionesotrascuentas : operacionesotrascuentasListOld) {
                if (!operacionesotrascuentasListNew.contains(operacionesotrascuentasListOldOperacionesotrascuentas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Operacionesotrascuentas " + operacionesotrascuentasListOldOperacionesotrascuentas + " since its idTipoOperacion field is not nullable.");
                }
            }
            for (Operacionescuentaspropias operacionescuentaspropiasListOldOperacionescuentaspropias : operacionescuentaspropiasListOld) {
                if (!operacionescuentaspropiasListNew.contains(operacionescuentaspropiasListOldOperacionescuentaspropias)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Operacionescuentaspropias " + operacionescuentaspropiasListOldOperacionescuentaspropias + " since its idTipoOperacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Operacionesotrascuentas> attachedOperacionesotrascuentasListNew = new ArrayList<Operacionesotrascuentas>();
            for (Operacionesotrascuentas operacionesotrascuentasListNewOperacionesotrascuentasToAttach : operacionesotrascuentasListNew) {
                operacionesotrascuentasListNewOperacionesotrascuentasToAttach = em.getReference(operacionesotrascuentasListNewOperacionesotrascuentasToAttach.getClass(), operacionesotrascuentasListNewOperacionesotrascuentasToAttach.getIdOperacionOC());
                attachedOperacionesotrascuentasListNew.add(operacionesotrascuentasListNewOperacionesotrascuentasToAttach);
            }
            operacionesotrascuentasListNew = attachedOperacionesotrascuentasListNew;
            tipooperacion.setOperacionesotrascuentasList(operacionesotrascuentasListNew);
            List<Operacionescuentaspropias> attachedOperacionescuentaspropiasListNew = new ArrayList<Operacionescuentaspropias>();
            for (Operacionescuentaspropias operacionescuentaspropiasListNewOperacionescuentaspropiasToAttach : operacionescuentaspropiasListNew) {
                operacionescuentaspropiasListNewOperacionescuentaspropiasToAttach = em.getReference(operacionescuentaspropiasListNewOperacionescuentaspropiasToAttach.getClass(), operacionescuentaspropiasListNewOperacionescuentaspropiasToAttach.getIdOperacionCP());
                attachedOperacionescuentaspropiasListNew.add(operacionescuentaspropiasListNewOperacionescuentaspropiasToAttach);
            }
            operacionescuentaspropiasListNew = attachedOperacionescuentaspropiasListNew;
            tipooperacion.setOperacionescuentaspropiasList(operacionescuentaspropiasListNew);
            tipooperacion = em.merge(tipooperacion);
            for (Operacionesotrascuentas operacionesotrascuentasListNewOperacionesotrascuentas : operacionesotrascuentasListNew) {
                if (!operacionesotrascuentasListOld.contains(operacionesotrascuentasListNewOperacionesotrascuentas)) {
                    Tipooperacion oldIdTipoOperacionOfOperacionesotrascuentasListNewOperacionesotrascuentas = operacionesotrascuentasListNewOperacionesotrascuentas.getIdTipoOperacion();
                    operacionesotrascuentasListNewOperacionesotrascuentas.setIdTipoOperacion(tipooperacion);
                    operacionesotrascuentasListNewOperacionesotrascuentas = em.merge(operacionesotrascuentasListNewOperacionesotrascuentas);
                    if (oldIdTipoOperacionOfOperacionesotrascuentasListNewOperacionesotrascuentas != null && !oldIdTipoOperacionOfOperacionesotrascuentasListNewOperacionesotrascuentas.equals(tipooperacion)) {
                        oldIdTipoOperacionOfOperacionesotrascuentasListNewOperacionesotrascuentas.getOperacionesotrascuentasList().remove(operacionesotrascuentasListNewOperacionesotrascuentas);
                        oldIdTipoOperacionOfOperacionesotrascuentasListNewOperacionesotrascuentas = em.merge(oldIdTipoOperacionOfOperacionesotrascuentasListNewOperacionesotrascuentas);
                    }
                }
            }
            for (Operacionescuentaspropias operacionescuentaspropiasListNewOperacionescuentaspropias : operacionescuentaspropiasListNew) {
                if (!operacionescuentaspropiasListOld.contains(operacionescuentaspropiasListNewOperacionescuentaspropias)) {
                    Tipooperacion oldIdTipoOperacionOfOperacionescuentaspropiasListNewOperacionescuentaspropias = operacionescuentaspropiasListNewOperacionescuentaspropias.getIdTipoOperacion();
                    operacionescuentaspropiasListNewOperacionescuentaspropias.setIdTipoOperacion(tipooperacion);
                    operacionescuentaspropiasListNewOperacionescuentaspropias = em.merge(operacionescuentaspropiasListNewOperacionescuentaspropias);
                    if (oldIdTipoOperacionOfOperacionescuentaspropiasListNewOperacionescuentaspropias != null && !oldIdTipoOperacionOfOperacionescuentaspropiasListNewOperacionescuentaspropias.equals(tipooperacion)) {
                        oldIdTipoOperacionOfOperacionescuentaspropiasListNewOperacionescuentaspropias.getOperacionescuentaspropiasList().remove(operacionescuentaspropiasListNewOperacionescuentaspropias);
                        oldIdTipoOperacionOfOperacionescuentaspropiasListNewOperacionescuentaspropias = em.merge(oldIdTipoOperacionOfOperacionescuentaspropiasListNewOperacionescuentaspropias);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipooperacion.getIdTipoOperacion();
                if (findTipooperacion(id) == null) {
                    throw new NonexistentEntityException("The tipooperacion with id " + id + " no longer exists.");
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
            Tipooperacion tipooperacion;
            try {
                tipooperacion = em.getReference(Tipooperacion.class, id);
                tipooperacion.getIdTipoOperacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipooperacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Operacionesotrascuentas> operacionesotrascuentasListOrphanCheck = tipooperacion.getOperacionesotrascuentasList();
            for (Operacionesotrascuentas operacionesotrascuentasListOrphanCheckOperacionesotrascuentas : operacionesotrascuentasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipooperacion (" + tipooperacion + ") cannot be destroyed since the Operacionesotrascuentas " + operacionesotrascuentasListOrphanCheckOperacionesotrascuentas + " in its operacionesotrascuentasList field has a non-nullable idTipoOperacion field.");
            }
            List<Operacionescuentaspropias> operacionescuentaspropiasListOrphanCheck = tipooperacion.getOperacionescuentaspropiasList();
            for (Operacionescuentaspropias operacionescuentaspropiasListOrphanCheckOperacionescuentaspropias : operacionescuentaspropiasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipooperacion (" + tipooperacion + ") cannot be destroyed since the Operacionescuentaspropias " + operacionescuentaspropiasListOrphanCheckOperacionescuentaspropias + " in its operacionescuentaspropiasList field has a non-nullable idTipoOperacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipooperacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipooperacion> findTipooperacionEntities() {
        return findTipooperacionEntities(true, -1, -1);
    }

    public List<Tipooperacion> findTipooperacionEntities(int maxResults, int firstResult) {
        return findTipooperacionEntities(false, maxResults, firstResult);
    }

    private List<Tipooperacion> findTipooperacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipooperacion.class));
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

    public Tipooperacion findTipooperacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipooperacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipooperacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipooperacion> rt = cq.from(Tipooperacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
