package practicasprofesionaleslis.controlador;

import practicasprofesionaleslis.controlador.profesoree.FXMLPrincipalProfesorEEController;
import practicasprofesionaleslis.controlador.evaluador.FXMLPrincipalEvaluadorController;
import practicasprofesionaleslis.controlador.estudiante.FXMLPrincipalEstudianteController;
import practicasprofesionaleslis.controlador.coordinador.FXMLPrincipalCoordinadorController;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import practicasprofesionaleslis.PracticasProfesionalesLIS;
import practicasprofesionaleslis.modelo.dao.ExpedienteDAO;
import practicasprofesionaleslis.modelo.dao.InicioSesionDAO;
import practicasprofesionaleslis.modelo.pojo.Coordinador;
import practicasprofesionaleslis.modelo.pojo.Estudiante;
import practicasprofesionaleslis.modelo.pojo.Evaluador;
import practicasprofesionaleslis.modelo.pojo.ProfesorEE;
import practicasprofesionaleslis.utilidades.VentanasUtils;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.ValidacionUtils;

public class FXMLInicioSesionController implements Initializable {
    @FXML
    private TextField txtfUsuario;
    @FXML
    private PasswordField pwdfPassword;
    @FXML
    private Label lblErrorUsuario;
    @FXML
    private Label lblErrorPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}    
    
    @FXML
    public void clicBtnVerificarSesion(ActionEvent event) {
        String usuario = txtfUsuario.getText().trim();
        String contraseña = pwdfPassword.getText();
        
        if (validarCampos(usuario, contraseña)) {
            int tipoUsuario = determinarTipoUsuario(usuario);
            
            if (!validarFormatoID(usuario, tipoUsuario)) {
                return;
            }
            
            switch (tipoUsuario) {
                case 1: validarCredencialesEstudiante(usuario, contraseña); break;
                case 2: validarCredencialesCoordinador(usuario, contraseña); break;
                case 3: validarCredencialesProfesorEE(usuario, contraseña); break;
                case 4: validarCredencialesEvaluador(usuario, contraseña); break;
                default: VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                        ConstantesUtils.TITULO_ADVERTENCIA,
                        ConstantesUtils.ALERTA_FORMATO_USUARIO_INVALIDO);
            }
        }
    }
    
    private boolean validarCampos(String usuario, String contraseña) {
        boolean camposValidos = true;
        lblErrorUsuario.setText("");
        lblErrorPassword.setText("");
        
        if (usuario.isEmpty()) {
            lblErrorUsuario.setText(ConstantesUtils.ALERTA_USUARIO_OBLIGATORIO);
            camposValidos = false;
        }
        if (contraseña.isEmpty()) {
            lblErrorPassword.setText(ConstantesUtils.ALERTA_CONTRASEÑA_OBLIGATORIA);
            camposValidos = false;
        }
        return camposValidos;
    }
    
    private boolean validarFormatoID(String usuario, int tipoUsuario) {
        boolean formatoValido = false;
        
        switch (tipoUsuario) {
            case 1:
                formatoValido = ValidacionUtils.validarIDEstudiante(usuario);
                break;
            case 2:
                formatoValido = ValidacionUtils.validarIDCoordinador(usuario);
                break;
            case 3:
                formatoValido = ValidacionUtils.validarIDProfesor(usuario);
                break;
            case 4: 
                formatoValido = ValidacionUtils.validarIDEvaluador(usuario);
                break;
            default: formatoValido = false;
        }
        
        if (!formatoValido) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                    ConstantesUtils.TITULO_ADVERTENCIA,
                    ConstantesUtils.ALERTA_FORMATO_USUARIO_INVALIDO);
        }
        return formatoValido;
    }
    
    private int determinarTipoUsuario(String usuario) {
        if (usuario.isEmpty()) {
            return 0;
        }
        
        char primerCaracter = usuario.charAt(0);
        switch (primerCaracter) {
            case 'S': return 1;
            case 'C': return 2;
            case 'P': return 3;
            case 'E': return 4;
            default: return 0;
        }
    }
    
    private void validarCredencialesEstudiante(String usuario, String contraseña) {
        try {
            Estudiante estudiante = InicioSesionDAO.verificarCredencialesEstudiante(usuario, contraseña);
            
            if (estudiante != null) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                        ConstantesUtils.TITULO_EXITO,
                        "Bienvenido(a) " + estudiante.toString() + " al sistema."
                );
                String nombreProyecto = ExpedienteDAO.obtenerNombreProyectoPorMatricula(estudiante.getMatricula());
                irPantallaPrincipalEstudiante(estudiante, nombreProyecto);
            } else {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                        ConstantesUtils.TITULO_ADVERTENCIA,
                        ConstantesUtils.ALERTA_CREDENCIALES_INVALIDAS
                );
            }
        } catch (SQLException e) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    e.getMessage()
            );
        }
    }
    
    private void validarCredencialesCoordinador(String usuario, String contraseña) {
        try {
            Coordinador coordinador = InicioSesionDAO.verificarCredencialesCoordinador(usuario, contraseña);
            
            if (coordinador != null) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                        ConstantesUtils.TITULO_EXITO,
                        "Bienvenido(a) " + coordinador.toString() + " al sistema."
                );
                irPantallaPrincipalCoordinador(coordinador);
            } else {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                        ConstantesUtils.TITULO_ADVERTENCIA,
                        ConstantesUtils.ALERTA_CREDENCIALES_INVALIDAS
                );
            }
        } catch (SQLException e) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    e.getMessage()
            );
        }
    }
    
    private void validarCredencialesProfesorEE(String usuario, String contraseña) {
        try {
            ProfesorEE profesorEE = InicioSesionDAO.verificarCredencialesProfesorEE(usuario, contraseña);
            
            if (profesorEE != null) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                        ConstantesUtils.TITULO_EXITO,
                        "Bienvenido(a) " + profesorEE.toString() + " al sistema."
                );
                irPantallaPrincipalProfesorEE(profesorEE);
            } else {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                        ConstantesUtils.TITULO_ADVERTENCIA,
                        ConstantesUtils.ALERTA_CREDENCIALES_INVALIDAS
                );
            }
        } catch (SQLException e) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    e.getMessage()
            );
        }
    }
    
    private void validarCredencialesEvaluador(String usuario, String contraseña) {
        try {
            Evaluador evaluador = InicioSesionDAO.verificarCredencialesEvaluador(usuario, contraseña);
            
            if (evaluador != null) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                        ConstantesUtils.TITULO_EXITO,
                        "Bienvenido(a) " + evaluador.toString() + " al sistema."
                );
                irPantallaPrincipalEvaluador(evaluador);
            } else {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.WARNING,
                        ConstantesUtils.TITULO_ADVERTENCIA,
                        ConstantesUtils.ALERTA_CREDENCIALES_INVALIDAS
                );
            }
        } catch (SQLException e) {
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    e.getMessage()
            );
        }
    }
    
    private void irPantallaPrincipalEstudiante(Estudiante estudiante, String nombreProyecto) {
        try {
            Stage escenarioBase = (Stage) txtfUsuario.getScene().getWindow();
            FXMLLoader cargador = new FXMLLoader(PracticasProfesionalesLIS.class.getResource("/practicasprofesionaleslis/vista/estudiante/FXMLPrincipalEstudiante.fxml"));
            Parent vista = cargador.load();
            FXMLPrincipalEstudianteController controlador = cargador.getController();
            controlador.inicializarDatosEstudiante(estudiante, nombreProyecto);
            
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle(ConstantesUtils.TITULO_PANTALLA_PRINCIPAL);
            escenarioBase.setResizable(false);
            escenarioBase.centerOnScreen();
            escenarioBase.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_CARGAR_VENTANA
            );
        }
    }
    
    private void irPantallaPrincipalCoordinador(Coordinador coordinador) {
        try {
            Stage escenarioBase = (Stage) txtfUsuario.getScene().getWindow();
            FXMLLoader cargador = new FXMLLoader(PracticasProfesionalesLIS.class.getResource("/practicasprofesionaleslis/vista/coordinador/FXMLPrincipalCoordinador.fxml"));
            Parent vista = cargador.load();
            FXMLPrincipalCoordinadorController controlador = cargador.getController();
            controlador.inicializarDatosCoordinador(coordinador);
            
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle(ConstantesUtils.TITULO_PANTALLA_PRINCIPAL);
            escenarioBase.setResizable(false);
            escenarioBase.centerOnScreen();
            escenarioBase.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_CARGAR_VENTANA
            );
        }
    }
    
    private void irPantallaPrincipalProfesorEE(ProfesorEE profesorEE) {
        try {
            Stage escenarioBase = (Stage) txtfUsuario.getScene().getWindow();
            FXMLLoader cargador = new FXMLLoader(PracticasProfesionalesLIS.class.getResource("/practicasprofesionaleslis/vista/profesoree/FXMLPrincipalProfesorEE.fxml"));
            Parent vista = cargador.load();
            FXMLPrincipalProfesorEEController controlador = cargador.getController();
            controlador.inicializarDatosProfesorEE(profesorEE);
            
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle(ConstantesUtils.TITULO_PANTALLA_PRINCIPAL);
            escenarioBase.setResizable(false);
            escenarioBase.centerOnScreen();
            escenarioBase.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_CARGAR_VENTANA
            );
        }
    }
    
    private void irPantallaPrincipalEvaluador(Evaluador evaluador) {
        try {
            Stage escenarioBase = (Stage) txtfUsuario.getScene().getWindow();
            FXMLLoader cargador = new FXMLLoader(PracticasProfesionalesLIS.class.getResource("/practicasprofesionaleslis/vista/evaluador/FXMLPrincipalEvaluador.fxml"));
            Parent vista = cargador.load();
            FXMLPrincipalEvaluadorController controlador = cargador.getController();
            controlador.inicializarDatosEvaluador(evaluador);
            
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle(ConstantesUtils.TITULO_PANTALLA_PRINCIPAL);
            escenarioBase.setResizable(false);
            escenarioBase.centerOnScreen();
            escenarioBase.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    ConstantesUtils.TITULO_ERROR,
                    ConstantesUtils.ALERTA_ERROR_CARGAR_VENTANA
            );
        }
    }
}
