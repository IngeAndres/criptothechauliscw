/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ing. Andres Gomez
 */
@Entity
@Table(name = "cuenta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuenta.findAll", query = "SELECT c FROM Cuenta c"),
    @NamedQuery(name = "Cuenta.findByCodigoCuenta", query = "SELECT c FROM Cuenta c WHERE c.codigoCuenta = :codigoCuenta"),
    @NamedQuery(name = "Cuenta.findByNumeCuenta", query = "SELECT c FROM Cuenta c WHERE c.numeCuenta = :numeCuenta"),
    @NamedQuery(name = "Cuenta.findBySaldoCuenta", query = "SELECT c FROM Cuenta c WHERE c.saldoCuenta = :saldoCuenta"),
    @NamedQuery(name = "Cuenta.findByFechApertCuenta", query = "SELECT c FROM Cuenta c WHERE c.fechApertCuenta = :fechApertCuenta"),
    @NamedQuery(name = "Cuenta.listar", query = "SELECT c.codigoCuenta, c.codigoCliente.docuClie, c.numeCuenta, c.codigoTipoCuenta.nombTipoCuenta, c.saldoCuenta, c.fechApertCuenta, c.codigoEstado.nombEsta FROM Cuenta c"),

    //listar por codigocuenta
    @NamedQuery(name = "Cuenta.listarporcodigo", query = "SELECT c.codigoCuenta, c.codigoCliente.docuClie, c.numeCuenta, c.codigoTipoCuenta.nombTipoCuenta, c.saldoCuenta, c.fechApertCuenta, c.codigoEstado.nombEsta FROM Cuenta c WHERE c.codigoCuenta = :codigoCuenta")})
public class Cuenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoCuenta")
    private Integer codigoCuenta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "numeCuenta")
    private String numeCuenta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "saldoCuenta")
    private double saldoCuenta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechApertCuenta")
    @Temporal(TemporalType.DATE)
    private Date fechApertCuenta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoCuentaOrigen")
    private List<Transferencia> transferenciaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoCuentaDestino")
    private List<Transferencia> transferenciaList1;
    @JoinColumn(name = "codigoCliente", referencedColumnName = "codigoCliente")
    @ManyToOne(optional = false)
    private Cliente codigoCliente;
    @JoinColumn(name = "codigoTipoCuenta", referencedColumnName = "codigoTipoCuenta")
    @ManyToOne(optional = false)
    private Tipocuenta codigoTipoCuenta;
    @JoinColumn(name = "codigoEstado", referencedColumnName = "codigoEstado")
    @ManyToOne(optional = false)
    private Estado codigoEstado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoCuenta")
    private List<Deposito> depositoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoCuenta")
    private List<Retiro> retiroList;

    public Cuenta() {
    }

    public Cuenta(Integer codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    public Cuenta(Integer codigoCuenta, String numeCuenta, double saldoCuenta, Date fechApertCuenta) {
        this.codigoCuenta = codigoCuenta;
        this.numeCuenta = numeCuenta;
        this.saldoCuenta = saldoCuenta;
        this.fechApertCuenta = fechApertCuenta;
    }

    public Integer getCodigoCuenta() {
        return codigoCuenta;
    }

    public void setCodigoCuenta(Integer codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    public String getNumeCuenta() {
        return numeCuenta;
    }

    public void setNumeCuenta(String numeCuenta) {
        this.numeCuenta = numeCuenta;
    }

    public double getSaldoCuenta() {
        return saldoCuenta;
    }

    public void setSaldoCuenta(double saldoCuenta) {
        this.saldoCuenta = saldoCuenta;
    }

    public Date getFechApertCuenta() {
        return fechApertCuenta;
    }

    public void setFechApertCuenta(Date fechApertCuenta) {
        this.fechApertCuenta = fechApertCuenta;
    }

    @XmlTransient
    public List<Transferencia> getTransferenciaList() {
        return transferenciaList;
    }

    public void setTransferenciaList(List<Transferencia> transferenciaList) {
        this.transferenciaList = transferenciaList;
    }

    @XmlTransient
    public List<Transferencia> getTransferenciaList1() {
        return transferenciaList1;
    }

    public void setTransferenciaList1(List<Transferencia> transferenciaList1) {
        this.transferenciaList1 = transferenciaList1;
    }

    public Cliente getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(Cliente codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public Tipocuenta getCodigoTipoCuenta() {
        return codigoTipoCuenta;
    }

    public void setCodigoTipoCuenta(Tipocuenta codigoTipoCuenta) {
        this.codigoTipoCuenta = codigoTipoCuenta;
    }

    public Estado getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(Estado codigoEstado) {
        this.codigoEstado = codigoEstado;
    }

    @XmlTransient
    public List<Deposito> getDepositoList() {
        return depositoList;
    }

    public void setDepositoList(List<Deposito> depositoList) {
        this.depositoList = depositoList;
    }

    @XmlTransient
    public List<Retiro> getRetiroList() {
        return retiroList;
    }

    public void setRetiroList(List<Retiro> retiroList) {
        this.retiroList = retiroList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoCuenta != null ? codigoCuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuenta)) {
            return false;
        }
        Cuenta other = (Cuenta) object;
        if ((this.codigoCuenta == null && other.codigoCuenta != null) || (this.codigoCuenta != null && !this.codigoCuenta.equals(other.codigoCuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.Cuenta[ codigoCuenta=" + codigoCuenta + " ]";
    }

}
