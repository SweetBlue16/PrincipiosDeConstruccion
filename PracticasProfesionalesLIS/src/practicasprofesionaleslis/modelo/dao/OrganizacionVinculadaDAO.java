package practicasprofesionaleslis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "UPDATE organizacionvinculada SET numProyectos = ?, "
                        + "razonSocial = ?, correoElectronico = ?, telefono = ?, "
                        + "domicilioFiscal = ? WHERE id = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, organizacionVinculada.getNumProyectos());
                sentencia.setString(2, organizacionVinculada.getRazonSocial());
                sentencia.setString(3, organizacionVinculada.getCorreoElectronico());
                sentencia.setString(4, organizacionVinculada.getTelefono());
                sentencia.setString(5, organizacionVinculada.getDomicilioFiscal());
                sentencia.setInt(6, organizacionVinculada.getId());
                
                int filasAfectadas = sentencia.executeUpdate();
                return filasAfectadas > 0;
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia);
        }
    }
    
    public static List<OrganizacionVinculada> obtenerOrganizacionesVinculadas() throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<OrganizacionVinculada> organizacionesVinculadas = new ArrayList<>();
        
        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT id, numProyectos, razonSocial, correoElectronico, "
                        + "telefono, domicilioFiscal FROM organizacionvinculada";
                sentencia = conexionBD.prepareStatement(consulta);
                
                resultado = sentencia.executeQuery();
                while (resultado.next()) {
                    OrganizacionVinculada organizacionVinculada = new OrganizacionVinculada();
                    organizacionVinculada.setId(resultado.getInt("id"));
                    organizacionVinculada.setRazonSocial(resultado.getString("razonSocial"));
                    organizacionVinculada.setNumProyectos(resultado.getInt("numProyectos"));
                    organizacionVinculada.setCorreoElectronico(resultado.getString("correoElectronico"));
                    organizacionVinculada.setTelefono(resultado.getString("telefono"));
                    organizacionVinculada.setDomicilioFiscal(resultado.getString("domicilioFiscal"));
                    organizacionesVinculadas.add(organizacionVinculada);
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia, resultado);
        }
        return organizacionesVinculadas;
    }
}
