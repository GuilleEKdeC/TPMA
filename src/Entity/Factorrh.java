/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "factorrh")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Factorrh.findAll", query = "SELECT f FROM Factorrh f"),
    @NamedQuery(name = "Factorrh.findByIdfactor", query = "SELECT f FROM Factorrh f WHERE f.idfactor = :idfactor"),
    @NamedQuery(name = "Factorrh.findByFactor", query = "SELECT f FROM Factorrh f WHERE f.factor = :factor")})
public class Factorrh implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idfactor")
    private Integer idfactor;
    @Basic(optional = false)
    @Column(name = "factor")
    private String factor;
    @OneToMany(mappedBy = "factorRHFK")
    private List<Titular> titularList;

    public Factorrh() {
    }

    public Factorrh(Integer idfactor) {
        this.idfactor = idfactor;
    }

    public Factorrh(Integer idfactor, String factor) {
        this.idfactor = idfactor;
        this.factor = factor;
    }

    public Integer getIdfactor() {
        return idfactor;
    }

    public void setIdfactor(Integer idfactor) {
        this.idfactor = idfactor;
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
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
        hash += (idfactor != null ? idfactor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Factorrh)) {
            return false;
        }
        Factorrh other = (Factorrh) object;
        if ((this.idfactor == null && other.idfactor != null) || (this.idfactor != null && !this.idfactor.equals(other.idfactor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Factorrh[ idfactor=" + idfactor + " ]";
    }
    
}
