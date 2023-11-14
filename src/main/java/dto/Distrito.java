/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ing. Andres Gomez
 */
@Entity
@Table(name = "distrito")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Distrito.findAll", query = "SELECT d FROM Distrito d"),
    @NamedQuery(name = "Distrito.findByCodigoDistrito", query = "SELECT d FROM Distrito d WHERE d.codigoDistrito = :codigoDistrito"),
    @NamedQuery(name = "Distrito.findByNombreDistrito", query = "SELECT d FROM Distrito d WHERE d.nombreDistrito = :nombreDistrito")})
public class Distrito implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoDistrito")
    private Integer codigoDistrito;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombreDistrito")
    private String nombreDistrito;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoDistrito")
    private List<Cliente> clienteList;

    public Distrito() {
    }

    public Distrito(Integer codigoDistrito) {
        this.codigoDistrito = codigoDistrito;
    }

    public Distrito(Integer codigoDistrito, String nombreDistrito) {
        this.codigoDistrito = codigoDistrito;
        this.nombreDistrito = nombreDistrito;
    }

    public Integer getCodigoDistrito() {
        return codigoDistrito;
    }

    public void setCodigoDistrito(Integer codigoDistrito) {
        this.codigoDistrito = codigoDistrito;
    }

    public String getNombreDistrito() {
        return nombreDistrito;
    }

    public void setNombreDistrito(String nombreDistrito) {
        this.nombreDistrito = nombreDistrito;
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoDistrito != null ? codigoDistrito.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Distrito)) {
            return false;
        }
        Distrito other = (Distrito) object;
        if ((this.codigoDistrito == null && other.codigoDistrito != null) || (this.codigoDistrito != null && !this.codigoDistrito.equals(other.codigoDistrito))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.Distrito[ codigoDistrito=" + codigoDistrito + " ]";
    }
    
}
