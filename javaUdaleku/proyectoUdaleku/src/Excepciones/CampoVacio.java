/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excepciones;


public class CampoVacio extends Exception{
    
    private String message;

    public CampoVacio() {
        message = "Nigún campo obligatorio debe quedar vacío";
    }
    
}
