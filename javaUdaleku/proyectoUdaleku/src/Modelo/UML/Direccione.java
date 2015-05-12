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
@Table(name = "DIRECCIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Direccione.findAll", query = "SELECT d FROM Direccione d"),
    @NamedQuery(name = "Direccione.findByIddireccion", query = "SELECT d FROM Direccione d WHERE d.iddireccion = :iddireccion"),
    @NamedQuery(name = "Direccione.findByNumdir", query = "SELECT d FROM Direccione d WHERE d.numdir = :numdir"),
    @NamedQuery(name = "Direccione.findByLetra", query = "SELECT d FROM Direccione d WHERE d.letra = :letra"),
    @NamedQuery(name = "Direccione.findByPiso", query = "SELECT d FROM Direccione d WHERE d.piso = :piso"),
    @NamedQuery(name = "Direccione.findByEscalera", query = "SELECT d FROM Direccione d WHERE d.escalera = :escalera"),
    @NamedQuery(name = "Direccione.findByMano", query = "SELECT d FROM Direccione d WHERE d.mano = :mano"),
    @NamedQuery(name = "Direccione.findByCp", query = "SELECT d FROM Direccione d WHERE d.cp = :cp")})
public class Direccione implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDDIRECCION")
    private String iddireccion;
    @Column(name = "NUMDIR")
    private String numdir;
    @Column(name = "LETRA")
    private String letra;
    @Column(name = "PISO")
    private String piso;
    @Column(name = "ESCALERA")
    private String escalera;
    @Column(name = "MANO")
    private String mano;
    @Basic(optional = false)
    @Column(name = "CP")
    private String cp;
    @JoinColumn(name = "IDVIA", referencedColumnName = "IDVIA")
    @ManyToOne
    private Via idvia;

    public Direccione() {
    }

    public Direccione(String iddireccion) {
        this.iddireccion = iddireccion;
    }

    public Direccione(String iddireccion, String cp) {
        this.iddireccion = iddireccion;
        this.cp = cp;
    }

    public String getIddireccion() {
        return iddireccion;
    }

    public void setIddireccion(String iddireccion) {
        this.iddireccion = iddireccion;
    }

    public String getNumdir() {
        return numdir;
    }

    public void setNumdir(String numdir) {
        this.numdir = numdir;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getEscalera() {
        return escalera;
    }

    public void setEscalera(String escalera) {
        this.escalera = escalera;
    }

    public String getMano() {
        return mano;
    }

    public void setMano(String mano) {
        this.mano = mano;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public Via getIdvia() {
        return idvia;
    }

    public void setIdvia(Via idvia) {
        this.idvia = idvia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddireccion != null ? iddireccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Direccione)) {
            return false;
        }
        Direccione other = (Direccione) object;
        if ((this.iddireccion == null && other.iddireccion != null) || (this.iddireccion != null && !this.iddireccion.equals(other.iddireccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.UML.Direccione[ iddireccion=" + iddireccion + " ]";
    }
    
}
