package practicasprofesionaleslis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.pojo.ProfesorEE;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

/**
 * Autor: Todos
 * Fecha de creación: 07/06/2025
 * Descripción: Gestiona las operaciones de bases de
 * datos relacionadas con la tabla Profesor EE.
 */
public class ProfesorEEDAO {
    
    public static boolean editarProfesorEE(ProfesorEE profesorEE) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "UPDATE profesoree SET nombre = ?, apellidoPaterno = ?, "
                        + "apellidoMaterno = ?, contrasena = ?, fotoPerfil = ? "
                        + "WHERE numeroPersonal = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setString(1, profesorEE.getNombre());
                sentencia.setString(2, profesorEE.getApellidoPaterno());
                sentencia.setString(3, profesorEE.getApellidoMaterno());
                sentencia.setString(4, profesorEE.getContraseña());
                sentencia.setBytes(5, profesorEE.getFotoPerfil());
                sentencia.setString(6, profesorEE.getNumeroPersonal());
                
                int filasAfectadas = sentencia.executeUpdate();
                return filasAfectadas > 0;
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia);
        }
    }
    
    public static byte[] obtenerFotoProfesorEE(int id) throws SQLException {
        byte[] foto = null;
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT fotoPerfil FROM profesoree "
                        + "WHERE id = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, id);
                
                resultado = sentencia.executeQuery();
                if (resultado.next()) {
                    foto = resultado.getBytes("fotoPerfil");
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return foto;
    }
    
    public static ProfesorEE obtenerProfesorPorCorreo(String correoInstitucional) throws SQLException {
        ProfesorEE profesor = null;
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT id, numeroPersonal, nombre, apellidoPaterno, " +
                               "apellidoMaterno, correoInstitucional, contrasena " +
                               "FROM profesoree " +
                               "WHERE correoInstitucional = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setString(1, correoInstitucional);

                resultado = sentencia.executeQuery();
                if (resultado.next()) {
                    profesor = new ProfesorEE();
                    profesor.setId(resultado.getInt("id"));
                    profesor.setNumeroPersonal(resultado.getString("numeroPersonal"));
                    profesor.setNombre(resultado.getString("nombre"));
                    profesor.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    profesor.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    profesor.setCorreoInstitucional(resultado.getString("correoInstitucional"));
                    profesor.setContraseña(resultado.getString("contrasena"));
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return profesor;
    }
}
