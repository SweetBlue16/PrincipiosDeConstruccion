package practicasprofesionaleslis.controlador.estudiante;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import practicasprofesionaleslis.PracticasProfesionalesLIS;
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
            mostrarFotoPerfilEstudiante(estudiante);
        }
    }
    
    private void mostrarFotoPerfilEstudiante(Estudiante estudiante) {
        Image imagen = null;
        try {
            byte[] foto = estudiante.getFotoPerfil();
            if (foto != null) {
                ByteArrayInputStream input = new ByteArrayInputStream(foto);
                imagen = new Image(input);
            } else {
                String ruta = "/practicasprofesionaleslis/recursos/fotoPerfilDefault.png";
                imagen = new Image(PracticasProfesionalesLIS.class.getResourceAsStream(ruta));
            }
            imgFotoPerfil.setImage(imagen);
        } catch (NullPointerException e) {
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_CARGAR_IMAGEN
            );
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
