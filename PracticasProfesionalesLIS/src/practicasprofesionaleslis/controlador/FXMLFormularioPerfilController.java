package practicasprofesionaleslis.controlador;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import practicasprofesionaleslis.modelo.dao.EstudianteDAO;
import practicasprofesionaleslis.modelo.pojo.Coordinador;
import practicasprofesionaleslis.modelo.pojo.Estudiante;
import practicasprofesionaleslis.modelo.pojo.Evaluador;
import practicasprofesionaleslis.modelo.pojo.ProfesorEE;
import practicasprofesionaleslis.utilidades.ConstantesUtils;
import practicasprofesionaleslis.utilidades.VentanasUtils;

public class FXMLFormularioPerfilController implements Initializable {
    private Estudiante estudiante;
    private Coordinador coordinador;
    private ProfesorEE profesorEE;
    private Evaluador evaluador;
    private char modoEdicion;
    private boolean operacionExitosa;
    private File archivoFoto;

    @FXML
    private ImageView imgFotoPerfil;
    @FXML
    private TextField txtfNombre;
    @FXML
    private TextField txtfApellidoPaterno;
    @FXML
    private TextField txtfApellidoMaterno;
    @FXML
    private TextField txtfCorreoInstitucional;
    @FXML
    private TextField txtfMatricula;
    @FXML
    private PasswordField pwdfContraseña;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}    
    
    public void inicializarInformacionPerfil(Object usuario) {
        if (usuario instanceof Coordinador) {
            this.coordinador = (Coordinador) usuario;
            txtfNombre.setText(coordinador.getNombre());
            txtfApellidoPaterno.setText(coordinador.getApellidoPaterno());
            txtfApellidoMaterno.setText(coordinador.getApellidoMaterno());
            txtfCorreoInstitucional.setText(coordinador.getCorreoInstitucional());
            txtfMatricula.setText(coordinador.getNumeroPersonal());
            pwdfContraseña.setText("");
            this.modoEdicion = 'C';
        } else if (usuario instanceof Estudiante) {
            this.estudiante = (Estudiante) usuario;
            txtfNombre.setText(estudiante.getNombre());
            txtfApellidoPaterno.setText(estudiante.getApellidoPaterno());
            txtfApellidoMaterno.setText(estudiante.getApellidoMaterno());
            txtfCorreoInstitucional.setText(estudiante.getCorreoInstitucional());
            txtfMatricula.setText(estudiante.getMatricula());
            pwdfContraseña.setText("");
            configurarFotoEstudiante(estudiante.getId());
            this.modoEdicion = 'S';
        } else if (usuario instanceof ProfesorEE) {
            this.profesorEE = (ProfesorEE) usuario;
            txtfNombre.setText(profesorEE.getNombre());
            txtfApellidoPaterno.setText(profesorEE.getApellidoPaterno());
            txtfApellidoMaterno.setText(profesorEE.getApellidoMaterno());
            txtfCorreoInstitucional.setText(profesorEE.getCorreoInstitucional());
            txtfMatricula.setText(profesorEE.getNumeroPersonal());
            pwdfContraseña.setText("");
            this.modoEdicion = 'P';
        } else if (usuario instanceof Evaluador) {
            this.evaluador = (Evaluador) usuario;
            txtfNombre.setText(evaluador.getNombre());
            txtfApellidoPaterno.setText(evaluador.getApellidoPaterno());
            txtfApellidoMaterno.setText(evaluador.getApellidoMaterno());
            txtfCorreoInstitucional.setText(evaluador.getCorreoInstitucional());
            txtfMatricula.setText(evaluador.getNumeroPersonal());
            pwdfContraseña.setText("");
            this.modoEdicion = 'E';
        }
        txtfMatricula.setDisable(true);
        txtfCorreoInstitucional.setDisable(true);
    }

    @FXML
    private void clicBtnCargarFoto(ActionEvent event) {
        FileChooser dialogoSeleccion = new FileChooser();
        dialogoSeleccion.setTitle("Selecciona una foto");
        FileChooser.ExtensionFilter filtroImg = new FileChooser.ExtensionFilter("Archivos de Imagen", "*.jpeg", "*.png", "*.jpg");
        dialogoSeleccion.getExtensionFilters().add(filtroImg);
        archivoFoto = dialogoSeleccion.showOpenDialog(VentanasUtils.obtenerEscenarioComponente(txtfNombre));
        
        if (archivoFoto != null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(archivoFoto);
                Image imagen = SwingFXUtils.toFXImage(bufferedImage, null);
                imgFotoPerfil.setImage(imagen);
            } catch (IOException e) {
                e.printStackTrace();
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                        ConstantesUtils.TITULO_ERROR,
                        ConstantesUtils.ALERTA_ERROR_CARGAR_IMAGEN
                );
            }
        }
    }

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        if (validarCamposVacios()) {
            try {
                switch (modoEdicion) {
                    case 'S':
                        obtenerEstudianteEdicion();
                        operacionExitosa = EstudianteDAO.editarEstudiante(estudiante);
                    case 'C':
                        // TODO
                    case 'P':
                        // TODO
                    case 'E':
                        // TODO
                }
                
                if (operacionExitosa) {
                    VentanasUtils.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                            ConstantesUtils.TITULO_EXITO,
                            ConstantesUtils.ALERTA_ACTUALIZACION_EXITOSA
                    );
                    VentanasUtils.cerrarVentana(txtfNombre);
                } else {
                    VentanasUtils.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                            ConstantesUtils.TITULO_ERROR,
                            ConstantesUtils.ALERTA_ACTUALIZACION_FALLIDA
                    );
                }
            } catch (SQLException | IOException e) {
                VentanasUtils.mostrarAlertaSimple(Alert.AlertType.ERROR,
                        ConstantesUtils.TITULO_ERROR,
                        e.getMessage());
            }
        }
    }
    
    private boolean validarCamposVacios() {
        boolean camposValidos = true;
        
        if (txtfNombre.getText().isEmpty()) camposValidos = false;
        if (txtfApellidoPaterno.getText().isEmpty()) camposValidos = false;
        if (txtfApellidoMaterno.getText().isEmpty()) camposValidos = false;
        if (pwdfContraseña.getText().isEmpty()) camposValidos = false;
        
        return camposValidos;
    }
    
    private void obtenerEstudianteEdicion() throws IOException {
        this.estudiante.setNombre(txtfNombre.getText().trim());
        this.estudiante.setApellidoPaterno(txtfApellidoPaterno.getText().trim());
        this.estudiante.setApellidoMaterno(txtfApellidoMaterno.getText().trim());
        this.estudiante.setContraseña(pwdfContraseña.getText().trim());
        this.estudiante.setFotoPerfil(Files.readAllBytes(archivoFoto.toPath()));
    }
    
    private void obtenerCoordinadorEdicion() throws IOException {
        this.coordinador.setNombre(txtfNombre.getText().trim());
        this.coordinador.setApellidoPaterno(txtfApellidoPaterno.getText().trim());
        this.coordinador.setApellidoMaterno(txtfApellidoMaterno.getText().trim());
        this.coordinador.setContraseña(pwdfContraseña.getText().trim());
        this.coordinador.setFotoPerfil(Files.readAllBytes(archivoFoto.toPath()));
    }
    
    private void obtenerProfesorEEEdicion() throws IOException {
        this.profesorEE.setNombre(txtfNombre.getText().trim());
        this.profesorEE.setApellidoPaterno(txtfApellidoPaterno.getText().trim());
        this.profesorEE.setApellidoMaterno(txtfApellidoMaterno.getText().trim());
        this.profesorEE.setContraseña(pwdfContraseña.getText().trim());
        this.profesorEE.setFotoPerfil(Files.readAllBytes(archivoFoto.toPath()));
    }
    
    private void obtenerEvaluadorEdicion() throws IOException {
        this.evaluador.setNombre(txtfNombre.getText().trim());
        this.evaluador.setApellidoPaterno(txtfApellidoPaterno.getText().trim());
        this.evaluador.setApellidoMaterno(txtfApellidoMaterno.getText().trim());
        this.evaluador.setContraseña(pwdfContraseña.getText().trim());
        this.evaluador.setFotoPerfil(Files.readAllBytes(archivoFoto.toPath()));
    }
    
    private void configurarFotoEstudiante(int idEstudiante) {
        try {
            byte[] foto = EstudianteDAO.obtenerFotoEstudiante(idEstudiante);
            estudiante.setFotoPerfil(foto);
            ByteArrayInputStream input = new ByteArrayInputStream(foto);
            Image imagen = new Image(input);
            imgFotoPerfil.setImage(imagen);
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
