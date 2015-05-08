"jdbc:oracle:thin:@server224:1521:orcl","proyecto","proyecto"
// driver@machineName:port:SID ,  userid,  password



package conexionoracle;

import oracle.jdbc.OracleTypes;
import java.sql.CallableStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

 

public class ConexionOracle {

    public static void main(String[] args) {
        
       Connection conn = null;
       try
       {
          DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
          conn = DriverManager.getConnection("jdbc:oracle:thin:@server224:1521:orcl","proyecto","proyecto");
                                               // driver@machineName:port:SID ,  userid,  password
          
          System.out.println("Base de datos abierta");
            
          // Ejecución de una sentencia SQL sin parámetros
           Statement stmt = conn.createStatement();
           ResultSet rset = stmt.executeQuery("select * from SYS.V_$VERSION");
           while (rset.next()){
                 System.out.println (rset.getString(1)); 
            }
            stmt.close();
            
            // Ejecución de un procedimiento que contiene una sentencia insert
            String sql ="{call gest_depart.insert_depart(?,?)}";
            CallableStatement cs = conn.prepareCall(sql);
               
            // Cargamos los parametros de entrada IN
            cs.setString(1,"dNieves2");
            cs.setString(2,"dNieves2");
            
            // Ejecutamos
            cs.execute();
            System.out.println("Procedimiento insert ejecutado");
            
            // visualizar los datos de la tabla departamentos a través de un procedimiento (select)
           sql ="{call gest_depart.visualizar_lista_depart(?)}";
           cs = conn.prepareCall(sql);
           // la select devuelve datos (parámetro de salida)
           cs.registerOutParameter(1,OracleTypes.CURSOR);
 
           cs.execute();
 
            // Con getObject Obtenemos un valor generico al que posteriormente se le hará cast para convertirlo en el tipo adecuado en este caso ResultSet
            rset = (ResultSet)cs.getObject(1);
            
            while(rset.next()){
                System.out.println(rset.getString(1) + rset.getString(2));
            }
            
            // procedimento con un update       
            sql ="{call gest_depart.cambiar_localidad(?,?)}";
            cs = conn.prepareCall(sql);
               
            cs.setInt(1,50);
            cs.setString(2,"Vitria");
 
            cs.execute();
            cs.close();
            conn.close();
     
            System.out.println("Procedimiento update");
    }
    catch(Exception e){
        System.out.println("Problemas");
    }
  }   
}