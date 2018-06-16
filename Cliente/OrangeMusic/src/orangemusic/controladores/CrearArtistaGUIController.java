package orangemusic.controladores;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import orangemusic.modelo.Artista;

/**
 * FXML Controller class
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class CrearArtistaGUIController implements Initializable {

    @FXML
    private JFXTextField tfNombreArtista;
    private SubirCancionGUIController controlador;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void cargarControlador(SubirCancionGUIController controlador){
        this.controlador = controlador;
    }

    @FXML
    private void accionGuardarArtista() {
        if(tfNombreArtista.getText().trim().isEmpty()){
            MensajeController.mensajeAdvertencia("Ingrese el nombre del artista");
        }else{
            Artista artista = new Artista();
            artista.setNombreArtista(tfNombreArtista.getText().trim());
            String mensaje = artista.guardarArtista(artista);
            
            if(mensaje.equals("Se ha registrado el artista")){
                MensajeController.mensajeInformacion(mensaje);
                controlador.cargarNuevoArtista();
                accionCancelar();
            }else{
                MensajeController.mensajeInformacion(mensaje);
            }
        }
        
    }

    @FXML
    private void accionCancelar() {
        Stage ventana = (Stage) tfNombreArtista.getScene().getWindow();
        ventana.close();
    }

    @FXML
    private void validarEntradas(KeyEvent evento) {
        if(tfNombreArtista.getText().length() >= 45){
            evento.consume();
        }
    }
    
}
