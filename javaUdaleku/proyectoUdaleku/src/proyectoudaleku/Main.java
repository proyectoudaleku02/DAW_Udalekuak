
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
    
<<<<<<< HEAD
    
=======
        public static boolean sendTutor(String dni, String nombre, String apel1, String apel2) {

        return true;
    }

    public static boolean sendMenor(String dni, String nombre, String apel1, String apel2, String sexo, String fechaNac, String discapacidad) {

        return true;
    }

    public static boolean sendDireccion(String municipio, String localidad, String calle, String cp, String numero, String letra, String piso, String escalera, String mano, ArrayList<String> telefonos) {

        return true;
    }

    public static boolean sendCentro(String provCentro, String nomCentro, String modelo) {

        return true;
    }
>>>>>>> 7dbd3f512f8c7c11b89012fdfe945d6ab037dcab
}
