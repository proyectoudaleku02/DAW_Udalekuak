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
@Table(name = "VIAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Via.findAll", query = "SELECT v FROM Via v"),
    @NamedQuery(name = "Via.findByIdvia", query = "SELECT v FROM Via v WHERE v.idvia = :idvia"),
    @NamedQuery(name = "Via.findByTipovia", query = "SELECT v FROM Via v WHERE v.tipovia = :tipovia"),
    @NamedQuery(name = "Via.findByNombrevia", query = "SELECT v FROM Via v WHERE v.nombrevia = :nombrevia")})
public class Via implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDVIA")
    private Long idvia;
    @Basic(optional = false)
    @Column(name = "TIPOVIA")
    private String tipovia;
    @Basic(optional = false)
    @Column(name = "NOMBREVIA")
    private String nombrevia;
    @OneToMany(mappedBy = "idvia")
    private Collection<Direccion> direccionCollection;
    @JoinColumn(name = "IDMUNICIPIO", referencedColumnName = "IDMUNICIPIO")
    @ManyToOne
    private Municipio idmunicipio;

    public Via() {
    }

    public Via(Long idvia) {
        this.idvia = idvia;
    }

    public Via(Long idvia, String tipovia, String nombrevia) {
        this.idvia = idvia;
        this.tipovia = tipovia;
        this.nombrevia = nombrevia;
    }

    public Long getIdvia() {
        return idvia;
    }

    public void setIdvia(Long idvia) {
        this.idvia = idvia;
    }

    public String getTipovia() {
        return tipovia;
    }

    public void setTipovia(String tipovia) {
        this.tipovia = tipovia;
    }

    public String getNombrevia() {
        return nombrevia;
    }

    public void setNombrevia(String nombrevia) {
        this.nombrevia = nombrevia;
    }

    @XmlTransient
    public Collection<Direccion> getDireccionCollection() {
        return direccionCollection;
    }

    public void setDireccionCollection(Collection<Direccion> direccionCollection) {
        this.direccionCollection = direccionCollection;
    }

    public Municipio getIdmunicipio() {
        return idmunicipio;
    }

    public void setIdmunicipio(Municipio idmunicipio) {
        this.idmunicipio = idmunicipio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idvia != null ? idvia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Via)) {
            return false;
        }
        Via other = (Via) object;
        if ((this.idvia == null && other.idvia != null) || (this.idvia != null && !this.idvia.equals(other.idvia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.UML.Via[ idvia=" + idvia + " ]";
    }
    
}
