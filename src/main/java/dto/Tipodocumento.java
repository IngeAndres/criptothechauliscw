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
@Table(name = "tipodocumento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipodocumento.findAll", query = "SELECT t FROM Tipodocumento t"),
    @NamedQuery(name = "Tipodocumento.findByCodigoTipoDocumento", query = "SELECT t FROM Tipodocumento t WHERE t.codigoTipoDocumento = :codigoTipoDocumento"),
    @NamedQuery(name = "Tipodocumento.findByNombTipoDocumento", query = "SELECT t FROM Tipodocumento t WHERE t.nombTipoDocumento = :nombTipoDocumento")})
public class Tipodocumento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoTipoDocumento")
    private Integer codigoTipoDocumento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombTipoDocumento")
    private String nombTipoDocumento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoTipoDocumento")
    private List<Cliente> clienteList;

    public Tipodocumento() {
    }

    public Tipodocumento(Integer codigoTipoDocumento) {
        this.codigoTipoDocumento = codigoTipoDocumento;
    }

    public Tipodocumento(Integer codigoTipoDocumento, String nombTipoDocumento) {
        this.codigoTipoDocumento = codigoTipoDocumento;
        this.nombTipoDocumento = nombTipoDocumento;
    }

    public Integer getCodigoTipoDocumento() {
        return codigoTipoDocumento;
    }

    public void setCodigoTipoDocumento(Integer codigoTipoDocumento) {
        this.codigoTipoDocumento = codigoTipoDocumento;
    }

    public String getNombTipoDocumento() {
        return nombTipoDocumento;
    }

    public void setNombTipoDocumento(String nombTipoDocumento) {
        this.nombTipoDocumento = nombTipoDocumento;
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
        hash += (codigoTipoDocumento != null ? codigoTipoDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipodocumento)) {
            return false;
        }
        Tipodocumento other = (Tipodocumento) object;
        if ((this.codigoTipoDocumento == null && other.codigoTipoDocumento != null) || (this.codigoTipoDocumento != null && !this.codigoTipoDocumento.equals(other.codigoTipoDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.Tipodocumento[ codigoTipoDocumento=" + codigoTipoDocumento + " ]";
    }
    
}
