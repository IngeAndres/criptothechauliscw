/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dto.Datospersonales;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.Tipodocumento;
import dto.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jeff
 */
public class DatospersonalesJpaController implements Serializable {

    public DatospersonalesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Datospersonales datospersonales) {
        if (datospersonales.getUsuarioList() == null) {
            datospersonales.setUsuarioList(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipodocumento idTipoDocumento = datospersonales.getIdTipoDocumento();
            if (idTipoDocumento != null) {
                idTipoDocumento = em.getReference(idTipoDocumento.getClass(), idTipoDocumento.getIdTipoDocumento());
                datospersonales.setIdTipoDocumento(idTipoDocumento);
            }
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : datospersonales.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getIdUsuario());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            datospersonales.setUsuarioList(attachedUsuarioList);
            em.persist(datospersonales);
            if (idTipoDocumento != null) {
                idTipoDocumento.getDatospersonalesList().add(datospersonales);
                idTipoDocumento = em.merge(idTipoDocumento);
            }
            for (Usuario usuarioListUsuario : datospersonales.getUsuarioList()) {
                Datospersonales oldIdPersonaOfUsuarioListUsuario = usuarioListUsuario.getIdPersona();
                usuarioListUsuario.setIdPersona(datospersonales);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldIdPersonaOfUsuarioListUsuario != null) {
                    oldIdPersonaOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldIdPersonaOfUsuarioListUsuario = em.merge(oldIdPersonaOfUsuarioListUsuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Datospersonales datospersonales) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Datospersonales persistentDatospersonales = em.find(Datospersonales.class, datospersonales.getIdPersona());
            Tipodocumento idTipoDocumentoOld = persistentDatospersonales.getIdTipoDocumento();
            Tipodocumento idTipoDocumentoNew = datospersonales.getIdTipoDocumento();
            List<Usuario> usuarioListOld = persistentDatospersonales.getUsuarioList();
            List<Usuario> usuarioListNew = datospersonales.getUsuarioList();
            List<String> illegalOrphanMessages = null;
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioListOldUsuario + " since its idPersona field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idTipoDocumentoNew != null) {
                idTipoDocumentoNew = em.getReference(idTipoDocumentoNew.getClass(), idTipoDocumentoNew.getIdTipoDocumento());
                datospersonales.setIdTipoDocumento(idTipoDocumentoNew);
            }
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getIdUsuario());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            datospersonales.setUsuarioList(usuarioListNew);
            datospersonales = em.merge(datospersonales);
            if (idTipoDocumentoOld != null && !idTipoDocumentoOld.equals(idTipoDocumentoNew)) {
                idTipoDocumentoOld.getDatospersonalesList().remove(datospersonales);
                idTipoDocumentoOld = em.merge(idTipoDocumentoOld);
            }
            if (idTipoDocumentoNew != null && !idTipoDocumentoNew.equals(idTipoDocumentoOld)) {
                idTipoDocumentoNew.getDatospersonalesList().add(datospersonales);
                idTipoDocumentoNew = em.merge(idTipoDocumentoNew);
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    Datospersonales oldIdPersonaOfUsuarioListNewUsuario = usuarioListNewUsuario.getIdPersona();
                    usuarioListNewUsuario.setIdPersona(datospersonales);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldIdPersonaOfUsuarioListNewUsuario != null && !oldIdPersonaOfUsuarioListNewUsuario.equals(datospersonales)) {
                        oldIdPersonaOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldIdPersonaOfUsuarioListNewUsuario = em.merge(oldIdPersonaOfUsuarioListNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = datospersonales.getIdPersona();
                if (findDatospersonales(id) == null) {
                    throw new NonexistentEntityException("The datospersonales with id " + id + " no longer exists.");
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
            Datospersonales datospersonales;
            try {
                datospersonales = em.getReference(Datospersonales.class, id);
                datospersonales.getIdPersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The datospersonales with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Usuario> usuarioListOrphanCheck = datospersonales.getUsuarioList();
            for (Usuario usuarioListOrphanCheckUsuario : usuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Datospersonales (" + datospersonales + ") cannot be destroyed since the Usuario " + usuarioListOrphanCheckUsuario + " in its usuarioList field has a non-nullable idPersona field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Tipodocumento idTipoDocumento = datospersonales.getIdTipoDocumento();
            if (idTipoDocumento != null) {
                idTipoDocumento.getDatospersonalesList().remove(datospersonales);
                idTipoDocumento = em.merge(idTipoDocumento);
            }
            em.remove(datospersonales);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Datospersonales> findDatospersonalesEntities() {
        return findDatospersonalesEntities(true, -1, -1);
    }

    public List<Datospersonales> findDatospersonalesEntities(int maxResults, int firstResult) {
        return findDatospersonalesEntities(false, maxResults, firstResult);
    }

    private List<Datospersonales> findDatospersonalesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Datospersonales.class));
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

    public Datospersonales findDatospersonales(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Datospersonales.class, id);
        } finally {
            em.close();
        }
    }

    public int getDatospersonalesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Datospersonales> rt = cq.from(Datospersonales.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
