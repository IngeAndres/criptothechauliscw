package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dto.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.Tipodocumento;
import dto.Estado;
import dto.Distrito;
import dto.Prestamo;
import java.util.ArrayList;
import java.util.List;
import dto.Cuenta;
import java.util.Arrays;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Ing. Andres Gomez
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController() {
    }

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_CriptoTheChaulisCW_war_1.0-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        if (cliente.getPrestamoList() == null) {
            cliente.setPrestamoList(new ArrayList<Prestamo>());
        }
        if (cliente.getCuentaList() == null) {
            cliente.setCuentaList(new ArrayList<Cuenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipodocumento codigoTipoDocumento = cliente.getCodigoTipoDocumento();
            if (codigoTipoDocumento != null) {
                codigoTipoDocumento = em.getReference(codigoTipoDocumento.getClass(), codigoTipoDocumento.getCodigoTipoDocumento());
                cliente.setCodigoTipoDocumento(codigoTipoDocumento);
            }
            Estado codigoEstado = cliente.getCodigoEstado();
            if (codigoEstado != null) {
                codigoEstado = em.getReference(codigoEstado.getClass(), codigoEstado.getCodigoEstado());
                cliente.setCodigoEstado(codigoEstado);
            }
            Distrito codigoDistrito = cliente.getCodigoDistrito();
            if (codigoDistrito != null) {
                codigoDistrito = em.getReference(codigoDistrito.getClass(), codigoDistrito.getCodigoDistrito());
                cliente.setCodigoDistrito(codigoDistrito);
            }
            List<Prestamo> attachedPrestamoList = new ArrayList<Prestamo>();
            for (Prestamo prestamoListPrestamoToAttach : cliente.getPrestamoList()) {
                prestamoListPrestamoToAttach = em.getReference(prestamoListPrestamoToAttach.getClass(), prestamoListPrestamoToAttach.getCodigoPrestamo());
                attachedPrestamoList.add(prestamoListPrestamoToAttach);
            }
            cliente.setPrestamoList(attachedPrestamoList);
            List<Cuenta> attachedCuentaList = new ArrayList<Cuenta>();
            for (Cuenta cuentaListCuentaToAttach : cliente.getCuentaList()) {
                cuentaListCuentaToAttach = em.getReference(cuentaListCuentaToAttach.getClass(), cuentaListCuentaToAttach.getCodigoCuenta());
                attachedCuentaList.add(cuentaListCuentaToAttach);
            }
            cliente.setCuentaList(attachedCuentaList);
            em.persist(cliente);
            if (codigoTipoDocumento != null) {
                codigoTipoDocumento.getClienteList().add(cliente);
                codigoTipoDocumento = em.merge(codigoTipoDocumento);
            }
            if (codigoEstado != null) {
                codigoEstado.getClienteList().add(cliente);
                codigoEstado = em.merge(codigoEstado);
            }
            if (codigoDistrito != null) {
                codigoDistrito.getClienteList().add(cliente);
                codigoDistrito = em.merge(codigoDistrito);
            }
            for (Prestamo prestamoListPrestamo : cliente.getPrestamoList()) {
                Cliente oldCodigoClienteOfPrestamoListPrestamo = prestamoListPrestamo.getCodigoCliente();
                prestamoListPrestamo.setCodigoCliente(cliente);
                prestamoListPrestamo = em.merge(prestamoListPrestamo);
                if (oldCodigoClienteOfPrestamoListPrestamo != null) {
                    oldCodigoClienteOfPrestamoListPrestamo.getPrestamoList().remove(prestamoListPrestamo);
                    oldCodigoClienteOfPrestamoListPrestamo = em.merge(oldCodigoClienteOfPrestamoListPrestamo);
                }
            }
            for (Cuenta cuentaListCuenta : cliente.getCuentaList()) {
                Cliente oldCodigoClienteOfCuentaListCuenta = cuentaListCuenta.getCodigoCliente();
                cuentaListCuenta.setCodigoCliente(cliente);
                cuentaListCuenta = em.merge(cuentaListCuenta);
                if (oldCodigoClienteOfCuentaListCuenta != null) {
                    oldCodigoClienteOfCuentaListCuenta.getCuentaList().remove(cuentaListCuenta);
                    oldCodigoClienteOfCuentaListCuenta = em.merge(oldCodigoClienteOfCuentaListCuenta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getCodigoCliente());
            Tipodocumento codigoTipoDocumentoOld = persistentCliente.getCodigoTipoDocumento();
            Tipodocumento codigoTipoDocumentoNew = cliente.getCodigoTipoDocumento();
            Estado codigoEstadoOld = persistentCliente.getCodigoEstado();
            Estado codigoEstadoNew = cliente.getCodigoEstado();
            Distrito codigoDistritoOld = persistentCliente.getCodigoDistrito();
            Distrito codigoDistritoNew = cliente.getCodigoDistrito();
            List<Prestamo> prestamoListOld = persistentCliente.getPrestamoList();
            List<Prestamo> prestamoListNew = cliente.getPrestamoList();
            List<Cuenta> cuentaListOld = persistentCliente.getCuentaList();
            List<Cuenta> cuentaListNew = cliente.getCuentaList();
            List<String> illegalOrphanMessages = null;
            for (Prestamo prestamoListOldPrestamo : prestamoListOld) {
                if (!prestamoListNew.contains(prestamoListOldPrestamo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Prestamo " + prestamoListOldPrestamo + " since its codigoCliente field is not nullable.");
                }
            }
            for (Cuenta cuentaListOldCuenta : cuentaListOld) {
                if (!cuentaListNew.contains(cuentaListOldCuenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cuenta " + cuentaListOldCuenta + " since its codigoCliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codigoTipoDocumentoNew != null) {
                codigoTipoDocumentoNew = em.getReference(codigoTipoDocumentoNew.getClass(), codigoTipoDocumentoNew.getCodigoTipoDocumento());
                cliente.setCodigoTipoDocumento(codigoTipoDocumentoNew);
            }
            if (codigoEstadoNew != null) {
                codigoEstadoNew = em.getReference(codigoEstadoNew.getClass(), codigoEstadoNew.getCodigoEstado());
                cliente.setCodigoEstado(codigoEstadoNew);
            }
            if (codigoDistritoNew != null) {
                codigoDistritoNew = em.getReference(codigoDistritoNew.getClass(), codigoDistritoNew.getCodigoDistrito());
                cliente.setCodigoDistrito(codigoDistritoNew);
            }
            List<Prestamo> attachedPrestamoListNew = new ArrayList<Prestamo>();
            for (Prestamo prestamoListNewPrestamoToAttach : prestamoListNew) {
                prestamoListNewPrestamoToAttach = em.getReference(prestamoListNewPrestamoToAttach.getClass(), prestamoListNewPrestamoToAttach.getCodigoPrestamo());
                attachedPrestamoListNew.add(prestamoListNewPrestamoToAttach);
            }
            prestamoListNew = attachedPrestamoListNew;
            cliente.setPrestamoList(prestamoListNew);
            List<Cuenta> attachedCuentaListNew = new ArrayList<Cuenta>();
            for (Cuenta cuentaListNewCuentaToAttach : cuentaListNew) {
                cuentaListNewCuentaToAttach = em.getReference(cuentaListNewCuentaToAttach.getClass(), cuentaListNewCuentaToAttach.getCodigoCuenta());
                attachedCuentaListNew.add(cuentaListNewCuentaToAttach);
            }
            cuentaListNew = attachedCuentaListNew;
            cliente.setCuentaList(cuentaListNew);
            cliente = em.merge(cliente);
            if (codigoTipoDocumentoOld != null && !codigoTipoDocumentoOld.equals(codigoTipoDocumentoNew)) {
                codigoTipoDocumentoOld.getClienteList().remove(cliente);
                codigoTipoDocumentoOld = em.merge(codigoTipoDocumentoOld);
            }
            if (codigoTipoDocumentoNew != null && !codigoTipoDocumentoNew.equals(codigoTipoDocumentoOld)) {
                codigoTipoDocumentoNew.getClienteList().add(cliente);
                codigoTipoDocumentoNew = em.merge(codigoTipoDocumentoNew);
            }
            if (codigoEstadoOld != null && !codigoEstadoOld.equals(codigoEstadoNew)) {
                codigoEstadoOld.getClienteList().remove(cliente);
                codigoEstadoOld = em.merge(codigoEstadoOld);
            }
            if (codigoEstadoNew != null && !codigoEstadoNew.equals(codigoEstadoOld)) {
                codigoEstadoNew.getClienteList().add(cliente);
                codigoEstadoNew = em.merge(codigoEstadoNew);
            }
            if (codigoDistritoOld != null && !codigoDistritoOld.equals(codigoDistritoNew)) {
                codigoDistritoOld.getClienteList().remove(cliente);
                codigoDistritoOld = em.merge(codigoDistritoOld);
            }
            if (codigoDistritoNew != null && !codigoDistritoNew.equals(codigoDistritoOld)) {
                codigoDistritoNew.getClienteList().add(cliente);
                codigoDistritoNew = em.merge(codigoDistritoNew);
            }
            for (Prestamo prestamoListNewPrestamo : prestamoListNew) {
                if (!prestamoListOld.contains(prestamoListNewPrestamo)) {
                    Cliente oldCodigoClienteOfPrestamoListNewPrestamo = prestamoListNewPrestamo.getCodigoCliente();
                    prestamoListNewPrestamo.setCodigoCliente(cliente);
                    prestamoListNewPrestamo = em.merge(prestamoListNewPrestamo);
                    if (oldCodigoClienteOfPrestamoListNewPrestamo != null && !oldCodigoClienteOfPrestamoListNewPrestamo.equals(cliente)) {
                        oldCodigoClienteOfPrestamoListNewPrestamo.getPrestamoList().remove(prestamoListNewPrestamo);
                        oldCodigoClienteOfPrestamoListNewPrestamo = em.merge(oldCodigoClienteOfPrestamoListNewPrestamo);
                    }
                }
            }
            for (Cuenta cuentaListNewCuenta : cuentaListNew) {
                if (!cuentaListOld.contains(cuentaListNewCuenta)) {
                    Cliente oldCodigoClienteOfCuentaListNewCuenta = cuentaListNewCuenta.getCodigoCliente();
                    cuentaListNewCuenta.setCodigoCliente(cliente);
                    cuentaListNewCuenta = em.merge(cuentaListNewCuenta);
                    if (oldCodigoClienteOfCuentaListNewCuenta != null && !oldCodigoClienteOfCuentaListNewCuenta.equals(cliente)) {
                        oldCodigoClienteOfCuentaListNewCuenta.getCuentaList().remove(cuentaListNewCuenta);
                        oldCodigoClienteOfCuentaListNewCuenta = em.merge(oldCodigoClienteOfCuentaListNewCuenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getCodigoCliente();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getCodigoCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Prestamo> prestamoListOrphanCheck = cliente.getPrestamoList();
            for (Prestamo prestamoListOrphanCheckPrestamo : prestamoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Prestamo " + prestamoListOrphanCheckPrestamo + " in its prestamoList field has a non-nullable codigoCliente field.");
            }
            List<Cuenta> cuentaListOrphanCheck = cliente.getCuentaList();
            for (Cuenta cuentaListOrphanCheckCuenta : cuentaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Cuenta " + cuentaListOrphanCheckCuenta + " in its cuentaList field has a non-nullable codigoCliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Tipodocumento codigoTipoDocumento = cliente.getCodigoTipoDocumento();
            if (codigoTipoDocumento != null) {
                codigoTipoDocumento.getClienteList().remove(cliente);
                codigoTipoDocumento = em.merge(codigoTipoDocumento);
            }
            Estado codigoEstado = cliente.getCodigoEstado();
            if (codigoEstado != null) {
                codigoEstado.getClienteList().remove(cliente);
                codigoEstado = em.merge(codigoEstado);
            }
            Distrito codigoDistrito = cliente.getCodigoDistrito();
            if (codigoDistrito != null) {
                codigoDistrito.getClienteList().remove(cliente);
                codigoDistrito = em.merge(codigoDistrito);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public boolean eliminarLogico(int codigoCliente, Estado estadoCliente) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Cliente cliente = em.find(Cliente.class, codigoCliente);

            if (cliente != null) {

                cliente.setCodigoEstado(estadoCliente);
                em.merge(cliente);
                em.getTransaction().commit();
                return true;
            } else {

                return false;
            }
        } catch (Exception ex) {

            return false;
        } finally {
            em.close();
        }
    }

    public List<Object[]> listarClientes() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Cliente.listar");
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Object[]> listarClientesPorCodigo(int codigoCliente) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Cliente.listarPorCodigo");
            q.setParameter("codigoCliente", codigoCliente);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public boolean insertarCliente(Cliente cliente) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {

            return false;

        }
    }

    public boolean editarCliente(int codigoCliente, Cliente clienteActualizado) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Cliente clienteExistente = em.find(Cliente.class, codigoCliente);
            if (clienteExistente != null) {

                clienteExistente.setCodigoTipoDocumento(clienteActualizado.getCodigoTipoDocumento());
                clienteExistente.setDocuClie(clienteActualizado.getDocuClie());
                clienteExistente.setAppaClie(clienteActualizado.getAppaClie());
                clienteExistente.setApmaClie(clienteActualizado.getApmaClie());
                clienteExistente.setNombClie(clienteActualizado.getNombClie());
                clienteExistente.setCodigoDistrito(clienteActualizado.getCodigoDistrito());
                clienteExistente.setDireClie(clienteActualizado.getDireClie());
                clienteExistente.setTelfClie(clienteActualizado.getTelfClie());

                em.merge(clienteExistente);
                transaction.commit();
                return true;
            } else {

                transaction.rollback();
                return false;
            }
        } catch (Exception ex) {

            if (transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    public Cliente obtenerCodigoCliente(String documentoCliente) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNamedQuery("Cliente.findByDocuClie");
            query.setParameter("docuClie", documentoCliente);

            List<Cliente> results = query.getResultList();

            if (!results.isEmpty()) {
                return results.get(0);
            } else {
                return null;
            }
        } finally {
            em.close();
        }

    }

    public static void main(String[] args) {
        ClienteJpaController clienteController = new ClienteJpaController();
        List<Object[]> lista = clienteController.listarClientes();
        for (Object[] objects : lista) {
            System.out.println(Arrays.toString(objects));
        }
        /*List<Object[]> clientes = clienteController.listarClientesPorCodigo(1);

        for (Object[] clienteData : clientes) {
            String documento = (String) clienteData[0];
            String numeroDoc = (String) clienteData[1];
            String apellidoPaterno = (String) clienteData[2];
            String apellidoMaterno = (String) clienteData[3];
            String nombre = (String) clienteData[4];
            String distrito = (String) clienteData[5];
            String direccion = (String) clienteData[6];
            int estadoCliente = (int) clienteData[7];
            int codigoCliente = (int) clienteData[8];

            System.out.println("Documento: " + documento);
            System.out.println("Número de Documento: " + numeroDoc);
            System.out.println("Apellido Paterno: " + apellidoPaterno);
            System.out.println("Apellido Materno: " + apellidoMaterno);
            System.out.println("Nombre: " + nombre);
            System.out.println("Distrito: " + distrito);
            System.out.println("Dirección: " + direccion);
            System.out.println("Estado Cliente: " + estadoCliente);
            System.out.println("Código Cliente: " + codigoCliente);
            System.out.println("--------------------------");
        }*/
    }
}
