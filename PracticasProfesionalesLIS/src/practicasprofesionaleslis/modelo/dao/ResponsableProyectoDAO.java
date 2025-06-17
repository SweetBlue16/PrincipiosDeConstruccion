package practicasprofesionaleslis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.pojo.ResponsableProyecto;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

public class ResponsableProyectoDAO {

    public static boolean registrarResponsableProyecto(ResponsableProyecto responsableProyecto) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "INSERT INTO responsableproyecto (nombre, apellidoPaterno, "
                        + "apellidoMaterno, puesto, correoElectronico) "
                        + "VALUES (?, ?, ?, ?, ?)";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setString(1, responsableProyecto.getNombre());
                sentencia.setString(2, responsableProyecto.getApellidoPaterno());
                sentencia.setString(3, responsableProyecto.getApellidoMaterno());
                sentencia.setString(4, responsableProyecto.getPuesto());
                sentencia.setString(5, responsableProyecto.getCorreoElectronico());
                
                int filasAfectadas = sentencia.executeUpdate();
                return filasAfectadas > 0;
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia);
        }
    }
    
    public static boolean editarResponsableProyecto(ResponsableProyecto responsableProyecto) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "UPDATE responsableproyecto SET nombre = ?, "
                        + "apellidoPaterno = ?, apellidoMaterno = ?, puesto = ?, "
                        + "correoElectronico = ? WHERE id = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setString(1, responsableProyecto.getNombre());
                sentencia.setString(2, responsableProyecto.getApellidoPaterno());
                sentencia.setString(3, responsableProyecto.getApellidoMaterno());
                sentencia.setString(4, responsableProyecto.getPuesto());
                sentencia.setString(5, responsableProyecto.getCorreoElectronico());
                sentencia.setInt(6, responsableProyecto.getId());
                
                int filasAfectadas = sentencia.executeUpdate();
                return filasAfectadas > 0;
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia);
        }
    }
    
    public static List<ResponsableProyecto> obtenerResponsablesPorIdOrganizacion(int idOrganizacion) throws SQLException {
        List<ResponsableProyecto> responsables = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexion = ConexionBD.abrirConexion();
            if (conexion != null) {
                String consulta = "SELECT id, nombre, apellidoPaterno, apellidoMaterno, puesto, correoElectronico "
                                + "FROM responsableproyecto WHERE idOrganizacion = ?";
                sentencia = conexion.prepareStatement(consulta);
                sentencia.setInt(1, idOrganizacion);
                resultado = sentencia.executeQuery();

                while (resultado.next()) {
                    ResponsableProyecto responsable = new ResponsableProyecto();
                    responsable.setId(resultado.getInt("id"));
                    responsable.setNombre(resultado.getString("nombre"));
                    responsable.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    responsable.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    responsable.setApellidoMaterno(resultado.getString("puesto"));
                    responsable.setCorreoElectronico(resultado.getString("correoElectronico"));
                    responsables.add(responsable);
                }
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexion, sentencia, resultado);
        }
        return responsables;
    }
    
}
