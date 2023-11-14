/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.Cliente;
import java.util.ArrayList;
import java.util.List;
import dto.Cuenta;
import dto.Estado;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Ing. Andres Gomez
 */
public class EstadoJpaController implements Serializable {

    public EstadoJpaController() {
    }

    public EstadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_CriptoTheChaulisCW_war_1.0-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estado estado) throws PreexistingEntityException, Exception {
        if (estado.getClienteList() == null) {
            estado.setClienteList(new ArrayList<Cliente>());
        }
        if (estado.getCuentaList() == null) {
            estado.setCuentaList(new ArrayList<Cuenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : estado.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getCodigoCliente());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            estado.setClienteList(attachedClienteList);
            List<Cuenta> attachedCuentaList = new ArrayList<Cuenta>();
            for (Cuenta cuentaListCuentaToAttach : estado.getCuentaList()) {
                cuentaListCuentaToAttach = em.getReference(cuentaListCuentaToAttach.getClass(), cuentaListCuentaToAttach.getCodigoCuenta());
                attachedCuentaList.add(cuentaListCuentaToAttach);
            }
            estado.setCuentaList(attachedCuentaList);
            em.persist(estado);
            for (Cliente clienteListCliente : estado.getClienteList()) {
                Estado oldCodigoEstadoOfClienteListCliente = clienteListCliente.getCodigoEstado();
                clienteListCliente.setCodigoEstado(estado);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldCodigoEstadoOfClienteListCliente != null) {
                    oldCodigoEstadoOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldCodigoEstadoOfClienteListCliente = em.merge(oldCodigoEstadoOfClienteListCliente);
                }
            }
            for (Cuenta cuentaListCuenta : estado.getCuentaList()) {
                Estado oldCodigoEstadoOfCuentaListCuenta = cuentaListCuenta.getCodigoEstado();
                cuentaListCuenta.setCodigoEstado(estado);
                cuentaListCuenta = em.merge(cuentaListCuenta);
                if (oldCodigoEstadoOfCuentaListCuenta != null) {
                    oldCodigoEstadoOfCuentaListCuenta.getCuentaList().remove(cuentaListCuenta);
                    oldCodigoEstadoOfCuentaListCuenta = em.merge(oldCodigoEstadoOfCuentaListCuenta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstado(estado.getCodigoEstado()) != null) {
                throw new PreexistingEntityException("Estado " + estado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estado estado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado persistentEstado = em.find(Estado.class, estado.getCodigoEstado());
            List<Cliente> clienteListOld = persistentEstado.getClienteList();
            List<Cliente> clienteListNew = estado.getClienteList();
            List<Cuenta> cuentaListOld = persistentEstado.getCuentaList();
            List<Cuenta> cuentaListNew = estado.getCuentaList();
            List<String> illegalOrphanMessages = null;
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cliente " + clienteListOldCliente + " since its codigoEstado field is not nullable.");
                }
            }
            for (Cuenta cuentaListOldCuenta : cuentaListOld) {
                if (!cuentaListNew.contains(cuentaListOldCuenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cuenta " + cuentaListOldCuenta + " since its codigoEstado field is not nullable.");
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
            estado.setClienteList(clienteListNew);
            List<Cuenta> attachedCuentaListNew = new ArrayList<Cuenta>();
            for (Cuenta cuentaListNewCuentaToAttach : cuentaListNew) {
                cuentaListNewCuentaToAttach = em.getReference(cuentaListNewCuentaToAttach.getClass(), cuentaListNewCuentaToAttach.getCodigoCuenta());
                attachedCuentaListNew.add(cuentaListNewCuentaToAttach);
            }
            cuentaListNew = attachedCuentaListNew;
            estado.setCuentaList(cuentaListNew);
            estado = em.merge(estado);
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Estado oldCodigoEstadoOfClienteListNewCliente = clienteListNewCliente.getCodigoEstado();
                    clienteListNewCliente.setCodigoEstado(estado);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldCodigoEstadoOfClienteListNewCliente != null && !oldCodigoEstadoOfClienteListNewCliente.equals(estado)) {
                        oldCodigoEstadoOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldCodigoEstadoOfClienteListNewCliente = em.merge(oldCodigoEstadoOfClienteListNewCliente);
                    }
                }
            }
            for (Cuenta cuentaListNewCuenta : cuentaListNew) {
                if (!cuentaListOld.contains(cuentaListNewCuenta)) {
                    Estado oldCodigoEstadoOfCuentaListNewCuenta = cuentaListNewCuenta.getCodigoEstado();
                    cuentaListNewCuenta.setCodigoEstado(estado);
                    cuentaListNewCuenta = em.merge(cuentaListNewCuenta);
                    if (oldCodigoEstadoOfCuentaListNewCuenta != null && !oldCodigoEstadoOfCuentaListNewCuenta.equals(estado)) {
                        oldCodigoEstadoOfCuentaListNewCuenta.getCuentaList().remove(cuentaListNewCuenta);
                        oldCodigoEstadoOfCuentaListNewCuenta = em.merge(oldCodigoEstadoOfCuentaListNewCuenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estado.getCodigoEstado();
                if (findEstado(id) == null) {
                    throw new NonexistentEntityException("The estado with id " + id + " no longer exists.");
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
            Estado estado;
            try {
                estado = em.getReference(Estado.class, id);
                estado.getCodigoEstado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cliente> clienteListOrphanCheck = estado.getClienteList();
            for (Cliente clienteListOrphanCheckCliente : clienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estado (" + estado + ") cannot be destroyed since the Cliente " + clienteListOrphanCheckCliente + " in its clienteList field has a non-nullable codigoEstado field.");
            }
            List<Cuenta> cuentaListOrphanCheck = estado.getCuentaList();
            for (Cuenta cuentaListOrphanCheckCuenta : cuentaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estado (" + estado + ") cannot be destroyed since the Cuenta " + cuentaListOrphanCheckCuenta + " in its cuentaList field has a non-nullable codigoEstado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estado> findEstadoEntities() {
        return findEstadoEntities(true, -1, -1);
    }

    public List<Estado> findEstadoEntities(int maxResults, int firstResult) {
        return findEstadoEntities(false, maxResults, firstResult);
    }

    private List<Estado> findEstadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estado.class));
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

    public Estado findEstado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estado> rt = cq.from(Estado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Estado obtenerCodigoEstado(int estadoCliente) {
        EntityManager em = getEntityManager();
        try {

            Query query = em.createNamedQuery("Estado.findByCodigoEstado");
            query.setParameter("codigoEstado", estadoCliente);

            List<Estado> results = query.getResultList();

            if (!results.isEmpty()) {
                return results.get(0);
            } else {
                return null;
            }
        } finally {
            em.close();
        }
    }

    public List<Estado> listarEstadoCuenta() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Estado.findAll");
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Estado obtenerEstadoCuenta(String estado) {
        EntityManager em = getEntityManager();
        try {

            Query query = em.createNamedQuery("Estado.findByNombEsta");
            query.setParameter("nombEsta", estado);

            List<Estado> results = query.getResultList();

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
