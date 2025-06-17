package practicasprofesionaleslis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.pojo.Coordinador;
import practicasprofesionaleslis.modelo.pojo.ExperienciaEducativa;
import practicasprofesionaleslis.modelo.pojo.Periodo;
import practicasprofesionaleslis.modelo.pojo.ProfesorEE;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

public class ExperienciaEducativaDAO {
    
    public static List<ExperienciaEducativa> obtenerExperienciasPorProfesorEE(int idProfesorEE) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<ExperienciaEducativa> experiencias = new ArrayList<>();

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT ee.id, ee.nrc, ee.nombre, ee.creditos, ee.numeroHoras, "
                        + "ee.bloque, ee.seccion, "
                        + "p.id AS idProfesor, p.numeroPersonal, p.nombre AS nombreProfesor, "
                        + "p.apellidoPaterno AS apellidoPaternoProfesor, p.apellidoMaterno AS apellidoMaternoProfesor, "
                        + "p.correoInstitucional, p.contrasena "
                        + "FROM experienciaeducativa ee "
                        + "JOIN profesoree p ON ee.idProfesorEE = p.id "
                        + "WHERE ee.idProfesorEE = ?;";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idProfesorEE);

                resultado = sentencia.executeQuery();
                while (resultado.next()) {
                    ExperienciaEducativa experiencia = new ExperienciaEducativa();
                    experiencia.setId(resultado.getInt("id"));
                    experiencia.setNrc(resultado.getInt("nrc"));
                    experiencia.setNombre(resultado.getString("nombre"));
                    experiencia.setCreditos(resultado.getInt("creditos"));
                    experiencia.setNumHoras(resultado.getInt("numeroHoras"));
                    experiencia.setBloque(resultado.getString("bloque"));
                    experiencia.setSeccion(resultado.getString("seccion"));

                    ProfesorEE profesor = new ProfesorEE();
                    profesor.setId(resultado.getInt("idProfesor"));
                    profesor.setNumeroPersonal(resultado.getString("numeroPersonal"));
                    profesor.setNombre(resultado.getString("nombreProfesor"));
                    profesor.setApellidoPaterno(resultado.getString("apellidoPaternoProfesor"));
                    profesor.setApellidoMaterno(resultado.getString("apellidoMaternoProfesor"));
                    profesor.setCorreoInstitucional(resultado.getString("correoInstitucional"));
                    experiencia.setProfesorEE(profesor);

                    experiencias.add(experiencia);
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return experiencias;
    }
    
    public static ExperienciaEducativa obtenerExperienciaEducativaPorId(int idExperienciaEducativa) throws SQLException {
        ExperienciaEducativa experienciaEducativa = null;
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT id, nombre, nrc, bloque, seccion, idProfesorEE, idCoordinador, idPeriodo, creditos, numeroHoras " +
                               "FROM experienciaeducativa WHERE id = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idExperienciaEducativa);
                resultado = sentencia.executeQuery();

                if (resultado.next()) {
                    ProfesorEE profesorEE = new ProfesorEE();
                    profesorEE.setId(resultado.getInt("idProfesorEE"));
                    
                    Coordinador coordinador = new Coordinador();
                    coordinador.setId(resultado.getInt("idProfesorEE"));
                    
                    Periodo periodo = new Periodo();
                    periodo.setId(resultado.getInt("idPeriodo"));
                    
                    experienciaEducativa = new ExperienciaEducativa();
                    experienciaEducativa.setId(resultado.getInt("id"));
                    experienciaEducativa.setNombre(resultado.getString("nombre"));
                    experienciaEducativa.setNrc(resultado.getInt("nrc"));
                    experienciaEducativa.setBloque(resultado.getString("bloque"));
                    experienciaEducativa.setSeccion(resultado.getString("seccion"));
                    experienciaEducativa.setProfesorEE(profesorEE);
                    experienciaEducativa.setCoordinador(coordinador);
                    experienciaEducativa.setPeriodo(periodo);
                    experienciaEducativa.setCreditos(resultado.getInt("creditos"));
                    experienciaEducativa.setNumHoras(resultado.getInt("numeroHoras"));
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return experienciaEducativa;
    }
    
    public static List<ExperienciaEducativa> obtenerExperienciasPorCoordinador(int idCoordinador) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<ExperienciaEducativa> experiencias = new ArrayList<>();

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT ee.id, ee.nrc, ee.nombre, ee.creditos, ee.numeroHoras, "
                        + "ee.bloque, ee.seccion, "
                        + "c.id AS idCoordinador, c.numeroPersonal, c.nombre AS nombreCoordinador, "
                        + "c.apellidoPaterno AS apellidoPaternoCoordinador, c.apellidoMaterno AS apellidoMaternoCoordinador, "
                        + "c.correoInstitucional "
                        + "FROM experienciaeducativa ee "
                        + "JOIN coordinador c ON ee.idCoordinador = c.id "
                        + "WHERE ee.idCoordinador = ?;";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idCoordinador);

                resultado = sentencia.executeQuery();
                while (resultado.next()) {
                    ExperienciaEducativa experiencia = new ExperienciaEducativa();
                    experiencia.setId(resultado.getInt("id"));
                    experiencia.setNrc(resultado.getInt("nrc"));
                    experiencia.setNombre(resultado.getString("nombre"));
                    experiencia.setCreditos(resultado.getInt("creditos"));
                    experiencia.setNumHoras(resultado.getInt("numeroHoras"));
                    experiencia.setBloque(resultado.getString("bloque"));
                    experiencia.setSeccion(resultado.getString("seccion"));

                    Coordinador coordinador = new Coordinador();
                    coordinador.setId(resultado.getInt("idCoordinador"));
                    coordinador.setNumeroPersonal(resultado.getString("numeroPersonal"));
                    coordinador.setNombre(resultado.getString("nombreCoordinador"));
                    coordinador.setApellidoPaterno(resultado.getString("apellidoPaternoCoordinador"));
                    coordinador.setApellidoMaterno(resultado.getString("apellidoMaternoCoordinador"));
                    coordinador.setCorreoInstitucional(resultado.getString("correoInstitucional"));
                    experiencia.setCoordinador(coordinador);

                    experiencias.add(experiencia);
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return experiencias;
    }
}
