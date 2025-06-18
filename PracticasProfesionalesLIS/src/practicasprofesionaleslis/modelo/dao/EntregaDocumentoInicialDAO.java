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
import practicasprofesionaleslis.modelo.pojo.EntregaDocumentoInicial;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

/**
 * Autor: Todos
 * Fecha de creación: 03/06/2025
 * Descripción: Gestiona las operaciones de bases de
 * datos relacionadas con la tabla Entrega Documento Inicial.
 */
public class EntregaDocumentoInicialDAO {
    
    public static EntregaDocumentoInicial registrarDocumentoInicial(LocalDate fechaInicio, LocalDate fechaFin, 
                                                              int puntaje, String tipoDocumento, 
                                                              int idExperienciaEducativa) throws SQLException {
        EntregaDocumentoInicial documentoRegistrado = null;
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "INSERT INTO EntregaDoctolnicial " +
                                 "(fechaInicio, fechaFin, tipoDoctolnicial, puntaje, idExperienciaEducativa) " +
                                 "VALUES (?, ?, ?, ?, ?);";

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
                        int idGenerado = resultado.getInt(1);
                        documentoRegistrado = new EntregaDocumentoInicial(idGenerado, fechaInicio, fechaFin, tipoDocumento, puntaje);
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
    
    public static EntregaDocumentoInicial obtenerEntregaDocumentoInicialPorId(int idEntregaDocumentoInicial) throws SQLException {
        EntregaDocumentoInicial entrega = null;
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT id, fechaInicio, fechaFin, tipoDoctoInicial, puntaje, idExperienciaEducativa " +
                                 "FROM entregadoctoinicial " +
                                 "WHERE id = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idEntregaDocumentoInicial);

                resultado = sentencia.executeQuery();
                if (resultado.next()) {
                    LocalDate fechaInicio = resultado.getDate("fechaInicio").toLocalDate();
                    LocalDate fechaFin = resultado.getDate("fechaFin").toLocalDate();
                    String tipoDocumento = resultado.getString("tipoDoctoInicial");
                    int puntaje = resultado.getInt("puntaje");

                    entrega = new EntregaDocumentoInicial(
                        resultado.getInt("id"),
                        fechaInicio,
                        fechaFin,
                        tipoDocumento,
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

    public static List<EntregaDocumentoInicial> obtenerEntregasPorExperienciaEducativa(int idExperienciaEducativa) throws SQLException {
        List<EntregaDocumentoInicial> entregas = new ArrayList<>();
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT id, fechaInicio, fechaFin, tipoDoctoInicial, puntaje " +
                                 "FROM entregadoctoinicial " +
                                 "WHERE idExperienciaEducativa = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idExperienciaEducativa);

                resultado = sentencia.executeQuery();
                while (resultado.next()) {
                    LocalDate fechaInicio = resultado.getDate("fechaInicio").toLocalDate();
                    LocalDate fechaFin = resultado.getDate("fechaFin").toLocalDate();
                    String tipoDocumento = resultado.getString("tipoDoctoInicial");
                    int puntaje = resultado.getInt("puntaje");

                    EntregaDocumentoInicial entrega = new EntregaDocumentoInicial(
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
    
    public static List<EntregaDocumentoInicial> obtenerEntregasDisponibles(int idExperienciaEducativa, int idExpediente) throws SQLException {
        List<EntregaDocumentoInicial> entregasDisponibles = new ArrayList<>();
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT edi.id, edi.idExperienciaEducativa, edi.fechaInicio, "
                        + "edi.fechaFin, edi.tipoDoctoInicial, edi.puntaje "
                        + "FROM entregadoctoinicial edi "
                        + "LEFT JOIN documentoinicial di ON edi.id = di.idEntregaDoctoInicial AND di.idExpediente = ? "
                        + "WHERE edi.idExperienciaEducativa = ? "
                        + "AND di.id IS NULL "
                        + "AND CURDATE() BETWEEN edi.fechaInicio AND edi.fechaFin";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idExpediente);
                sentencia.setInt(2, idExperienciaEducativa);
                
                resultado = sentencia.executeQuery();
                while (resultado.next()) {
                    int id = resultado.getInt("id");
                    LocalDate fechaInicio = resultado.getDate("fechaInicio").toLocalDate();
                    LocalDate fechaFin = resultado.getDate("fechaFin").toLocalDate();
                    String tipoDocumentoInicial = resultado.getString("tipoDoctoInicial");
                    int puntaje = resultado.getInt("puntaje");
                    EntregaDocumentoInicial entregaDocumentoInicial = new EntregaDocumentoInicial(id, fechaInicio, fechaFin, puntaje, tipoDocumentoInicial);
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
    
    public static boolean actualizarFechasEntregaInicial(int id, LocalDate fechaInicio, LocalDate fechaFin) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        boolean actualizado = false;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "UPDATE entregadoctoinicial SET fechaInicio = ?, fechaFin = ? WHERE id = ?";
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
