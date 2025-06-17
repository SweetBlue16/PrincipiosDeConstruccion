package practicasprofesionaleslis.controlador.coordinador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class FXMLEntregasDocumentosController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private TableView<?> tableViewExperiencias;
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colBloque;
    @FXML
    private TableColumn<?, ?> colSeccion;
    @FXML
    private TableColumn<?, ?> colNrc;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
    }

    @FXML
    private void clicBtnSeleccionar(ActionEvent event) {
    }

}
