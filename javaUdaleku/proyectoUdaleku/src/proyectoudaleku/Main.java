package proyectoudaleku;

import Modelo.BD.LocalidadJpaController;
import Modelo.BD.MunicipioJpaController;
import Modelo.BD.ViaJpaController;
import Modelo.UML.Centro;
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

    private static ConexionOracle conn;
    private static Connection conexion;
    private static PreparedStatement sentencia;
    private static ResultSet rset;

    private static ArrayList<Via> vias;
    private static ArrayList<Municipio> municipios;
    private static ArrayList<Localidad> localidades;
    private static ArrayList<Centro> centros;

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

    public static ArrayList<Municipio> findMunicipios(String idProvincia) {

        //MunicipioJpaController munJpa= new MunicipioJpaController(Persistence.createEntityManagerFactory("udalekuPU"));    
        //municipios=(ArrayList<Municipio>) munJpa.findMunicipioEntities2(idProvincia);
        conn = new ConexionOracle();
        //conectamos
        conn.setConexion();

        try {
            //preparamos la sentencia y la ejecutamos recogiendo el resultado
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
        public static ArrayList<Localidad> findLocalidades(String idMunicipio) {

        //MunicipioJpaController munJpa= new MunicipioJpaController(Persistence.createEntityManagerFactory("udalekuPU"));    
        //municipios=(ArrayList<Municipio>) munJpa.findMunicipioEntities2(idProvincia);
        conn = new ConexionOracle();
        //conectamos
        conn.setConexion();

        try {
            //preparamos la sentencia y la ejecutamos recogiendo el resultado
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
   public static ArrayList<Via> findVias(String idLocalidad) {

        //MunicipioJpaController munJpa= new MunicipioJpaController(Persistence.createEntityManagerFactory("udalekuPU"));    
        //municipios=(ArrayList<Municipio>) munJpa.findMunicipioEntities2(idProvincia);
        conn = new ConexionOracle();
        //conectamos
        conn.setConexion();

        try {
            //preparamos la sentencia y la ejecutamos recogiendo el resultado
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
      public static ArrayList<Centro> findCentros(String idProvincia) {

        //MunicipioJpaController munJpa= new MunicipioJpaController(Persistence.createEntityManagerFactory("udalekuPU"));    
        //municipios=(ArrayList<Municipio>) munJpa.findMunicipioEntities2(idProvincia);
        conn = new ConexionOracle();
        //conectamos
        conn.setConexion();

        try {
            //preparamos la sentencia y la ejecutamos recogiendo el resultado
            sentencia = conn.getConexion().prepareStatement("select * from centros where upper(idProvincia)=? order by nombreCent");
            sentencia.setString(1, idProvincia);
            rset = sentencia.executeQuery();

            while (rset.next()) {
                //centros.add(new Centro(rset.getLong("idCentro"), rset.getString("nombrecent")));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e.getMessage());
        }
        //desconectamos
        conn.desconectar();
        return centros;
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

}
