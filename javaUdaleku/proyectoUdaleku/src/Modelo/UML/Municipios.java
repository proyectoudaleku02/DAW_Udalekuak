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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "MUNICIPIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Municipios.findAll", query = "SELECT m FROM Municipios m"),
    @NamedQuery(name = "Municipios.findByIdmunicipio", query = "SELECT m FROM Municipios m WHERE m.idmunicipio = :idmunicipio"),
    @NamedQuery(name = "Municipios.findByNombremunic", query = "SELECT m FROM Municipios m WHERE m.nombremunic = :nombremunic")})
public class Municipios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDMUNICIPIO")
    private Long idmunicipio;
    @Basic(optional = false)
    @Column(name = "NOMBREMUNIC")
    private String nombremunic;
    @JoinColumn(name = "IDPROVINCIA", referencedColumnName = "IDPROVINCIA")
    @ManyToOne
    private Provincias idprovincia;
    @OneToMany(mappedBy = "idmunicipio")
    private Collection<Vias> viasCollection;

    public Municipios() {
    }

    public Municipios(Long idmunicipio) {
        this.idmunicipio = idmunicipio;
    }

    public Municipios(Long idmunicipio, String nombremunic) {
        this.idmunicipio = idmunicipio;
        this.nombremunic = nombremunic;
    }

    public Long getIdmunicipio() {
        return idmunicipio;
    }

    public void setIdmunicipio(Long idmunicipio) {
        this.idmunicipio = idmunicipio;
    }

    public String getNombremunic() {
        return nombremunic;
    }

    public void setNombremunic(String nombremunic) {
        this.nombremunic = nombremunic;
    }

    public Provincias getIdprovincia() {
        return idprovincia;
    }

    public void setIdprovincia(Provincias idprovincia) {
        this.idprovincia = idprovincia;
    }

    @XmlTransient
    public Collection<Vias> getViasCollection() {
        return viasCollection;
    }

    public void setViasCollection(Collection<Vias> viasCollection) {
        this.viasCollection = viasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmunicipio != null ? idmunicipio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Municipios)) {
            return false;
        }
        Municipios other = (Municipios) object;
        if ((this.idmunicipio == null && other.idmunicipio != null) || (this.idmunicipio != null && !this.idmunicipio.equals(other.idmunicipio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.UML.Municipios[ idmunicipio=" + idmunicipio + " ]";
    }
    
}
