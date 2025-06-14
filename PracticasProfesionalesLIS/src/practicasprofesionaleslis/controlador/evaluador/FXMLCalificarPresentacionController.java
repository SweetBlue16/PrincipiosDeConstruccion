package practicasprofesionaleslis.controlador.evaluador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import practicasprofesionaleslis.modelo.dao.ExpedienteDAO;
import practicasprofesionaleslis.modelo.dao.OrganizacionVinculadaDAO;
import practicasprofesionaleslis.modelo.dao.ProyectoDAO;
import practicasprofesionaleslis.modelo.pojo.Estudiante;
import practicasprofesionaleslis.modelo.pojo.EvaluacionPresentacion;
import practicasprofesionaleslis.modelo.pojo.Expediente;
import practicasprofesionaleslis.modelo.pojo.OrganizacionVinculada;
import practicasprofesionaleslis.modelo.pojo.Proyecto;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLCalificarPresentacionController implements Initializable {

    @FXML
    private TextArea tfComentario;
    @FXML
    private Label lbNombreEstudiante;
    @FXML
    private Label lbNombreProyecto;
    @FXML
    private Label lbNombreOV;
    @FXML
    private Label lbCalifiCritUno;
    @FXML
    private Label lbCalifiCritDos;
    @FXML
    private Label lbCalifiCritTres;
    @FXML
    private Label lbCalifiCritCuatro;
    @FXML
    private Label lbCalifiCritCinco;
    private Estudiante estudiante;
    private Proyecto proyecto;
    @FXML
    private Slider slCritUno;
    @FXML
    private Slider slCritDos;
    @FXML
    private Slider slCritCinco;
    @FXML
    private Slider slCritCuatro;
    @FXML
    private Slider slCritTres;
    private int numeroEvaluacion;
    private int idExpediente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarSlider(slCritUno, lbCalifiCritUno);
        configurarSlider(slCritDos, lbCalifiCritDos);
        configurarSlider(slCritTres, lbCalifiCritTres);
        configurarSlider(slCritCuatro, lbCalifiCritCuatro);
        configurarSlider(slCritCinco, lbCalifiCritCinco);
    }    
    
    public void inicializarDatosEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
        
        try {
            idExpediente = ExpedienteDAO.obtenerIdExpedientePorIdEstudiante(estudiante.getId());
        } catch(SQLException e) {
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                ConstantesUtils.TITULO_ERROR_BD,
                ConstantesUtils.ALERTA_ERROR_BD);
        }
            
        if (estudiante != null) {
            lbNombreEstudiante.setText(estudiante.toString());
            
            try {
                proyecto = ProyectoDAO.obtenerProyectoPorIdEstudiante(estudiante.getId());
                if (proyecto != null ) {
                    lbNombreProyecto.setText(proyecto.getNombre());
                    OrganizacionVinculada ov = OrganizacionVinculadaDAO.obtenerOrganizacionPorId(
                        proyecto.getOrganizacionVinculada().getId()
                    );
                    lbNombreOV.setText(ov.getRazonSocial());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ERROR_BD,
                    ConstantesUtils.ALERTA_ERROR_BD
                );        
            }
        }
    }
    
    private void configurarSlider(Slider slider, Label label) {
        //Para configurar rangos de incremento en decimal y con valores mínimos y máximos
        slider.setMin(5.0);
        slider.setMax(10.0);
        slider.setBlockIncrement(0.1);
        slider.setValue(5.0);  
        label.setText(String.format("%.1f", slider.getValue()));
        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            // Para solo plasmar a un decimal
            label.setText(String.format("%.1f", newVal.doubleValue()));
        });
    } 
    
    private double calcularCalificacionFinal() {
        double sumaCalifCriterios = slCritUno.getValue() + slCritDos.getValue() + slCritTres.getValue() + slCritCuatro.getValue() + slCritCinco.getValue();
        return Math.round((sumaCalifCriterios / 5.0) * 10.0) / 10.0; 
    }
    
    public void setNumeroEvaluacion(int numeroEvaluacion) {
        this.numeroEvaluacion = numeroEvaluacion;
    }

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        if (tfComentario.getText().trim().isEmpty()) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING, ConstantesUtils.TITULO_ADVERTENCIA, "Escribe un comentario para guardar la evaluación.");
            return;
        }
        
        if (idExpediente == 0) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR, ConstantesUtils.TITULO_ERROR, "No se encontró expediente para el estudiante.");
            return;
        }

        double calificacionFinal = calcularCalificacionFinal();

        EvaluacionPresentacion evaluacion = new EvaluacionPresentacion();
        evaluacion.setNumeroEvaluacion(numeroEvaluacion);
        evaluacion.setCalificacionFinal(calificacionFinal);
        evaluacion.setFechaEvaluacion(java.time.LocalDate.now());
        evaluacion.setComentario(tfComentario.getText().trim());
        evaluacion.setIdExpediente(this.idExpediente);

        try {
            boolean exito = practicasprofesionaleslis.modelo.dao.EvaluacionPresentacionDAO.registrarEvaluacionPresentacion(evaluacion);
            if (exito) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.INFORMATION, ConstantesUtils.TITULO_GUARDADO_CORRECTO, 
                        "Calificacion final: " + calificacionFinal + "\nCalificación asignada correctamente.");
                VentanasUtils.cerrarVentana(lbNombreEstudiante);
            } else {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR, ConstantesUtils.TITULO_ERROR, "No se pudo guardar la evaluación.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR, ConstantesUtils.TITULO_ERROR, ConstantesUtils.ALERTA_ERROR_BD);
        }
    }
    
    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(lbNombreEstudiante);
    }
    
}
