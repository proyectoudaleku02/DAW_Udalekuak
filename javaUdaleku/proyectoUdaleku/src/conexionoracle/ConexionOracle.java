package conexionoracle;

import oracle.jdbc.OracleTypes;
import java.sql.CallableStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionOracle {


    private static Connection conn;

    public static Connection getConexion() {
        return conn;
    }

    public static void setConexion() {

        //obtener el driver de la conexion para mysql Class.forName("com.mysql.jdbc.Driver");
        //obtener la conexion
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection("jdbc:oracle:thin:@server224:1521:orcl", "daw02", "daw02");
        } catch (SQLException e) {
            System.out.println();
        } catch (Exception e) {
            System.out.println();
        }

    }

    public static void desconectar() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println();
        } catch (Exception e) {
            System.out.println();
        }
    }

}
