package practicasprofesionaleslis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.pojo.OrganizacionVinculada;
import practicasprofesionaleslis.modelo.pojo.Proyecto;
import practicasprofesionaleslis.modelo.pojo.ResponsableProyecto;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

public class ProyectoDAO {
    
    public static boolean registrarProyecto(Proyecto proyecto) throws SQLException {
        // TODO
    }
    
    public static boolean editarProyecto(Proyecto proyecto) throws SQLException {
        // TODO
    }
    
    public static Proyecto obtenerResponsablePorProyecto(String nombreProyecto) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        Proyecto proyecto = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT p.id AS idProyecto, p.nombre AS nombreProyecto, p.numIntegrantes, p.descripcion, "
                        + "r.id AS idResponsable, r.nombre AS nombreResponsable, r.apellidoPaterno, r.apellidoMaterno, "
                        + "r.puesto, r.correoElectronico AS correoResponsable, "
                        + "o.id AS idOrganizacion, o.razonSocial, o.numProyectos, o.correoElectronico AS correoOrganizacion, "
                        + "o.telefono, o.domicilioFiscal "
                        + "FROM proyecto p "
                        + "JOIN responsableproyecto r ON p.idResponsable = r.id "
                        + "JOIN organizacionvinculada o ON p.idOrganizacion = o.id "
                        + "WHERE p.nombre = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setString(1, nombreProyecto);
                
                resultado = sentencia.executeQuery();
                if (resultado.next()) {
                    ResponsableProyecto responsableProyecto = new ResponsableProyecto();
                    responsableProyecto.setId(resultado.getInt("idResponsable"));
                    responsableProyecto.setNombre(resultado.getString("nombreResponsable"));
                    responsableProyecto.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    responsableProyecto.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    responsableProyecto.setPuesto(resultado.getString("puesto"));
                    responsableProyecto.setCorreoElectronico(resultado.getString("correoResponsable"));
                    
                    OrganizacionVinculada organizacionVinculada = new OrganizacionVinculada();
                    organizacionVinculada.setId(resultado.getInt("idOrganizacion"));
                    organizacionVinculada.setRazonSocial(resultado.getString("razonSocial"));
                    organizacionVinculada.setNumProyectos(resultado.getInt("numProyectos"));
                    organizacionVinculada.setCorreoElectronico(resultado.getString("correoOrganizacion"));
                    organizacionVinculada.setTelefono(resultado.getString("telefono"));
                    organizacionVinculada.setDomicilioFiscal(resultado.getString("domicilioFiscal"));
                    
                    proyecto = new Proyecto();
                    proyecto.setId(resultado.getInt("idProyecto"));
                    proyecto.setNombre(resultado.getString("nombreProyecto"));
                    proyecto.setNumIntegrantes(resultado.getInt("numIntegrantes"));
                    proyecto.setDescripcion(resultado.getString("descripcion"));
                    proyecto.setResponsableProyecto(responsableProyecto);
                    proyecto.setOrganizacionVinculada(organizacionVinculada);
                } else {
                    throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
                }
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return proyecto;
    }
}
