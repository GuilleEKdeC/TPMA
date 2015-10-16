/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author franc
 */
@Entity
@Table(name = "titular")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Titular.findAll", query = "SELECT t FROM Titular t"),
    @NamedQuery(name = "Titular.findByIdTitular", query = "SELECT t FROM Titular t WHERE t.idTitular = :idTitular"),
    @NamedQuery(name = "Titular.findByNombre", query = "SELECT t FROM Titular t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "Titular.findByApellido", query = "SELECT t FROM Titular t WHERE t.apellido = :apellido"),
    @NamedQuery(name = "Titular.findByNroDocumento", query = "SELECT t FROM Titular t WHERE t.nroDocumento = :nroDocumento"),
    @NamedQuery(name = "Titular.findByFechaNacimiento", query = "SELECT t FROM Titular t WHERE t.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "Titular.findByDonante", query = "SELECT t FROM Titular t WHERE t.donante = :donante"),
    @NamedQuery(name = "Titular.findByClaseSolicitada", query = "SELECT t FROM Titular t WHERE t.claseSolicitada = :claseSolicitada")})
public class Titular implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTitular")
    private Integer idTitular;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "apellido")
    private String apellido;
    @Basic(optional = false)
    @Column(name = "nroDocumento")
    private int nroDocumento;
    @Basic(optional = false)
    @Column(name = "fechaNacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Lob
    @Column(name = "foto")
    private byte[] foto;
    @Basic(optional = false)
    @Column(name = "donante")
    private boolean donante;
    @Basic(optional = false)
    @Column(name = "claseSolicitada")
    private String claseSolicitada;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtitularFK")
    private List<Licencia> licenciaList;
    @JoinColumn(name = "direccion_FK", referencedColumnName = "idDireccion")
    @ManyToOne(optional = false)
    private Direccion direccionFK;
    @JoinColumn(name = "factorRH_FK", referencedColumnName = "idfactor")
    @ManyToOne
    private Factorrh factorRHFK;
    @JoinColumn(name = "grupoSanguineo_FK", referencedColumnName = "idGrupo")
    @ManyToOne
    private Gruposanguineo grupoSanguineoFK;
    @JoinColumn(name = "tipoDocumento_FK", referencedColumnName = "idTipoDocumento")
    @ManyToOne
    private Tipodocumento tipoDocumentoFK;

    public Titular() {
    }

    public Titular(Integer idTitular) {
        this.idTitular = idTitular;
    }

    public Titular(Integer idTitular, String nombre, String apellido, int nroDocumento, Date fechaNacimiento, boolean donante, String claseSolicitada) {
        this.idTitular = idTitular;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nroDocumento = nroDocumento;
        this.fechaNacimiento = fechaNacimiento;
        this.donante = donante;
        this.claseSolicitada = claseSolicitada;
    }

    public Integer getIdTitular() {
        return idTitular;
    }

    public void setIdTitular(Integer idTitular) {
        this.idTitular = idTitular;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(int nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public boolean getDonante() {
        return donante;
    }

    public void setDonante(boolean donante) {
        this.donante = donante;
    }

    public String getClaseSolicitada() {
        return claseSolicitada;
    }

    public void setClaseSolicitada(String claseSolicitada) {
        this.claseSolicitada = claseSolicitada;
    }

    @XmlTransient
    public List<Licencia> getLicenciaList() {
        return licenciaList;
    }

    public void setLicenciaList(List<Licencia> licenciaList) {
        this.licenciaList = licenciaList;
    }

    public Direccion getDireccionFK() {
        return direccionFK;
    }

    public void setDireccionFK(Direccion direccionFK) {
        this.direccionFK = direccionFK;
    }

    public Factorrh getFactorRHFK() {
        return factorRHFK;
    }

    public void setFactorRHFK(Factorrh factorRHFK) {
        this.factorRHFK = factorRHFK;
    }

    public Gruposanguineo getGrupoSanguineoFK() {
        return grupoSanguineoFK;
    }

    public void setGrupoSanguineoFK(Gruposanguineo grupoSanguineoFK) {
        this.grupoSanguineoFK = grupoSanguineoFK;
    }

    public Tipodocumento getTipoDocumentoFK() {
        return tipoDocumentoFK;
    }

    public void setTipoDocumentoFK(Tipodocumento tipoDocumentoFK) {
        this.tipoDocumentoFK = tipoDocumentoFK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTitular != null ? idTitular.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Titular)) {
            return false;
        }
        Titular other = (Titular) object;
        if ((this.idTitular == null && other.idTitular != null) || (this.idTitular != null && !this.idTitular.equals(other.idTitular))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Titular[ idTitular=" + idTitular + " ]";
    }
    
}
