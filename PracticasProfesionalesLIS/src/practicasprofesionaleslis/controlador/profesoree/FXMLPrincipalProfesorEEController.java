package practicasprofesionaleslis.controlador.profesoree;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import practicasprofesionaleslis.modelo.dao.ProfesorEEDAO;
import practicasprofesionaleslis.modelo.pojo.ProfesorEE;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLPrincipalProfesorEEController implements Initializable, IObservador {
    private ProfesorEE profesorEE;

    @FXML
    private ImageView imgFotoPerfil;
    @FXML
    private Label lblNombreProfesorEE;
    @FXML
    private Label lblCorreoElectronico;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}
    
    @Override
    public void operacionExitosa() {
        inicializarDatosProfesorEE(profesorEE);
    }

    public void inicializarDatosProfesorEE(ProfesorEE profesorEE) {
        this.profesorEE = profesorEE;
        try {
            this.profesorEE = ProfesorEEDAO.obtenerProfesorPorCorreo(profesorEE.getCorreoInstitucional());
        }catch (SQLException e){
                    e.printStackTrace();
        VentanasUtils.mostrarAlertaSimple(
            Alert.AlertType.ERROR,
            ConstantesUtils.TITULO_ERROR,
            ConstantesUtils.ALERTA_ERROR_BD
        );
        }

        if (profesorEE != null) {
            lblNombreProfesorEE.setText(profesorEE.toString());
            lblCorreoElectronico.setText(profesorEE.getCorreoInstitucional());
            mostrarFotoPerfilProfesorEE(profesorEE);
            
            
        }
    }
    
    private void mostrarFotoPerfilProfesorEE(ProfesorEE profesorEE) {
        Image imagen = null;
        try {
            byte[] foto = profesorEE.getFotoPerfil();
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
            VentanasUtils.irInicioSesion(lblNombreProfesorEE);
        }
    }

    @FXML
    private void clicBtnConsultarExpedienteEstudiante(ActionEvent event) {
    }

@FXML
private void clicBtnValidarEntregaDocumentos(ActionEvent event) {
    try {
        String rutaRecurso = "/practicasprofesionaleslis/vista/profesoree/FXMLExperienciasEducativas.fxml";
        FXMLLoader cargador = new FXMLLoader(PracticasProfesionalesLIS.class.getResource(rutaRecurso));
        Parent vista = cargador.load();
        
        // Obtener el controlador y pasarle los datos necesarios
        FXMLExperienciasEducativasController controlador = cargador.getController();
        controlador.inicializarDatos(this.profesorEE);

        // Mostrar la ventana
        Stage escenarioBase = new Stage();
        Scene escena = new Scene(vista);
        escenarioBase.setScene(escena);
        escenarioBase.setTitle("Experiencias Educativas");
        escenarioBase.initModality(Modality.APPLICATION_MODAL);
        escenarioBase.setResizable(false);
        escenarioBase.centerOnScreen();
        escenarioBase.show();
    } catch (IOException e) {
        e.printStackTrace();
        VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                ConstantesUtils.TITULO_ERROR,
                ConstantesUtils.ALERTA_ERROR_CARGAR_VENTANA);
    }
}



    @FXML
    private void clicBtnMiPerfil(ActionEvent event) {
        VentanasUtils.irMiPerfil(lblNombreProfesorEE, profesorEE, this);
    }
}