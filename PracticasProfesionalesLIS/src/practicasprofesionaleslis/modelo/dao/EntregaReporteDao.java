package practicasprofesionaleslis.modelo.dao;
import java.time.LocalDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.pojo.EntregaReporte;
/**
 *
 * @author yael
 */
public class EntregaReporteDao {
        public static EntregaReporte registrarReporte(LocalDate fechaInicio, LocalDate fechaFin,
                                                int puntaje, int numeroReporte,
                                                int idExperienciaEducativa) throws SQLException {
        Connection conexionBD = ConexionBD.abrirConexion();
        EntregaReporte reporteRegistrado = null;
        
        if (conexionBD != null) {
            String consulta = "INSERT INTO EntregaReporte " +
                             "(fechaInicio, fechaFin, numeroReporte, puntaje, idExperienciaEducativa) " +
                             "VALUES (?, ?, ?, ?, ?)";
            
            try (PreparedStatement sentencia = conexionBD.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS)) {
                sentencia.setDate(1, java.sql.Date.valueOf(fechaInicio));
                sentencia.setDate(2, java.sql.Date.valueOf(fechaFin));
                sentencia.setInt(3, numeroReporte);
                sentencia.setInt(4, puntaje);
                sentencia.setInt(5, idExperienciaEducativa);
                
                int filasAfectadas = sentencia.executeUpdate();
                
                if (filasAfectadas > 0) {
                    try (ResultSet generatedKeys = sentencia.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            reporteRegistrado = new EntregaReporte(
                                generatedKeys.getInt(1),
                                fechaInicio,
                                fechaFin,
                                numeroReporte,
                                puntaje
                            );
                        }
                    }
                }
            } finally {
                conexionBD.close();
            }
        }
        return reporteRegistrado;
    }
}
