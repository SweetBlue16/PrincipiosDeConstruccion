package practicasprofesionaleslis.modelo.dao;

import java.time.LocalDate;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.pojo.EntregaReporte;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

public class EntregaReporteDAO {
    
    public static EntregaReporte registrarReporte(LocalDate fechaInicio, LocalDate fechaFin,
                                                int puntaje, int numeroReporte,
                                                int idExperienciaEducativa) throws SQLException {
        EntregaReporte reporteRegistrado = null;
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "INSERT INTO EntregaReporte " +
                                 "(fechaInicio, fechaFin, numeroReporte, puntaje, idExperienciaEducativa) " +
                                 "VALUES (?, ?, ?, ?, ?)";

                sentencia = conexionBD.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
                sentencia.setDate(1, Date.valueOf(fechaInicio));
                sentencia.setDate(2, Date.valueOf(fechaFin));
                sentencia.setInt(3, numeroReporte);
                sentencia.setInt(4, puntaje);
                sentencia.setInt(5, idExperienciaEducativa);

                int filasAfectadas = sentencia.executeUpdate();
                if (filasAfectadas > 0) {
                    resultado = sentencia.getGeneratedKeys();
                    if (resultado.next()) {
                        reporteRegistrado = new EntregaReporte(resultado.getInt(1), fechaInicio, fechaFin, numeroReporte, puntaje);
                    }
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return reporteRegistrado;
    }
}
