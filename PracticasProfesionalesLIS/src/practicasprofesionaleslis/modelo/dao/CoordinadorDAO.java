package practicasprofesionaleslis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.pojo.Coordinador;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

/**
 * Autor: Todos
 * Fecha de creación: 07/06/2025
 * Descripción: Gestiona las operaciones de bases de
 * datos relacionadas con la tabla Coordinador.
 */
public class CoordinadorDAO {
    
    public static boolean editarCoordinador(Coordinador coordinador) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "UPDATE coordinador SET nombre = ?, apellidoPaterno = ?, "
                        + "apellidoMaterno = ?, contrasena = ?, fotoPerfil = ? "
                        + "WHERE numeroPersonal = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setString(1, coordinador.getNombre());
                sentencia.setString(2, coordinador.getApellidoPaterno());
                sentencia.setString(3, coordinador.getApellidoMaterno());
                sentencia.setString(4, coordinador.getContraseña());
                sentencia.setBytes(5, coordinador.getFotoPerfil());
                sentencia.setString(6, coordinador.getNumeroPersonal());
                
                int filasAfectadas = sentencia.executeUpdate();
                return filasAfectadas > 0;
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia);
        }
    }
    
    public static byte[] obtenerFotoCoordinador(int id) throws SQLException {
        byte[] foto = null;
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT fotoPerfil FROM coordinador "
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
    
        public static Coordinador obtenerCoordinadorPorCorreo(String correoInstitucional) throws SQLException {
        Coordinador coordinador = null;
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT id, numeroPersonal, nombre, apellidoPaterno, " +
                               "apellidoMaterno, correoInstitucional, contrasena, fotoPerfil " +
                               "FROM coordinador " +
                               "WHERE correoInstitucional = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setString(1, correoInstitucional);

                resultado = sentencia.executeQuery();
                if (resultado.next()) {
                    coordinador = new Coordinador();
                    coordinador.setId(resultado.getInt("id"));
                    coordinador.setNumeroPersonal(resultado.getString("numeroPersonal"));
                    coordinador.setNombre(resultado.getString("nombre"));
                    coordinador.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    coordinador.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    coordinador.setCorreoInstitucional(resultado.getString("correoInstitucional"));
                    coordinador.setContraseña(resultado.getString("contrasena"));
                    coordinador.setFotoPerfil(resultado.getBytes("fotoPerfil"));
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return coordinador;
    }
}
