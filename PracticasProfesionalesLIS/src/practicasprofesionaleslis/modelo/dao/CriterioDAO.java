/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicasprofesionaleslis.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import practicasprofesionaleslis.modelo.ConexionBD;
import practicasprofesionaleslis.modelo.pojo.Criterio;
import practicasprofesionaleslis.utilidades.BaseDeDatosUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;

/**
 *
 * @author acrca
 */
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
    
}
