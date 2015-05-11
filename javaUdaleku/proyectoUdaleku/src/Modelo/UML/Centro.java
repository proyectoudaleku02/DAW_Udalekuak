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
@Table(name = "CENTROS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Centro.findAll", query = "SELECT c FROM Centro c"),
    @NamedQuery(name = "Centro.findByIdcentro", query = "SELECT c FROM Centro c WHERE c.idcentro = :idcentro"),
    @NamedQuery(name = "Centro.findByNombrecent", query = "SELECT c FROM Centro c WHERE c.nombrecent = :nombrecent"),
    @NamedQuery(name = "Centro.findByModelocent", query = "SELECT c FROM Centro c WHERE c.modelocent = :modelocent")})
public class Centro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDCENTRO")
    private Long idcentro;
    @Basic(optional = false)
    @Column(name = "NOMBRECENT")
    private String nombrecent;
    @Basic(optional = false)
    @Column(name = "MODELOCENT")
    private String modelocent;
    @JoinColumn(name = "IDPROVINCIA", referencedColumnName = "IDPROVINCIA")
    @ManyToOne
    private Provincia idprovincia;

    public Centro() {
    }

    public Centro(Long idcentro) {
        this.idcentro = idcentro;
    }

    public Centro(Long idcentro, String nombrecent, String modelocent) {
        this.idcentro = idcentro;
        this.nombrecent = nombrecent;
        this.modelocent = modelocent;
    }

    public Long getIdcentro() {
        return idcentro;
    }

    public void setIdcentro(Long idcentro) {
        this.idcentro = idcentro;
    }

    public String getNombrecent() {
        return nombrecent;
    }

    public void setNombrecent(String nombrecent) {
        this.nombrecent = nombrecent;
    }

    public String getModelocent() {
        return modelocent;
    }

    public void setModelocent(String modelocent) {
        this.modelocent = modelocent;
    }

    public Provincia getIdprovincia() {
        return idprovincia;
    }

    public void setIdprovincia(Provincia idprovincia) {
        this.idprovincia = idprovincia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcentro != null ? idcentro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Centro)) {
            return false;
        }
        Centro other = (Centro) object;
        if ((this.idcentro == null && other.idcentro != null) || (this.idcentro != null && !this.idcentro.equals(other.idcentro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.UML.Centro[ idcentro=" + idcentro + " ]";
    }
    
}
