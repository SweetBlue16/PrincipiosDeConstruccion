package practicasprofesionaleslis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
    
    public static List<Reporte> obtenerReportesPorExpediente(int idExpediente) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<Reporte> reportes = new ArrayList<>();
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT r.id, r.nombreArchivo, r.archivo, er.numeroReporte, r.horasCubiertas "
                        + "FROM reporte r "
                        + "JOIN entregareporte er ON r.idEntregaReporte = er.id "
                        + "WHERE r.idExpediente = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idExpediente);
                
                resultado = sentencia.executeQuery();
                while (resultado.next()) {
                    Reporte reporte = new Reporte();
                    reporte.setId(resultado.getInt("id"));
                    reporte.setNombreArchivo(resultado.getString("nombreArchivo"));
                    reporte.setArchivo(resultado.getBytes("archivo"));
                    reporte.setNumeroReporte(resultado.getInt("numeroReporte"));
                    reporte.setHorasCubiertas(resultado.getInt("horasCubiertas"));
                    reportes.add(reporte);
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return reportes;
    }
}
