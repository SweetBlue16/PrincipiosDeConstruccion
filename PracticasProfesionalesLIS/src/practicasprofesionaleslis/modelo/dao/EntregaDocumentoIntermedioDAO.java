package practicasprofesionaleslis.modelo.dao;

import java.time.LocalDate;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.pojo.EntregaDocumentoIntermedio;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

public class EntregaDocumentoIntermedioDAO {
    
    public static EntregaDocumentoIntermedio registrarDocumentoIntermedio(LocalDate fechaInicio, LocalDate fechaFin, 
                                                                           int puntaje, String tipoDocumento, 
                                                                           int idExperienciaEducativa) throws SQLException {
        EntregaDocumentoIntermedio documentoRegistrado = null;
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "INSERT INTO EntregaDoctoIntermedio "
                        + "(fechaInicio, fechaFin, tipoDoctoIntermedio, puntaje, idExperienciaEducativa) "
                        + "VALUES (?, ?, ?, ?, ?)";
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
                        documentoRegistrado = new EntregaDocumentoIntermedio(idGenerado, fechaInicio, fechaFin, puntaje, tipoDocumento);
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
}
