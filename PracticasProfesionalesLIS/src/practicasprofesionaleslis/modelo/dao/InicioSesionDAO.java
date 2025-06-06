package practicasprofesionaleslis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.pojo.Coordinador;
import practicasprofesionaleslis.modelo.pojo.Estudiante;
import practicasprofesionaleslis.modelo.pojo.Evaluador;
import practicasprofesionaleslis.modelo.pojo.ProfesorEE;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

public class InicioSesionDAO {

    public static Estudiante verificarCredencialesEstudiante(String matricula, String contraseña) throws SQLException {
        Estudiante estudiante = null;
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT id, matricula, nombre, apellidoPaterno, "
                        + "apellidoMaterno, correoInstitucional, semestre "
                        + "FROM estudiante "
                        + "WHERE matricula = ? AND contrasena = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setString(1, matricula);
                sentencia.setString(2, contraseña);

                resultado = sentencia.executeQuery();
                if (resultado.next()) {
                    estudiante = new Estudiante();
                    estudiante.setId(resultado.getInt("id"));
                    estudiante.setMatricula(resultado.getString("matricula"));
                    estudiante.setNombre(resultado.getString("nombre"));
                    estudiante.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    estudiante.setApellidoMaterno((resultado.getString("apellidoMaterno") != null) ?
                            resultado.getString("apellidoMaterno") : "");
                    estudiante.setCorreoInstitucional(resultado.getString("correoInstitucional"));
                    estudiante.setSemestre(resultado.getInt("semestre"));
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return estudiante;
    }
    
    public static Coordinador verificarCredencialesCoordinador(String numeroPersonal, String contraseña) throws SQLException {
        Coordinador coordinador = null;
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT id, numeroPersonal, nombre, apellidoPaterno, "
                        + "apellidoMaterno, correoInstitucional "
                        + "FROM coordinador "
                        + "WHERE numeroPersonal = ? AND contrasena = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setString(1, numeroPersonal);
                sentencia.setString(2, contraseña);

                resultado = sentencia.executeQuery();
                if (resultado.next()) {
                    coordinador = new Coordinador();
                    coordinador.setId(resultado.getInt("id"));
                    coordinador.setNumeroPersonal(resultado.getString("numeroPersonal"));
                    coordinador.setNombre(resultado.getString("nombre"));
                    coordinador.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    coordinador.setApellidoMaterno((resultado.getString("apellidoMaterno") != null) ?
                            resultado.getString("apellidoMaterno") : "");
                    coordinador.setCorreoInstitucional(resultado.getString("correoInstitucional"));
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return coordinador;
    }
    
    public static ProfesorEE verificarCredencialesProfesorEE(String numeroPersonal, String contraseña) throws SQLException {
        ProfesorEE profesorEE = null;
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT id, numeroPersonal, nombre, apellidoPaterno, "
                        + "apellidoMaterno, correoInstitucional "
                        + "FROM profesoree "
                        + "WHERE numeroPersonal = ? AND contrasena = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setString(1, numeroPersonal);
                sentencia.setString(2, contraseña);

                resultado = sentencia.executeQuery();
                if (resultado.next()) {
                    profesorEE = new ProfesorEE();
                    profesorEE.setId(resultado.getInt("id"));
                    profesorEE.setNumeroPersonal(resultado.getString("numeroPersonal"));
                    profesorEE.setNombre(resultado.getString("nombre"));
                    profesorEE.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    profesorEE.setApellidoMaterno((resultado.getString("apellidoMaterno") != null) ?
                            resultado.getString("apellidoMaterno") : "");
                    profesorEE.setCorreoInstitucional(resultado.getString("correoInstitucional"));
                }
            } else {
                 throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return profesorEE;
    }
    
    public static Evaluador verificarCredencialesEvaluador(String numeroPersonal, String contraseña) throws SQLException {
        Evaluador evaluador = null;
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT id, numeroPersonal, nombre, apellidoPaterno, "
                        + "apellidoMaterno, correoInstitucional "
                        + "FROM evaluador "
                        + "WHERE numeroPersonal = ? AND contrasena = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setString(1, numeroPersonal);
                sentencia.setString(2, contraseña);

                resultado = sentencia.executeQuery();
                if (resultado.next()) {
                    evaluador = new Evaluador();
                    evaluador.setId(resultado.getInt("id"));
                    evaluador.setNumeroPersonal(resultado.getString("numeroPersonal"));
                    evaluador.setNombre(resultado.getString("nombre"));
                    evaluador.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    evaluador.setApellidoMaterno((resultado.getString("apellidoMaterno") != null) ?
                            resultado.getString("apellidoMaterno") : "");
                    evaluador.setCorreoInstitucional(resultado.getString("correoInstitucional"));
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return evaluador;
    }
}