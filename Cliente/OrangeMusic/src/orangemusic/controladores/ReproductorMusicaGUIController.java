/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orangemusic.controladores;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import orangemusic.modelo.Cancion;
import orangemusic.modelo.ListaReproduccion;

/**
 * FXML Controller class
 *
 * @author Cristhian Ubaldo Promotor
 */
public class ReproductorMusicaGUIController implements Initializable {

    @FXML
    private ImageView imagenAlbum;
    @FXML
    private ComboBox<String> comboBoxCalidad;
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

    //variables propias
    private Cancion cancion;
    private MenuPrincipalGUIController menuPricipal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxCalidad.getItems().setAll("Alta", "Media", "Baja");
    }

    public void setMenuPrincipal(MenuPrincipalGUIController menuPricipal) {
        this.menuPricipal = menuPricipal;

    }

    @FXML
    private void cambiarCalidad(ActionEvent event) {
        String calidadElegida = comboBoxCalidad.getSelectionModel().getSelectedItem();
        switch (calidadElegida) {
            case "Alta":
                calidadElegida="-1";
                break;
            case "Media":
                calidadElegida="-2";
                break;
            case "Baja":
                calidadElegida="-3";
                break;
            default:
                calidadElegida = "-1";
                break;
        }
        this.menuPricipal.getControladorBarra().cambiarCalidadCancion(calidadElegida);

    }

    @FXML
    private void crearEstacion(ActionEvent event) {
        List<Cancion> estacion = cancion.crearListaDeEstacion(cancion.getAlbum().getGenero().getIdGenero(),System.getProperty("usuario"));
        ListaReproduccion listaEstacion = new ListaReproduccion();
        listaEstacion.setCanciones(estacion);
        listaEstacion.setNombreLista("estacion");
        this.menuPricipal.getControladorBarra().cargarCancion(listaEstacion, estacion.get(0));
    }

    void cargarIformacionCancion(Cancion cancion) {

        this.cancion = cancion;
        this.labelAlbum.setText(labelAlbum.getText() + "   " + cancion.getAlbum().getNombreAlbum());
        this.labelArtista.setText(labelArtista.getText() + "   " + cancion.getAlbum().getArtista().getNombreArtista());
        this.labelCancion.setText(labelCancion.getText() + "   " + cancion.getNombreCancion());
        this.labelGenero.setText(labelGenero.getText() + "   " + cancion.getAlbum().getGenero().getNombreGenero());

    }

}
