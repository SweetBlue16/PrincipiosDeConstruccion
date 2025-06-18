package practicasprofesionaleslis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.pojo.DocumentoInicial;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

/**
 * Autor: Todos
 * Fecha de creación: 14/06/2025
 * Descripción: Gestiona las operaciones de bases de
 * datos relacionadas con la tabla Documento Inicial.
 */
public class DocumentoInicialDAO {
    
    public static boolean subirDocumentoInicial(DocumentoInicial documento, int idEntregaDocumentoInicial, int idExpediente) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "INSERT INTO documentoinicial (nombreArchivo, fechaEntregado, "
                        + "idEntregaDoctoInicial, idExpediente, archivo) "
                        + "VALUES (?, CURDATE(), ?, ?, ?)";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setString(1, documento.getNombreArchivo());
                sentencia.setInt(2, idEntregaDocumentoInicial);
                sentencia.setInt(3, idExpediente);
                sentencia.setBytes(4, documento.getArchivo());
                
                int filasAfectadas = sentencia.executeUpdate();
                return filasAfectadas > 0;
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia);
        }
    }
    
    public static boolean existeDocumentoInicial(int idExpediente) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT COUNT(*) FROM documentoinicial WHERE idExpediente = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idExpediente);
                
                resultado = sentencia.executeQuery();
                if (resultado.next()) {
                    return resultado.getInt(1) > 0;
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return false;
    }
    
    public static List<DocumentoInicial> obtenerDocumentosInicialesPorExpediente(int idExpediente) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<DocumentoInicial> documentos = new ArrayList<>();
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT di.id, di.nombreArchivo, di.archivo, edi.tipoDoctoInicial "
                        + "FROM documentoinicial di "
                        + "JOIN entregadoctoinicial edi ON di.idEntregaDoctoInicial = edi.id "
                        + "WHERE di.idExpediente = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idExpediente);
                
                resultado = sentencia.executeQuery();
                while (resultado.next()) {
                    DocumentoInicial documentoInicial = new DocumentoInicial();
                    documentoInicial.setId(resultado.getInt("id"));
                    documentoInicial.setNombreArchivo(resultado.getString("nombreArchivo"));
                    documentoInicial.setArchivo(resultado.getBytes("archivo"));
                    String tipoDocumentoInicialString = resultado.getString("tipoDoctoInicial").toUpperCase();
                    documentoInicial.setTipoDocumentoInicial(DocumentoInicial.TipoDocumentoInicial.valueOf(tipoDocumentoInicialString));
                    documentos.add(documentoInicial);
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return documentos;
    }
    
    public static DocumentoInicial obtenerDocumentoInicial(int idExpediente, int idEntregaDocumentoInicial) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        DocumentoInicial documentoInicial = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT di.id, di.nombreArchivo, di.fechaEntregado, di.fechaRevisado, "
                        + "di.puntajeObtenido, di.comentario, di.archivo, edi.tipoDoctoInicial "
                        + "FROM documentoinicial di "
                        + "JOIN entregadoctoinicial edi ON di.idEntregaDoctoInicial = edi.id "
                        + "WHERE di.idExpediente = ? AND di.idEntregaDoctoInicial = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idExpediente);
                sentencia.setInt(2, idEntregaDocumentoInicial);

                resultado = sentencia.executeQuery();
                if (resultado.next()) {
                    documentoInicial = new DocumentoInicial();
                    documentoInicial.setId(resultado.getInt("id"));
                    documentoInicial.setNombreArchivo(resultado.getString("nombreArchivo"));
                    documentoInicial.setFechaEntregado(resultado.getDate("fechaEntregado").toLocalDate());
                    if (resultado.getDate("fechaRevisado") != null) {
                        documentoInicial.setFechaRevisado(resultado.getDate("fechaRevisado").toLocalDate());
                    }
                    documentoInicial.setPuntajeObtenido(resultado.getInt("puntajeObtenido"));
                    documentoInicial.setComentario(resultado.getString("comentario"));
                    documentoInicial.setArchivo(resultado.getBytes("archivo"));
                    String tipoDocumentoInicialString = resultado.getString("tipoDoctoInicial").toUpperCase();
                    documentoInicial.setTipoDocumentoInicial(DocumentoInicial.TipoDocumentoInicial.valueOf(tipoDocumentoInicialString));
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return documentoInicial;
    }
    
    public static boolean actualizarRevisionDocumentoInicial(int idDocumentoInicial, int puntajeObtenido, String comentario) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "UPDATE documentoinicial SET puntajeObtenido = ?, comentario = ?, fechaRevisado = CURDATE() WHERE id = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, puntajeObtenido);
                sentencia.setString(2, comentario);
                sentencia.setInt(3, idDocumentoInicial);

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
