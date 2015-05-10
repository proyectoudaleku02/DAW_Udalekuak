/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.UML;

import java.util.Date;

/**
 *
 * @author Noemi
 */
public class Sorteo {
    private String idSorteo;
    private int numSorteo;
    private int cadenciaSort;
    private Date fechaSort;
    private Date fechaCierre;
    private Date fechaApertura;

    public Sorteo() {
    }

    public Sorteo(String idSorteo, int numSorteo, int cadenciaSort, Date fechaSort, Date fechaCierre, Date fechaApertura) {
        this.idSorteo = idSorteo;
        this.numSorteo = numSorteo;
        this.cadenciaSort = cadenciaSort;
        this.fechaSort = fechaSort;
        this.fechaCierre = fechaCierre;
        this.fechaApertura = fechaApertura;
    }

    public String getIdSorteo() {
        return idSorteo;
    }

    public void setIdSorteo(String idSorteo) {
        this.idSorteo = idSorteo;
    }

    public int getNumSorteo() {
        return numSorteo;
    }

    public void setNumSorteo(int numSorteo) {
        this.numSorteo = numSorteo;
    }

    public int getCadenciaSort() {
        return cadenciaSort;
    }

    public void setCadenciaSort(int cadenciaSort) {
        this.cadenciaSort = cadenciaSort;
    }

    public Date getFechaSort() {
        return fechaSort;
    }

    public void setFechaSort(Date fechaSort) {
        this.fechaSort = fechaSort;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }
    
    
    
}
