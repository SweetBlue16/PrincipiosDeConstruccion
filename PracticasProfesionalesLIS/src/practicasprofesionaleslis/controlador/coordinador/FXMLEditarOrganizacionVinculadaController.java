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

public class FXMLEditarOrganizacionVinculadaController implements Initializable {
    private OrganizacionVinculada organizacionVinculada;

    @FXML
    private TextField txtfCorreoElectronico;
    @FXML
    private TextField txtfTelefono;
    @FXML
    private TextField txtfDomicilioFiscal;
    @FXML
    private TextField txtfRazonSocial;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    public void inicializarOrganizacionVinculada(OrganizacionVinculada organizacionVinculada) {
        if (organizacionVinculada != null) {
            this.organizacionVinculada = organizacionVinculada;
            configurarCamosOrganizacion();
        }
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        VentanasUtils.cerrarVentana(txtfTelefono);
    }

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        try {
            validarCamposVacios();
            validarFormatoCampos();
            
            OrganizacionVinculada organizacionVinculada = new OrganizacionVinculada();
            organizacionVinculada.setId(this.organizacionVinculada.getId());
            organizacionVinculada.setNumProyectos(this.organizacionVinculada.getNumProyectos());
            organizacionVinculada.setRazonSocial(txtfRazonSocial.getText().trim());
            organizacionVinculada.setDomicilioFiscal(txtfDomicilioFiscal.getText().trim());
            organizacionVinculada.setCorreoElectronico(txtfCorreoElectronico.getText().trim());
            organizacionVinculada.setTelefono(txtfTelefono.getText().trim());
            
            boolean confirmacion = VentanasUtils.mostrarAlertaConfirmacion(ConstantesUtils.TITULO_CONFIRMAR, ConstantesUtils.ALERTA_CONFIRMAR_OPERACION);
            if (confirmacion) {
                boolean operacionExitosa = OrganizacionVinculadaDAO.editarOrganizacionVinculada(organizacionVinculada);
                if (operacionExitosa) {
                    VentanasUtils.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                        ConstantesUtils.TITULO_EXITO,
                        ConstantesUtils.ALERTA_ACTUALIZACION_EXITOSA
                    );
                    VentanasUtils.cerrarVentana(txtfTelefono);
                } else {
                    VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                        ConstantesUtils.TITULO_ERROR,
                        ConstantesUtils.ALERTA_ACTUALIZACION_FALLIDA
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
    
    private void configurarCamosOrganizacion() {
        txtfRazonSocial.setText(organizacionVinculada.getRazonSocial());
        txtfDomicilioFiscal.setText(organizacionVinculada.getDomicilioFiscal());
        txtfCorreoElectronico.setText(organizacionVinculada.getCorreoElectronico());
        txtfTelefono.setText(organizacionVinculada.getTelefono() != null ? organizacionVinculada.getTelefono() : "");
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
