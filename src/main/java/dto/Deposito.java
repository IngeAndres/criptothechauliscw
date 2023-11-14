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
@Table(name = "deposito")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deposito.findAll", query = "SELECT d FROM Deposito d"),
    @NamedQuery(name = "Deposito.findByCodigoDeposito", query = "SELECT d FROM Deposito d WHERE d.codigoDeposito = :codigoDeposito"),
    @NamedQuery(name = "Deposito.findByMontoDeposito", query = "SELECT d FROM Deposito d WHERE d.montoDeposito = :montoDeposito"),
    @NamedQuery(name = "Deposito.findByFechDeposito", query = "SELECT d FROM Deposito d WHERE d.fechDeposito = :fechDeposito")})
public class Deposito implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoDeposito")
    private Integer codigoDeposito;
    @Basic(optional = false)
    @NotNull
    @Column(name = "montoDeposito")
    private double montoDeposito;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechDeposito")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechDeposito;
    @JoinColumn(name = "codigoCuenta", referencedColumnName = "codigoCuenta")
    @ManyToOne(optional = false)
    private Cuenta codigoCuenta;

    public Deposito() {
    }

    public Deposito(Integer codigoDeposito) {
        this.codigoDeposito = codigoDeposito;
    }

    public Deposito(Integer codigoDeposito, double montoDeposito, Date fechDeposito) {
        this.codigoDeposito = codigoDeposito;
        this.montoDeposito = montoDeposito;
        this.fechDeposito = fechDeposito;
    }

    public Integer getCodigoDeposito() {
        return codigoDeposito;
    }

    public void setCodigoDeposito(Integer codigoDeposito) {
        this.codigoDeposito = codigoDeposito;
    }

    public double getMontoDeposito() {
        return montoDeposito;
    }

    public void setMontoDeposito(double montoDeposito) {
        this.montoDeposito = montoDeposito;
    }

    public Date getFechDeposito() {
        return fechDeposito;
    }

    public void setFechDeposito(Date fechDeposito) {
        this.fechDeposito = fechDeposito;
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
        hash += (codigoDeposito != null ? codigoDeposito.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Deposito)) {
            return false;
        }
        Deposito other = (Deposito) object;
        if ((this.codigoDeposito == null && other.codigoDeposito != null) || (this.codigoDeposito != null && !this.codigoDeposito.equals(other.codigoDeposito))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.Deposito[ codigoDeposito=" + codigoDeposito + " ]";
    }
    
}
