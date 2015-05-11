
package proyectoudaleku;

import Modelo.UML.Via;
import gui.*;
import java.util.ArrayList;


public class Main {

    private static Inicio inic;
    private static panInicio panInic;
    private static panInscripcion panInscrip;
    
    private static ArrayList<Via> vias;
   
    
    public static void main(String[] args) {
        panInic = new panInicio();
        inic=new Inicio(); inic.setContentPane(panInic); panInic.setVisible(true);inic.setVisible(true);

    }

    public static void verPanInscrip() {
        panInscrip = new panInscripcion();
        inic.getContentPane().setVisible(false);
        inic.setContentPane(panInscrip);
        panInscrip.setVisible(true);
    }

    public static void cancelarPanel() {
        inic.getContentPane().setVisible(false);
        inic.setContentPane(panInic);panInic.setVisible(true);
    }

    public static void salir() {
        System.exit(0);
    }
    
    
}
