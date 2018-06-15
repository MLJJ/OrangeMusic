/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orangemusic.controladores;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import orangemusic.OrangeMusic;
import orangemusic.modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author arkadwn
 */
public class MenuPrincipalGUIController implements Initializable {

    @FXML
    private GridPane panelReproductor;
    @FXML
    private GridPane gridPanelPrincipal;
    @FXML
    private TextField txtBusqueda;
    @FXML
    private JFXButton btnBuscar;
    private OrangeMusic main;
    private Usuario usr;
    private ListaReproduccionGUIController controladorListas;
    private ReproductorMusicaGUIController controladorReproductor;
    private BarraReproductorGUIController controladorBarra;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            cargarReproductor();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public ListaReproduccionGUIController getControladorListas() {
        return controladorListas;
    }

    public ReproductorMusicaGUIController getControladorReproductor() {
        return controladorReproductor;
    }

    public BarraReproductorGUIController getControladorBarra() {
        return controladorBarra;
    }

    private void cargarReproductor() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/orangemusic/vistas/BarraReproductoGUI.fxml"));
        Parent fxml = (Parent) loader.load();
        BarraReproductorGUIController controlador = loader.getController();
        this.controladorBarra = controlador;
        //controlador.setControladorMenu(this);

        panelReproductor.getChildren().clear();
        panelReproductor.getChildren().addAll(fxml.getChildrenUnmodifiable());
    }

    @FXML
    private void lanzarBuscar(ActionEvent event) throws IOException {
        gridPanelPrincipal.getChildren().clear();
        Parent fxml = FXMLLoader.load(getClass().getResource("/orangemusic/vistas/BuscarGUI.fxml"));
        gridPanelPrincipal.getChildren().addAll(fxml.getChildrenUnmodifiable());
    }

    public void lanzarMusicaDetalles() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/orangemusic/vistas/ReproductorMusicaGUI.fxml"));
        Parent fxml = (Parent) loader.load();
        ReproductorMusicaGUIController controlador = loader.getController();
        this.controladorReproductor = controlador;
        //controlador.setControladorMenu(this);

        gridPanelPrincipal.getChildren().clear();
        gridPanelPrincipal.getChildren().addAll(fxml.getChildrenUnmodifiable());
    }

    public void setMain(OrangeMusic main) {
        this.main = main;
    }

    public void setUsuario(Usuario usr) {
        this.usr = usr;
    }

    @FXML
    private void lanzarAdministrarListas(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("HOLAAAAAAAAAAAAAAAA");
        alert.show();
    }
}
