package practicasprofesionaleslis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.pojo.DocumentoIntermedio;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

public class DocumentoIntermedioDAO {
    
    public boolean subirDocumentoIntermedio(DocumentoIntermedio documento, int idEntregaDocumentoIntermedio, int idExpediente) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "INSERT INTO documentointermedio (nombreArchivo, fechaEntregado, "
                        + "idEntregaDoctoInicial, idExpediente, archivo) "
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
}
