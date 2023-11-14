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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByCodigoCliente", query = "SELECT c FROM Cliente c WHERE c.codigoCliente = :codigoCliente"),
    @NamedQuery(name = "Cliente.findByDocuClie", query = "SELECT c FROM Cliente c WHERE c.docuClie = :docuClie"),
    @NamedQuery(name = "Cliente.findByAppaClie", query = "SELECT c FROM Cliente c WHERE c.appaClie = :appaClie"),
    @NamedQuery(name = "Cliente.findByApmaClie", query = "SELECT c FROM Cliente c WHERE c.apmaClie = :apmaClie"),
    @NamedQuery(name = "Cliente.findByNombClie", query = "SELECT c FROM Cliente c WHERE c.nombClie = :nombClie"),
    @NamedQuery(name = "Cliente.findByTelfClie", query = "SELECT c FROM Cliente c WHERE c.telfClie = :telfClie"),
    @NamedQuery(name = "Cliente.findByDireClie", query = "SELECT c FROM Cliente c WHERE c.direClie = :direClie"),
    @NamedQuery(name = "Cliente.listar", query = "SELECT t.nombTipoDocumento, c.docuClie, c.appaClie, c.apmaClie, c.nombClie, d.nombreDistrito, c.direClie, e.codigoEstado, c.codigoCliente, c.telfClie "
            + "FROM Cliente c "
            + "JOIN c.codigoDistrito d "
            + "JOIN c.codigoTipoDocumento t "
            + "JOIN c.codigoEstado e"),

    //listarPorCodigo
    @NamedQuery(name = "Cliente.listarPorCodigo", query = "SELECT t.nombTipoDocumento, c.docuClie, c.appaClie, c.apmaClie, c.nombClie, d.nombreDistrito, c.direClie, e.codigoEstado, c.codigoCliente, c.telfClie "
            + "FROM Cliente c "
            + "JOIN c.codigoDistrito d "
            + "JOIN c.codigoTipoDocumento t "
            + "JOIN c.codigoEstado e "
            + "WHERE c.codigoCliente = :codigoCliente")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoCliente")
    private Integer codigoCliente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "docuClie")
    private String docuClie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "appaClie")
    private String appaClie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "apmaClie")
    private String apmaClie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombClie")
    private String nombClie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "telfClie")
    private String telfClie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "direClie")
    private String direClie;
    @JoinColumn(name = "codigoTipoDocumento", referencedColumnName = "codigoTipoDocumento")
    @ManyToOne(optional = false)
    private Tipodocumento codigoTipoDocumento;
    @JoinColumn(name = "codigoEstado", referencedColumnName = "codigoEstado")
    @ManyToOne(optional = false)
    private Estado codigoEstado;
    @JoinColumn(name = "codigoDistrito", referencedColumnName = "codigoDistrito")
    @ManyToOne(optional = false)
    private Distrito codigoDistrito;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoCliente")
    private List<Prestamo> prestamoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoCliente")
    private List<Cuenta> cuentaList;

    public Cliente() {
    }

    public Cliente(Integer codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public Cliente(Integer codigoCliente, String docuClie, String appaClie, String apmaClie, String nombClie, String telfClie, String direClie) {
        this.codigoCliente = codigoCliente;
        this.docuClie = docuClie;
        this.appaClie = appaClie;
        this.apmaClie = apmaClie;
        this.nombClie = nombClie;
        this.telfClie = telfClie;
        this.direClie = direClie;
    }

    public Integer getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(Integer codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getDocuClie() {
        return docuClie;
    }

    public void setDocuClie(String docuClie) {
        this.docuClie = docuClie;
    }

    public String getAppaClie() {
        return appaClie;
    }

    public void setAppaClie(String appaClie) {
        this.appaClie = appaClie;
    }

    public String getApmaClie() {
        return apmaClie;
    }

    public void setApmaClie(String apmaClie) {
        this.apmaClie = apmaClie;
    }

    public String getNombClie() {
        return nombClie;
    }

    public void setNombClie(String nombClie) {
        this.nombClie = nombClie;
    }

    public String getTelfClie() {
        return telfClie;
    }

    public void setTelfClie(String telfClie) {
        this.telfClie = telfClie;
    }

    public String getDireClie() {
        return direClie;
    }

    public void setDireClie(String direClie) {
        this.direClie = direClie;
    }

    public Tipodocumento getCodigoTipoDocumento() {
        return codigoTipoDocumento;
    }

    public void setCodigoTipoDocumento(Tipodocumento codigoTipoDocumento) {
        this.codigoTipoDocumento = codigoTipoDocumento;
    }

    public Estado getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(Estado codigoEstado) {
        this.codigoEstado = codigoEstado;
    }

    public Distrito getCodigoDistrito() {
        return codigoDistrito;
    }

    public void setCodigoDistrito(Distrito codigoDistrito) {
        this.codigoDistrito = codigoDistrito;
    }

    @XmlTransient
    public List<Prestamo> getPrestamoList() {
        return prestamoList;
    }

    public void setPrestamoList(List<Prestamo> prestamoList) {
        this.prestamoList = prestamoList;
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
        hash += (codigoCliente != null ? codigoCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.codigoCliente == null && other.codigoCliente != null) || (this.codigoCliente != null && !this.codigoCliente.equals(other.codigoCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.Cliente[ codigoCliente=" + codigoCliente + " ]";
    }

}
