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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 1glm02
 */
@Entity
@Table(name = "MODELOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Modelo.findAll", query = "SELECT m FROM Modelo m"),
    @NamedQuery(name = "Modelo.findByIdmodelo", query = "SELECT m FROM Modelo m WHERE m.idmodelo = :idmodelo"),
    @NamedQuery(name = "Modelo.findByDescmodelo", query = "SELECT m FROM Modelo m WHERE m.descmodelo = :descmodelo")})
public class Modelo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDMODELO")
    private String idmodelo;
    @Basic(optional = false)
    @Column(name = "DESCMODELO")
    private String descmodelo;

    public Modelo() {
    }

    public Modelo(String idmodelo) {
        this.idmodelo = idmodelo;
    }

    public Modelo(String idmodelo, String descmodelo) {
        this.idmodelo = idmodelo;
        this.descmodelo = descmodelo;
    }

    public String getIdmodelo() {
        return idmodelo;
    }

    public void setIdmodelo(String idmodelo) {
        this.idmodelo = idmodelo;
    }

    public String getDescmodelo() {
        return descmodelo;
    }

    public void setDescmodelo(String descmodelo) {
        this.descmodelo = descmodelo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmodelo != null ? idmodelo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Modelo)) {
            return false;
        }
        Modelo other = (Modelo) object;
        if ((this.idmodelo == null && other.idmodelo != null) || (this.idmodelo != null && !this.idmodelo.equals(other.idmodelo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.UML.Modelo[ idmodelo=" + idmodelo + " ]";
    }
    
}
