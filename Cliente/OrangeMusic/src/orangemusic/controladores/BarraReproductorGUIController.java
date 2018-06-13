/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orangemusic.controladores;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaView;

/**
 * FXML Controller class
 *
 * @author Cristhian Ubaldo Promotor
 */
public class BarraReproductorGUIController implements Initializable {

    @FXML
    private JFXButton buttonReproducir;
    @FXML
    private JFXButton buttonParar;
    @FXML
    private JFXButton buttonAnterior;
    @FXML
    private JFXButton buttonSiguiente;
    @FXML
    private MediaView mediaViewReproccion;
    @FXML
    private Slider sliderReproducion;
    @FXML
    private Label labelNombreCancion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void PararCancion(ActionEvent event) {
    }

    @FXML
    private void cambiarAnterior(ActionEvent event) {
    }

    @FXML
    private void cambiarsiguiente(ActionEvent event) {
    }
    
}
