/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicasprofesionaleslis.modelo.dao;
import java.time.LocalDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.pojo.EntregaDocumentoFinal;

/**
 *
 * @author yael
 */
public class EntregaDocumentoFinalDAO {
    public static EntregaDocumentoFinal registrarDocumentoFinal(LocalDate fechaInicio, LocalDate fechaFin,
                                                              int puntaje, String tipoDocumento,
                                                              int idExperienciaEducativa) throws SQLException {
        Connection conexionBD = ConexionBD.abrirConexion();
        EntregaDocumentoFinal documentoRegistrado = null;
        
        if (conexionBD != null) {
            String consulta = "INSERT INTO EntregaDoctoFinal " +
                             "(fechaInicio, fechaFin, tipoDoctoFinal, puntaje, idExperienciaEducativa) " +
                             "VALUES (?, ?, ?, ?, ?)";
            
            try (PreparedStatement sentencia = conexionBD.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS)) {
                sentencia.setDate(1, java.sql.Date.valueOf(fechaInicio));
                sentencia.setDate(2, java.sql.Date.valueOf(fechaFin));
                sentencia.setString(3, tipoDocumento);
                sentencia.setInt(4, puntaje);
                sentencia.setInt(5, idExperienciaEducativa);
                
                int filasAfectadas = sentencia.executeUpdate();
                
                if (filasAfectadas > 0) {
                    try (ResultSet generatedKeys = sentencia.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            documentoRegistrado = new EntregaDocumentoFinal(
                                generatedKeys.getInt(1),
                                fechaInicio,
                                fechaFin,
                                tipoDocumento,
                                puntaje
                            );
                        }
                    }
                }
            } finally {
                conexionBD.close();
            }
        }
        return documentoRegistrado;
    }
}
