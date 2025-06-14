package practicasprofesionaleslis.controlador.estudiante;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import practicasprofesionaleslis.PracticasProfesionalesLIS;
import practicasprofesionaleslis.interfaz.IObservador;
import practicasprofesionaleslis.modelo.pojo.Estudiante;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLPrincipalEstudianteController implements Initializable, IObservador {
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
    
    @Override
    public void operacionExitosa() {
        inicializarDatosEstudiante(estudiante, lblProyecto.getText());
    }
    
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
    private void clicBtnEvaluacionDeLaOrganizacionVinculada(ActionEvent event) {
    }

    @FXML
    private void clicBtnGenerarFormatoAutoevaluacion(ActionEvent event) {
        String rutaRecurso = "/practicasprofesionaleslis/vista/estudiante/FXMLFormatoEvaluacionOV.fxml";
        irVentanaDesdeBoton(rutaRecurso, ConstantesUtils.TITULO_ARCHIVO);
    }

    @FXML
    private void clicBtnMiPerfil(ActionEvent event) {
        VentanasUtils.irMiPerfil(lblProyecto, estudiante, this);
    }
    
    private void irVentanaDesdeBoton(String rutaRecurso, String tituloVentana) {
        try {
            Stage escenarioBase = new Stage();
            Parent vista = FXMLLoader.load(PracticasProfesionalesLIS.class.getResource(rutaRecurso));
            Scene escenaBuscarProyecto = new Scene(vista);
            
            escenarioBase.setScene(escenaBuscarProyecto);
            escenarioBase.setTitle(tituloVentana);
            escenarioBase.initModality(Modality.APPLICATION_MODAL);
            escenarioBase.show();
            escenarioBase.setResizable(false);
            escenarioBase.centerOnScreen();
        } catch (IOException e) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_CARGAR_VENTANA
            );
        }
    }
}
