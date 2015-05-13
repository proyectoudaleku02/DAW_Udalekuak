package proyectoudaleku;

import Modelo.BD.LocalidadJpaController;
import Modelo.BD.MunicipioJpaController;
import Modelo.BD.ViaJpaController;
import Modelo.UML.Centro;
import Modelo.UML.Localidad;
import Modelo.UML.Municipio;
import Modelo.UML.Provincia;
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
import javax.swing.JComboBox;
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
    
    private static Provincia provSelected;
    private static Municipio munSelected;
    private static Localidad locSelected;
    private static Via viaSelected;
    private static Centro centSelected;
    

    public static void main(String[] args) {
        panInic = new panInicio();
        inic = new Inicio();
        inic.setContentPane(panInic);
        panInic.setVisible(true);
        inic.setVisible(true);
        
        municipios = new ArrayList(); localidades= new ArrayList();
        vias=new ArrayList(); centros=new ArrayList(); 
               
        provSelected=new Provincia();provSelected.setNombreprov("ARB");
        munSelected=new Municipio(); locSelected=new Localidad();
        viaSelected=new Via(); centSelected=new Centro();

        llenarPaPruebas();
    }
    
    private static void llenarPaPruebas() {
        municipios.add(new Municipio());municipios.get(0).setNombremunic("mun1");
        municipios.add(new Municipio());municipios.get(1).setNombremunic("mun2");
        municipios.add(new Municipio());municipios.get(2).setNombremunic("mun3");
        
        localidades.add(new Localidad());localidades.get(0).setNombreloc("loc1");
        localidades.add(new Localidad());localidades.get(1).setNombreloc("loc2");
        localidades.add(new Localidad());localidades.get(2).setNombreloc("loc3");
        
        vias.add(new Via());vias.get(0).setNombrevia("via1");vias.get(0).setTipovia("calle");
        vias.add(new Via());vias.get(1).setNombrevia("via2");vias.get(1).setTipovia("calle");
        vias.add(new Via());vias.get(2).setNombrevia("via3");vias.get(2).setTipovia("calle");
        
        centros.add(new Centro());centros.get(0).setNombrecent("cent1");
        centros.add(new Centro());centros.get(1).setNombrecent("cent2");
        centros.add(new Centro());centros.get(2).setNombrecent("cent3");
    }
    
    public static Provincia getProvSelected() {
        return provSelected;
    }

    public static void setProvSelected(Provincia provSelected) {
        Main.provSelected = provSelected;
    }

    public static Municipio getMunSelected() {
        return munSelected;
    }

    public static void setMunSelected(Municipio munSelected) {
        Main.munSelected = munSelected;
    }

    public static Localidad getLocSelected() {
        return locSelected;
    }

    public static void setLocSelected(Localidad locSelected) {
        Main.locSelected = locSelected;
    }
    
    public static Via getViaSelected() {
        return viaSelected;
    }

    public static void setViaSelected(Via viaSelected) {
        Main.viaSelected = viaSelected;
    }

    public static Centro getCentSelected() {
        return centSelected;
    }

    public static void setCentSelected(Centro centSelected) {
        Main.centSelected = centSelected;
    }

    public static ArrayList<Municipio> getMunicipios() {
        return municipios;
    }

    public static ArrayList<Localidad> getLocalidades() {
        return localidades;
    }
   
    public static ArrayList<Via> getVias() {
        return vias;
    }    

    public static ArrayList<Centro> getCentros() {
        return centros;
    }
    
    // Control de paneles.
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
    
    public static void buildLupa(String tipo,String loc,String mun) {
        switch(tipo){
            case "calles":
                panLupa=new panLupa(tipo, loc);
                panLupa.setVisible(true);
                break;
            case "centros":
                panLupa=new panLupa(tipo, mun);
                panLupa.setVisible(true);                
                break;
    }
    }
    
    public static void sendViaToInscripcion() {
        cancelarLupa();
        panInscrip.rellenarTfCalle();
    }
    public static void cancelarLupa() {
        panLupa.dispose();
    }

    public static void fillComboMun(JComboBox cbMunicipio) {
        // Vaciamos lista desplegable de municipios.
        //cbMunicipio.removeAllItems();
        // Llenar lista desplegable de municipios.
        for(int x=0;x<municipios.size();x++)
        {
            cbMunicipio.insertItemAt(municipios.get(x).getNombremunic(), x);
        }
    }

    public static void fillComboLoc(JComboBox cbLocalidad) {
        // Vaciamos lista desplegable de municipios.
        //cbLocalidad.removeAllItems();
        // Llenar lista desplegable de municipios.
        for(int x=0;x<localidades.size();x++)
        {
            cbLocalidad.insertItemAt(localidades.get(x).getNombreloc(), x);
        }
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
