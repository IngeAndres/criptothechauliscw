package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dto.Cuenta;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.Usuario;
import dto.Tipocuenta;
import dto.Operacionesotrascuentas;
import java.util.ArrayList;
import java.util.List;
import dto.Operacionescuentaspropias;
import dto.Prestamo;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
        if (cuenta.getOperacionesotrascuentasList() == null) {
            cuenta.setOperacionesotrascuentasList(new ArrayList<Operacionesotrascuentas>());
        }
        if (cuenta.getOperacionesotrascuentasList1() == null) {
            cuenta.setOperacionesotrascuentasList1(new ArrayList<Operacionesotrascuentas>());
        }
        if (cuenta.getOperacionescuentaspropiasList() == null) {
            cuenta.setOperacionescuentaspropiasList(new ArrayList<Operacionescuentaspropias>());
        }
        if (cuenta.getPrestamoList() == null) {
            cuenta.setPrestamoList(new ArrayList<Prestamo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idUsuario = cuenta.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                cuenta.setIdUsuario(idUsuario);
            }
            Tipocuenta idTipoCuenta = cuenta.getIdTipoCuenta();
            if (idTipoCuenta != null) {
                idTipoCuenta = em.getReference(idTipoCuenta.getClass(), idTipoCuenta.getIdTipoCuenta());
                cuenta.setIdTipoCuenta(idTipoCuenta);
            }
            List<Operacionesotrascuentas> attachedOperacionesotrascuentasList = new ArrayList<Operacionesotrascuentas>();
            for (Operacionesotrascuentas operacionesotrascuentasListOperacionesotrascuentasToAttach : cuenta.getOperacionesotrascuentasList()) {
                operacionesotrascuentasListOperacionesotrascuentasToAttach = em.getReference(operacionesotrascuentasListOperacionesotrascuentasToAttach.getClass(), operacionesotrascuentasListOperacionesotrascuentasToAttach.getIdOperacionOC());
                attachedOperacionesotrascuentasList.add(operacionesotrascuentasListOperacionesotrascuentasToAttach);
            }
            cuenta.setOperacionesotrascuentasList(attachedOperacionesotrascuentasList);
            List<Operacionesotrascuentas> attachedOperacionesotrascuentasList1 = new ArrayList<Operacionesotrascuentas>();
            for (Operacionesotrascuentas operacionesotrascuentasList1OperacionesotrascuentasToAttach : cuenta.getOperacionesotrascuentasList1()) {
                operacionesotrascuentasList1OperacionesotrascuentasToAttach = em.getReference(operacionesotrascuentasList1OperacionesotrascuentasToAttach.getClass(), operacionesotrascuentasList1OperacionesotrascuentasToAttach.getIdOperacionOC());
                attachedOperacionesotrascuentasList1.add(operacionesotrascuentasList1OperacionesotrascuentasToAttach);
            }
            cuenta.setOperacionesotrascuentasList1(attachedOperacionesotrascuentasList1);
            List<Operacionescuentaspropias> attachedOperacionescuentaspropiasList = new ArrayList<Operacionescuentaspropias>();
            for (Operacionescuentaspropias operacionescuentaspropiasListOperacionescuentaspropiasToAttach : cuenta.getOperacionescuentaspropiasList()) {
                operacionescuentaspropiasListOperacionescuentaspropiasToAttach = em.getReference(operacionescuentaspropiasListOperacionescuentaspropiasToAttach.getClass(), operacionescuentaspropiasListOperacionescuentaspropiasToAttach.getIdOperacionCP());
                attachedOperacionescuentaspropiasList.add(operacionescuentaspropiasListOperacionescuentaspropiasToAttach);
            }
            cuenta.setOperacionescuentaspropiasList(attachedOperacionescuentaspropiasList);
            List<Prestamo> attachedPrestamoList = new ArrayList<Prestamo>();
            for (Prestamo prestamoListPrestamoToAttach : cuenta.getPrestamoList()) {
                prestamoListPrestamoToAttach = em.getReference(prestamoListPrestamoToAttach.getClass(), prestamoListPrestamoToAttach.getIdPrestamo());
                attachedPrestamoList.add(prestamoListPrestamoToAttach);
            }
            cuenta.setPrestamoList(attachedPrestamoList);
            em.persist(cuenta);
            if (idUsuario != null) {
                idUsuario.getCuentaList().add(cuenta);
                idUsuario = em.merge(idUsuario);
            }
            if (idTipoCuenta != null) {
                idTipoCuenta.getCuentaList().add(cuenta);
                idTipoCuenta = em.merge(idTipoCuenta);
            }
            for (Operacionesotrascuentas operacionesotrascuentasListOperacionesotrascuentas : cuenta.getOperacionesotrascuentasList()) {
                Cuenta oldIdCuentaOrigenOfOperacionesotrascuentasListOperacionesotrascuentas = operacionesotrascuentasListOperacionesotrascuentas.getIdCuentaOrigen();
                operacionesotrascuentasListOperacionesotrascuentas.setIdCuentaOrigen(cuenta);
                operacionesotrascuentasListOperacionesotrascuentas = em.merge(operacionesotrascuentasListOperacionesotrascuentas);
                if (oldIdCuentaOrigenOfOperacionesotrascuentasListOperacionesotrascuentas != null) {
                    oldIdCuentaOrigenOfOperacionesotrascuentasListOperacionesotrascuentas.getOperacionesotrascuentasList().remove(operacionesotrascuentasListOperacionesotrascuentas);
                    oldIdCuentaOrigenOfOperacionesotrascuentasListOperacionesotrascuentas = em.merge(oldIdCuentaOrigenOfOperacionesotrascuentasListOperacionesotrascuentas);
                }
            }
            for (Operacionesotrascuentas operacionesotrascuentasList1Operacionesotrascuentas : cuenta.getOperacionesotrascuentasList1()) {
                Cuenta oldIdCuentaDestinoOfOperacionesotrascuentasList1Operacionesotrascuentas = operacionesotrascuentasList1Operacionesotrascuentas.getIdCuentaDestino();
                operacionesotrascuentasList1Operacionesotrascuentas.setIdCuentaDestino(cuenta);
                operacionesotrascuentasList1Operacionesotrascuentas = em.merge(operacionesotrascuentasList1Operacionesotrascuentas);
                if (oldIdCuentaDestinoOfOperacionesotrascuentasList1Operacionesotrascuentas != null) {
                    oldIdCuentaDestinoOfOperacionesotrascuentasList1Operacionesotrascuentas.getOperacionesotrascuentasList1().remove(operacionesotrascuentasList1Operacionesotrascuentas);
                    oldIdCuentaDestinoOfOperacionesotrascuentasList1Operacionesotrascuentas = em.merge(oldIdCuentaDestinoOfOperacionesotrascuentasList1Operacionesotrascuentas);
                }
            }
            for (Operacionescuentaspropias operacionescuentaspropiasListOperacionescuentaspropias : cuenta.getOperacionescuentaspropiasList()) {
                Cuenta oldIdCuentaOfOperacionescuentaspropiasListOperacionescuentaspropias = operacionescuentaspropiasListOperacionescuentaspropias.getIdCuenta();
                operacionescuentaspropiasListOperacionescuentaspropias.setIdCuenta(cuenta);
                operacionescuentaspropiasListOperacionescuentaspropias = em.merge(operacionescuentaspropiasListOperacionescuentaspropias);
                if (oldIdCuentaOfOperacionescuentaspropiasListOperacionescuentaspropias != null) {
                    oldIdCuentaOfOperacionescuentaspropiasListOperacionescuentaspropias.getOperacionescuentaspropiasList().remove(operacionescuentaspropiasListOperacionescuentaspropias);
                    oldIdCuentaOfOperacionescuentaspropiasListOperacionescuentaspropias = em.merge(oldIdCuentaOfOperacionescuentaspropiasListOperacionescuentaspropias);
                }
            }
            for (Prestamo prestamoListPrestamo : cuenta.getPrestamoList()) {
                Cuenta oldIdCuentaOfPrestamoListPrestamo = prestamoListPrestamo.getIdCuenta();
                prestamoListPrestamo.setIdCuenta(cuenta);
                prestamoListPrestamo = em.merge(prestamoListPrestamo);
                if (oldIdCuentaOfPrestamoListPrestamo != null) {
                    oldIdCuentaOfPrestamoListPrestamo.getPrestamoList().remove(prestamoListPrestamo);
                    oldIdCuentaOfPrestamoListPrestamo = em.merge(oldIdCuentaOfPrestamoListPrestamo);
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
            Cuenta persistentCuenta = em.find(Cuenta.class, cuenta.getIdCuenta());
            Usuario idUsuarioOld = persistentCuenta.getIdUsuario();
            Usuario idUsuarioNew = cuenta.getIdUsuario();
            Tipocuenta idTipoCuentaOld = persistentCuenta.getIdTipoCuenta();
            Tipocuenta idTipoCuentaNew = cuenta.getIdTipoCuenta();
            List<Operacionesotrascuentas> operacionesotrascuentasListOld = persistentCuenta.getOperacionesotrascuentasList();
            List<Operacionesotrascuentas> operacionesotrascuentasListNew = cuenta.getOperacionesotrascuentasList();
            List<Operacionesotrascuentas> operacionesotrascuentasList1Old = persistentCuenta.getOperacionesotrascuentasList1();
            List<Operacionesotrascuentas> operacionesotrascuentasList1New = cuenta.getOperacionesotrascuentasList1();
            List<Operacionescuentaspropias> operacionescuentaspropiasListOld = persistentCuenta.getOperacionescuentaspropiasList();
            List<Operacionescuentaspropias> operacionescuentaspropiasListNew = cuenta.getOperacionescuentaspropiasList();
            List<Prestamo> prestamoListOld = persistentCuenta.getPrestamoList();
            List<Prestamo> prestamoListNew = cuenta.getPrestamoList();
            List<String> illegalOrphanMessages = null;
            for (Operacionesotrascuentas operacionesotrascuentasListOldOperacionesotrascuentas : operacionesotrascuentasListOld) {
                if (!operacionesotrascuentasListNew.contains(operacionesotrascuentasListOldOperacionesotrascuentas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Operacionesotrascuentas " + operacionesotrascuentasListOldOperacionesotrascuentas + " since its idCuentaOrigen field is not nullable.");
                }
            }
            for (Operacionesotrascuentas operacionesotrascuentasList1OldOperacionesotrascuentas : operacionesotrascuentasList1Old) {
                if (!operacionesotrascuentasList1New.contains(operacionesotrascuentasList1OldOperacionesotrascuentas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Operacionesotrascuentas " + operacionesotrascuentasList1OldOperacionesotrascuentas + " since its idCuentaDestino field is not nullable.");
                }
            }
            for (Operacionescuentaspropias operacionescuentaspropiasListOldOperacionescuentaspropias : operacionescuentaspropiasListOld) {
                if (!operacionescuentaspropiasListNew.contains(operacionescuentaspropiasListOldOperacionescuentaspropias)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Operacionescuentaspropias " + operacionescuentaspropiasListOldOperacionescuentaspropias + " since its idCuenta field is not nullable.");
                }
            }
            for (Prestamo prestamoListOldPrestamo : prestamoListOld) {
                if (!prestamoListNew.contains(prestamoListOldPrestamo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Prestamo " + prestamoListOldPrestamo + " since its idCuenta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                cuenta.setIdUsuario(idUsuarioNew);
            }
            if (idTipoCuentaNew != null) {
                idTipoCuentaNew = em.getReference(idTipoCuentaNew.getClass(), idTipoCuentaNew.getIdTipoCuenta());
                cuenta.setIdTipoCuenta(idTipoCuentaNew);
            }
            List<Operacionesotrascuentas> attachedOperacionesotrascuentasListNew = new ArrayList<Operacionesotrascuentas>();
            for (Operacionesotrascuentas operacionesotrascuentasListNewOperacionesotrascuentasToAttach : operacionesotrascuentasListNew) {
                operacionesotrascuentasListNewOperacionesotrascuentasToAttach = em.getReference(operacionesotrascuentasListNewOperacionesotrascuentasToAttach.getClass(), operacionesotrascuentasListNewOperacionesotrascuentasToAttach.getIdOperacionOC());
                attachedOperacionesotrascuentasListNew.add(operacionesotrascuentasListNewOperacionesotrascuentasToAttach);
            }
            operacionesotrascuentasListNew = attachedOperacionesotrascuentasListNew;
            cuenta.setOperacionesotrascuentasList(operacionesotrascuentasListNew);
            List<Operacionesotrascuentas> attachedOperacionesotrascuentasList1New = new ArrayList<Operacionesotrascuentas>();
            for (Operacionesotrascuentas operacionesotrascuentasList1NewOperacionesotrascuentasToAttach : operacionesotrascuentasList1New) {
                operacionesotrascuentasList1NewOperacionesotrascuentasToAttach = em.getReference(operacionesotrascuentasList1NewOperacionesotrascuentasToAttach.getClass(), operacionesotrascuentasList1NewOperacionesotrascuentasToAttach.getIdOperacionOC());
                attachedOperacionesotrascuentasList1New.add(operacionesotrascuentasList1NewOperacionesotrascuentasToAttach);
            }
            operacionesotrascuentasList1New = attachedOperacionesotrascuentasList1New;
            cuenta.setOperacionesotrascuentasList1(operacionesotrascuentasList1New);
            List<Operacionescuentaspropias> attachedOperacionescuentaspropiasListNew = new ArrayList<Operacionescuentaspropias>();
            for (Operacionescuentaspropias operacionescuentaspropiasListNewOperacionescuentaspropiasToAttach : operacionescuentaspropiasListNew) {
                operacionescuentaspropiasListNewOperacionescuentaspropiasToAttach = em.getReference(operacionescuentaspropiasListNewOperacionescuentaspropiasToAttach.getClass(), operacionescuentaspropiasListNewOperacionescuentaspropiasToAttach.getIdOperacionCP());
                attachedOperacionescuentaspropiasListNew.add(operacionescuentaspropiasListNewOperacionescuentaspropiasToAttach);
            }
            operacionescuentaspropiasListNew = attachedOperacionescuentaspropiasListNew;
            cuenta.setOperacionescuentaspropiasList(operacionescuentaspropiasListNew);
            List<Prestamo> attachedPrestamoListNew = new ArrayList<Prestamo>();
            for (Prestamo prestamoListNewPrestamoToAttach : prestamoListNew) {
                prestamoListNewPrestamoToAttach = em.getReference(prestamoListNewPrestamoToAttach.getClass(), prestamoListNewPrestamoToAttach.getIdPrestamo());
                attachedPrestamoListNew.add(prestamoListNewPrestamoToAttach);
            }
            prestamoListNew = attachedPrestamoListNew;
            cuenta.setPrestamoList(prestamoListNew);
            cuenta = em.merge(cuenta);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getCuentaList().remove(cuenta);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getCuentaList().add(cuenta);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            if (idTipoCuentaOld != null && !idTipoCuentaOld.equals(idTipoCuentaNew)) {
                idTipoCuentaOld.getCuentaList().remove(cuenta);
                idTipoCuentaOld = em.merge(idTipoCuentaOld);
            }
            if (idTipoCuentaNew != null && !idTipoCuentaNew.equals(idTipoCuentaOld)) {
                idTipoCuentaNew.getCuentaList().add(cuenta);
                idTipoCuentaNew = em.merge(idTipoCuentaNew);
            }
            for (Operacionesotrascuentas operacionesotrascuentasListNewOperacionesotrascuentas : operacionesotrascuentasListNew) {
                if (!operacionesotrascuentasListOld.contains(operacionesotrascuentasListNewOperacionesotrascuentas)) {
                    Cuenta oldIdCuentaOrigenOfOperacionesotrascuentasListNewOperacionesotrascuentas = operacionesotrascuentasListNewOperacionesotrascuentas.getIdCuentaOrigen();
                    operacionesotrascuentasListNewOperacionesotrascuentas.setIdCuentaOrigen(cuenta);
                    operacionesotrascuentasListNewOperacionesotrascuentas = em.merge(operacionesotrascuentasListNewOperacionesotrascuentas);
                    if (oldIdCuentaOrigenOfOperacionesotrascuentasListNewOperacionesotrascuentas != null && !oldIdCuentaOrigenOfOperacionesotrascuentasListNewOperacionesotrascuentas.equals(cuenta)) {
                        oldIdCuentaOrigenOfOperacionesotrascuentasListNewOperacionesotrascuentas.getOperacionesotrascuentasList().remove(operacionesotrascuentasListNewOperacionesotrascuentas);
                        oldIdCuentaOrigenOfOperacionesotrascuentasListNewOperacionesotrascuentas = em.merge(oldIdCuentaOrigenOfOperacionesotrascuentasListNewOperacionesotrascuentas);
                    }
                }
            }
            for (Operacionesotrascuentas operacionesotrascuentasList1NewOperacionesotrascuentas : operacionesotrascuentasList1New) {
                if (!operacionesotrascuentasList1Old.contains(operacionesotrascuentasList1NewOperacionesotrascuentas)) {
                    Cuenta oldIdCuentaDestinoOfOperacionesotrascuentasList1NewOperacionesotrascuentas = operacionesotrascuentasList1NewOperacionesotrascuentas.getIdCuentaDestino();
                    operacionesotrascuentasList1NewOperacionesotrascuentas.setIdCuentaDestino(cuenta);
                    operacionesotrascuentasList1NewOperacionesotrascuentas = em.merge(operacionesotrascuentasList1NewOperacionesotrascuentas);
                    if (oldIdCuentaDestinoOfOperacionesotrascuentasList1NewOperacionesotrascuentas != null && !oldIdCuentaDestinoOfOperacionesotrascuentasList1NewOperacionesotrascuentas.equals(cuenta)) {
                        oldIdCuentaDestinoOfOperacionesotrascuentasList1NewOperacionesotrascuentas.getOperacionesotrascuentasList1().remove(operacionesotrascuentasList1NewOperacionesotrascuentas);
                        oldIdCuentaDestinoOfOperacionesotrascuentasList1NewOperacionesotrascuentas = em.merge(oldIdCuentaDestinoOfOperacionesotrascuentasList1NewOperacionesotrascuentas);
                    }
                }
            }
            for (Operacionescuentaspropias operacionescuentaspropiasListNewOperacionescuentaspropias : operacionescuentaspropiasListNew) {
                if (!operacionescuentaspropiasListOld.contains(operacionescuentaspropiasListNewOperacionescuentaspropias)) {
                    Cuenta oldIdCuentaOfOperacionescuentaspropiasListNewOperacionescuentaspropias = operacionescuentaspropiasListNewOperacionescuentaspropias.getIdCuenta();
                    operacionescuentaspropiasListNewOperacionescuentaspropias.setIdCuenta(cuenta);
                    operacionescuentaspropiasListNewOperacionescuentaspropias = em.merge(operacionescuentaspropiasListNewOperacionescuentaspropias);
                    if (oldIdCuentaOfOperacionescuentaspropiasListNewOperacionescuentaspropias != null && !oldIdCuentaOfOperacionescuentaspropiasListNewOperacionescuentaspropias.equals(cuenta)) {
                        oldIdCuentaOfOperacionescuentaspropiasListNewOperacionescuentaspropias.getOperacionescuentaspropiasList().remove(operacionescuentaspropiasListNewOperacionescuentaspropias);
                        oldIdCuentaOfOperacionescuentaspropiasListNewOperacionescuentaspropias = em.merge(oldIdCuentaOfOperacionescuentaspropiasListNewOperacionescuentaspropias);
                    }
                }
            }
            for (Prestamo prestamoListNewPrestamo : prestamoListNew) {
                if (!prestamoListOld.contains(prestamoListNewPrestamo)) {
                    Cuenta oldIdCuentaOfPrestamoListNewPrestamo = prestamoListNewPrestamo.getIdCuenta();
                    prestamoListNewPrestamo.setIdCuenta(cuenta);
                    prestamoListNewPrestamo = em.merge(prestamoListNewPrestamo);
                    if (oldIdCuentaOfPrestamoListNewPrestamo != null && !oldIdCuentaOfPrestamoListNewPrestamo.equals(cuenta)) {
                        oldIdCuentaOfPrestamoListNewPrestamo.getPrestamoList().remove(prestamoListNewPrestamo);
                        oldIdCuentaOfPrestamoListNewPrestamo = em.merge(oldIdCuentaOfPrestamoListNewPrestamo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cuenta.getIdCuenta();
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
                cuenta.getIdCuenta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuenta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Operacionesotrascuentas> operacionesotrascuentasListOrphanCheck = cuenta.getOperacionesotrascuentasList();
            for (Operacionesotrascuentas operacionesotrascuentasListOrphanCheckOperacionesotrascuentas : operacionesotrascuentasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuenta (" + cuenta + ") cannot be destroyed since the Operacionesotrascuentas " + operacionesotrascuentasListOrphanCheckOperacionesotrascuentas + " in its operacionesotrascuentasList field has a non-nullable idCuentaOrigen field.");
            }
            List<Operacionesotrascuentas> operacionesotrascuentasList1OrphanCheck = cuenta.getOperacionesotrascuentasList1();
            for (Operacionesotrascuentas operacionesotrascuentasList1OrphanCheckOperacionesotrascuentas : operacionesotrascuentasList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuenta (" + cuenta + ") cannot be destroyed since the Operacionesotrascuentas " + operacionesotrascuentasList1OrphanCheckOperacionesotrascuentas + " in its operacionesotrascuentasList1 field has a non-nullable idCuentaDestino field.");
            }
            List<Operacionescuentaspropias> operacionescuentaspropiasListOrphanCheck = cuenta.getOperacionescuentaspropiasList();
            for (Operacionescuentaspropias operacionescuentaspropiasListOrphanCheckOperacionescuentaspropias : operacionescuentaspropiasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuenta (" + cuenta + ") cannot be destroyed since the Operacionescuentaspropias " + operacionescuentaspropiasListOrphanCheckOperacionescuentaspropias + " in its operacionescuentaspropiasList field has a non-nullable idCuenta field.");
            }
            List<Prestamo> prestamoListOrphanCheck = cuenta.getPrestamoList();
            for (Prestamo prestamoListOrphanCheckPrestamo : prestamoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuenta (" + cuenta + ") cannot be destroyed since the Prestamo " + prestamoListOrphanCheckPrestamo + " in its prestamoList field has a non-nullable idCuenta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario idUsuario = cuenta.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getCuentaList().remove(cuenta);
                idUsuario = em.merge(idUsuario);
            }
            Tipocuenta idTipoCuenta = cuenta.getIdTipoCuenta();
            if (idTipoCuenta != null) {
                idTipoCuenta.getCuentaList().remove(cuenta);
                idTipoCuenta = em.merge(idTipoCuenta);
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

    public List<Object[]> listarCuentasPorId(int codigocuentas) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Cuenta.listarporcodigo");
            q.setParameter("codigoCuenta", codigocuentas);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    public Cuenta getCodCuenta(String numeroCuenta) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Cuenta.findByNumbCuenta");
            q.setParameter("numbCuenta", numeroCuenta);

            Cuenta c = (Cuenta) q.getSingleResult();
            return c;
        } catch (Exception e) {
            return null;
        }
    }
    
    public boolean retirarDinero(String numeroCuenta, double monto) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Cuenta.findByNumbCuenta");
            q.setParameter("numbCuenta", numeroCuenta);
            Cuenta c = (Cuenta) q.getSingleResult();

            if (c != null) {
                em.getTransaction().begin();
                double descontarSaldo = c.getSaldoDisponible();
                if (descontarSaldo >= monto) {
                    descontarSaldo = c.getSaldoDisponible() - monto;
                } else {
                    return false;
                }
                c.setSaldoDisponible(descontarSaldo);
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
            Query q = em.createNamedQuery("Cuenta.findByNumbCuenta");
            q.setParameter("numbCuenta", numeroCuenta);
            Cuenta c = (Cuenta) q.getSingleResult();

            if (c != null) {
                em.getTransaction().begin();
                double aumentarSaldo = c.getSaldoDisponible() + monto;
                c.setSaldoDisponible(aumentarSaldo);
                em.getTransaction().commit();
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
