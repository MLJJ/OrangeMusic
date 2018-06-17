package orangemusic.controladores;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import orangemusic.conexion.ClienteUpload;
import orangemusic.modelo.Album;
import orangemusic.modelo.Artista;
import orangemusic.modelo.Genero;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        System.getProperties().put("servicio", "http://localhost:8080/OrangeMusic/");
        System.getProperties().put("ip", "localhost");
        cargarGeneros(new Genero().sacarGeneros());
        cargarArtistas(new Artista().sacarArtista());
    }
    
    private void cargarGeneros(List<Genero> generos){
        cbGenero.getItems().clear();
        cbGenero.setItems(FXCollections.observableArrayList(generos));
    }
    
    private void cargarArtistas(List<Artista> artistas){
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
            Album album = new Album();
            album.setAñoLanzamiento(Integer.parseInt(tfAñoLanzamiento.getText()));
            album.setDisquera(tfDiscografico.getText());
            album.setNombreAlbum(tfNombreAlbum.getText());
            album.setArtista(cbArtista.getValue());
            album.setGenero(cbGenero.getValue());
            String resultado = album.subirAlbum(album, true, zipCanciones, imagen);
            try{
                JSONArray lista = new JSONArray(resultado);
            }catch(JSONException e){
                MensajeController.mensajeAdvertencia("No se podido guardar el album");
                return;
            }
            ClienteUpload upload = new ClienteUpload(zipCanciones, resultado);
            Thread hilo = new Thread(upload);
            hilo.start();
            
            try {
                hilo.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(SubirCancionGUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.out.println("Se subio");
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
    private void accionCrearArtista(ActionEvent evento) throws IOException {
        Stage stage = new Stage();
        FXMLLoader cargador = new FXMLLoader(getClass().getResource("/orangemusic/vistas/CrearArtistaGUI.fxml"));
        Parent root = (Parent) cargador.load();
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("/recursos/imagenes/logo.png"));

        CrearArtistaGUIController controlador = cargador.getController();
        controlador.cargarControlador(this);

        stage.setScene(scene);
        stage.show();
    }

    public void cargarNuevoArtista(){
        cargarArtistas(new Artista().sacarArtista());
    }
    
    @FXML
    private void accionCargarZip(ActionEvent evento) {
        FileChooser elegir = new FileChooser();
        FileChooser.ExtensionFilter extensionZip = new FileChooser.ExtensionFilter("Add Files(*.zip)", "*.zip");
        elegir.getExtensionFilters().add(extensionZip);
        File rutaZip = elegir.showOpenDialog(null);
        
        if(rutaZip != null){
            zipCanciones = rutaZip;
            tfCancion.setText(zipCanciones.getName());
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
        return tfAñoLanzamiento.getText().trim().isEmpty() || zipCanciones == null || imagen == null
                || tfDiscografico.getText().trim().isEmpty() || cbArtista.getValue() == null || cbGenero.getValue() == null;
    }
}
