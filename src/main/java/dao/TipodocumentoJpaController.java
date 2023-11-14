/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.Cliente;
import dto.Tipodocumento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Ing. Andres Gomez
 */
public class TipodocumentoJpaController implements Serializable {

    public TipodocumentoJpaController() {
    }

    public TipodocumentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_CriptoTheChaulisCW_war_1.0-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipodocumento tipodocumento) {
        if (tipodocumento.getClienteList() == null) {
            tipodocumento.setClienteList(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : tipodocumento.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getCodigoCliente());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            tipodocumento.setClienteList(attachedClienteList);
            em.persist(tipodocumento);
            for (Cliente clienteListCliente : tipodocumento.getClienteList()) {
                Tipodocumento oldCodigoTipoDocumentoOfClienteListCliente = clienteListCliente.getCodigoTipoDocumento();
                clienteListCliente.setCodigoTipoDocumento(tipodocumento);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldCodigoTipoDocumentoOfClienteListCliente != null) {
                    oldCodigoTipoDocumentoOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldCodigoTipoDocumentoOfClienteListCliente = em.merge(oldCodigoTipoDocumentoOfClienteListCliente);
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
            Tipodocumento persistentTipodocumento = em.find(Tipodocumento.class, tipodocumento.getCodigoTipoDocumento());
            List<Cliente> clienteListOld = persistentTipodocumento.getClienteList();
            List<Cliente> clienteListNew = tipodocumento.getClienteList();
            List<String> illegalOrphanMessages = null;
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cliente " + clienteListOldCliente + " since its codigoTipoDocumento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getCodigoCliente());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            tipodocumento.setClienteList(clienteListNew);
            tipodocumento = em.merge(tipodocumento);
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Tipodocumento oldCodigoTipoDocumentoOfClienteListNewCliente = clienteListNewCliente.getCodigoTipoDocumento();
                    clienteListNewCliente.setCodigoTipoDocumento(tipodocumento);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldCodigoTipoDocumentoOfClienteListNewCliente != null && !oldCodigoTipoDocumentoOfClienteListNewCliente.equals(tipodocumento)) {
                        oldCodigoTipoDocumentoOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldCodigoTipoDocumentoOfClienteListNewCliente = em.merge(oldCodigoTipoDocumentoOfClienteListNewCliente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipodocumento.getCodigoTipoDocumento();
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
                tipodocumento.getCodigoTipoDocumento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipodocumento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cliente> clienteListOrphanCheck = tipodocumento.getClienteList();
            for (Cliente clienteListOrphanCheckCliente : clienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipodocumento (" + tipodocumento + ") cannot be destroyed since the Cliente " + clienteListOrphanCheckCliente + " in its clienteList field has a non-nullable codigoTipoDocumento field.");
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

    public List<Tipodocumento> listarTipoDoc() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Tipodocumento.findAll");
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Tipodocumento obtenerCodigoTipoDoc(String nombreDocumento) {
        EntityManager em = getEntityManager();
        try {

            Query query = em.createNamedQuery("Tipodocumento.findByNombTipoDocumento");
            query.setParameter("nombTipoDocumento", nombreDocumento);

            List<Tipodocumento> results = query.getResultList();

            if (!results.isEmpty()) {
                return results.get(0);
            } else {
                return null;
            }
        } finally {
            em.close();
        }
    }
}
