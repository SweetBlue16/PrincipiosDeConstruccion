package practicasprofesionaleslis.controlador.coordinador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import practicasprofesionaleslis.modelo.dao.OrganizacionVinculadaDAO;
import practicasprofesionaleslis.modelo.pojo.OrganizacionVinculada;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLRegistrarOrganizacionVinculadaController implements Initializable {
    private static final int NUMERO_PROYECTOS_INICIAL = 0;

    @FXML
    private TextField txtfRazonSocial;
    @FXML
    private TextField txtfDomicilioFiscal;
    @FXML
    private TextField txtfTelefono;
    @FXML
    private TextField txtfCorreoElectronico;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}    

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        try {
            validarCamposVacios();
            validarFormatoCampos();
            
            OrganizacionVinculada organizacionVinculada = new OrganizacionVinculada();
            organizacionVinculada.setNumProyectos(NUMERO_PROYECTOS_INICIAL);
            organizacionVinculada.setRazonSocial(txtfRazonSocial.getText().trim());
            organizacionVinculada.setDomicilioFiscal(txtfDomicilioFiscal.getText().trim());
            organizacionVinculada.setCorreoElectronico(txtfCorreoElectronico.getText().trim());
            organizacionVinculada.setTelefono(txtfTelefono.getText().trim());

            boolean operacionExitosa = OrganizacionVinculadaDAO.registrarOrganizacionVinculada(organizacionVinculada);
            if (operacionExitosa) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                    ConstantesUtils.TITULO_EXITO,
                    ConstantesUtils.ALERTA_REGISTRO_EXITOSO
                );
                VentanasUtils.cerrarVentana(txtfTelefono);
            } else {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_REGISTRO_FALLIDO
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
        VentanasUtils.cerrarVentana(txtfTelefono);
    }
    
    private void validarCamposVacios() {
        if (txtfRazonSocial.getText().trim().isEmpty()) throw new IllegalArgumentException();
        if (txtfCorreoElectronico.getText().trim().isEmpty()) throw new IllegalArgumentException();
        if (txtfDomicilioFiscal.getText().trim().isEmpty()) throw new IllegalArgumentException();
    }
    
    private void validarFormatoCampos() {
        String razonSocial = txtfRazonSocial.getText().trim();
        String domicilioFiscal = txtfDomicilioFiscal.getText().trim();
        String correoElectronico = txtfCorreoElectronico.getText().trim();
        String telefono = txtfTelefono.getText().trim();
        
        if (razonSocial == null || razonSocial.length() > 100)
            throw new IllegalArgumentException();
        if (domicilioFiscal == null)
            throw new IllegalArgumentException();
        if (correoElectronico == null || !correoElectronico.matches(ConstantesUtils.REGEX_EMAIL) || correoElectronico.length() > 100)
            throw new IllegalArgumentException();
        if (!telefono.trim().isEmpty() && !telefono.matches(ConstantesUtils.REGEX_TELEFONO))
            throw new IllegalArgumentException();
    }
}
