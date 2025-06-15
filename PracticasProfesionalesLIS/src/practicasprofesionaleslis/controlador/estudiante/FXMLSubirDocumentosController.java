package practicasprofesionaleslis.controlador.estudiante;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import practicasprofesionaleslis.modelo.dao.DocumentoInicialDAO;
import practicasprofesionaleslis.modelo.dao.EntregaDocumentoInicialDAO;
import practicasprofesionaleslis.modelo.dao.EntregaDocumentoIntermedioDAO;
import practicasprofesionaleslis.modelo.pojo.DocumentoInicial;
import practicasprofesionaleslis.modelo.pojo.EntregaDocumentoInicial;
import practicasprofesionaleslis.modelo.pojo.EntregaDocumentoIntermedio;
import practicasprofesionaleslis.modelo.pojo.Expediente;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLSubirDocumentosController implements Initializable {
    private Expediente expediente;
    private File archivoSeleccionado;

    @FXML
    private ComboBox<String> cbxTiposEntregas;
    @FXML
    private ListView<Object> lstEntregasDisponibles;
    @FXML
    private Button btnSeleccionarArchivo;
    @FXML
    private Button btnGuardar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbxTiposEntregas.getItems().addAll("Iniciales", "Intermedios", "Finales", "Reportes");
        personalizarComboBox();
        personalizarListView();
        cbxTiposEntregas.setOnAction(evento -> cargarEntregasDisponibles());
        btnSeleccionarArchivo.setDisable(true);
    }
    
    public void inicializarExpediente(Expediente expediente) {
        this.expediente = expediente;
        cargarEntregasDisponibles();
    }

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        Object entregaSeleccionada = lstEntregasDisponibles.getSelectionModel().getSelectedItem();
        String tipo = cbxTiposEntregas.getValue();
        
        if (archivoSeleccionado == null) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ADVERTENCIA,
                    ConstantesUtils.ALERTA_ADVERTENCIA_SELECCION_ARCHIVO
            );
            return;
        }
        
        try {
            byte[] contenido = Files.readAllBytes(archivoSeleccionado.toPath());
            String nombreArchivo = archivoSeleccionado.getName();
            boolean operacionExitosa = false;
            
            switch(tipo) {
                case "Iniciales":
                    DocumentoInicial documentoInicial = new DocumentoInicial();
                    documentoInicial.setArchivo(contenido);
                    documentoInicial.setNombreArchivo(nombreArchivo);
                    EntregaDocumentoInicial entregaDocumentoInicial = (EntregaDocumentoInicial) entregaSeleccionada;
                    operacionExitosa = DocumentoInicialDAO.subirDocumentoInicial(documentoInicial,
                            entregaDocumentoInicial.getId(),
                            expediente.getId());
                    break;
                case "Intermedios":
                    break;
                case "Finales":
                    break;
                case "Reportes":
                    break;
            }
            
            if (operacionExitosa) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                        ConstantesUtils.TITULO_EXITO,
                        ConstantesUtils.ALERTA_SUBIDA_ARCHIVO_EXITOSA
                );
                cargarEntregasDisponibles();
            } else {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                        ConstantesUtils.TITULO_ERROR,
                        ConstantesUtils.ALERTA_SUBIDA_ARCHIVO_FALLIDA);
            }
        } catch (IOException | SQLException e) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    e.getMessage()
            );
        }
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(cbxTiposEntregas);
    }
    
    @FXML
    private void clicBtnSeleccionarArchivo(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(ConstantesUtils.TITULO_ARCHIVO);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));
        File archivo = fileChooser.showOpenDialog(VentanasUtils.obtenerEscenarioComponente(cbxTiposEntregas));
        
        if (archivo != null && archivo.exists() && archivo.length() > 0) {
            this.archivoSeleccionado = archivo;
            Object entregaSeleccionada = lstEntregasDisponibles.getSelectionModel().getSelectedItem();
            btnGuardar.setDisable(entregaSeleccionada == null);
        } else {
            this.archivoSeleccionado = null;
            btnGuardar.setDisable(true);
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ADVERTENCIA,
                    ConstantesUtils.ALERTA_SELECCION_ARCHIVO_FALLIDA
            );
        }
    }
    
    private void cargarEntregasDisponibles() {
        lstEntregasDisponibles.getItems().clear();
        String tipo = cbxTiposEntregas.getValue();
        
        try {
            switch (tipo) {
                case "Iniciales":
                    List<EntregaDocumentoInicial> entregasInicialesDisponibles = EntregaDocumentoInicialDAO.obtenerEntregasDisponibles(
                            expediente.getExperienciaEducativa().getId(), expediente.getId()
                    );
                    lstEntregasDisponibles.getItems().addAll(entregasInicialesDisponibles);
                    break;
                case "Intermedios":
                    List<EntregaDocumentoIntermedio> entregasIntermediasDisponibles = EntregaDocumentoIntermedioDAO.obtenerEntregasDisponibles(
                            expediente.getId(), expediente.getExperienciaEducativa().getId()
                    );
                    lstEntregasDisponibles.getItems().addAll(entregasIntermediasDisponibles);
                    break;
                case "Finales":
                    break;
                case "Reportes":
                    break;
            }
        } catch (SQLException e) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_BD
            );
        }
    }
    
    private void personalizarComboBox() {
        cbxTiposEntregas.setCellFactory(lista -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    setStyle("-fx-font-size: 14px; -fx-padding: 5px;");
                    setTextFill(Color.BLACK);
                }
            }
        });

        cbxTiposEntregas.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                    setFont(Font.font("Gill Sans MT", FontWeight.NORMAL, 15));
                    setTextFill(Color.BLACK);
                    setStyle("-fx-padding: 5px;");
                }
            }
        });
        
        cbxTiposEntregas.getSelectionModel().select("Iniciales");
    }
    
    private void personalizarListView() {
        lstEntregasDisponibles.setCellFactory(lista -> new ListCell<Object>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.toString());
                    setFont(Font.font("Gill Sans MT", FontWeight.NORMAL, 13));
                    setPadding(new Insets(5));
                    setTextFill(Color.BLACK);
                    setStyle("-fx-background-color: #F0F8FF; -fx-border-color: lightgray; -fx-border-radius: 5px;");
                }
            }
        });

        lstEntregasDisponibles.getSelectionModel().selectedItemProperty().addListener((observable, valorAntiguo, nuevoValor) -> {
            boolean haySeleccion = nuevoValor != null;
            btnSeleccionarArchivo.setDisable(!haySeleccion);

            btnGuardar.setDisable(!haySeleccion || archivoSeleccionado == null);
        });
    }
}
