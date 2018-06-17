package orangemusic.controladores;

import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import orangemusic.conexion.Conexion;

/**
 * FXML Controller class
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class IpConfiguracionGUIController implements Initializable {

    @FXML
    private JFXTextField tfIp1;
    @FXML
    private JFXTextField tfIp3;
    @FXML
    private JFXTextField tfIp2;
    @FXML
    private JFXTextField tfIp4;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        File ruta = new File(System.getProperty("user.home") + "/orangeMusic/");
        if(!ruta.exists()){
            ruta.mkdir();
        }
    }

    @FXML
    private void restringirIP(KeyEvent evento) {
        char caracter = evento.getCharacter().charAt(0);
        JFXTextField campo = (JFXTextField) evento.getSource();

        if (!Character.isDigit(caracter) || campo.getText().length() >= 3) {
            evento.consume();
        }

    }

    @FXML
    private void accionValidarIP(ActionEvent event) {

        if (validarCampos()) {
            MensajeController.mensajeAdvertencia("Hay campos vacios");
        } else {
            String ip = tfIp1.getText() + "." + tfIp2.getText() + "." + tfIp3.getText() + "." + tfIp4.getText();
            Conexion conexion = new Conexion();
            if (conexion.provarConexion(ip)) {
                String ipArchivada = null;
                ipArchivada = System.getProperty("servicio");
                if(ipArchivada == null){
                    System.getProperties().put("servicio", "http://"+ip+":8080/OrangeMusic/");
                }else{
                    System.getProperties().replace("servicio", "http://"+ip+":8080/OrangeMusic/");
                }
                
                ipArchivada = System.getProperty("ip");
                
                if(ipArchivada == null){
                    System.getProperties().put("ip",ip);
                }else{
                    System.getProperties().replace("ip",ip);
                }
                
                conexion.guardarArchivo(ip);
                MensajeController.mensajeInformacion("Se ha configurado al ip exitosamente");
                Stage ventana = (Stage) tfIp1.getScene().getWindow();
                ventana.close();
            } else {
                MensajeController.mensajeAdvertencia("No se ha podido conectar al servidor");
            }
        }
    }

    

    private boolean validarCampos() {
        return tfIp1.getText().trim().isEmpty() || tfIp2.getText().trim().isEmpty() || tfIp3.getText().trim().isEmpty()
                || tfIp4.getText().trim().isEmpty();
    }

}
