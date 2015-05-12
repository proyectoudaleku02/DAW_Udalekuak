/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.UML;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author 1glm02
 */
@Embeddable
public class CentroHasModeloPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "IDCENTRO")
    private long idcentro;
    @Basic(optional = false)
    @Column(name = "IDMODELO")
    private String idmodelo;

    public CentroHasModeloPK() {
    }

    public CentroHasModeloPK(long idcentro, String idmodelo) {
        this.idcentro = idcentro;
        this.idmodelo = idmodelo;
    }

    public long getIdcentro() {
        return idcentro;
    }

    public void setIdcentro(long idcentro) {
        this.idcentro = idcentro;
    }

    public String getIdmodelo() {
        return idmodelo;
    }

    public void setIdmodelo(String idmodelo) {
        this.idmodelo = idmodelo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idcentro;
        hash += (idmodelo != null ? idmodelo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CentroHasModeloPK)) {
            return false;
        }
        CentroHasModeloPK other = (CentroHasModeloPK) object;
        if (this.idcentro != other.idcentro) {
            return false;
        }
        if ((this.idmodelo == null && other.idmodelo != null) || (this.idmodelo != null && !this.idmodelo.equals(other.idmodelo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.UML.CentroHasModeloPK[ idcentro=" + idcentro + ", idmodelo=" + idmodelo + " ]";
    }
    
}
