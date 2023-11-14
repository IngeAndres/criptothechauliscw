/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ing. Andres Gomez
 */
@Entity
@Table(name = "retiro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Retiro.findAll", query = "SELECT r FROM Retiro r"),
    @NamedQuery(name = "Retiro.findByCodigoRetiro", query = "SELECT r FROM Retiro r WHERE r.codigoRetiro = :codigoRetiro"),
    @NamedQuery(name = "Retiro.findByMontoRetiro", query = "SELECT r FROM Retiro r WHERE r.montoRetiro = :montoRetiro"),
    @NamedQuery(name = "Retiro.findByFechRetiro", query = "SELECT r FROM Retiro r WHERE r.fechRetiro = :fechRetiro")})
public class Retiro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoRetiro")
    private Integer codigoRetiro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "montoRetiro")
    private double montoRetiro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechRetiro")
    @Temporal(TemporalType.DATE)
    private Date fechRetiro;
    @JoinColumn(name = "codigoCuenta", referencedColumnName = "codigoCuenta")
    @ManyToOne(optional = false)
    private Cuenta codigoCuenta;

    public Retiro() {
    }

    public Retiro(Integer codigoRetiro) {
        this.codigoRetiro = codigoRetiro;
    }

    public Retiro(Integer codigoRetiro, double montoRetiro, Date fechRetiro) {
        this.codigoRetiro = codigoRetiro;
        this.montoRetiro = montoRetiro;
        this.fechRetiro = fechRetiro;
    }

    public Integer getCodigoRetiro() {
        return codigoRetiro;
    }

    public void setCodigoRetiro(Integer codigoRetiro) {
        this.codigoRetiro = codigoRetiro;
    }

    public double getMontoRetiro() {
        return montoRetiro;
    }

    public void setMontoRetiro(double montoRetiro) {
        this.montoRetiro = montoRetiro;
    }

    public Date getFechRetiro() {
        return fechRetiro;
    }

    public void setFechRetiro(Date fechRetiro) {
        this.fechRetiro = fechRetiro;
    }

    public Cuenta getCodigoCuenta() {
        return codigoCuenta;
    }

    public void setCodigoCuenta(Cuenta codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoRetiro != null ? codigoRetiro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Retiro)) {
            return false;
        }
        Retiro other = (Retiro) object;
        if ((this.codigoRetiro == null && other.codigoRetiro != null) || (this.codigoRetiro != null && !this.codigoRetiro.equals(other.codigoRetiro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.Retiro[ codigoRetiro=" + codigoRetiro + " ]";
    }
    
}
