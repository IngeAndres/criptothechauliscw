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
@Table(name = "tipocuenta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipocuenta.findAll", query = "SELECT t FROM Tipocuenta t"),
    @NamedQuery(name = "Tipocuenta.findByCodigoTipoCuenta", query = "SELECT t FROM Tipocuenta t WHERE t.codigoTipoCuenta = :codigoTipoCuenta"),
    @NamedQuery(name = "Tipocuenta.findByNombTipoCuenta", query = "SELECT t FROM Tipocuenta t WHERE t.nombTipoCuenta = :nombTipoCuenta")})
public class Tipocuenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoTipoCuenta")
    private Integer codigoTipoCuenta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombTipoCuenta")
    private String nombTipoCuenta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoTipoCuenta")
    private List<Cuenta> cuentaList;

    public Tipocuenta() {
    }

    public Tipocuenta(Integer codigoTipoCuenta) {
        this.codigoTipoCuenta = codigoTipoCuenta;
    }

    public Tipocuenta(Integer codigoTipoCuenta, String nombTipoCuenta) {
        this.codigoTipoCuenta = codigoTipoCuenta;
        this.nombTipoCuenta = nombTipoCuenta;
    }

    public Integer getCodigoTipoCuenta() {
        return codigoTipoCuenta;
    }

    public void setCodigoTipoCuenta(Integer codigoTipoCuenta) {
        this.codigoTipoCuenta = codigoTipoCuenta;
    }

    public String getNombTipoCuenta() {
        return nombTipoCuenta;
    }

    public void setNombTipoCuenta(String nombTipoCuenta) {
        this.nombTipoCuenta = nombTipoCuenta;
    }

    @XmlTransient
    public List<Cuenta> getCuentaList() {
        return cuentaList;
    }

    public void setCuentaList(List<Cuenta> cuentaList) {
        this.cuentaList = cuentaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoTipoCuenta != null ? codigoTipoCuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipocuenta)) {
            return false;
        }
        Tipocuenta other = (Tipocuenta) object;
        if ((this.codigoTipoCuenta == null && other.codigoTipoCuenta != null) || (this.codigoTipoCuenta != null && !this.codigoTipoCuenta.equals(other.codigoTipoCuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.Tipocuenta[ codigoTipoCuenta=" + codigoTipoCuenta + " ]";
    }
    
}
