package proyectoudaleku;

import Modelo.BD.LocalidadJpaController;
import Modelo.BD.MunicipioJpaController;
import Modelo.BD.ViaJpaController;
import Modelo.UML.Centro;
import Modelo.UML.CentroHasModelo;
import Modelo.UML.Localidad;
import Modelo.UML.Municipio;
import Modelo.UML.Via;
import conexionoracle.ConexionOracle;
import gui.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

public class Main {

    private static Inicio inic;
    private static panInicio panInic;
    private static panInscripcion panInscrip;
    private static panLupa panLupa;

    private static ConexionOracle conn;
    private static Connection conexion;
    private static PreparedStatement sentencia;
    private static ResultSet rset;

    private static ArrayList<Via> vias;
    private static ArrayList<Municipio> municipios;
    private static ArrayList<Localidad> localidades;
    private static ArrayList<Centro> centros;

     private static ArrayList<CentroHasModelo> centroHasModelo;

    private static String provincia;


    public static void main(String[] args) {
        panInic = new panInicio();
        inic = new Inicio();
        inic.setContentPane(panInic);
        panInic.setVisible(true);
        inic.setVisible(true);

        municipios = new ArrayList();
        localidades= new ArrayList();
        vias=new ArrayList();
        centros=new ArrayList();
        centroHasModelo=new ArrayList();

    }

    public static String getProvincia() {
        return provincia;
    }

    public static void setProvincia(String provincia) {
        Main.provincia = provincia;
    }
    
    public static void verPanInscrip() {
        panInscrip = new panInscripcion();
        inic.getContentPane().setVisible(false);
        inic.setContentPane(panInscrip);
        panInscrip.setVisible(true);
    }

    public static void cancelarPanel() {
        inic.getContentPane().setVisible(false);
        inic.setContentPane(panInic);
        panInic.setVisible(true);
    }

    public static void salir() {
        System.exit(0);
    }
/**
 * 
 * @param idProvincia
 * Método que me devuelve todos los municipios de la provincia seleccionada.
 * @return 
 */
    public static ArrayList<Municipio> findMunicipios(String idProvincia) {

        //MunicipioJpaController munJpa= new MunicipioJpaController(Persistence.createEntityManagerFactory("udalekuPU"));    
        //municipios=(ArrayList<Municipio>) munJpa.findMunicipioEntities2(idProvincia);
        conn = new ConexionOracle();
        //conectamos
        conn.setConexion();

        try {
            sentencia = conn.getConexion().prepareStatement("select idMunicipio, nombreMunic from municipios where upper(idProvincia)=? order by nombremunic");
            sentencia.setString(1, idProvincia);
            rset = sentencia.executeQuery();

            while (rset.next()) {
                municipios.add(new Municipio(rset.getLong("idMunicipio"), rset.getString(2)));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e.getMessage());
        }
        //desconectamos
        conn.desconectar();
        return municipios;
    }
    /**
     * 
     * @param idMunicipio
     * Método que me devuelve todas las localidades del municipio seleccionado.
     * @return 
     */
        public static ArrayList<Localidad> findLocalidades(String idMunicipio) {
        conn = new ConexionOracle();
        //conectamos
        conn.setConexion();

        try {
            sentencia = conn.getConexion().prepareStatement("select IDLOCALIDAD, NOMBRELOC from localidades where IDMUNICIPIO=? order by NOMBRELOC");
            sentencia.setString(1, idMunicipio);
            rset = sentencia.executeQuery();

            while (rset.next()) {
                localidades.add(new Localidad(rset.getLong("IDLOCALIDAD"), rset.getString(2)));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e.getMessage());
        }
        //desconectamos
        conn.desconectar();
        return localidades;
    }
        /**
         * 
         * @param idLocalidad
         * Método que devuelva todas las vías perteneciente a la localidad seleccionada
         * @return 
         */
   public static ArrayList<Via> findVias(String idLocalidad) {
        conn = new ConexionOracle();
        //conectamos
        conn.setConexion();

        try {
            sentencia = conn.getConexion().prepareStatement("select * from vias where idlocalidad=? order by nombreVia");
            sentencia.setString(1, idLocalidad);
            rset = sentencia.executeQuery();

            while (rset.next()) {
                vias.add(new Via(rset.getLong("idVia"), rset.getString("tipoVia"),rset.getString(3)));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e.getMessage());
        }
        //desconectamos
        conn.desconectar();
        return vias;
    }
   /**
    * 
    * @param idProvincia
    * @param seleccion
    * si ha seleccionado la misma provincia por la que ha entrado se cargan los centros de esa provincia (en cuyo caso seleccion = idProvincia)
    * sinocarga los restos que no esten en la provincia seleccionada al comienzo de la aplicacion
    * @return 
    */
      public static ArrayList<Centro> findCentros(String idProvincia, String seleccion) {

        conn = new ConexionOracle();
        //conectamos
        conn.setConexion();

        try {           
            if(seleccion.equalsIgnoreCase("ARB")||seleccion.equalsIgnoreCase("GZK")||seleccion.equalsIgnoreCase("BZK")){
            sentencia = conn.getConexion().prepareStatement("select idCentro, nombreCent from centros where upper(idProvincia)=? order by nombreCent");
            sentencia.setString(1, idProvincia);
            rset = sentencia.executeQuery();
            }else{               
            sentencia = conn.getConexion().prepareStatement("select idCentro, nombreCent from centros where upper(idProvincia)<>? order by nombreCent");
            sentencia.setString(1, idProvincia);
            rset = sentencia.executeQuery();            
            }

            while (rset.next()) {
                centros.add(new Centro(rset.getLong("idCentro"), rset.getString("nombreCent")));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e.getMessage());
        }
        //desconectamos
        conn.desconectar();
        return centros;
    }
      /**
       * 
       * @param idCentro
       * @return 
       *  sentencia para que devuelva todos los modelos que se imparten en el centro seleccionado
       */
      public static ArrayList<CentroHasModelo> findModelosCentro(Long idCentro){
           conn = new ConexionOracle();
        //conectamos
        conn.setConexion();

        try {
            sentencia = conn.getConexion().prepareStatement("select idCentro,idModelo from Centros_Has_Modelos where idCentro=?");
            sentencia.setLong(1, idCentro);
            rset = sentencia.executeQuery();

            while (rset.next()) {
                centroHasModelo.add(new CentroHasModelo(rset.getLong(1),rset.getString(2)));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e.getMessage());
        }
        //desconectamos
        conn.desconectar();
        return centroHasModelo;
      }
    

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

    public static void buildLupa(String tipo,String prov) {
        switch(tipo){
            case "municipios":
                panLupa=new panLupa(tipo, prov);
                panLupa.setVisible(true);
                break;
            case "centros":
                
                break;
    }
    }
}
