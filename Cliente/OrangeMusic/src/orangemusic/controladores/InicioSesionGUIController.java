/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orangemusic.controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import orangemusic.OrangeMusic;
import orangemusic.modelo.Usuario;
import orangemusic.utilerias.UtileriaSHA2;

/**
 * FXML Controller class
 *
 * @author Cristhian Ubaldo Promotor
 */
public class InicioSesionGUIController implements Initializable {

    @FXML
    private JFXButton btnCerrar;
    @FXML
    private JFXButton btnIniciarSesion;
    @FXML
    private JFXPasswordField txtContrasena;
    @FXML
    private JFXTextField txtUsuario;
    private OrangeMusic main;
    @FXML
    private JFXButton btnRegistrarUsuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cerrar(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void iniciarSesion(ActionEvent event) {
        Usuario usr = new Usuario();
        try {
            usr.setContraseña(UtileriaSHA2.encriptarContrasena(txtContrasena.getText()));
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Error al encriptar la contraseña");
        }
        usr.setCorreo(txtUsuario.getText());
        usr = usr.autenticar(usr);
        if(usr != null){
            try {
                main.desplegarMenuPrincipal(usr);
            } catch (IOException ex) {
                System.out.println("Error al desplegar ventana de menu principal: " + ex.getMessage());
                ex.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error, usuario y/o contraseña incorrectos");
            alert.show();
        }
    }

    public void setMain(OrangeMusic main) {
        this.main = main;
    }

    @FXML
    private void irRegistrarUsuario(ActionEvent event) {
        try {
            this.main.desplegarRegistrarSesion();
        } catch (IOException ex) {
            System.out.println("Error al desplegar la ventana de Registro de usuario");
        }
    }
    
}
