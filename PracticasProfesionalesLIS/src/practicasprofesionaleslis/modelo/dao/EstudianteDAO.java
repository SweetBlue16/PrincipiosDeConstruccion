package practicasprofesionaleslis.modelo.dao;

import com.sun.javafx.scene.control.skin.VirtualFlow;
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

public class EstudianteDAO {
    
    public static boolean editarEstudiante(Estudiante estudiante) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "UPDATE estudiante SET nombre = ?, apellidoPaterno = ?, "
                        + "apellidoMaterno = ?, contrasena = ?, fotoPerfil = ? "
                        + "WHERE matricula = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setString(1, estudiante.getNombre());
                sentencia.setString(2, estudiante.getApellidoPaterno());
                sentencia.setString(3, estudiante.getApellidoMaterno());
                sentencia.setString(4, estudiante.getContraseña());
                sentencia.setBytes(5, estudiante.getFotoPerfil());
                sentencia.setString(6, estudiante.getMatricula());
                
                int filasAfectadas = sentencia.executeUpdate();
                return filasAfectadas > 0;
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia);
        }
    }
    
    public static byte[] obtenerFotoEstudiante(int id) throws SQLException {
        byte[] foto = null;
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT fotoPerfil FROM estudiante "
                        + "WHERE id = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, id);
                
                resultado = sentencia.executeQuery();
                if (resultado.next()) {
                    foto = resultado.getBytes("fotoPerfil");
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return foto;
    }
    
    /*public static List<Estudiante> obtenerEstudianteSinProyecto() throws SQLException {
        // TODO
    }*/
    
    public static List<Estudiante> obtenerEstudiantesSinPresentacion(int numeroEvaluacion) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<Estudiante> estudiantes = new ArrayList<>();
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT e.id, e.nombre, e.apellidoPaterno, e.apellidoMaterno, e.matricula "
                        + "FROM estudiante e "
                        + "JOIN expediente ex ON e.id = ex.idEstudiante "
                        + "WHERE ex.id NOT IN ( "
                        + "SELECT ep.idExpediente FROM evaluacionpresentacion ep WHERE ep.numeroEvaluacion = ? "
                        + ")";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, numeroEvaluacion);
                resultado = sentencia.executeQuery();
                
                while (resultado.next()) {
                    Estudiante estudiante = new Estudiante();
                    estudiante.setId(resultado.getInt("id"));
                    estudiante.setNombre(resultado.getString("nombre"));
                    estudiante.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    estudiante.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    estudiante.setMatricula(resultado.getString("matricula"));
                    estudiantes.add(estudiante);
            }

            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia);
        }
        
        return estudiantes;
        
    }
    
    public static List<Estudiante> obtenerEstudiantesSinProyecto() throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<Estudiante> estudiantes = new ArrayList<>();

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT e.id, e.nombre, e.apellidoPaterno, e.apellidoMaterno, e.matricula, e.correoInstitucional, e.semestre "
                                  + "FROM estudiante e "
                                  + "JOIN expediente ex ON e.id = ex.idEstudiante "
                                  + "WHERE ex.idProyecto IS NULL";
                sentencia = conexionBD.prepareStatement(consulta);
                resultado = sentencia.executeQuery();

                while (resultado.next()) {
                    Estudiante estudiante = new Estudiante();
                    estudiante.setId(resultado.getInt("id"));
                    estudiante.setNombre(resultado.getString("nombre"));
                    estudiante.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    estudiante.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    estudiante.setMatricula(resultado.getString("matricula"));
                    estudiante.setCorreoInstitucional(resultado.getString("correoInstitucional"));
                    estudiante.setSemestre(resultado.getInt("semestre"));
                    estudiantes.add(estudiante);
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia);
        }

        return estudiantes;
    }

    public static List<Estudiante> obtenerEstudiantesPorExperienciaEducativa(int idExperienciaEducativa) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<Estudiante> estudiantes = new ArrayList<>();
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT e.id, e.matricula, e.nombre, e.apellidoPaterno, " + "e.apellidoMaterno, e.correoInstitucional, e.contrasena, " + "e.semestre " + "FROM estudiante e " + "JOIN expediente ex ON e.id = ex.idEstudiante " + "WHERE ex.idExperienciaEducativa = ?;";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idExperienciaEducativa);
                resultado = sentencia.executeQuery();
                while (resultado.next()) {
                    Estudiante estudiante = new Estudiante();
                    estudiante.setId(resultado.getInt("id"));
                    estudiante.setMatricula(resultado.getString("matricula"));
                    estudiante.setNombre(resultado.getString("nombre"));
                    estudiante.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    estudiante.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    estudiante.setCorreoInstitucional(resultado.getString("correoInstitucional"));
                    estudiante.setContraseña(resultado.getString("contrasena"));
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
