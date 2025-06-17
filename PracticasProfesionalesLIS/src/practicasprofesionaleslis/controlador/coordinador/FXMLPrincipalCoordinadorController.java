package practicasprofesionaleslis.controlador.coordinador;

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
import practicasprofesionaleslis.modelo.pojo.Coordinador;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import static practicasprofesionaleslis.utilidades.ConstantesUtils.ALERTA_ERROR_BD;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLPrincipalCoordinadorController implements Initializable, IObservador {
    private Coordinador coordinador;

    @FXML
    private ImageView imgFotoPerfil;
    @FXML
    private Label lblNombreCoordinador;
    @FXML
    private Label lblCorreoInstitucional;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}
    
    @Override
    public void operacionExitosa() {
        inicializarDatosCoordinador(coordinador);
    }

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
        String rutaRecurso = "/practicasprofesionaleslis/vista/coordinador/FXMLRegistrarResponsableProyecto.fxml";
        irVentanaDesdeBoton(rutaRecurso, ConstantesUtils.TITULO_REGISTRAR);
    }

    @FXML
    private void clicBtnRegistrarOrganizacionVinculada(ActionEvent event) {
        String rutaRecurso = "/practicasprofesionaleslis/vista/coordinador/FXMLRegistrarOrganizacionVinculada.fxml";
        irVentanaDesdeBoton(rutaRecurso, ConstantesUtils.TITULO_REGISTRAR);
    }

    @FXML
    private void clicBtnGenerarDoctosAsignacion(ActionEvent event) {
        String rutaRecurso = "/practicasprofesionaleslis/vista/coordinador/FXMLBuscarEstudiante.fxml";
        irVentanaDesdeBoton(rutaRecurso, ConstantesUtils.TITULO_BUSCAR);
    }

    @FXML
    private void clicBtnRegistrarProyecto(ActionEvent event) {
        String rutaRecurso = "/practicasprofesionaleslis/vista/coordinador/FXMLBuscarOrganizacionYResponsable.fxml";
        irVentanaDesdeBoton(rutaRecurso, "BUSCAR OV Y RESPONSABLE");
    }

    @FXML
    private void clicBtnActualizarProyecto(ActionEvent event) {
        String rutaRecurso = "/practicasprofesionaleslis/vista/coordinador/FXMLBuscarProyecto.fxml";
        irVentanaDesdeBoton(rutaRecurso, ConstantesUtils.TITULO_BUSCAR);
    }

    @FXML
    private void clicBtnAsignarProyecto(ActionEvent event) {
        String rutaRecurso = "/practicasprofesionaleslis/vista/coordinador/FXMLBuscarEstudianteSinProyecto.fxml";
        irVentanaDesdeBoton(rutaRecurso, ConstantesUtils.TITULO_BUSCAR);
    }

    @FXML
    private void clicBtnActualizarOrganizacionVinculada(ActionEvent event) {
        String rutaRecurso = "/practicasprofesionaleslis/vista/coordinador/FXMLListaOrganizacionesVinculadas.fxml";
        irVentanaDesdeBoton(rutaRecurso, ConstantesUtils.TITULO_BUSCAR);
    }

    @FXML
    private void clicBtnActualizarResponsableProyecto(ActionEvent event) {
        String rutaRecurso = "/practicasprofesionaleslis/vista/coordinador/FXMLBuscarResponsableProyecto.fxml";
        irVentanaDesdeBoton(rutaRecurso, ConstantesUtils.TITULO_BUSCAR);
    }

    @FXML
    private void clicBtnProgramarEntregasPracticas(ActionEvent event) {
        String rutaRecurso = "/practicasprofesionaleslis/vista/coordinador/FXMLExperienciasEducativas.fxml";
        irVentanaDesdeBotonConObjeto(rutaRecurso, ConstantesUtils.TITULO_BUSCAR);
    }

    @FXML
    private void clicBtnMiPerfil(ActionEvent event) {
        VentanasUtils.irMiPerfil(lblNombreCoordinador, coordinador, this);
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
    
    private void irVentanaDesdeBotonConObjeto(String rutaRecurso, String tituloVentana)  {
        try {
            FXMLLoader cargador = new FXMLLoader(PracticasProfesionalesLIS.class.getResource(rutaRecurso));
            Parent vista = cargador.load();


        if (rutaRecurso.contains("FXMLExperienciasEducativas.fxml")) {
            FXMLExperienciasEducativasController controlador = cargador.getController();
            try {
                controlador.inicializarDatos(coordinador);
            } catch (SQLException e) {
                VentanasUtils.mostrarAlertaSimple(
                    Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    ALERTA_ERROR_BD
                );
                return;
            }
        }

            Stage escenarioBase = new Stage();
            escenarioBase.setScene(new Scene(vista));
            escenarioBase.setTitle(tituloVentana);
            escenarioBase.initModality(Modality.APPLICATION_MODAL);
            escenarioBase.setResizable(false);
            escenarioBase.centerOnScreen();
            escenarioBase.show();
        } catch (IOException e) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_CARGAR_VENTANA
            );
        }
    }


}
