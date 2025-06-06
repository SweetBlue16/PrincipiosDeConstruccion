package practicasprofesionaleslis.utilidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDeDatosUtils {
    public static void cerrarRecursos(Connection conexion, PreparedStatement sentencia, ResultSet resultado) throws SQLException {
        if (conexion != null) conexion.close();
        if (sentencia != null) sentencia.close();
        if (resultado != null) resultado.close();
    }
    
    public static void cerrarRecursos(Connection conexion, PreparedStatement sentencia) throws SQLException {
        if (conexion != null) conexion.close();
        if (sentencia != null) sentencia.close();
    }
}
