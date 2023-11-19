package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dto.Tipousuario;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ing. Andres Gomez
 */
public class TipousuarioJpaController implements Serializable {

    public TipousuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipousuario tipousuario) {
        if (tipousuario.getUsuarioList() == null) {
            tipousuario.setUsuarioList(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : tipousuario.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getIdUsuario());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            tipousuario.setUsuarioList(attachedUsuarioList);
            em.persist(tipousuario);
            for (Usuario usuarioListUsuario : tipousuario.getUsuarioList()) {
                Tipousuario oldIdTipoUsuarioOfUsuarioListUsuario = usuarioListUsuario.getIdTipoUsuario();
                usuarioListUsuario.setIdTipoUsuario(tipousuario);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldIdTipoUsuarioOfUsuarioListUsuario != null) {
                    oldIdTipoUsuarioOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldIdTipoUsuarioOfUsuarioListUsuario = em.merge(oldIdTipoUsuarioOfUsuarioListUsuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipousuario tipousuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipousuario persistentTipousuario = em.find(Tipousuario.class, tipousuario.getIdTipoUsuario());
            List<Usuario> usuarioListOld = persistentTipousuario.getUsuarioList();
            List<Usuario> usuarioListNew = tipousuario.getUsuarioList();
            List<String> illegalOrphanMessages = null;
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioListOldUsuario + " since its idTipoUsuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getIdUsuario());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            tipousuario.setUsuarioList(usuarioListNew);
            tipousuario = em.merge(tipousuario);
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    Tipousuario oldIdTipoUsuarioOfUsuarioListNewUsuario = usuarioListNewUsuario.getIdTipoUsuario();
                    usuarioListNewUsuario.setIdTipoUsuario(tipousuario);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldIdTipoUsuarioOfUsuarioListNewUsuario != null && !oldIdTipoUsuarioOfUsuarioListNewUsuario.equals(tipousuario)) {
                        oldIdTipoUsuarioOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldIdTipoUsuarioOfUsuarioListNewUsuario = em.merge(oldIdTipoUsuarioOfUsuarioListNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipousuario.getIdTipoUsuario();
                if (findTipousuario(id) == null) {
                    throw new NonexistentEntityException("The tipousuario with id " + id + " no longer exists.");
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
            Tipousuario tipousuario;
            try {
                tipousuario = em.getReference(Tipousuario.class, id);
                tipousuario.getIdTipoUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipousuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Usuario> usuarioListOrphanCheck = tipousuario.getUsuarioList();
            for (Usuario usuarioListOrphanCheckUsuario : usuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipousuario (" + tipousuario + ") cannot be destroyed since the Usuario " + usuarioListOrphanCheckUsuario + " in its usuarioList field has a non-nullable idTipoUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipousuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipousuario> findTipousuarioEntities() {
        return findTipousuarioEntities(true, -1, -1);
    }

    public List<Tipousuario> findTipousuarioEntities(int maxResults, int firstResult) {
        return findTipousuarioEntities(false, maxResults, firstResult);
    }

    private List<Tipousuario> findTipousuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipousuario.class));
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

    public Tipousuario findTipousuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipousuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipousuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipousuario> rt = cq.from(Tipousuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
