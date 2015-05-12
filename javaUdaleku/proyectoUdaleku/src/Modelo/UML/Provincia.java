/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.UML;

import java.io.Serializable;
import java.util.Collection;
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
 * @author 1glm02
 */
@Entity
@Table(name = "PROVINCIAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Provincia.findAll", query = "SELECT p FROM Provincia p"),
    @NamedQuery(name = "Provincia.findByIdprovincia", query = "SELECT p FROM Provincia p WHERE p.idprovincia = :idprovincia"),
    @NamedQuery(name = "Provincia.findByNombreprov", query = "SELECT p FROM Provincia p WHERE p.nombreprov = :nombreprov")})
public class Provincia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDPROVINCIA")
    private String idprovincia;
    @Basic(optional = false)
    @Column(name = "NOMBREPROV")
    private String nombreprov;
    @OneToMany(mappedBy = "idprovincia")
    private Collection<Municipio> municipioCollection;
    @OneToMany(mappedBy = "idprovincia")
    private Collection<Centro> centroCollection;

    public Provincia() {
    }

    public Provincia(String idprovincia) {
        this.idprovincia = idprovincia;
    }

    public Provincia(String idprovincia, String nombreprov) {
        this.idprovincia = idprovincia;
        this.nombreprov = nombreprov;
    }

    public String getIdprovincia() {
        return idprovincia;
    }

    public void setIdprovincia(String idprovincia) {
        this.idprovincia = idprovincia;
    }

    public String getNombreprov() {
        return nombreprov;
    }

    public void setNombreprov(String nombreprov) {
        this.nombreprov = nombreprov;
    }

    @XmlTransient
    public Collection<Municipio> getMunicipioCollection() {
        return municipioCollection;
    }

    public void setMunicipioCollection(Collection<Municipio> municipioCollection) {
        this.municipioCollection = municipioCollection;
    }

    @XmlTransient
    public Collection<Centro> getCentroCollection() {
        return centroCollection;
    }

    public void setCentroCollection(Collection<Centro> centroCollection) {
        this.centroCollection = centroCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprovincia != null ? idprovincia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Provincia)) {
            return false;
        }
        Provincia other = (Provincia) object;
        if ((this.idprovincia == null && other.idprovincia != null) || (this.idprovincia != null && !this.idprovincia.equals(other.idprovincia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.UML.Provincia[ idprovincia=" + idprovincia + " ]";
    }
    
}
