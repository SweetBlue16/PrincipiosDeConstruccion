package practicasprofesionaleslis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

public class ExpedienteDAO {
    
    public static String obtenerNombreProyectoPorMatricula(String matricula) throws SQLException {
        String nombreProyecto = "";
        Connection conexionBD = ConexionBD.abrirConexion();
        
        if (conexionBD != null) {
            String consulta = "SELECT p.nombre "
                    + "FROM proyecto p "
                    + "JOIN expediente e ON p.id = e.idProyecto "
                    + "JOIN estudiante est ON e.idEstudiante = est.id "
                    + "WHERE est.matricula = ?;";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, matricula);
            
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                nombreProyecto = resultado.getString("nombre");
            }
            conexionBD.close();
            sentencia.close();
            resultado.close();
        } else {
            throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
        }
        return nombreProyecto;
    }
}
