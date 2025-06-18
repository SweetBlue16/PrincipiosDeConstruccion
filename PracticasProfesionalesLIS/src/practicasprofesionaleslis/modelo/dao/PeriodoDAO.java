package practicasprofesionaleslis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.pojo.Periodo;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

/**
 * Autor: Todos
 * Fecha de creación: 14/06/2025
 * Descripción: Gestiona las operaciones de bases de
 * datos relacionadas con la tabla Periodo.
 */
public class PeriodoDAO {
    
    public static Periodo obtenerPeriodoPorId(int idPeriodo) throws SQLException {
        Periodo periodo = null;
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String query = "SELECT id, nombrePeriodo, fechaInicio, fechaFin FROM periodo WHERE id = ?";
                sentencia = conexionBD.prepareStatement(query);
                sentencia.setInt(1, idPeriodo);
                resultado = sentencia.executeQuery();

                if (resultado.next()) {
                    periodo = new Periodo(
                        resultado.getInt("id"),
                        resultado.getString("nombrePeriodo"),
                        resultado.getString("fechaInicio"),
                        resultado.getString("fechaFin")
                    );
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return periodo;
    }
}
