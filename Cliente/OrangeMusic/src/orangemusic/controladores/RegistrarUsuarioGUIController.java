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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import orangemusic.OrangeMusic;
import orangemusic.modelo.ListaReproduccion;
import orangemusic.modelo.Usuario;
import orangemusic.utilerias.UtileriaSHA2;

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
    private JFXPasswordField txtContrasena;
    @FXML
    private JFXTextField txtUsuario;
    @FXML
    private JFXPasswordField txtContrasena1;
    @FXML
    private JFXTextField txtCorreo;
    private OrangeMusic main;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void irIniciarSesion(ActionEvent event) {
        try {
            main.desplegarInicioSesion();
        } catch (IOException ex) {
            System.out.println("Error al desplegar inicio de sesion");
        }
    }

    @FXML
    private void registrarUsuario(ActionEvent event) {
        if (txtContrasena.getText().equals(txtContrasena1.getText())) {
            if (!txtCorreo.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("No puedes dejar el campo de correo vacio");
                alert.show();
            } else if (!txtUsuario.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("No puedes dejar el campo de usuario vacio");
                alert.show();
            } else if (!txtUsuario.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("No puedes dejar el campo de contraseña vacio");
                alert.show();
            } else {
                Usuario usr = new Usuario();
                try {
                    usr.setContraseña(UtileriaSHA2.encriptarContrasena(txtContrasena.getText()));
                } catch (NoSuchAlgorithmException ex) {
                    System.out.println("Error al encriptar las contraseña");
                }
                usr.setCorreo(txtCorreo.getText());
                usr.setNombre(txtUsuario.getText());
                try {
                    if (usr.registrarUsuario(usr)) {
                        try {
                            new ListaReproduccion().crearHistorial(usr.getCorreo());
                            main.desplegarInicioSesion();
                        } catch (IOException ex) {
                            System.out.println("Error al desplegar inicio de sesion");
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("No se pudo registrar el usuario verifique conexion con el servidor");
                        alert.show();
                    }
                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Este usuario ya esta registrado");
                    alert.show();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Las contraseñas no coinciden, por favor vuelve a introducirlas");
            alert.show();
            limpiarContrasenas();
        }
    }

    private void limpiarContrasenas() {
        txtContrasena.setText("");
        txtContrasena1.setText("");
    }

    public void setMain(OrangeMusic main) {
        this.main = main;
    }

}
