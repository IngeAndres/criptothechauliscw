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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ing. Andres Gomez
 */
@Entity
@Table(name = "prestamo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prestamo.findAll", query = "SELECT p FROM Prestamo p"),
    @NamedQuery(name = "Prestamo.findByCodigoPrestamo", query = "SELECT p FROM Prestamo p WHERE p.codigoPrestamo = :codigoPrestamo"),
    @NamedQuery(name = "Prestamo.findByPlazoPrestamo", query = "SELECT p FROM Prestamo p WHERE p.plazoPrestamo = :plazoPrestamo"),
    @NamedQuery(name = "Prestamo.findByTasasPrestamo", query = "SELECT p FROM Prestamo p WHERE p.tasasPrestamo = :tasasPrestamo"),
    @NamedQuery(name = "Prestamo.findByTotalPedidoPrestamo", query = "SELECT p FROM Prestamo p WHERE p.totalPedidoPrestamo = :totalPedidoPrestamo"),
    @NamedQuery(name = "Prestamo.findByTotalPagarPrestamo", query = "SELECT p FROM Prestamo p WHERE p.totalPagarPrestamo = :totalPagarPrestamo"),
    @NamedQuery(name = "Prestamo.findByFechPrestamo", query = "SELECT p FROM Prestamo p WHERE p.fechPrestamo = :fechPrestamo")})
public class Prestamo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoPrestamo")
    private Integer codigoPrestamo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "plazoPrestamo")
    private String plazoPrestamo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tasasPrestamo")
    private double tasasPrestamo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "totalPedidoPrestamo")
    private double totalPedidoPrestamo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "totalPagarPrestamo")
    private double totalPagarPrestamo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechPrestamo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechPrestamo;
    @JoinColumn(name = "codigoCliente", referencedColumnName = "codigoCliente")
    @ManyToOne(optional = false)
    private Cliente codigoCliente;

    public Prestamo() {
    }

    public Prestamo(Integer codigoPrestamo) {
        this.codigoPrestamo = codigoPrestamo;
    }

    public Prestamo(Integer codigoPrestamo, String plazoPrestamo, double tasasPrestamo, double totalPedidoPrestamo, double totalPagarPrestamo, Date fechPrestamo) {
        this.codigoPrestamo = codigoPrestamo;
        this.plazoPrestamo = plazoPrestamo;
        this.tasasPrestamo = tasasPrestamo;
        this.totalPedidoPrestamo = totalPedidoPrestamo;
        this.totalPagarPrestamo = totalPagarPrestamo;
        this.fechPrestamo = fechPrestamo;
    }

    public Integer getCodigoPrestamo() {
        return codigoPrestamo;
    }

    public void setCodigoPrestamo(Integer codigoPrestamo) {
        this.codigoPrestamo = codigoPrestamo;
    }

    public String getPlazoPrestamo() {
        return plazoPrestamo;
    }

    public void setPlazoPrestamo(String plazoPrestamo) {
        this.plazoPrestamo = plazoPrestamo;
    }

    public double getTasasPrestamo() {
        return tasasPrestamo;
    }

    public void setTasasPrestamo(double tasasPrestamo) {
        this.tasasPrestamo = tasasPrestamo;
    }

    public double getTotalPedidoPrestamo() {
        return totalPedidoPrestamo;
    }

    public void setTotalPedidoPrestamo(double totalPedidoPrestamo) {
        this.totalPedidoPrestamo = totalPedidoPrestamo;
    }

    public double getTotalPagarPrestamo() {
        return totalPagarPrestamo;
    }

    public void setTotalPagarPrestamo(double totalPagarPrestamo) {
        this.totalPagarPrestamo = totalPagarPrestamo;
    }

    public Date getFechPrestamo() {
        return fechPrestamo;
    }

    public void setFechPrestamo(Date fechPrestamo) {
        this.fechPrestamo = fechPrestamo;
    }

    public Cliente getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(Cliente codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoPrestamo != null ? codigoPrestamo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prestamo)) {
            return false;
        }
        Prestamo other = (Prestamo) object;
        if ((this.codigoPrestamo == null && other.codigoPrestamo != null) || (this.codigoPrestamo != null && !this.codigoPrestamo.equals(other.codigoPrestamo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.Prestamo[ codigoPrestamo=" + codigoPrestamo + " ]";
    }
    
}
