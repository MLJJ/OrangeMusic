/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orangemusic.controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Cristhian Ubaldo Promotor
 */
public class ListaReproduccionGUIController implements Initializable {

    @FXML
    private JFXTextField textFieldNombreLista;
    @FXML
    private JFXButton buttonCrearListaGuardar;
    @FXML
    private JFXToggleButton toggleButton;
    @FXML
    private JFXButton buttonEditarListaGuardar;
    @FXML
    private JFXButton buttonEliminarCancion;
    @FXML
    private JFXButton buttonAgregarCancion;
    @FXML
    private JFXButton buttonDescargar;
    @FXML
    private JFXButton buttoneliminarCancion;
    @FXML
    private JFXButton buttonCancelar;
    @FXML
    private TableView<?> tableCanciones;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void crearListaReproduccion(ActionEvent event) {
    }

    @FXML
    private void cambiarPrivacidad(ActionEvent event) {
    }

    @FXML
    private void editarListaReproduccion(ActionEvent event) {
    }

    @FXML
    private void eliminarListaReproduccion(ActionEvent event) {
    }

    @FXML
    private void agregarCancion(ActionEvent event) {
    }

    @FXML
    private void descargar(ActionEvent event) {
    }

    @FXML
    private void eliminarCancion(ActionEvent event) {
    }

    @FXML
    private void cancelarOperacion(ActionEvent event) {
    }

    @FXML
    private void reproducirCancion(MouseEvent event) {
    }
    
}
