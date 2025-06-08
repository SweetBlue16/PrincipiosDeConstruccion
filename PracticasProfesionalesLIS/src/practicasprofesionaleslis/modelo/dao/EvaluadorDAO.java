package practicasprofesionaleslis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.pojo.Evaluador;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

public class EvaluadorDAO {
    
    public static boolean editarEvaluador(Evaluador evaluador) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "UPDATE evaluador SET nombre = ?, apellidoPaterno = ?, "
                        + "apellidoMaterno = ?, contrasena = ?, fotoPerfil = ? "
                        + "WHERE numeroPersonal = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setString(1, evaluador.getNombre());
                sentencia.setString(2, evaluador.getApellidoPaterno());
                sentencia.setString(3, evaluador.getApellidoMaterno());
                sentencia.setString(4, evaluador.getContraseña());
                sentencia.setBytes(5, evaluador.getFotoPerfil());
                sentencia.setString(6, evaluador.getNumeroPersonal());
                
                int filasAfectadas = sentencia.executeUpdate();
                return filasAfectadas > 0;
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia);
        }
    }
    
    public static byte[] obtenerFotoEvaluador(int id) throws SQLException {
        byte[] foto = null;
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT fotoPerfil FROM evaluador "
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
