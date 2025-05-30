package practicasprofesionaleslis.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import practicasprofesionaleslis.modelo.pojo.Estudiante;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLPrincipalEstudianteController implements Initializable {
    private Estudiante estudiante;
    
    @FXML
    private ImageView imgFotoPerfil;
    @FXML
    private Label lblNombreEstudiante;
    @FXML
    private Label lblMatricula;
    @FXML
    private Label lblProyecto;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {}
    
    public void inicializarDatosEstudiante(Estudiante estudiante, String nombreProyecto) {
        this.estudiante = estudiante;
        if (estudiante != null && !nombreProyecto.isEmpty()) {
            lblNombreEstudiante.setText(estudiante.toString());
            lblMatricula.setText(estudiante.getMatricula());
            lblProyecto.setText(nombreProyecto);
        }
    }

    @FXML
    private void clicBtnCerrarSesion(ActionEvent event) {
        String titulo = ConstantesUtils.TITULO_CIERRE_SESION;
        String contenido = ConstantesUtils.ALERTA_CERRAR_SESION;
        if (VentanasUtils.mostrarAlertaConfirmacion(titulo, contenido)) {
            VentanasUtils.irInicioSesion(lblNombreEstudiante);
        }
    }

    @FXML
    private void clicBtnConsultarAvance(ActionEvent event) {
    }

    @FXML
    private void clicBtnActualizarExpediente(ActionEvent event) {
    }

    @FXML
    private void clicBtnEvaluarOrganizacionVinculada(ActionEvent event) {
    }

    @FXML
    private void clicBtnGenerarFormatoEvaluacion(ActionEvent event) {
    }
}
