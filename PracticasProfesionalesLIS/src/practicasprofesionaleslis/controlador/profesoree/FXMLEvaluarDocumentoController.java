package practicasprofesionaleslis.controlador.profesoree;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import practicasprofesionaleslis.modelo.dao.DocumentoFinalDAO;
import practicasprofesionaleslis.modelo.dao.DocumentoInicialDAO;
import practicasprofesionaleslis.modelo.dao.DocumentoIntermedioDAO;
import practicasprofesionaleslis.modelo.dao.ReporteDAO;
import practicasprofesionaleslis.modelo.pojo.DocumentoFinal;
import practicasprofesionaleslis.modelo.pojo.DocumentoInicial;
import practicasprofesionaleslis.modelo.pojo.DocumentoIntermedio;
import practicasprofesionaleslis.modelo.pojo.Entrega;
import practicasprofesionaleslis.modelo.pojo.EntregaDocumentoFinal;
import practicasprofesionaleslis.modelo.pojo.EntregaDocumentoInicial;
import practicasprofesionaleslis.modelo.pojo.EntregaDocumentoIntermedio;
import practicasprofesionaleslis.modelo.pojo.EntregaReporte;
import practicasprofesionaleslis.modelo.pojo.Entregable;
import practicasprofesionaleslis.modelo.pojo.Estudiante;
import practicasprofesionaleslis.modelo.pojo.Expediente;
import practicasprofesionaleslis.modelo.pojo.ExperienciaEducativa;
import practicasprofesionaleslis.modelo.pojo.Reporte;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLEvaluarDocumentoController implements Initializable {

    @FXML private Label lblInicia;
    @FXML private Label lblValor;
    @FXML private Label lblTermina;
    @FXML private Label lblValorMaximo;
    @FXML private Label lblNombreEntrega;
    @FXML
    private TextField txtfCalificacion;
    private Button btnGuardar;
    private Button btnCancelar;

    private ExperienciaEducativa experienciaEducativa;
    private Estudiante estudiante;
    private Expediente expediente;
    private EntregaDocumentoInicial entregaInicial;
    private EntregaDocumentoIntermedio entregaIntermedia;
    private EntregaDocumentoFinal entregaFinal;
    private EntregaReporte entregaReporte;
    private String tipoDocumento;
    private Entrega entrega;
    
    private DocumentoInicial documentoInicial; 
    private DocumentoIntermedio documentoIntermedio;
    private DocumentoFinal documentoFinal;
    private Reporte reporte;
    
    private int idDocumento;
    
    private Stage stage; 
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @FXML
    private TextField txtfComentario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarValidaciones();
    }
    
    private void configurarValidaciones() {
        // Only allow numbers in the grade field
        txtfCalificacion.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtfCalificacion.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
    
public void inicializarDatos(ExperienciaEducativa experienciaEducativa, 
                           Estudiante estudiante, 
                           Expediente expediente,
                           EntregaDocumentoInicial entregaInicial,
                           EntregaDocumentoIntermedio entregaIntermedia,
                           EntregaDocumentoFinal entregaFinal,
                           EntregaReporte entregaReporte,
                           Stage stage) throws SQLException {
    this.experienciaEducativa = experienciaEducativa;
    this.estudiante = estudiante;
    this.expediente = expediente;
    this.stage = stage;

    if (entregaInicial != null) {
        this.entregaInicial = entregaInicial;
        this.tipoDocumento = "INICIAL";
        this.entrega = entregaInicial;
        this.documentoInicial = DocumentoInicialDAO.obtenerDocumentoInicial(expediente.getId(), entregaInicial.getId());
        this.idDocumento = documentoInicial.getId();
        
    } else if (entregaIntermedia != null) {
        this.entregaIntermedia = entregaIntermedia;
        this.tipoDocumento = "INTERMEDIO";
        this.entrega = entregaIntermedia;
        this.documentoIntermedio = DocumentoIntermedioDAO.obtenerDocumentoIntermedio(expediente.getId(), entregaIntermedia.getId());
        this.idDocumento = documentoIntermedio.getId();
        
    } else if (entregaFinal != null) {
        this.entregaFinal = entregaFinal;
        this.tipoDocumento = "FINAL";
        this.entrega = entregaFinal;
        this.documentoFinal = DocumentoFinalDAO.obtenerDocumentoFinal(expediente.getId(), entregaFinal.getId());
        this.idDocumento = documentoFinal.getId();
        
    } else if (entregaReporte != null) {
        this.entregaReporte = entregaReporte;
        this.tipoDocumento = "REPORTE";
        this.entrega = entregaReporte;
        this.reporte = ReporteDAO.obtenerReporte(expediente.getId(), entregaReporte.getId());
        this.idDocumento = reporte.getId();
        
    } else {
        throw new IllegalArgumentException("At least one type of Entrega must be provided");
    }

    mostrarDetallesDocumento();
}

    private void mostrarDetallesDocumento() {
        lblNombreEntrega.setText(tipoDocumento);
        lblInicia.setText(entrega.getFechaInicio().format(DATE_FORMATTER));
        lblTermina.setText(entrega.getFechaFin().format(DATE_FORMATTER));
        lblValorMaximo.setText(String.valueOf(entrega.getPuntaje()));


        }

@FXML
private void btnCalificar(ActionEvent event) {
      try {
        // Validate input
        if (txtfCalificacion.getText().isEmpty()) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Debe ingresar una calificación");
            return;
        }
        
        int puntaje = Integer.parseInt(txtfCalificacion.getText());
        String comentario = txtfComentario.getText();
        
        // Validate the score doesn't exceed maximum
        if (puntaje > entrega.getPuntaje()) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", 
                "La calificación no puede ser mayor al valor máximo (" + entrega.getPuntaje() + ")"
                );
            return;
        }
        
        boolean resultado;
        
        // Update the appropriate document type
        switch (tipoDocumento) {
            case "INICIAL":
                resultado = DocumentoInicialDAO.actualizarRevisionDocumentoInicial(
                    idDocumento, puntaje, comentario);
                break;
                
            case "INTERMEDIO":
                resultado = DocumentoIntermedioDAO.actualizarRevisionDocumentoIntermedio(
                    idDocumento, puntaje, comentario);
                break;
                
            case "FINAL":
                resultado = DocumentoFinalDAO.actualizarRevisionDocumentoFinal(
                    idDocumento, puntaje, comentario);
                break;
                
            case "REPORTE":
                resultado = ReporteDAO.actualizarRevisionReporte(
                    idDocumento, puntaje, comentario);
                break;
                
            default:
                throw new IllegalStateException("Tipo de documento desconocido: " + tipoDocumento);
        }
        
        if (resultado) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Exito", 
                "La evaluación se ha guardado correctamente"
                );
            stage.close();
        } else {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", 
                "No se pudo guardar la evaluación"
                );
        }
        
    } catch (NumberFormatException e) {
        VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", 
            "La calificación debe ser un número válido"
            );
    } catch (SQLException e) {
        VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", 
            "Error al guardar la evaluación: " + e.getMessage()
            );
    }
}


    @FXML
    private void btnDescargar(ActionEvent event) {
        try {
            Entregable documento = null;
            String nombreArchivo = "";

            if (documentoInicial != null) {
                documento = documentoInicial;
                nombreArchivo = documentoInicial.getNombreArchivo();
            } else if (documentoIntermedio != null) {
                documento = documentoIntermedio;
                nombreArchivo = documentoIntermedio.getNombreArchivo();
            } else if (documentoFinal != null) {
                documento = documentoFinal;
                nombreArchivo = documentoFinal.getNombreArchivo();
            } else if (reporte != null) {
                documento = reporte;
                nombreArchivo = reporte.getNombreArchivo();
            }

            if (documento == null || documento.getArchivo() == null || documento.getArchivo().length == 0) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", 
                    "No hay documento para descargar");
                return;
            }

            // Get user's home directory
            String homeDir = System.getProperty("user.home");
            Path filePath = Paths.get(homeDir, nombreArchivo);

            int counter = 1;
            while (Files.exists(filePath)) {
                String newName = nombreArchivo.replaceFirst("[.][^.]+$", "") 
                               + "(" + counter + ")" 
                               + nombreArchivo.substring(nombreArchivo.lastIndexOf('.'));
                filePath = Paths.get(homeDir, newName);
                counter++;
            }

            Files.write(filePath, documento.getArchivo());

            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", 
                "Documento descargado en: " + filePath.toString());

        } catch (IOException e) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", 
                "No se pudo descargar el documento: " + e.getMessage());
        }
    }

    @FXML
    private void txtfCalificacion(ActionEvent event) {
    }

    @FXML
    private void txtfComentario(ActionEvent event) {
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
                VentanasUtils.cerrarVentana(lblInicia);
    }
}
    


    



