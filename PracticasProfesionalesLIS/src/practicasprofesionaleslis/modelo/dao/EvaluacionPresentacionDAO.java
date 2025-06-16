package practicasprofesionaleslis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.pojo.EvaluacionPresentacion;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

public class EvaluacionPresentacionDAO {
    
    public static List<EvaluacionPresentacion> obtenerNumeroEvaluaciones() throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<EvaluacionPresentacion> evaluaciones = new java.util.ArrayList<>();
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT DISTINCT numeroEvaluacion "
                        + "FROM evaluacionpresentacion "
                        + "ORDER BY numeroEvaluacion";
                sentencia = conexionBD.prepareStatement(consulta);
                resultado = sentencia.executeQuery();
                
                while (resultado.next()) {
                    EvaluacionPresentacion evaluacion = new EvaluacionPresentacion();
                    evaluacion.setNumeroEvaluacion(resultado.getInt("numeroEvaluacion"));
                    evaluaciones.add(evaluacion);
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        
        return evaluaciones;
    }
    
    public static int registrarEvaluacionPresentacion(EvaluacionPresentacion evaluacionPresentacion) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "INSERT INTO evaluacionpresentacion "
                        + "(numeroEvaluacion, calificacionFinal, fechaEvaluacion, comentario, idExpediente) "
                        + "VALUES (?, ?, ?, ?, ?)";
                sentencia = conexionBD.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
                sentencia.setInt(1, evaluacionPresentacion.getNumeroEvaluacion());
                sentencia.setDouble(2, evaluacionPresentacion.getCalificacionFinal());
                sentencia.setDate(3, java.sql.Date.valueOf(evaluacionPresentacion.getFechaEvaluacion()));
                sentencia.setString(4, evaluacionPresentacion.getComentario());
                sentencia.setInt(5, evaluacionPresentacion.getIdExpediente());
                
                int filasAfectadas = sentencia.executeUpdate();
                if (filasAfectadas > 0) {
                    resultado = sentencia.getGeneratedKeys();
                    if (resultado.next()) {
                        return resultado.getInt(1);
                    }
                }
                } else {
                    throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
                }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia);
        }
        
        return -1;
    }
    
    public static List<EvaluacionPresentacion> obtenerEvaluacionesPorExpediente(int idExpediente) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<EvaluacionPresentacion> evaluaciones = new ArrayList<>();
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT id, numeroEvaluacion, calificacionFinal, comentario "
                        + "FROM evaluacionpresentacion "
                        + "WHERE idExpediente = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idExpediente);
                
                resultado = sentencia.executeQuery();
                while (resultado.next()) {
                    EvaluacionPresentacion evaluacionPresentacion = new EvaluacionPresentacion();
                    evaluacionPresentacion.setIdEvaluacionPresentacion(resultado.getInt("id"));
                    evaluacionPresentacion.setNumeroEvaluacion(resultado.getInt("numeroEvaluacion"));
                    evaluacionPresentacion.setCalificacionFinal(resultado.getDouble("calificacionFinal"));
                    evaluacionPresentacion.setComentario(resultado.getString("comentario"));
                    evaluaciones.add(evaluacionPresentacion);
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return evaluaciones;
    }
}
