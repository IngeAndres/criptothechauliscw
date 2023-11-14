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
import dto.Distrito;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Ing. Andres Gomez
 */
public class DistritoJpaController implements Serializable {

    public DistritoJpaController() {
    }

    public DistritoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_CriptoTheChaulisCW_war_1.0-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Distrito distrito) {
        if (distrito.getClienteList() == null) {
            distrito.setClienteList(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : distrito.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getCodigoCliente());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            distrito.setClienteList(attachedClienteList);
            em.persist(distrito);
            for (Cliente clienteListCliente : distrito.getClienteList()) {
                Distrito oldCodigoDistritoOfClienteListCliente = clienteListCliente.getCodigoDistrito();
                clienteListCliente.setCodigoDistrito(distrito);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldCodigoDistritoOfClienteListCliente != null) {
                    oldCodigoDistritoOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldCodigoDistritoOfClienteListCliente = em.merge(oldCodigoDistritoOfClienteListCliente);
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
            Distrito persistentDistrito = em.find(Distrito.class, distrito.getCodigoDistrito());
            List<Cliente> clienteListOld = persistentDistrito.getClienteList();
            List<Cliente> clienteListNew = distrito.getClienteList();
            List<String> illegalOrphanMessages = null;
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cliente " + clienteListOldCliente + " since its codigoDistrito field is not nullable.");
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
            distrito.setClienteList(clienteListNew);
            distrito = em.merge(distrito);
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Distrito oldCodigoDistritoOfClienteListNewCliente = clienteListNewCliente.getCodigoDistrito();
                    clienteListNewCliente.setCodigoDistrito(distrito);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldCodigoDistritoOfClienteListNewCliente != null && !oldCodigoDistritoOfClienteListNewCliente.equals(distrito)) {
                        oldCodigoDistritoOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldCodigoDistritoOfClienteListNewCliente = em.merge(oldCodigoDistritoOfClienteListNewCliente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = distrito.getCodigoDistrito();
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
                distrito.getCodigoDistrito();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The distrito with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cliente> clienteListOrphanCheck = distrito.getClienteList();
            for (Cliente clienteListOrphanCheckCliente : clienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Distrito (" + distrito + ") cannot be destroyed since the Cliente " + clienteListOrphanCheckCliente + " in its clienteList field has a non-nullable codigoDistrito field.");
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

    public List<Distrito> listarDistrito() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Distrito.findAll");
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Distrito obtenerCodigoDistrito(String nombreDistrito) {
        EntityManager em = getEntityManager();
        try {

            Query query = em.createNamedQuery("Distrito.findByNombreDistrito");
            query.setParameter("nombreDistrito", nombreDistrito);

            List<Distrito> results = query.getResultList();

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
