 package practicasprofesionaleslis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.pojo.Estudiante;
import practicasprofesionaleslis.modelo.pojo.Expediente;
import practicasprofesionaleslis.modelo.pojo.ExperienciaEducativa;
import practicasprofesionaleslis.modelo.pojo.Proyecto;
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

    public static int obtenerIdExpedientePorIdEstudiante(int idEstudiante) throws SQLException {
        int idExpediente = -1;
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT id FROM expediente WHERE idEstudiante = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idEstudiante);

                resultado = sentencia.executeQuery();
                if (resultado.next()) {
                    idExpediente = resultado.getInt("id");
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return idExpediente;
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

    public static boolean asignarProyectoAExpediente(int idExpediente, int idProyecto) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "UPDATE expediente SET idProyecto = ? WHERE id = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idProyecto);
                sentencia.setInt(2, idExpediente);
                int filasAfectadas = sentencia.executeUpdate();
                return filasAfectadas > 0;
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia);
        }
    }

    public static Expediente obtenerExpedientePorEstudiante(int idEstudiante) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        Expediente expediente = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT id, fechaCreacion, estado, calificacion, horasAcumuladas, "
                        + "idProyecto, idExperienciaEducativa "
                        + "FROM expediente WHERE idEstudiante = ?;";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idEstudiante);

                resultado = sentencia.executeQuery();
                if (resultado.next()) {
                    Proyecto proyecto = new Proyecto();
                    proyecto.setId(resultado.getInt("idProyecto"));

                    ExperienciaEducativa experienciaEducativa = new ExperienciaEducativa();
                    experienciaEducativa.setId(resultado.getInt("idExperienciaEducativa"));

                    expediente = new Expediente();
                    expediente.setId(resultado.getInt("id"));
                    expediente.setFechaCreacion(resultado.getString("fechaCreacion"));
                    String estadoString = resultado.getString("estado").toUpperCase();
                    expediente.setEstado(Expediente.Estado.valueOf(estadoString));
                    expediente.setCalificacion(resultado.getInt("calificacion"));
                    expediente.setHorasAcumuladas(resultado.getInt("horasAcumuladas"));
                    expediente.setProyecto(proyecto);
                    expediente.setExperienciaEducativa(experienciaEducativa);
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return expediente;
    }

    public static Expediente obtenerExpedienteActivoPorEstudiante(int idEstudiante) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        Expediente expediente = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT id, fechaCreacion, estado, calificacion, horasAcumuladas, "
                        + "idProyecto, idExperienciaEducativa "
                        + "FROM expediente "
                        + "WHERE idEstudiante = ? AND estado = 'Activo';";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idEstudiante);

                resultado = sentencia.executeQuery();
                if (resultado.next()) {
                    expediente = new Expediente();
                    expediente.setId(resultado.getInt("id"));
                    expediente.setFechaCreacion(resultado.getString("fechaCreacion"));

                    String estadoString = resultado.getString("estado");
                    expediente.setEstado(Expediente.Estado.valueOf(estadoString.toUpperCase()));

                    expediente.setCalificacion(resultado.getInt("calificacion"));
                    expediente.setHorasAcumuladas(resultado.getInt("horasAcumuladas"));

                    Estudiante estudiante = new Estudiante();
                    estudiante.setId(idEstudiante);
                    expediente.setEstudiante(estudiante);

                    int idProyecto = resultado.getInt("idProyecto");
                    if (!resultado.wasNull()) {
                        Proyecto proyecto = new Proyecto();
                        proyecto.setId(idProyecto);
                        expediente.setProyecto(proyecto);
                    }

                    ExperienciaEducativa experienciaEducativa = new ExperienciaEducativa();
                    experienciaEducativa.setId(resultado.getInt("idExperienciaEducativa"));
                    expediente.setExperienciaEducativa(experienciaEducativa);
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return expediente;
    }

    public static boolean registrarExpedienteEstudiante(int idEstudiante) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet rs = null;
        boolean registrado = false;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String verificar = "SELECT COUNT(*) FROM expediente WHERE idEstudiante = ? AND estado = 'Activo'";
                sentencia = conexionBD.prepareStatement(verificar);
                sentencia.setInt(1, idEstudiante);
                rs = sentencia.executeQuery();

                if (rs.next() && rs.getInt(1) == 0) {
                    String consulta = "INSERT INTO expediente " +
                            "(fechaCreacion, estado, idEstudiante, idExperienciaEducativa) " +
                            "VALUES (?, 'Activo', ?, 1)";

                    BaseDeDatosUtils.cerrarRecursos(null, sentencia, rs);

                    sentencia = conexionBD.prepareStatement(consulta);
                    sentencia.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
                    sentencia.setInt(2, idEstudiante);

                    int filasAfectadas = sentencia.executeUpdate();
                    registrado = filasAfectadas > 0;
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, rs);
        }
        return registrado;
    }

}
