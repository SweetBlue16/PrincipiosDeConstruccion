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
import practicasprofesionaleslis.modelo.pojo.EntregaDocumentoFinal;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

/**
 * Autor: Todos
 * Fecha de creación: 07/06/2025
 * Descripción: Gestiona las operaciones de bases de
 * datos relacionadas con la tabla Entrega Documento Final.
 */
public class EntregaDocumentoFinalDAO {
    
    public static EntregaDocumentoFinal registrarDocumentoFinal(LocalDate fechaInicio, LocalDate fechaFin,
                                                              int puntaje, String tipoDocumento,
                                                              int idExperienciaEducativa) throws SQLException {
        EntregaDocumentoFinal documentoRegistrado = null;
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "INSERT INTO EntregaDoctoFinal " +
                                 "(fechaInicio, fechaFin, tipoDoctoFinal, puntaje, idExperienciaEducativa) " +
                                 "VALUES (?, ?, ?, ?, ?)";

                sentencia = conexionBD.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
                sentencia.setDate(1, Date.valueOf(fechaInicio));
                sentencia.setDate(2, Date.valueOf(fechaFin));
                sentencia.setString(3, tipoDocumento);
                sentencia.setInt(4, puntaje);
                sentencia.setInt(5, idExperienciaEducativa);

                int filasAfectadas = sentencia.executeUpdate();    
                if (filasAfectadas > 0) {
                    resultado = sentencia.getGeneratedKeys();
                    if (resultado.next()) {
                        documentoRegistrado = new EntregaDocumentoFinal(resultado.getInt(1), fechaInicio, fechaFin, tipoDocumento, puntaje);
                    }
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return documentoRegistrado;
    }
    
    public static EntregaDocumentoFinal obtenerEntregaDocumentoFinalPorId(int idEntregaDocumentoFinal) throws SQLException {
        EntregaDocumentoFinal entrega = null;
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT id, fechaInicio, fechaFin, tipoDoctoFinal, puntaje, idExperienciaEducativa " +
                                 "FROM entregadoctofinal " +
                                 "WHERE id = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idEntregaDocumentoFinal);

                resultado = sentencia.executeQuery();
                if (resultado.next()) {
                    LocalDate fechaInicio = resultado.getDate("fechaInicio").toLocalDate();
                    LocalDate fechaFin = resultado.getDate("fechaFin").toLocalDate();
                    String tipoDocumento = resultado.getString("tipoDoctoFinal");
                    int puntaje = resultado.getInt("puntaje");

                    entrega = new EntregaDocumentoFinal(
                        resultado.getInt("id"),
                        fechaInicio,
                        fechaFin,
                        puntaje,
                        tipoDocumento
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
    
    public static List<EntregaDocumentoFinal> obtenerEntregasPorExperienciaEducativa(int idExperienciaEducativa) throws SQLException {
        List<EntregaDocumentoFinal> entregas = new ArrayList<>();
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT id, fechaInicio, fechaFin, tipoDoctoFinal, puntaje " +
                                 "FROM entregadoctofinal " +
                                 "WHERE idExperienciaEducativa = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idExperienciaEducativa);

                resultado = sentencia.executeQuery();
                while (resultado.next()) {
                    LocalDate fechaInicio = resultado.getDate("fechaInicio").toLocalDate();
                    LocalDate fechaFin = resultado.getDate("fechaFin").toLocalDate();
                    String tipoDocumento = resultado.getString("tipoDoctoFinal");
                    int puntaje = resultado.getInt("puntaje");

                    EntregaDocumentoFinal entrega = new EntregaDocumentoFinal(
                        resultado.getInt("id"),
                        fechaInicio,
                        fechaFin,
                        puntaje,
                        tipoDocumento
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
    
    public static List<EntregaDocumentoFinal> obtenerEntregasDisponibles(int idExperienciaEducativa, int idExpediente) throws SQLException {
        List<EntregaDocumentoFinal> entregasDisponibles = new ArrayList<>();
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT edf.id, edf.idExperienciaEducativa, edf.fechaInicio, "
                        + "edf.fechaFin, edf.tipoDoctoFinal, edf.puntaje "
                        + "FROM entregadoctofinal edf "
                        + "LEFT JOIN documentofinal df ON edf.id = df.idEntregaDoctoFinal AND df.idExpediente = ? "
                        + "WHERE edf.idExperienciaEducativa = ? "
                        + "AND df.id IS NULL "
                        + "AND CURDATE() BETWEEN edf.fechaInicio AND edf.fechaFin";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idExpediente);
                sentencia.setInt(2, idExperienciaEducativa);
                
                resultado = sentencia.executeQuery();
                while (resultado.next()) {
                    int id = resultado.getInt("id");
                    LocalDate fechaInicio = resultado.getDate("fechaInicio").toLocalDate();
                    LocalDate fechaFin = resultado.getDate("fechaFin").toLocalDate();
                    String tipoDocumentoFinal = resultado.getString("tipoDoctoFinal");
                    int puntaje = resultado.getInt("puntaje");
                    EntregaDocumentoFinal entregaDocumentoInicial = new EntregaDocumentoFinal(id, fechaInicio, fechaFin, puntaje, tipoDocumentoFinal);
                    entregasDisponibles.add(entregaDocumentoInicial);
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return entregasDisponibles;
    }
    
    public static boolean actualizarFechasEntregaFinal(int id, LocalDate fechaInicio, LocalDate fechaFin) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        boolean actualizado = false;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "UPDATE entregadoctofinal SET fechaInicio = ?, fechaFin = ? WHERE id = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setDate(1, Date.valueOf(fechaInicio));
                sentencia.setDate(2, Date.valueOf(fechaFin));
                sentencia.setInt(3, id);

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
}
