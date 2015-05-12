/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.UML;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 1glm02
 */
@Entity
@Table(name = "LOCALIDADES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Localidad.findAll", query = "SELECT l FROM Localidad l"),
    @NamedQuery(name = "Localidad.findByIdlocalidad", query = "SELECT l FROM Localidad l WHERE l.idlocalidad = :idlocalidad"),
    @NamedQuery(name = "Localidad.findByNombreloc", query = "SELECT l FROM Localidad l WHERE l.nombreloc = :nombreloc")})
public class Localidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDLOCALIDAD")
    private Long idlocalidad;
    @Basic(optional = false)
    @Column(name = "NOMBRELOC")
    private String nombreloc;
    @JoinColumn(name = "IDMUNICIPIO", referencedColumnName = "IDMUNICIPIO")
    @ManyToOne
    private Municipio idmunicipio;

    public Localidad() {
    }

    public Localidad(Long idlocalidad) {
        this.idlocalidad = idlocalidad;
    }

    public Localidad(Long idlocalidad, String nombreloc) {
        this.idlocalidad = idlocalidad;
        this.nombreloc = nombreloc;
    }

    public Long getIdlocalidad() {
        return idlocalidad;
    }

    public void setIdlocalidad(Long idlocalidad) {
        this.idlocalidad = idlocalidad;
    }

    public String getNombreloc() {
        return nombreloc;
    }

    public void setNombreloc(String nombreloc) {
        this.nombreloc = nombreloc;
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
        hash += (idlocalidad != null ? idlocalidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Localidad)) {
            return false;
        }
        Localidad other = (Localidad) object;
        if ((this.idlocalidad == null && other.idlocalidad != null) || (this.idlocalidad != null && !this.idlocalidad.equals(other.idlocalidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.UML.Localidad[ idlocalidad=" + idlocalidad + " ]";
    }
    
}
