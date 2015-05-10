/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.UML;
import java.util.Arrays;
/**
 *
 * @author Noemi
 */
public class Tutor extends Solicitante{
    
    private String dni;
    private int telefonos[];

    public Tutor(String dni, int[] telefonos) {
        this.dni = dni;
        this.telefonos = telefonos;
    }

    public Tutor() {
    }
    
    

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int[] getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(int[] telefonos) {
        this.telefonos = telefonos;
    }
    
    
    
}
