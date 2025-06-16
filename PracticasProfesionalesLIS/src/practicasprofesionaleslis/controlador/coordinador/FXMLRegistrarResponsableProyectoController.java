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

public class FXMLRegistrarResponsableProyectoController implements Initializable {

    @FXML
    private TextField txtfCorreoElectronico;
    @FXML
    private TextField txtfPuesto;
    @FXML
    private TextField txtfApellidoMaterno;
    @FXML
    private TextField txtfApellidoPaterno;
    @FXML
    private TextField txtfNombre;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}    

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(txtfPuesto);
    }

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        try {
            validarCamposVacios();
            validarFormatoCampos();
            
            ResponsableProyecto responsableProyecto = new ResponsableProyecto();
            responsableProyecto.setNombre(txtfNombre.getText().trim());
            responsableProyecto.setApellidoPaterno(txtfApellidoPaterno.getText().trim());
            responsableProyecto.setApellidoMaterno(txtfApellidoMaterno.getText().trim());
            responsableProyecto.setCorreoElectronico(txtfCorreoElectronico.getText().trim());
            responsableProyecto.setPuesto(txtfPuesto.getText().trim());
            
            boolean confirmacion = VentanasUtils.mostrarAlertaConfirmacion(ConstantesUtils.TITULO_CONFIRMAR,
                    ConstantesUtils.ALERTA_CONFIRMAR_OPERACION
            );
            if (confirmacion) {
                boolean operacionExitosa = ResponsableProyectoDAO.registrarResponsableProyecto(responsableProyecto);
                if (operacionExitosa) {
                    VentanasUtils.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                        ConstantesUtils.TITULO_EXITO,
                        ConstantesUtils.ALERTA_REGISTRO_EXITOSO
                    );
                    VentanasUtils.cerrarVentana(txtfPuesto);
                } else {
                    VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                        ConstantesUtils.TITULO_ERROR,
                        ConstantesUtils.ALERTA_REGISTRO_FALLIDO
                    );
                }
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
