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
@Table(name = "transferencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transferencia.findAll", query = "SELECT t FROM Transferencia t"),
    @NamedQuery(name = "Transferencia.findByCodigoTransferencia", query = "SELECT t FROM Transferencia t WHERE t.codigoTransferencia = :codigoTransferencia"),
    @NamedQuery(name = "Transferencia.findByMontoTransferencia", query = "SELECT t FROM Transferencia t WHERE t.montoTransferencia = :montoTransferencia"),
    @NamedQuery(name = "Transferencia.findByFechTransferencia", query = "SELECT t FROM Transferencia t WHERE t.fechTransferencia = :fechTransferencia"),
    @NamedQuery(name = "Transferencia.findByFechTransferencia", query = "SELECT t FROM Transferencia t WHERE t.fechTransferencia = :fechTransferencia"),
    @NamedQuery(name = "Transferencia.listar", query = "SELECT t.codigoTransferencia, cuo.numeCuenta, clo.appaClie, clo.apmaClie, clo.nombClie, "
            + "t.montoTransferencia, cud.numeCuenta, cld.appaClie, cld.apmaClie, cld.nombClie, t.fechTransferencia "
            + "FROM Transferencia t "
            + "JOIN t.codigoCuentaOrigen cuo "
            + "JOIN cuo.codigoCliente clo "
            + "JOIN t.codigoCuentaDestino cud "
            + "JOIN cud.codigoCliente cld")})
public class Transferencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoTransferencia")
    private Integer codigoTransferencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "montoTransferencia")
    private double montoTransferencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechTransferencia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechTransferencia;
    @JoinColumn(name = "codigoCuentaOrigen", referencedColumnName = "codigoCuenta")
    @ManyToOne(optional = false)
    private Cuenta codigoCuentaOrigen;
    @JoinColumn(name = "codigoCuentaDestino", referencedColumnName = "codigoCuenta")
    @ManyToOne(optional = false)
    private Cuenta codigoCuentaDestino;

    public Transferencia() {
    }

    public Transferencia(Integer codigoTransferencia) {
        this.codigoTransferencia = codigoTransferencia;
    }

    public Transferencia(Integer codigoTransferencia, double montoTransferencia, Date fechTransferencia) {
        this.codigoTransferencia = codigoTransferencia;
        this.montoTransferencia = montoTransferencia;
        this.fechTransferencia = fechTransferencia;
    }

    public Integer getCodigoTransferencia() {
        return codigoTransferencia;
    }

    public void setCodigoTransferencia(Integer codigoTransferencia) {
        this.codigoTransferencia = codigoTransferencia;
    }

    public double getMontoTransferencia() {
        return montoTransferencia;
    }

    public void setMontoTransferencia(double montoTransferencia) {
        this.montoTransferencia = montoTransferencia;
    }

    public Date getFechTransferencia() {
        return fechTransferencia;
    }

    public void setFechTransferencia(Date fechTransferencia) {
        this.fechTransferencia = fechTransferencia;
    }

    public Cuenta getCodigoCuentaOrigen() {
        return codigoCuentaOrigen;
    }

    public void setCodigoCuentaOrigen(Cuenta codigoCuentaOrigen) {
        this.codigoCuentaOrigen = codigoCuentaOrigen;
    }

    public Cuenta getCodigoCuentaDestino() {
        return codigoCuentaDestino;
    }

    public void setCodigoCuentaDestino(Cuenta codigoCuentaDestino) {
        this.codigoCuentaDestino = codigoCuentaDestino;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoTransferencia != null ? codigoTransferencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transferencia)) {
            return false;
        }
        Transferencia other = (Transferencia) object;
        if ((this.codigoTransferencia == null && other.codigoTransferencia != null) || (this.codigoTransferencia != null && !this.codigoTransferencia.equals(other.codigoTransferencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.Transferencia[ codigoTransferencia=" + codigoTransferencia + " ]";
    }

}
