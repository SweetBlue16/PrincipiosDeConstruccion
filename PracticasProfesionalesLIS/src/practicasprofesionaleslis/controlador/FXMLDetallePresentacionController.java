package practicasprofesionaleslis.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import practicasprofesionaleslis.modelo.dao.CriterioDAO;
import practicasprofesionaleslis.modelo.pojo.Criterio;
import practicasprofesionaleslis.modelo.pojo.EvaluacionPresentacion;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

/**
 * Autor: Mauricio Noriega
 * Fecha de creación: 09/06/2025
 * Descripción: Controla la ventana para consultar el
 * detalle de una evaluación de la presentación que hace
 * un profesor evaluador.
 */
public class FXMLDetallePresentacionController implements Initializable {
    private EvaluacionPresentacion evaluacionPresentacion;
    private List<Criterio> criteriosEvaluacion;

    @FXML
    private Label lblCalificacionFinal;
    @FXML
    private Label lblCriterio1;
    @FXML
    private Label lblCriterio2;
    @FXML
    private Label lblCriterio3;
    @FXML
    private Label lblCriterio4;
    @FXML
    private Label lblCriterio5;
    @FXML
    private TextArea txtaComentarios;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}
    
    public void inicializarPresentacion(EvaluacionPresentacion evaluacionPresentacion) {
        if (evaluacionPresentacion != null) {
            this.evaluacionPresentacion = evaluacionPresentacion;
            try {
                criteriosEvaluacion = CriterioDAO.obtenerCriteriosEvaluacionPorPresentacion(evaluacionPresentacion.getIdEvaluacionPresentacion());
                lblCalificacionFinal.setText("Calificación Final: " + evaluacionPresentacion.getCalificacionFinal());
                lblCriterio1.setText(criteriosEvaluacion.get(0).toString());
                lblCriterio2.setText(criteriosEvaluacion.get(1).toString());
                lblCriterio3.setText(criteriosEvaluacion.get(2).toString());
                lblCriterio4.setText(criteriosEvaluacion.get(3).toString());
                lblCriterio5.setText(criteriosEvaluacion.get(4).toString());
                txtaComentarios.setText(evaluacionPresentacion.getComentario());
            } catch (SQLException e) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                        ConstantesUtils.TITULO_ERROR,
                        ConstantesUtils.ALERTA_ERROR_BD
                );
            }
        }
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(lblCriterio1);
    }
    
}
