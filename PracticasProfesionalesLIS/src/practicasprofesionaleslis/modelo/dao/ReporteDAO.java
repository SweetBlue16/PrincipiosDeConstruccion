package practicasprofesionaleslis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.pojo.Reporte;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

public class ReporteDAO {
    
    public static boolean subirReporte(Reporte reporte, int idEntregaReporte, int idExpediente) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "INSERT INTO reporte (nombreArchivo, fechaEntregado, "
                        + "idEntregaReporte, idExpediente, archivo, horasCubiertas) "
                        + "VALUES (?, CURDATE(), ?, ?, ?, ?)";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setString(1, reporte.getNombreArchivo());
                sentencia.setInt(2, idEntregaReporte);
                sentencia.setInt(3, idExpediente);
                sentencia.setBytes(4, reporte.getArchivo());
                sentencia.setInt(5, reporte.getHorasCubiertas());
                
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
