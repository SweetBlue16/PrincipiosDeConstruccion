package practicasprofesionaleslis.modelo.dao;

import java.time.LocalDate;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
    
    public static EntregaReporte obtenerEntregaReportePorId(int idEntregaReporte) throws SQLException {
        EntregaReporte entrega = null;
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT idEntregaReporte, fechaInicio, fechaFin, numeroReporte, puntaje, idExperienciaEducativa " +
                                 "FROM entregareporte " +
                                 "WHERE idEntregaReporte = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idEntregaReporte);

                resultado = sentencia.executeQuery();
                if (resultado.next()) {
                    LocalDate fechaInicio = resultado.getDate("fechaInicio").toLocalDate();
                    LocalDate fechaFin = resultado.getDate("fechaFin").toLocalDate();
                    int numeroReporte = resultado.getInt("numeroReporte");
                    int puntaje = resultado.getInt("puntaje");

                    entrega = new EntregaReporte(
                        resultado.getInt("idEntregaReporte"),
                        fechaInicio,
                        fechaFin,
                        numeroReporte,
                        puntaje
                    );
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return entrega;
    }
    
    
    public static List<EntregaReporte> obtenerEntregasPorExperienciaEducativa(int idExperienciaEducativa) throws SQLException {
        List<EntregaReporte> entregas = new ArrayList<>();
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT idEntregaReporte, fechaInicio, fechaFin, numeroReporte, puntaje " +
                                 "FROM entregareporte " +
                                 "WHERE idExperienciaEducativa = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idExperienciaEducativa);

                resultado = sentencia.executeQuery();
                while (resultado.next()) {
                    LocalDate fechaInicio = resultado.getDate("fechaInicio").toLocalDate();
                    LocalDate fechaFin = resultado.getDate("fechaFin").toLocalDate();
                    int numeroReporte = resultado.getInt("numeroReporte");
                    int puntaje = resultado.getInt("puntaje");

                    EntregaReporte entrega = new EntregaReporte(
                        resultado.getInt("idEntregaReporte"),
                        fechaInicio,
                        fechaFin,
                        numeroReporte,
                        puntaje
                    );
                    entregas.add(entrega);
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return entregas;
    }
    
    public static List<EntregaReporte> obtenerEntregasDisponibles(int idExperienciaEducativa, int idExpediente) throws SQLException {
        List<EntregaReporte> entregasDisponibles = new ArrayList<>();
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT er.idEntregaReporte, er.idExperienciaEducativa, er.fechaInicio, "
                        + "er.fechaFin, er.numeroReporte, er.puntaje "
                        + "FROM entregareporte er "
                        + "LEFT JOIN reporte r ON er.idEntregaReporte = r.idEntregaReporte AND r.idExpediente = ? "
                        + "WHERE er.idExperienciaEducativa = ? "
                        + "AND r.id IS NULL "
                        + "AND CURDATE() BETWEEN er.fechaInicio AND er.fechaFin";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idExpediente);
                sentencia.setInt(2, idExperienciaEducativa);
                
                resultado = sentencia.executeQuery();
                while (resultado.next()) {
                    int id = resultado.getInt("idEntregaReporte");
                    LocalDate fechaInicio = resultado.getDate("fechaInicio").toLocalDate();
                    LocalDate fechaFin = resultado.getDate("fechaFin").toLocalDate();
                    int numeroReporte = resultado.getInt("numeroReporte");
                    int puntaje = resultado.getInt("puntaje");
                    EntregaReporte entregaReporte = new EntregaReporte(id, fechaInicio, fechaFin, puntaje, numeroReporte);
                    entregasDisponibles.add(entregaReporte);
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return entregasDisponibles;
    }
    
    public static List<EntregaReporte> obtenerTodasLasEntregasReporte() throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<EntregaReporte> entregas = new ArrayList<>();

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT idEntregaReporte, fechaInicio, fechaFin, puntaje, numeroReporte " 
                               + "FROM entregareporte";
                sentencia = conexionBD.prepareStatement(consulta);

                resultado = sentencia.executeQuery();
                while (resultado.next()) {
                    EntregaReporte entrega = new EntregaReporte();
                    entrega.setId(resultado.getInt("idEntregaReporte"));
                    entrega.setFechaInicio(resultado.getDate("fechaInicio").toLocalDate());
                    entrega.setFechaFin(resultado.getDate("fechaFin").toLocalDate());
                    entrega.setPuntaje(resultado.getInt("puntaje"));
                    entrega.setNumeroReporte(resultado.getInt("numeroReporte"));
                    
                    entregas.add(entrega);
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return entregas;
    }
    
    public static boolean actualizarFechaFinPorNumeroReporte(int numeroReporte, LocalDate nuevaFechaFin) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        boolean actualizado = false;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "UPDATE entregareporte SET fechaFin = ? WHERE numeroReporte = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setDate(1, Date.valueOf(nuevaFechaFin));
                sentencia.setInt(2, numeroReporte);

                int filasAfectadas = sentencia.executeUpdate();
                actualizado = filasAfectadas > 0;
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, null);
        }
        return actualizado;
    }
    
    public static LocalDate obtenerFechaInicioPorNumeroReporte(int numeroReporte) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        LocalDate fechaInicio = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT fechaInicio FROM entregareporte WHERE numeroReporte = ? LIMIT 1";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, numeroReporte);

                resultado = sentencia.executeQuery();
                if (resultado.next()) {
                    fechaInicio = resultado.getDate("fechaInicio").toLocalDate();
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return fechaInicio;
    }
}
