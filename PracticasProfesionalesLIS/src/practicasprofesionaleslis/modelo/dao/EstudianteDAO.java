package practicasprofesionaleslis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.pojo.Estudiante;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

public class EstudianteDAO {
    
    public static boolean editarEstudiante(Estudiante estudiante) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "UPDATE estudiante SET nombre = ?, apellidoPaterno = ?, "
                        + "apellidoMaterno = ?, contrasena = ?, fotoPerfil = ? "
                        + "WHERE matricula = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setString(1, estudiante.getNombre());
                sentencia.setString(2, estudiante.getApellidoPaterno());
                sentencia.setString(3, estudiante.getApellidoMaterno());
                sentencia.setString(4, estudiante.getContraseña());
                sentencia.setBytes(5, estudiante.getFotoPerfil());
                sentencia.setString(6, estudiante.getMatricula());
                
                int filasAfectadas = sentencia.executeUpdate();
                return filasAfectadas > 0;
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia);
        }
    }
    
    public static byte[] obtenerFotoEstudiante(int id) throws SQLException {
        byte[] foto = null;
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT fotoPerfil FROM estudiante "
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
}
