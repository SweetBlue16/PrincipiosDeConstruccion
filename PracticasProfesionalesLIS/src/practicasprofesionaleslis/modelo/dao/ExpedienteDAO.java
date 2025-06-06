package practicasprofesionaleslis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

public class ExpedienteDAO {
    
    public static String obtenerNombreProyectoPorMatricula(String matricula) throws SQLException {
        String nombreProyecto = "";
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT p.nombre "
                        + "FROM proyecto p "
                        + "JOIN expediente e ON p.id = e.idProyecto "
                        + "JOIN estudiante est ON e.idEstudiante = est.id "
                        + "WHERE est.matricula = ?;";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setString(1, matricula);

                resultado = sentencia.executeQuery();
                if (resultado.next()) {
                    nombreProyecto = resultado.getString("nombre");
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return nombreProyecto;
    }
}
