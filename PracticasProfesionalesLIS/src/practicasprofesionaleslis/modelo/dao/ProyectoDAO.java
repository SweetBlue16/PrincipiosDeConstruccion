package practicasprofesionaleslis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.pojo.OrganizacionVinculada;
import practicasprofesionaleslis.modelo.pojo.Proyecto;
import practicasprofesionaleslis.modelo.pojo.ResponsableProyecto;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

public class ProyectoDAO {
    
    /*public static boolean registrarProyecto(Proyecto proyecto) throws SQLException {
        // TODO
    }
    
    public static boolean editarProyecto(Proyecto proyecto) throws SQLException {
        // TODO
    }*/
    
    public static List<Proyecto> obtenerProyecto() throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<Proyecto> proyectos = new ArrayList<>();
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT id, nombre, numIntegrantes, descripcion, idResponsable, idOrganizacion FROM proyecto ";
                sentencia = conexionBD.prepareStatement(consulta);
                resultado = sentencia.executeQuery();
                
                while (resultado.next()) {
                    Proyecto proyecto = new Proyecto();
                    proyecto.setId(resultado.getInt("id"));
                    proyecto.setNombre(resultado.getString("nombre"));
                    proyecto.setNumIntegrantes(resultado.getInt("numIntegrantes"));
                    proyecto.setDescripcion(resultado.getString("descripcion"));
                    
                    ResponsableProyecto responsable = new ResponsableProyecto();
                    responsable.setId(resultado.getInt("idResponsable"));
                    proyecto.setResponsableProyecto(responsable);  
                    
                    OrganizacionVinculada organizacion = new OrganizacionVinculada();
                    organizacion.setId(resultado.getInt("idOrganizacion"));
                    proyecto.setOrganizacionVinculada(organizacion);
                    
                    proyectos.add(proyecto);
            }

            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia);
        }
        
        return proyectos;
        
    }
    
    public static Proyecto obtenerProyectoPorIdEstudiante(int idEstudiante) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        Proyecto proyecto = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT p.id, p.nombre, p.numIntegrantes, p.descripcion, p.idResponsable, p.idOrganizacion " 
                                  + "FROM expediente e " 
                                  + "JOIN proyecto p ON e.idProyecto = p.id " 
                                  + "WHERE e.idEstudiante = ? LIMIT 1";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idEstudiante);
                resultado = sentencia.executeQuery();

                if (resultado.next()) {
                    proyecto = new Proyecto();
                    proyecto.setId(resultado.getInt("id"));
                    proyecto.setNombre(resultado.getString("nombre"));
                    proyecto.setNumIntegrantes(resultado.getInt("numIntegrantes"));
                    proyecto.setDescripcion(resultado.getString("descripcion"));

                    ResponsableProyecto responsable = new ResponsableProyecto();
                    responsable.setId(resultado.getInt("idResponsable"));
                    proyecto.setResponsableProyecto(responsable);

                    OrganizacionVinculada organizacion = new OrganizacionVinculada();
                    organizacion.setId(resultado.getInt("idOrganizacion"));
                    proyecto.setOrganizacionVinculada(organizacion);
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia);
        }

        return proyecto;
    }
    
}
