package practicasprofesionaleslis.controlador.coordinador;

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
import practicasprofesionaleslis.modelo.pojo.Coordinador;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLPrincipalCoordinadorController implements Initializable {
    private Coordinador coordinador;

    @FXML
    private ImageView imgFotoPerfil;
    @FXML
    private Label lblNombreCoordinador;
    @FXML
    private Label lblCorreoInstitucional;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    public void inicializarDatosCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
        if (coordinador != null) {
            lblNombreCoordinador.setText(coordinador.toString());
            lblCorreoInstitucional.setText(coordinador.getCorreoInstitucional());
            mostrarFotoPerfilCoordinador(coordinador);
        }
    }
    
    private void mostrarFotoPerfilCoordinador(Coordinador coordinador) {
        Image imagen = null;
        try {
            byte[] foto = coordinador.getFotoPerfil();
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
            VentanasUtils.irInicioSesion(lblNombreCoordinador);
        }
    }

    @FXML
    private void clicBtnRegistrarResponsableProyecto(ActionEvent event) {
    }

    @FXML
    private void clicBtnRegistrarOrganizacionVinculada(ActionEvent event) {
    }

    @FXML
    private void clicBtnGenerarDoctosAsignacion(ActionEvent event) {
    }

    @FXML
    private void clicBtnRegistrarProyecto(ActionEvent event) {
    }

    @FXML
    private void clicBtnActualizarProyecto(ActionEvent event) {
    }

    @FXML
    private void clicBtnAsignarProyecto(ActionEvent event) {
    }

    @FXML
    private void clicBtnActualizarOrganizacionVinculada(ActionEvent event) {
    }

    @FXML
    private void clicBtnActualizarResponsableProyecto(ActionEvent event) {
    }

    @FXML
    private void clicBtnProgramarEntregasPracticas(ActionEvent event) {
    }

    @FXML
    private void clicBtnMiPerfil(ActionEvent event) {
        VentanasUtils.irMiPerfil(lblNombreCoordinador, this.coordinador);
    }
}
