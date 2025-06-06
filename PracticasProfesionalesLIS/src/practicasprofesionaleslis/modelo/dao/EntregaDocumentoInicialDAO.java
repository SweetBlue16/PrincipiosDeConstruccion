package practicasprofesionaleslis.modelo.dao;

import java.time.LocalDate;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.pojo.EntregaDocumentoInicial;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

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
}
