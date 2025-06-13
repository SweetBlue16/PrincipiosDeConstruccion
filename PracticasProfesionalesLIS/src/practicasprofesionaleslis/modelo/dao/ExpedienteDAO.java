package practicasprofesionaleslis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.pojo.Estudiante;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

public class ExpedienteDAO {
    
    public static String obtenerNombreProyectoPorMatricula(String matricula) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        String nombreProyecto = "";
        
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
    
    public static List<Estudiante> obtenerEstudianteSinProyecto() throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<Estudiante> estudiantes = new ArrayList<>();
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT DISTINCT e.id, e.matricula, e.nombre, e.apellidoPaterno, "
                        + "e.apellidoMaterno, e.correoInstitucional, e.semestre "
                        + "FROM estudiante e "
                        + "JOIN expediente ex ON e.id = ex.idEstudiante "
                        + "WHERE ex.idProyecto IS NULL;";
                sentencia = conexionBD.prepareStatement(consulta);
                
                resultado = sentencia.executeQuery();
                while (resultado.next()) {
                    Estudiante estudiante = new Estudiante();
                    estudiante.setId(resultado.getInt("id"));
                    estudiante.setMatricula(resultado.getString("matricula"));
                    estudiante.setNombre(resultado.getString("apellidoMaterno"));
                    estudiante.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    estudiante.setCorreoInstitucional(resultado.getString("correoInstitucional"));
                    estudiante.setSemestre(resultado.getInt("semestre"));
                    estudiantes.add(estudiante);
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return estudiantes;
    }
}
