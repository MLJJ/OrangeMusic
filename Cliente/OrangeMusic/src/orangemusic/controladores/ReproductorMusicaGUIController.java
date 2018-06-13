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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Cristhian Ubaldo Promotor
 */
public class ReproductorMusicaGUIController implements Initializable {

    @FXML
    private ImageView imagenAlbum;
    @FXML
    private ComboBox<?> comboBoxCalidad;
    @FXML
    private JFXButton buttonRadio;
    @FXML
    private Label labelArtista;
    @FXML
    private Label labelAlbum;
    @FXML
    private Label labelCancion;
    @FXML
    private Label labelGenero;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cambiarCalidad(ActionEvent event) {
    }

    @FXML
    private void crearEstacion(ActionEvent event) {
    }
    
}
