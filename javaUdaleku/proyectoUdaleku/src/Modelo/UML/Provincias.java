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
    @NamedQuery(name = "Provincias.findAll", query = "SELECT p FROM Provincias p"),
    @NamedQuery(name = "Provincias.findByIdprovincia", query = "SELECT p FROM Provincias p WHERE p.idprovincia = :idprovincia"),
    @NamedQuery(name = "Provincias.findByNombreprov", query = "SELECT p FROM Provincias p WHERE p.nombreprov = :nombreprov")})
public class Provincias implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDPROVINCIA")
    private String idprovincia;
    @Basic(optional = false)
    @Column(name = "NOMBREPROV")
    private String nombreprov;
    @OneToMany(mappedBy = "idprovincia")
    private Collection<Municipios> municipiosCollection;
    @OneToMany(mappedBy = "idprovincia")
    private Collection<Centros> centrosCollection;

    public Provincias() {
    }

    public Provincias(String idprovincia) {
        this.idprovincia = idprovincia;
    }

    public Provincias(String idprovincia, String nombreprov) {
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
    public Collection<Municipios> getMunicipiosCollection() {
        return municipiosCollection;
    }

    public void setMunicipiosCollection(Collection<Municipios> municipiosCollection) {
        this.municipiosCollection = municipiosCollection;
    }

    @XmlTransient
    public Collection<Centros> getCentrosCollection() {
        return centrosCollection;
    }

    public void setCentrosCollection(Collection<Centros> centrosCollection) {
        this.centrosCollection = centrosCollection;
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
        if (!(object instanceof Provincias)) {
            return false;
        }
        Provincias other = (Provincias) object;
        if ((this.idprovincia == null && other.idprovincia != null) || (this.idprovincia != null && !this.idprovincia.equals(other.idprovincia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.UML.Provincias[ idprovincia=" + idprovincia + " ]";
    }
    
}
