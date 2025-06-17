package practicasprofesionaleslis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.pojo.DocumentoIntermedio;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

public class DocumentoIntermedioDAO {
    
    public static boolean subirDocumentoIntermedio(DocumentoIntermedio documento, int idEntregaDocumentoIntermedio, int idExpediente) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "INSERT INTO documentointermedio (nombreArchivo, fechaEntregado, "
                        + "idEntregaDoctoIntermedio, idExpediente, archivo) "
                        + "VALUES (?, CURDATE(), ?, ?, ?)";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setString(1, documento.getNombreArchivo());
                sentencia.setInt(2, idEntregaDocumentoIntermedio);
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
    
    public static boolean existeDocumentoIntermedio(int idExpediente) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT COUNT(*) FROM documentointermedio WHERE idExpediente = ?";
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
    
    public static List<DocumentoIntermedio> obtenerDocumentosIntermediosPorExpediente(int idExpediente) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<DocumentoIntermedio> documentos = new ArrayList<>();
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT di.id, di.nombreArchivo, di.archivo, edi.tipoDoctoIntermedio "
                        + "FROM documentointermedio di "
                        + "JOIN entregadoctointermedio edi ON di.idEntregaDoctoIntermedio = edi.id "
                        + "WHERE di.idExpediente = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idExpediente);
                
                resultado = sentencia.executeQuery();
                while (resultado.next()) {
                    DocumentoIntermedio documentoIntermedio = new DocumentoIntermedio();
                    documentoIntermedio.setId(resultado.getInt("id"));
                    documentoIntermedio.setNombreArchivo(resultado.getString("nombreArchivo"));
                    documentoIntermedio.setArchivo(resultado.getBytes("archivo"));
                    String tipoDocumentoIntermedioString = resultado.getString("tipoDoctoIntermedio").toUpperCase();
                    documentoIntermedio.setTipoDocumentoIntermedio(DocumentoIntermedio.TipoDocumentoIntermedio.valueOf(tipoDocumentoIntermedioString));
                    documentos.add(documentoIntermedio);
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return documentos;
    }
    
    public static DocumentoIntermedio obtenerDocumentoIntermedio(int idExpediente, int idEntregaDocumentoIntermedio) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        DocumentoIntermedio documentoIntermedio = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT di.id, di.nombreArchivo, di.fechaEntregado, di.fechaRevisado, "
                        + "di.puntajeObtenido, di.comentario, di.archivo, edi.tipoDoctoIntermedio "
                        + "FROM documentointermedio di "
                        + "JOIN entregadoctointermedio edi ON di.idEntregaDoctoIntermedio = edi.id "
                        + "WHERE di.idExpediente = ? AND di.idEntregaDoctoIntermedio = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idExpediente);
                sentencia.setInt(2, idEntregaDocumentoIntermedio);

                resultado = sentencia.executeQuery();
                if (resultado.next()) {
                    documentoIntermedio = new DocumentoIntermedio();
                    documentoIntermedio.setId(resultado.getInt("id"));
                    documentoIntermedio.setNombreArchivo(resultado.getString("nombreArchivo"));
                    documentoIntermedio.setFechaEntregado(resultado.getDate("fechaEntregado").toLocalDate());
                    if (resultado.getDate("fechaRevisado") != null) {
                        documentoIntermedio.setFechaRevisado(resultado.getDate("fechaRevisado").toLocalDate());
                    }
                    documentoIntermedio.setPuntajeObtenido(resultado.getInt("puntajeObtenido"));
                    documentoIntermedio.setComentario(resultado.getString("comentario"));
                    documentoIntermedio.setArchivo(resultado.getBytes("archivo"));
                    String tipoDocumentoIntermedioString = resultado.getString("tipoDoctoIntermedio").toUpperCase();
                    documentoIntermedio.setTipoDocumentoIntermedio(DocumentoIntermedio.TipoDocumentoIntermedio.valueOf(tipoDocumentoIntermedioString));
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return documentoIntermedio;
    }
    
    public static boolean actualizarRevisionDocumentoIntermedio(int idDocumentoIntermedio, int puntajeObtenido, String comentario) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "UPDATE documentointermedio SET puntajeObtenido = ?, comentario = ?, fechaRevisado = CURDATE() WHERE id = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, puntajeObtenido);
                sentencia.setString(2, comentario);
                sentencia.setInt(3, idDocumentoIntermedio);

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
