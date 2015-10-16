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
@Table(name = "gruposanguineo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gruposanguineo.findAll", query = "SELECT g FROM Gruposanguineo g"),
    @NamedQuery(name = "Gruposanguineo.findByIdGrupo", query = "SELECT g FROM Gruposanguineo g WHERE g.idGrupo = :idGrupo"),
    @NamedQuery(name = "Gruposanguineo.findByGrupo", query = "SELECT g FROM Gruposanguineo g WHERE g.grupo = :grupo")})
public class Gruposanguineo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idGrupo")
    private Integer idGrupo;
    @Basic(optional = false)
    @Column(name = "grupo")
    private String grupo;
    @OneToMany(mappedBy = "grupoSanguineoFK")
    private List<Titular> titularList;

    public Gruposanguineo() {
    }

    public Gruposanguineo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Gruposanguineo(Integer idGrupo, String grupo) {
        this.idGrupo = idGrupo;
        this.grupo = grupo;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
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
        hash += (idGrupo != null ? idGrupo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gruposanguineo)) {
            return false;
        }
        Gruposanguineo other = (Gruposanguineo) object;
        if ((this.idGrupo == null && other.idGrupo != null) || (this.idGrupo != null && !this.idGrupo.equals(other.idGrupo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Gruposanguineo[ idGrupo=" + idGrupo + " ]";
    }
    
}
