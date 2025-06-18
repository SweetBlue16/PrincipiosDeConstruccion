package practicasprofesionaleslis.utilidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Autor: Todos
 * Fecha de creación: 06/06/2025
 * Descripción: Proporciona métodos auxiliares para
 * manejar los recursos de la base de datos, como conexión,
 * sentencias y resultados de consultas.
 */
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
