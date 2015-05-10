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
public class Solicitud {
    private int idSolicitud;
    
    private String situacionSolc;
    private int ordenSorteo;
    private Date fechaHoraCita;

    public Solicitud() {
    }

    public Solicitud(int idSolicitud, String situacionSolc) {
        this.idSolicitud = idSolicitud;
        this.situacionSolc = situacionSolc;
    }

    public Solicitud(int idSolicitud, String situacionSolc, int ordenSorteo, Date fechaHoraCita) {
        this.idSolicitud = idSolicitud;
        this.situacionSolc = situacionSolc;
        this.ordenSorteo = ordenSorteo;
        this.fechaHoraCita = fechaHoraCita;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getSituacionSolc() {
        return situacionSolc;
    }

    public void setSituacionSolc(String situacionSolc) {
        this.situacionSolc = situacionSolc;
    }

    public int getOrdenSorteo() {
        return ordenSorteo;
    }

    public void setOrdenSorteo(int ordenSorteo) {
        this.ordenSorteo = ordenSorteo;
    }

    public Date getFechaHoraCita() {
        return fechaHoraCita;
    }

    public void setFechaHoraCita(Date fechaHoraCita) {
        this.fechaHoraCita = fechaHoraCita;
    }
    
    
    
    
}
