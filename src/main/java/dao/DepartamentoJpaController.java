/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dto.Departamento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.Provincia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jeff
 */
public class DepartamentoJpaController implements Serializable {

    public DepartamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Departamento departamento) {
        if (departamento.getProvinciaList() == null) {
            departamento.setProvinciaList(new ArrayList<Provincia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Provincia> attachedProvinciaList = new ArrayList<Provincia>();
            for (Provincia provinciaListProvinciaToAttach : departamento.getProvinciaList()) {
                provinciaListProvinciaToAttach = em.getReference(provinciaListProvinciaToAttach.getClass(), provinciaListProvinciaToAttach.getIdProvincia());
                attachedProvinciaList.add(provinciaListProvinciaToAttach);
            }
            departamento.setProvinciaList(attachedProvinciaList);
            em.persist(departamento);
            for (Provincia provinciaListProvincia : departamento.getProvinciaList()) {
                Departamento oldIdDepartamentoOfProvinciaListProvincia = provinciaListProvincia.getIdDepartamento();
                provinciaListProvincia.setIdDepartamento(departamento);
                provinciaListProvincia = em.merge(provinciaListProvincia);
                if (oldIdDepartamentoOfProvinciaListProvincia != null) {
                    oldIdDepartamentoOfProvinciaListProvincia.getProvinciaList().remove(provinciaListProvincia);
                    oldIdDepartamentoOfProvinciaListProvincia = em.merge(oldIdDepartamentoOfProvinciaListProvincia);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Departamento departamento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento persistentDepartamento = em.find(Departamento.class, departamento.getIdDepartamento());
            List<Provincia> provinciaListOld = persistentDepartamento.getProvinciaList();
            List<Provincia> provinciaListNew = departamento.getProvinciaList();
            List<String> illegalOrphanMessages = null;
            for (Provincia provinciaListOldProvincia : provinciaListOld) {
                if (!provinciaListNew.contains(provinciaListOldProvincia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Provincia " + provinciaListOldProvincia + " since its idDepartamento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Provincia> attachedProvinciaListNew = new ArrayList<Provincia>();
            for (Provincia provinciaListNewProvinciaToAttach : provinciaListNew) {
                provinciaListNewProvinciaToAttach = em.getReference(provinciaListNewProvinciaToAttach.getClass(), provinciaListNewProvinciaToAttach.getIdProvincia());
                attachedProvinciaListNew.add(provinciaListNewProvinciaToAttach);
            }
            provinciaListNew = attachedProvinciaListNew;
            departamento.setProvinciaList(provinciaListNew);
            departamento = em.merge(departamento);
            for (Provincia provinciaListNewProvincia : provinciaListNew) {
                if (!provinciaListOld.contains(provinciaListNewProvincia)) {
                    Departamento oldIdDepartamentoOfProvinciaListNewProvincia = provinciaListNewProvincia.getIdDepartamento();
                    provinciaListNewProvincia.setIdDepartamento(departamento);
                    provinciaListNewProvincia = em.merge(provinciaListNewProvincia);
                    if (oldIdDepartamentoOfProvinciaListNewProvincia != null && !oldIdDepartamentoOfProvinciaListNewProvincia.equals(departamento)) {
                        oldIdDepartamentoOfProvinciaListNewProvincia.getProvinciaList().remove(provinciaListNewProvincia);
                        oldIdDepartamentoOfProvinciaListNewProvincia = em.merge(oldIdDepartamentoOfProvinciaListNewProvincia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = departamento.getIdDepartamento();
                if (findDepartamento(id) == null) {
                    throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.");
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
            Departamento departamento;
            try {
                departamento = em.getReference(Departamento.class, id);
                departamento.getIdDepartamento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Provincia> provinciaListOrphanCheck = departamento.getProvinciaList();
            for (Provincia provinciaListOrphanCheckProvincia : provinciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamento (" + departamento + ") cannot be destroyed since the Provincia " + provinciaListOrphanCheckProvincia + " in its provinciaList field has a non-nullable idDepartamento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(departamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Departamento> findDepartamentoEntities() {
        return findDepartamentoEntities(true, -1, -1);
    }

    public List<Departamento> findDepartamentoEntities(int maxResults, int firstResult) {
        return findDepartamentoEntities(false, maxResults, firstResult);
    }

    private List<Departamento> findDepartamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Departamento.class));
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

    public Departamento findDepartamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Departamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getDepartamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Departamento> rt = cq.from(Departamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
