/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicasprofesionaleslis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.pojo.EvaluacionPresentacion;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

/**
 *
 * @author acrca
 */
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
    
        public static boolean registrarEvaluacionPresentacion(EvaluacionPresentacion evaluacionPresentacion) throws SQLException {
            Connection conexionBD = null;
            PreparedStatement sentencia = null;

            try {
                conexionBD = ConexionBD.abrirConexion();
                if (conexionBD != null) {
                    String consulta = "INSERT INTO evaluacionpresentacion "
                                      + "(numeroEvaluacion, calificacionFinal, fechaEvaluacion, comentario, idExpediente) "
                                      + "VALUES (?, ?, ?, ?, ?)";
                    sentencia = conexionBD.prepareStatement(consulta);
                    sentencia.setInt(1, evaluacionPresentacion.getNumeroEvaluacion());
                    sentencia.setDouble(2, evaluacionPresentacion.getCalificacionFinal());
                    sentencia.setDate(3, java.sql.Date.valueOf(evaluacionPresentacion.getFechaEvaluacion()));
                    sentencia.setString(4, evaluacionPresentacion.getComentario());
                    sentencia.setInt(5, evaluacionPresentacion.getIdExpediente());

                    int filasAfectadas = sentencia.executeUpdate();
                    return filasAfectadas > 0;
                } else {
                    throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
                }
            } finally {
                BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia);
            }
        }
    
}
