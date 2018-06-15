package orangemusic.controladores;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import orangemusic.modelo.Artista;
import orangemusic.modelo.Genero;

/**
 * FXML Controller class
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class SubirCancionGUIController implements Initializable {

    @FXML
    private JFXTextField tfNombreAlbum;
    @FXML
    private JFXTextField tfAñoLanzamiento;
    @FXML
    private JFXTextField tfDiscografico;
    @FXML
    private JFXComboBox<Genero> cbGenero;
    @FXML
    private JFXComboBox<Artista> cbArtista;
    @FXML
    private JFXTextField tfCancion;
    @FXML
    private JFXCheckBox chDisponibilidad;
    @FXML
    private ImageView imgImagen;
    
    private File imagen;
    private File zipCanciones;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbArtista.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Artista>(){
            @Override
            public void changed(ObservableValue<? extends Artista> observable, Artista oldValue, Artista newValue) {
                
            }
            
        });
    }
    
    public void cargarGeneros(List<Genero> generos){
        cbGenero.getItems().clear();
        cbGenero.setItems(FXCollections.observableArrayList(generos));
    }
    
    public void cargarArtistas(List<Artista> artistas){
        cbArtista.getItems().clear();
        cbArtista.setItems(FXCollections.observableArrayList(artistas));
    }

    @FXML
    private void accionAbrirImagen() {
        FileChooser elegir = new FileChooser();
        FileChooser.ExtensionFilter extensionJPG = new FileChooser.ExtensionFilter("Imagen", "*.jpg","*.png");
        elegir.getExtensionFilters().add(extensionJPG);
        File rutaImagen = elegir.showOpenDialog(null);
        if (rutaImagen == null) {
            System.out.println("");
        } else {
            Image image = null;
            this.imagen = rutaImagen;
            try {
                image = new Image(rutaImagen.toURI().toURL().toString(), 225, 225, false, true, true);
            } catch (MalformedURLException ex) {
                Logger.getLogger(SubirCancionGUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.imgImagen.setImage(image);
        }
    }

    @FXML
    private void accionSubirAlbum(ActionEvent evento) {
        if(validarCampos()){
            MensajeController.mensajeAdvertencia("Hay campos vacios");
        }else{
            
        }
    }

    @FXML
    private void accionCancelar(ActionEvent evento) {
        this.tfAñoLanzamiento.setText("");
        this.tfCancion.setText("");
        this.tfDiscografico.setText("");
        this.tfNombreAlbum.setText("");
        this.cbArtista.getItems().clear();
        this.cbGenero.getSelectionModel().select(null);
        this.chDisponibilidad.setSelected(false);
        this.zipCanciones = null;
        this.imagen = null;
        this.imgImagen.setImage(new Image("recursos/imagenes/logo.png", 225, 225, false, true, true));
    }

    @FXML
    private void accionCrearArtista(ActionEvent evento) {
        
    }

    @FXML
    private void accionCargarZip(ActionEvent evento) {
        FileChooser elegir = new FileChooser();
        FileChooser.ExtensionFilter extensionZip = new FileChooser.ExtensionFilter("Add Files(*.zip)", "*.zip");
        elegir.getExtensionFilters().add(extensionZip);
        File rutaZip = elegir.showOpenDialog(null);
        
        if(rutaZip != null){
            zipCanciones = rutaZip;
            tfNombreAlbum.setText(zipCanciones.getName());
        }else{
            MensajeController.mensajeAdvertencia("No es un zip");
        }
    }
    
    @FXML
    private void restringirEntradaDeTeclado(KeyEvent evento){
        char caracter = evento.getCharacter().charAt(0);
        JFXTextField texto = (JFXTextField) evento.getSource();
        if(texto.getText().length() >= 50){
            evento.consume();
        }
    }

    @FXML
    private void restringirLetras(KeyEvent evento) {
        char caracter = evento.getCharacter().charAt(0);
        JFXTextField texto = (JFXTextField) evento.getSource();
        
        if(!Character.isDigit(caracter) || texto.getText().length() >= 4){
            evento.consume();
        }
    }
    
    private boolean validarCampos(){
        return tfAñoLanzamiento.getText().trim().isEmpty() || tfCancion.getText().trim().isEmpty() || tfDiscografico.getText().trim().isEmpty()
                || tfDiscografico.getText().trim().isEmpty() || cbArtista.getValue() == null || cbGenero.getValue() == null;
    }
}
