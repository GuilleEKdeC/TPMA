/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author franc
 */
@Entity
@Table(name = "clase")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clase.findAll", query = "SELECT c FROM Clase c"),
    @NamedQuery(name = "Clase.findByIdClaseSolicitada", query = "SELECT c FROM Clase c WHERE c.idClaseSolicitada = :idClaseSolicitada"),
    @NamedQuery(name = "Clase.findByDescripcion", query = "SELECT c FROM Clase c WHERE c.descripcion = :descripcion")})
public class Clase implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idClaseSolicitada")
    private Integer idClaseSolicitada;
    @Basic(optional = false)
    @Column(name = "Descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "claseSolicitada")
    private List<Titular> titularList;

    public Clase() {
    }

    public Clase(Integer idClaseSolicitada) {
        this.idClaseSolicitada = idClaseSolicitada;
    }

    public Clase(Integer idClaseSolicitada, String descripcion) {
        this.idClaseSolicitada = idClaseSolicitada;
        this.descripcion = descripcion;
    }

    public Integer getIdClaseSolicitada() {
        return idClaseSolicitada;
    }

    public void setIdClaseSolicitada(Integer idClaseSolicitada) {
        this.idClaseSolicitada = idClaseSolicitada;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Titular> getTitularList() {
        return titularList;
    }

    public void setTitularList(List<Titular> titularList) {
        this.titularList = titularList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClaseSolicitada != null ? idClaseSolicitada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clase)) {
            return false;
        }
        Clase other = (Clase) object;
        if ((this.idClaseSolicitada == null && other.idClaseSolicitada != null) || (this.idClaseSolicitada != null && !this.idClaseSolicitada.equals(other.idClaseSolicitada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Clase[ idClaseSolicitada=" + idClaseSolicitada + " ]";
    }
    
}
