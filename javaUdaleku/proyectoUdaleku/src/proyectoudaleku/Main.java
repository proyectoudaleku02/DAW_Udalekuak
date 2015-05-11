
package proyectoudaleku;

import gui.*;


public class Main {

    private static Inicio inic;
    private static panInicio panInic;
    private static panInscripcion panInscrip;
    
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
