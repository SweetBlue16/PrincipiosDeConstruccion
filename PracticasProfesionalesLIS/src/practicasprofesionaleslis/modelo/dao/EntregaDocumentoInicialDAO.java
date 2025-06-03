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
import practicasprofesionaleslis.modelo.pojo.EntregaDocumentoInicial;

/**
 *
 * @author yael
 */
public class EntregaDocumentoInicialDAO {
    
        public static EntregaDocumentoInicial registrarDocumentoInicial(LocalDate fechaInicio, LocalDate fechaFin, 
                                                              int puntaje, String tipoDocumento, 
                                                              int idExperienciaEducativa) throws SQLException {
    Connection conexionBD = ConexionBD.abrirConexion();
    EntregaDocumentoInicial documentoRegistrado = null;
    
    if (conexionBD != null) {
        String consulta = "INSERT INTO EntregaDoctolnicial " +
                         "(fechaInicio, fechaFin, tipoDoctolnicial, puntaje, idExperienciaEducativa) " +
                         "VALUES (?, ?, ?, ?, ?);";
        
        try (PreparedStatement sentencia = conexionBD.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS)) {
            sentencia.setDate(1, java.sql.Date.valueOf(fechaInicio));  // Conversión directa a java.sql.Date
            sentencia.setDate(2, java.sql.Date.valueOf(fechaFin));
            sentencia.setString(3, tipoDocumento);
            sentencia.setInt(4, puntaje);
            sentencia.setInt(5, idExperienciaEducativa);
            
            int filasAfectadas = sentencia.executeUpdate();
            
            if (filasAfectadas > 0) {
                ResultSet generatedKeys = sentencia.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idGenerado = generatedKeys.getInt(1);
                    documentoRegistrado = new EntregaDocumentoInicial(
                        idGenerado, fechaInicio, fechaFin, tipoDocumento, puntaje);
                }
            }
        } finally {
            conexionBD.close();
        }
    } else {
        throw new SQLException("Error: Sin conexión a la base de datos.");
    }
    return documentoRegistrado;
}
    
}
