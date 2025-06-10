package practicasprofesionaleslis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.pojo.OrganizacionVinculada;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

public class OrganizacionVinculadaDAO {
    
    public static boolean registrarOrganizacionVinculada(OrganizacionVinculada organizacionVinculada) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "INSERT INTO organizacionvinculada (numProyectos, "
                        + "razonSocial, correoElectronico, telefono, domicilioFiscal) "
                        + "VALUES (?, ?, ?, ?, ?)";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, organizacionVinculada.getNumProyectos());
                sentencia.setString(2, organizacionVinculada.getRazonSocial());
                sentencia.setString(3, organizacionVinculada.getCorreoElectronico());
                sentencia.setString(4, organizacionVinculada.getTelefono());
                sentencia.setString(5, organizacionVinculada.getDomicilioFiscal());
                
                int filasAfectadas = sentencia.executeUpdate();
                return filasAfectadas > 0;
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia);
        }
    }
    
    public static boolean editarOrganizacionVinculada(OrganizacionVinculada organizacionVinculada) throws SQLException {
        // TODO
    }
    
    public static List<OrganizacionVinculada> obtenerOrganizacionesVinculadas() throws SQLException {
        // TODO
    }
}
