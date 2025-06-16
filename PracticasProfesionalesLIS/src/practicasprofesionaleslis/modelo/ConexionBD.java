package practicasprofesionaleslis.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String IP = "localhost";
    private static final String PUERTO = "3306";
    private static final String NOMBRE_BD = "pplis";
    private static final String USUARIO = "pplisAdmin";
    private static final String PASSWORD = "pplis-15811";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    
    public static Connection abrirConexion() {
        Connection conexionBD = null;
        String url = String.format(
            "jdbc:mysql://%s:%s/%s?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
            IP, PUERTO, NOMBRE_BD
        );
        
        try {
            Class.forName(DRIVER);
            conexionBD = DriverManager.getConnection(url, USUARIO, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conexionBD;
    }
}