package practicasprofesionaleslis.controlador.coordinador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import practicasprofesionaleslis.PracticasProfesionalesLIS;
import practicasprofesionaleslis.modelo.dao.OrganizacionVinculadaDAO;
import practicasprofesionaleslis.modelo.pojo.OrganizacionVinculada;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLListaOrganizacionesVinculadasController implements Initializable {

    @FXML
    private ListView<OrganizacionVinculada> lstOrganizacionesVinculadas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            List<OrganizacionVinculada> organizacionesVinculadas = OrganizacionVinculadaDAO.obtenerOrganizacionesVinculadas();
            ObservableList<OrganizacionVinculada> observableList = FXCollections.observableArrayList(organizacionesVinculadas);
            lstOrganizacionesVinculadas.setItems(observableList);
            configurarCeldaPersonalizada();
        } catch (SQLException e) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    e.getMessage()
            );
        }
    }    

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(lstOrganizacionesVinculadas);
    }
    
    private void configurarCeldaPersonalizada() {
        lstOrganizacionesVinculadas.setCellFactory(lv -> new ListCell<OrganizacionVinculada>() {
            private final HBox contenido;
            private final Label lblNombre;
            private final Button btnEditar;
            private final Region espacio;
            
            {
                lblNombre = new Label();
                lblNombre.setFont(Font.font("Gill Sans MT", 14));
                lblNombre.setTextFill(Color.BLACK);
                
                btnEditar = new Button("Editar Información");
                btnEditar.setStyle(
                        "-fx-background-color: #28ad56;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-family: 'Gill Sans MT';" +
                        "-fx-font-size: 14px;" +
                        "-fx-background-radius: 8;"
                );
                btnEditar.setOnAction(e -> {
                    OrganizacionVinculada organizacionVinculada = getItem();
                    if (organizacionVinculada != null) {
                        irEditarOrganizacionVinculada(organizacionVinculada);
                    }
                });
                
                espacio = new Region();
                HBox.setHgrow(espacio, Priority.ALWAYS);
                
                contenido = new HBox(10, lblNombre, espacio,btnEditar);
                contenido.setAlignment(Pos.CENTER_LEFT);
                contenido.setPadding(new Insets(5));
            }
            
            @Override
            protected void updateItem(OrganizacionVinculada item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    lblNombre.setText(item.getRazonSocial() != null ? item.getRazonSocial() : "(Sin razón social");
                    setGraphic(contenido);
                }
            }
        });
    }
    
    private void irEditarOrganizacionVinculada(OrganizacionVinculada organizacionVinculada) {
        try {
            String rutaRecurso = "/practicasprofesionaleslis/vista/coordinador/FXMLEditarOrganizacionVinculada.fxml";
            FXMLLoader cargador = new FXMLLoader(PracticasProfesionalesLIS.class.getResource(rutaRecurso));
            Parent vista = cargador.load();
            FXMLEditarOrganizacionVinculadaController controlador = cargador.getController();
            controlador.inicializarOrganizacionVinculada(organizacionVinculada);
            
            Stage escenarioBase = VentanasUtils.obtenerEscenarioComponente(lstOrganizacionesVinculadas);
            Scene escenaEditarOrganizacion = new Scene(vista);
            escenarioBase.setScene(escenaEditarOrganizacion);
            escenarioBase.setTitle(ConstantesUtils.TITULO_ACTUALIZAR);
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
