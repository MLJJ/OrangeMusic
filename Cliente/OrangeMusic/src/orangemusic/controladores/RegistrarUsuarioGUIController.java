/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orangemusic.controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author arkadwn
 */
public class RegistrarUsuarioGUIController implements Initializable {

    @FXML
    private JFXButton btnRegresar;
    @FXML
    private JFXButton btnRegistrar;
    @FXML
    private JFXTextField txtContrasena;
    @FXML
    private JFXTextField txtUsuario;
    @FXML
    private JFXTextField txtContrasena1;
    @FXML
    private JFXTextField txtCorreo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cerrar(ActionEvent event) {
    }

    @FXML
    private void iniciarSesion(ActionEvent event) {
    }
    
}
