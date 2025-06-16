package practicasprofesionaleslis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.pojo.DocumentoFinal;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

public class DocumentoFinalDAO {
    
    public static boolean subirDocumentoFinal(DocumentoFinal documento, int idEntregaDocumentoFinal, int idExpediente) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "INSERT INTO documentofinal (nombreArchivo, fechaEntregado, "
                        + "idEntregaDoctoFinal, idExpediente, archivo) "
                        + "VALUES (?, CURDATE(), ?, ?, ?)";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setString(1, documento.getNombreArchivo());
                sentencia.setInt(2, idEntregaDocumentoFinal);
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
    
    public static List<DocumentoFinal> obtenerDocumentosFinalesPorExpediente(int idExpediente) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<DocumentoFinal> documentos = new ArrayList<>();
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT df.id, df.nombreArchivo, df.archivo, edf.tipoDoctoFinal "
                        + "FROM documentofinal df "
                        + "JOIN entregadoctofinal edf ON df.idEntregaDoctoFinal = edf.id "
                        + "WHERE df.idExpediente = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idExpediente);
                
                resultado = sentencia.executeQuery();
                while (resultado.next()) {
                    DocumentoFinal documentoFinal = new DocumentoFinal();
                    documentoFinal.setId(resultado.getInt("id"));
                    documentoFinal.setNombreArchivo(resultado.getString("nombreArchivo"));
                    documentoFinal.setArchivo(resultado.getBytes("archivo"));
                    String tipoDocumentoFinalString = resultado.getString("tipoDoctoFinal").toUpperCase();
                    documentoFinal.setTipoDocumentoFinal(DocumentoFinal.TipoDocumentoFinal.valueOf(tipoDocumentoFinalString));
                    documentos.add(documentoFinal);
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return documentos;
    }
}
