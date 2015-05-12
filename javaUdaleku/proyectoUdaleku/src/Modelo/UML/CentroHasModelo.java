/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.UML;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 1glm02
 */
@Entity
@Table(name = "CENTROS_HAS_MODELOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CentroHasModelo.findAll", query = "SELECT c FROM CentroHasModelo c"),
    @NamedQuery(name = "CentroHasModelo.findByIdcentro", query = "SELECT c FROM CentroHasModelo c WHERE c.centroHasModeloPK.idcentro = :idcentro"),
    @NamedQuery(name = "CentroHasModelo.findByIdmodelo", query = "SELECT c FROM CentroHasModelo c WHERE c.centroHasModeloPK.idmodelo = :idmodelo")})
public class CentroHasModelo implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CentroHasModeloPK centroHasModeloPK;

    public CentroHasModelo() {
    }

    public CentroHasModelo(CentroHasModeloPK centroHasModeloPK) {
        this.centroHasModeloPK = centroHasModeloPK;
    }

    public CentroHasModelo(long idcentro, String idmodelo) {
        this.centroHasModeloPK = new CentroHasModeloPK(idcentro, idmodelo);
    }
   

    public CentroHasModeloPK getCentroHasModeloPK() {
        return centroHasModeloPK;
    }

    public void setCentroHasModeloPK(CentroHasModeloPK centroHasModeloPK) {
        this.centroHasModeloPK = centroHasModeloPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (centroHasModeloPK != null ? centroHasModeloPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CentroHasModelo)) {
            return false;
        }
        CentroHasModelo other = (CentroHasModelo) object;
        if ((this.centroHasModeloPK == null && other.centroHasModeloPK != null) || (this.centroHasModeloPK != null && !this.centroHasModeloPK.equals(other.centroHasModeloPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.UML.CentroHasModelo[ centroHasModeloPK=" + centroHasModeloPK + " ]";
    }
    
}
