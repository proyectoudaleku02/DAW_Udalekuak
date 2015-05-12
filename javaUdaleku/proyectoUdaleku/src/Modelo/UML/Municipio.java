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
    @NamedQuery(name = "Municipio.findAll", query = "SELECT m FROM Municipio m"),
    @NamedQuery(name = "Municipio.findByIdmunicipio", query = "SELECT m FROM Municipio m WHERE m.idmunicipio = :idmunicipio"),
    @NamedQuery(name = "Municipio.findByNombremunic", query = "SELECT m FROM Municipio m WHERE m.nombremunic = :nombremunic")})
public class Municipio implements Serializable {
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
    private Provincia idprovincia;
    @OneToMany(mappedBy = "idmunicipio")
    private Collection<Localidad> localidadCollection;
    @OneToMany(mappedBy = "idmunicipio")
    private Collection<Via> viaCollection;

    public Municipio() {
    }

    public Municipio(Long idmunicipio) {
        this.idmunicipio = idmunicipio;
    }

    public Municipio(Long idmunicipio, String nombremunic) {
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

    public Provincia getIdprovincia() {
        return idprovincia;
    }

    public void setIdprovincia(Provincia idprovincia) {
        this.idprovincia = idprovincia;
    }

    @XmlTransient
    public Collection<Localidad> getLocalidadCollection() {
        return localidadCollection;
    }

    public void setLocalidadCollection(Collection<Localidad> localidadCollection) {
        this.localidadCollection = localidadCollection;
    }

    @XmlTransient
    public Collection<Via> getViaCollection() {
        return viaCollection;
    }

    public void setViaCollection(Collection<Via> viaCollection) {
        this.viaCollection = viaCollection;
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
        if (!(object instanceof Municipio)) {
            return false;
        }
        Municipio other = (Municipio) object;
        if ((this.idmunicipio == null && other.idmunicipio != null) || (this.idmunicipio != null && !this.idmunicipio.equals(other.idmunicipio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.UML.Municipio[ idmunicipio=" + idmunicipio + " ]";
    }
    
}
