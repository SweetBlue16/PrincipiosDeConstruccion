package practicasprofesionaleslis.controlador.coordinador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import practicasprofesionaleslis.modelo.dao.ResponsableProyectoDAO;
import practicasprofesionaleslis.modelo.pojo.ResponsableProyecto;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLModificarDatosDelResponsableController implements Initializable {
    private ResponsableProyecto responsableProyecto;
    
    @FXML
    private TextField txtfNombre;
    @FXML
    private TextField txtfApellidoPaterno;
    @FXML
    private TextField txtfApellidoMaterno;
    @FXML
    private TextField txtfPuesto;
    @FXML
    private TextField txtfCorreoElectronico;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    public void inicializarResponsableProyecto(ResponsableProyecto responsableProyecto) {
        if (responsableProyecto != null) {
            this.responsableProyecto = responsableProyecto;
            configurarCamposDelResponsable();
        }
    }

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        try {
            validarCamposVacios();
            validarFormatoCampos();
            
            ResponsableProyecto responsableProyecto = new ResponsableProyecto();
            responsableProyecto.setId(this.responsableProyecto.getId());
            responsableProyecto.setNombre(txtfNombre.getText().trim());
            responsableProyecto.setApellidoPaterno(txtfApellidoPaterno.getText().trim());
            responsableProyecto.setApellidoMaterno(txtfApellidoMaterno.getText().trim());
            responsableProyecto.setPuesto(txtfPuesto.getText().trim());
            responsableProyecto.setCorreoElectronico(txtfCorreoElectronico.getText().trim());
            
            boolean operacionExitosa = ResponsableProyectoDAO.editarResponsableProyecto(responsableProyecto);
            if (operacionExitosa) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                    ConstantesUtils.TITULO_EXITO,
                    ConstantesUtils.ALERTA_ACTUALIZACION_EXITOSA
                );
                VentanasUtils.cerrarVentana(txtfPuesto);
            } else {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ACTUALIZACION_FALLIDA
                );
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                ConstantesUtils.TITULO_ADVERTENCIA,
                ConstantesUtils.ALERTA_DATOS_INVALIDOS
            );
        } catch (SQLException e) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                ConstantesUtils.TITULO_ERROR,
                e.getMessage()
            );
        }
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(txtfPuesto);
    }
    
    private void configurarCamposDelResponsable() {
        txtfNombre.setText(responsableProyecto.getNombre());
        txtfApellidoPaterno.setText(responsableProyecto.getApellidoPaterno());
        txtfApellidoMaterno.setText(responsableProyecto.getApellidoMaterno() != null ? responsableProyecto.getApellidoMaterno() : "");
        txtfPuesto.setText(responsableProyecto.getPuesto());
        txtfCorreoElectronico.setText(responsableProyecto.getCorreoElectronico());
    }
    
    private void validarCamposVacios() {
        if (txtfNombre.getText().isEmpty()) throw new IllegalArgumentException();
        if (txtfApellidoPaterno.getText().isEmpty()) throw new IllegalArgumentException();
        if (txtfPuesto.getText().isEmpty()) throw new IllegalArgumentException();
        if (txtfCorreoElectronico.getText().isEmpty()) throw new IllegalArgumentException();
    }
    
    private void validarFormatoCampos() {
        String nombreResponsable = txtfNombre.getText().trim();
        String apellidoPaterno = txtfApellidoPaterno.getText().trim();
        String apellidoMaterno = txtfApellidoMaterno.getText().trim();
        String puesto = txtfApellidoMaterno.getText().trim();
        String correoElectronico = txtfCorreoElectronico.getText().trim();
        
        if (nombreResponsable == null || nombreResponsable.length() > 50)
            throw new IllegalArgumentException();
        if (apellidoPaterno == null || apellidoPaterno.length() > 50)
            throw new IllegalArgumentException();
        if (!apellidoMaterno.isEmpty() && apellidoMaterno.length() > 50)
            throw new IllegalArgumentException();
        if (puesto == null || puesto.length() > 50)
            throw new IllegalArgumentException();
        if (correoElectronico == null || !correoElectronico.matches(ConstantesUtils.REGEX_EMAIL) || correoElectronico.length() > 100)
            throw new IllegalArgumentException();
    }
}
