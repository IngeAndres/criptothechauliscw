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
import dto.Cuenta;
import dto.Tipocuenta;
import dto.Estado;
import dto.Transferencia;
import java.util.ArrayList;
import java.util.List;
import dto.Deposito;
import dto.Retiro;
import java.util.Arrays;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Ing. Andres Gomez
 */
public class CuentaJpaController implements Serializable {

    public CuentaJpaController() {
    }

    public CuentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_CriptoTheChaulisCW_war_1.0-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cuenta cuenta) {
        if (cuenta.getTransferenciaList() == null) {
            cuenta.setTransferenciaList(new ArrayList<Transferencia>());
        }
        if (cuenta.getTransferenciaList1() == null) {
            cuenta.setTransferenciaList1(new ArrayList<Transferencia>());
        }
        if (cuenta.getDepositoList() == null) {
            cuenta.setDepositoList(new ArrayList<Deposito>());
        }
        if (cuenta.getRetiroList() == null) {
            cuenta.setRetiroList(new ArrayList<Retiro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente codigoCliente = cuenta.getCodigoCliente();
            if (codigoCliente != null) {
                codigoCliente = em.getReference(codigoCliente.getClass(), codigoCliente.getCodigoCliente());
                cuenta.setCodigoCliente(codigoCliente);
            }
            Tipocuenta codigoTipoCuenta = cuenta.getCodigoTipoCuenta();
            if (codigoTipoCuenta != null) {
                codigoTipoCuenta = em.getReference(codigoTipoCuenta.getClass(), codigoTipoCuenta.getCodigoTipoCuenta());
                cuenta.setCodigoTipoCuenta(codigoTipoCuenta);
            }
            Estado codigoEstado = cuenta.getCodigoEstado();
            if (codigoEstado != null) {
                codigoEstado = em.getReference(codigoEstado.getClass(), codigoEstado.getCodigoEstado());
                cuenta.setCodigoEstado(codigoEstado);
            }
            List<Transferencia> attachedTransferenciaList = new ArrayList<Transferencia>();
            for (Transferencia transferenciaListTransferenciaToAttach : cuenta.getTransferenciaList()) {
                transferenciaListTransferenciaToAttach = em.getReference(transferenciaListTransferenciaToAttach.getClass(), transferenciaListTransferenciaToAttach.getCodigoTransferencia());
                attachedTransferenciaList.add(transferenciaListTransferenciaToAttach);
            }
            cuenta.setTransferenciaList(attachedTransferenciaList);
            List<Transferencia> attachedTransferenciaList1 = new ArrayList<Transferencia>();
            for (Transferencia transferenciaList1TransferenciaToAttach : cuenta.getTransferenciaList1()) {
                transferenciaList1TransferenciaToAttach = em.getReference(transferenciaList1TransferenciaToAttach.getClass(), transferenciaList1TransferenciaToAttach.getCodigoTransferencia());
                attachedTransferenciaList1.add(transferenciaList1TransferenciaToAttach);
            }
            cuenta.setTransferenciaList1(attachedTransferenciaList1);
            List<Deposito> attachedDepositoList = new ArrayList<Deposito>();
            for (Deposito depositoListDepositoToAttach : cuenta.getDepositoList()) {
                depositoListDepositoToAttach = em.getReference(depositoListDepositoToAttach.getClass(), depositoListDepositoToAttach.getCodigoDeposito());
                attachedDepositoList.add(depositoListDepositoToAttach);
            }
            cuenta.setDepositoList(attachedDepositoList);
            List<Retiro> attachedRetiroList = new ArrayList<Retiro>();
            for (Retiro retiroListRetiroToAttach : cuenta.getRetiroList()) {
                retiroListRetiroToAttach = em.getReference(retiroListRetiroToAttach.getClass(), retiroListRetiroToAttach.getCodigoRetiro());
                attachedRetiroList.add(retiroListRetiroToAttach);
            }
            cuenta.setRetiroList(attachedRetiroList);
            em.persist(cuenta);
            if (codigoCliente != null) {
                codigoCliente.getCuentaList().add(cuenta);
                codigoCliente = em.merge(codigoCliente);
            }
            if (codigoTipoCuenta != null) {
                codigoTipoCuenta.getCuentaList().add(cuenta);
                codigoTipoCuenta = em.merge(codigoTipoCuenta);
            }
            if (codigoEstado != null) {
                codigoEstado.getCuentaList().add(cuenta);
                codigoEstado = em.merge(codigoEstado);
            }
            for (Transferencia transferenciaListTransferencia : cuenta.getTransferenciaList()) {
                Cuenta oldCodigoCuentaOrigenOfTransferenciaListTransferencia = transferenciaListTransferencia.getCodigoCuentaOrigen();
                transferenciaListTransferencia.setCodigoCuentaOrigen(cuenta);
                transferenciaListTransferencia = em.merge(transferenciaListTransferencia);
                if (oldCodigoCuentaOrigenOfTransferenciaListTransferencia != null) {
                    oldCodigoCuentaOrigenOfTransferenciaListTransferencia.getTransferenciaList().remove(transferenciaListTransferencia);
                    oldCodigoCuentaOrigenOfTransferenciaListTransferencia = em.merge(oldCodigoCuentaOrigenOfTransferenciaListTransferencia);
                }
            }
            for (Transferencia transferenciaList1Transferencia : cuenta.getTransferenciaList1()) {
                Cuenta oldCodigoCuentaDestinoOfTransferenciaList1Transferencia = transferenciaList1Transferencia.getCodigoCuentaDestino();
                transferenciaList1Transferencia.setCodigoCuentaDestino(cuenta);
                transferenciaList1Transferencia = em.merge(transferenciaList1Transferencia);
                if (oldCodigoCuentaDestinoOfTransferenciaList1Transferencia != null) {
                    oldCodigoCuentaDestinoOfTransferenciaList1Transferencia.getTransferenciaList1().remove(transferenciaList1Transferencia);
                    oldCodigoCuentaDestinoOfTransferenciaList1Transferencia = em.merge(oldCodigoCuentaDestinoOfTransferenciaList1Transferencia);
                }
            }
            for (Deposito depositoListDeposito : cuenta.getDepositoList()) {
                Cuenta oldCodigoCuentaOfDepositoListDeposito = depositoListDeposito.getCodigoCuenta();
                depositoListDeposito.setCodigoCuenta(cuenta);
                depositoListDeposito = em.merge(depositoListDeposito);
                if (oldCodigoCuentaOfDepositoListDeposito != null) {
                    oldCodigoCuentaOfDepositoListDeposito.getDepositoList().remove(depositoListDeposito);
                    oldCodigoCuentaOfDepositoListDeposito = em.merge(oldCodigoCuentaOfDepositoListDeposito);
                }
            }
            for (Retiro retiroListRetiro : cuenta.getRetiroList()) {
                Cuenta oldCodigoCuentaOfRetiroListRetiro = retiroListRetiro.getCodigoCuenta();
                retiroListRetiro.setCodigoCuenta(cuenta);
                retiroListRetiro = em.merge(retiroListRetiro);
                if (oldCodigoCuentaOfRetiroListRetiro != null) {
                    oldCodigoCuentaOfRetiroListRetiro.getRetiroList().remove(retiroListRetiro);
                    oldCodigoCuentaOfRetiroListRetiro = em.merge(oldCodigoCuentaOfRetiroListRetiro);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cuenta cuenta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta persistentCuenta = em.find(Cuenta.class, cuenta.getCodigoCuenta());
            Cliente codigoClienteOld = persistentCuenta.getCodigoCliente();
            Cliente codigoClienteNew = cuenta.getCodigoCliente();
            Tipocuenta codigoTipoCuentaOld = persistentCuenta.getCodigoTipoCuenta();
            Tipocuenta codigoTipoCuentaNew = cuenta.getCodigoTipoCuenta();
            Estado codigoEstadoOld = persistentCuenta.getCodigoEstado();
            Estado codigoEstadoNew = cuenta.getCodigoEstado();
            List<Transferencia> transferenciaListOld = persistentCuenta.getTransferenciaList();
            List<Transferencia> transferenciaListNew = cuenta.getTransferenciaList();
            List<Transferencia> transferenciaList1Old = persistentCuenta.getTransferenciaList1();
            List<Transferencia> transferenciaList1New = cuenta.getTransferenciaList1();
            List<Deposito> depositoListOld = persistentCuenta.getDepositoList();
            List<Deposito> depositoListNew = cuenta.getDepositoList();
            List<Retiro> retiroListOld = persistentCuenta.getRetiroList();
            List<Retiro> retiroListNew = cuenta.getRetiroList();
            List<String> illegalOrphanMessages = null;
            for (Transferencia transferenciaListOldTransferencia : transferenciaListOld) {
                if (!transferenciaListNew.contains(transferenciaListOldTransferencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Transferencia " + transferenciaListOldTransferencia + " since its codigoCuentaOrigen field is not nullable.");
                }
            }
            for (Transferencia transferenciaList1OldTransferencia : transferenciaList1Old) {
                if (!transferenciaList1New.contains(transferenciaList1OldTransferencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Transferencia " + transferenciaList1OldTransferencia + " since its codigoCuentaDestino field is not nullable.");
                }
            }
            for (Deposito depositoListOldDeposito : depositoListOld) {
                if (!depositoListNew.contains(depositoListOldDeposito)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Deposito " + depositoListOldDeposito + " since its codigoCuenta field is not nullable.");
                }
            }
            for (Retiro retiroListOldRetiro : retiroListOld) {
                if (!retiroListNew.contains(retiroListOldRetiro)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Retiro " + retiroListOldRetiro + " since its codigoCuenta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codigoClienteNew != null) {
                codigoClienteNew = em.getReference(codigoClienteNew.getClass(), codigoClienteNew.getCodigoCliente());
                cuenta.setCodigoCliente(codigoClienteNew);
            }
            if (codigoTipoCuentaNew != null) {
                codigoTipoCuentaNew = em.getReference(codigoTipoCuentaNew.getClass(), codigoTipoCuentaNew.getCodigoTipoCuenta());
                cuenta.setCodigoTipoCuenta(codigoTipoCuentaNew);
            }
            if (codigoEstadoNew != null) {
                codigoEstadoNew = em.getReference(codigoEstadoNew.getClass(), codigoEstadoNew.getCodigoEstado());
                cuenta.setCodigoEstado(codigoEstadoNew);
            }
            List<Transferencia> attachedTransferenciaListNew = new ArrayList<Transferencia>();
            for (Transferencia transferenciaListNewTransferenciaToAttach : transferenciaListNew) {
                transferenciaListNewTransferenciaToAttach = em.getReference(transferenciaListNewTransferenciaToAttach.getClass(), transferenciaListNewTransferenciaToAttach.getCodigoTransferencia());
                attachedTransferenciaListNew.add(transferenciaListNewTransferenciaToAttach);
            }
            transferenciaListNew = attachedTransferenciaListNew;
            cuenta.setTransferenciaList(transferenciaListNew);
            List<Transferencia> attachedTransferenciaList1New = new ArrayList<Transferencia>();
            for (Transferencia transferenciaList1NewTransferenciaToAttach : transferenciaList1New) {
                transferenciaList1NewTransferenciaToAttach = em.getReference(transferenciaList1NewTransferenciaToAttach.getClass(), transferenciaList1NewTransferenciaToAttach.getCodigoTransferencia());
                attachedTransferenciaList1New.add(transferenciaList1NewTransferenciaToAttach);
            }
            transferenciaList1New = attachedTransferenciaList1New;
            cuenta.setTransferenciaList1(transferenciaList1New);
            List<Deposito> attachedDepositoListNew = new ArrayList<Deposito>();
            for (Deposito depositoListNewDepositoToAttach : depositoListNew) {
                depositoListNewDepositoToAttach = em.getReference(depositoListNewDepositoToAttach.getClass(), depositoListNewDepositoToAttach.getCodigoDeposito());
                attachedDepositoListNew.add(depositoListNewDepositoToAttach);
            }
            depositoListNew = attachedDepositoListNew;
            cuenta.setDepositoList(depositoListNew);
            List<Retiro> attachedRetiroListNew = new ArrayList<Retiro>();
            for (Retiro retiroListNewRetiroToAttach : retiroListNew) {
                retiroListNewRetiroToAttach = em.getReference(retiroListNewRetiroToAttach.getClass(), retiroListNewRetiroToAttach.getCodigoRetiro());
                attachedRetiroListNew.add(retiroListNewRetiroToAttach);
            }
            retiroListNew = attachedRetiroListNew;
            cuenta.setRetiroList(retiroListNew);
            cuenta = em.merge(cuenta);
            if (codigoClienteOld != null && !codigoClienteOld.equals(codigoClienteNew)) {
                codigoClienteOld.getCuentaList().remove(cuenta);
                codigoClienteOld = em.merge(codigoClienteOld);
            }
            if (codigoClienteNew != null && !codigoClienteNew.equals(codigoClienteOld)) {
                codigoClienteNew.getCuentaList().add(cuenta);
                codigoClienteNew = em.merge(codigoClienteNew);
            }
            if (codigoTipoCuentaOld != null && !codigoTipoCuentaOld.equals(codigoTipoCuentaNew)) {
                codigoTipoCuentaOld.getCuentaList().remove(cuenta);
                codigoTipoCuentaOld = em.merge(codigoTipoCuentaOld);
            }
            if (codigoTipoCuentaNew != null && !codigoTipoCuentaNew.equals(codigoTipoCuentaOld)) {
                codigoTipoCuentaNew.getCuentaList().add(cuenta);
                codigoTipoCuentaNew = em.merge(codigoTipoCuentaNew);
            }
            if (codigoEstadoOld != null && !codigoEstadoOld.equals(codigoEstadoNew)) {
                codigoEstadoOld.getCuentaList().remove(cuenta);
                codigoEstadoOld = em.merge(codigoEstadoOld);
            }
            if (codigoEstadoNew != null && !codigoEstadoNew.equals(codigoEstadoOld)) {
                codigoEstadoNew.getCuentaList().add(cuenta);
                codigoEstadoNew = em.merge(codigoEstadoNew);
            }
            for (Transferencia transferenciaListNewTransferencia : transferenciaListNew) {
                if (!transferenciaListOld.contains(transferenciaListNewTransferencia)) {
                    Cuenta oldCodigoCuentaOrigenOfTransferenciaListNewTransferencia = transferenciaListNewTransferencia.getCodigoCuentaOrigen();
                    transferenciaListNewTransferencia.setCodigoCuentaOrigen(cuenta);
                    transferenciaListNewTransferencia = em.merge(transferenciaListNewTransferencia);
                    if (oldCodigoCuentaOrigenOfTransferenciaListNewTransferencia != null && !oldCodigoCuentaOrigenOfTransferenciaListNewTransferencia.equals(cuenta)) {
                        oldCodigoCuentaOrigenOfTransferenciaListNewTransferencia.getTransferenciaList().remove(transferenciaListNewTransferencia);
                        oldCodigoCuentaOrigenOfTransferenciaListNewTransferencia = em.merge(oldCodigoCuentaOrigenOfTransferenciaListNewTransferencia);
                    }
                }
            }
            for (Transferencia transferenciaList1NewTransferencia : transferenciaList1New) {
                if (!transferenciaList1Old.contains(transferenciaList1NewTransferencia)) {
                    Cuenta oldCodigoCuentaDestinoOfTransferenciaList1NewTransferencia = transferenciaList1NewTransferencia.getCodigoCuentaDestino();
                    transferenciaList1NewTransferencia.setCodigoCuentaDestino(cuenta);
                    transferenciaList1NewTransferencia = em.merge(transferenciaList1NewTransferencia);
                    if (oldCodigoCuentaDestinoOfTransferenciaList1NewTransferencia != null && !oldCodigoCuentaDestinoOfTransferenciaList1NewTransferencia.equals(cuenta)) {
                        oldCodigoCuentaDestinoOfTransferenciaList1NewTransferencia.getTransferenciaList1().remove(transferenciaList1NewTransferencia);
                        oldCodigoCuentaDestinoOfTransferenciaList1NewTransferencia = em.merge(oldCodigoCuentaDestinoOfTransferenciaList1NewTransferencia);
                    }
                }
            }
            for (Deposito depositoListNewDeposito : depositoListNew) {
                if (!depositoListOld.contains(depositoListNewDeposito)) {
                    Cuenta oldCodigoCuentaOfDepositoListNewDeposito = depositoListNewDeposito.getCodigoCuenta();
                    depositoListNewDeposito.setCodigoCuenta(cuenta);
                    depositoListNewDeposito = em.merge(depositoListNewDeposito);
                    if (oldCodigoCuentaOfDepositoListNewDeposito != null && !oldCodigoCuentaOfDepositoListNewDeposito.equals(cuenta)) {
                        oldCodigoCuentaOfDepositoListNewDeposito.getDepositoList().remove(depositoListNewDeposito);
                        oldCodigoCuentaOfDepositoListNewDeposito = em.merge(oldCodigoCuentaOfDepositoListNewDeposito);
                    }
                }
            }
            for (Retiro retiroListNewRetiro : retiroListNew) {
                if (!retiroListOld.contains(retiroListNewRetiro)) {
                    Cuenta oldCodigoCuentaOfRetiroListNewRetiro = retiroListNewRetiro.getCodigoCuenta();
                    retiroListNewRetiro.setCodigoCuenta(cuenta);
                    retiroListNewRetiro = em.merge(retiroListNewRetiro);
                    if (oldCodigoCuentaOfRetiroListNewRetiro != null && !oldCodigoCuentaOfRetiroListNewRetiro.equals(cuenta)) {
                        oldCodigoCuentaOfRetiroListNewRetiro.getRetiroList().remove(retiroListNewRetiro);
                        oldCodigoCuentaOfRetiroListNewRetiro = em.merge(oldCodigoCuentaOfRetiroListNewRetiro);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cuenta.getCodigoCuenta();
                if (findCuenta(id) == null) {
                    throw new NonexistentEntityException("The cuenta with id " + id + " no longer exists.");
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
            Cuenta cuenta;
            try {
                cuenta = em.getReference(Cuenta.class, id);
                cuenta.getCodigoCuenta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuenta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Transferencia> transferenciaListOrphanCheck = cuenta.getTransferenciaList();
            for (Transferencia transferenciaListOrphanCheckTransferencia : transferenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuenta (" + cuenta + ") cannot be destroyed since the Transferencia " + transferenciaListOrphanCheckTransferencia + " in its transferenciaList field has a non-nullable codigoCuentaOrigen field.");
            }
            List<Transferencia> transferenciaList1OrphanCheck = cuenta.getTransferenciaList1();
            for (Transferencia transferenciaList1OrphanCheckTransferencia : transferenciaList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuenta (" + cuenta + ") cannot be destroyed since the Transferencia " + transferenciaList1OrphanCheckTransferencia + " in its transferenciaList1 field has a non-nullable codigoCuentaDestino field.");
            }
            List<Deposito> depositoListOrphanCheck = cuenta.getDepositoList();
            for (Deposito depositoListOrphanCheckDeposito : depositoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuenta (" + cuenta + ") cannot be destroyed since the Deposito " + depositoListOrphanCheckDeposito + " in its depositoList field has a non-nullable codigoCuenta field.");
            }
            List<Retiro> retiroListOrphanCheck = cuenta.getRetiroList();
            for (Retiro retiroListOrphanCheckRetiro : retiroListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuenta (" + cuenta + ") cannot be destroyed since the Retiro " + retiroListOrphanCheckRetiro + " in its retiroList field has a non-nullable codigoCuenta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cliente codigoCliente = cuenta.getCodigoCliente();
            if (codigoCliente != null) {
                codigoCliente.getCuentaList().remove(cuenta);
                codigoCliente = em.merge(codigoCliente);
            }
            Tipocuenta codigoTipoCuenta = cuenta.getCodigoTipoCuenta();
            if (codigoTipoCuenta != null) {
                codigoTipoCuenta.getCuentaList().remove(cuenta);
                codigoTipoCuenta = em.merge(codigoTipoCuenta);
            }
            Estado codigoEstado = cuenta.getCodigoEstado();
            if (codigoEstado != null) {
                codigoEstado.getCuentaList().remove(cuenta);
                codigoEstado = em.merge(codigoEstado);
            }
            em.remove(cuenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cuenta> findCuentaEntities() {
        return findCuentaEntities(true, -1, -1);
    }

    public List<Cuenta> findCuentaEntities(int maxResults, int firstResult) {
        return findCuentaEntities(false, maxResults, firstResult);
    }

    private List<Cuenta> findCuentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cuenta.class));
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

    public Cuenta findCuenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cuenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cuenta> rt = cq.from(Cuenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Object[]> listarCuentas() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Cuenta.listar");
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Object[]> listarCuentasPorCodigo(int codigocuentas) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Cuenta.listarporcodigo");
            q.setParameter("codigoCuenta", codigocuentas);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public boolean insertarCuentas(Cuenta cuentas) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cuentas);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean editarCuenta(int codigocuenta, Cuenta cuentaActualizado) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Cuenta cuentaExistente = em.find(Cuenta.class, codigocuenta);
            if (cuentaExistente != null) {
                cuentaExistente.setCodigoCliente(cuentaActualizado.getCodigoCliente());
                cuentaExistente.setNumeCuenta(cuentaActualizado.getNumeCuenta());
                cuentaExistente.setCodigoTipoCuenta(cuentaActualizado.getCodigoTipoCuenta());
                cuentaExistente.setSaldoCuenta(cuentaActualizado.getSaldoCuenta());
                cuentaExistente.setCodigoEstado(cuentaActualizado.getCodigoEstado());
                em.merge(cuentaExistente);

                transaction.commit();
                return true;
            } else {

                transaction.rollback();
                return false;
            }
        } catch (Exception ex) {
            System.out.println("error" + ex);

            return false;
        } finally {
            em.close();
        }

    }

    public boolean eliminarLogico(int codigocuenta, Estado estadoCuenta) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Cuenta cuenta = em.find(Cuenta.class, codigocuenta);

            if (cuenta != null) {

                cuenta.setCodigoEstado(estadoCuenta);
                em.merge(cuenta);
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

    public Cuenta obtenerCodigoCuenta(String numeroCuenta) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNamedQuery("Cuenta.findByNumeCuenta");
            query.setParameter("numeCuenta", numeroCuenta);

            Cuenta c = (Cuenta) query.getSingleResult();
            return c;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean retirarDinero(String numeroCuenta, double monto) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Cuenta.findByNumeCuenta");
            q.setParameter("numeCuenta", numeroCuenta);
            Cuenta c = (Cuenta) q.getSingleResult();

            if (c != null) {
                em.getTransaction().begin();
                double descontarSaldo = c.getSaldoCuenta();
                if (descontarSaldo >= monto) {
                    descontarSaldo = c.getSaldoCuenta() - monto;
                } else {
                    return false;
                }
                c.setSaldoCuenta(descontarSaldo);
                em.getTransaction().commit();
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean ingresarDinero(String numeroCuenta, double monto) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Cuenta.findByNumeCuenta");
            q.setParameter("numeCuenta", numeroCuenta);
            Cuenta c = (Cuenta) q.getSingleResult();

            if (c != null) {
                em.getTransaction().begin();
                double aumentarSaldo = c.getSaldoCuenta() + monto;
                c.setSaldoCuenta(aumentarSaldo);
                em.getTransaction().commit();
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
