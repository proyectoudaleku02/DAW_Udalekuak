/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excepciones;


public class CampoVacio extends Exception{
    
    private final String mensaje = "Nigún campo obligatorio debe quedar vacío";

    public String getMensaje() {
        return mensaje;
    }
}
