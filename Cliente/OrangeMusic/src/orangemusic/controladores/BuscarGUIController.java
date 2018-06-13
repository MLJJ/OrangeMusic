/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orangemusic.controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author arkadwn
 */
public class BuscarGUIController implements Initializable {

    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXListView<?> listResultados;
    @FXML
    private ImageView imgResultado;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbInformacion;
    @FXML
    private Label lbExtras;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buscar(ActionEvent event) {
    }
    
}
