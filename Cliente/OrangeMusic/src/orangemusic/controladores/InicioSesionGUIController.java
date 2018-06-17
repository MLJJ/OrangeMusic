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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import orangemusic.OrangeMusic;
import orangemusic.modelo.Usuario;
import orangemusic.conexion.Conexion;
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
        Conexion conexion = new Conexion();
        if (conexion.validarConfiguracion()) {
            Usuario usr = new Usuario();
            try {
                usr.setContraseña(UtileriaSHA2.encriptarContrasena(txtContrasena.getText()));
            } catch (NoSuchAlgorithmException ex) {
                System.out.println("Error al encriptar la contraseña");
            }
            usr.setCorreo(txtUsuario.getText());
            usr = usr.autenticar(usr);
            if (usr != null) {
                try {
                    main.desplegarMenuPrincipal(usr);
                } catch (IOException ex) {
                    System.out.println("Error al desplegar ventana de menu principal: " + ex.getMessage());
                    ex.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Error, usuario y/o contraseña incorrectos");
                alert.show();
            }
        } else {
            this.configurarIP();
        }
    }

    public void setMain(OrangeMusic main) {
        this.main = main;
    }

    @FXML
    private void irRegistrarUsuario(ActionEvent event) {
        Conexion conexion = new Conexion();
        if (conexion.validarConfiguracion()) {
            try {
                this.main.desplegarRegistrarSesion();
            } catch (IOException ex) {
                System.out.println("Error al desplegar la ventana de Registro de usuario");
            }
        }else{
            this.configurarIP();
        }
    }

    private void configurarIP() {
        try {
            Stage ventana = new Stage();
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("/orangemusic/vistas/IpConfiguracionGUI.fxml"));
            Parent root = (Parent) cargador.load();
            Scene scene = new Scene(root);
            ventana.getIcons().add(new Image("/recursos/imagenes/logo.png"));

            ventana.setScene(scene);
            ventana.show();
        } catch (IOException ex) {
            Logger.getLogger(InicioSesionGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
