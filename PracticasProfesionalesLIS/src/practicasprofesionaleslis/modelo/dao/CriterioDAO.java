package practicasprofesionaleslis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.pojo.Criterio;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

public class CriterioDAO {
    
    public static boolean registrarCriterio(Criterio criterio) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "INSERT INTO criterios (idEvaluacion, criterio, calificacion) VALUES (?, ?, ?)";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, criterio.getIdEvaluacion());
                sentencia.setString(2, criterio.getCriterio());
                sentencia.setDouble(3, criterio.getCalificacion());
                int filas = sentencia.executeUpdate();
                return filas > 0;
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia);
        }
    }
    
    public static List<Criterio> obtenerCriteriosEvaluacionPorPresentacion(int idEvaluacionPresentacion) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<Criterio> criteriosEvaluacion = new ArrayList<>();

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT id, idEvaluacion, criterio, calificacion FROM criterios WHERE idEvaluacion = ?";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idEvaluacionPresentacion);
                
                resultado = sentencia.executeQuery();
                while (resultado.next()) {
                    Criterio criterio = new Criterio();
                    criterio.setId(resultado.getInt("id"));
                    criterio.setIdEvaluacion(resultado.getInt("idEvaluacion"));
                    criterio.setCriterio(resultado.getString("criterio"));
                    criterio.setCalificacion(resultado.getDouble("calificacion"));
                    criteriosEvaluacion.add(criterio);
                }
            } else {
                throw new SQLException(ConstantesUtils.ALERTA_ERROR_BD);
            }
        } finally {
            BaseDeDatosUtils.cerrarRecursos(conexionBD, sentencia);
        }
        return  criteriosEvaluacion;
    }
    
}
