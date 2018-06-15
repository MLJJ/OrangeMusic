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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            cargarReproductor();
        } catch (IOException ex) {
            System.out.println("Error");
        }
    }

    private void cargarReproductor() throws IOException {
        panelReproductor.getChildren().clear();
        Parent fxml = FXMLLoader.load(getClass().getResource("/orangemusic/vistas/BarraReproductorGUI.fxml"));
        panelReproductor.getChildren().addAll(fxml.getChildrenUnmodifiable());
        //barraMenu.setVisible(false);
        //menuDesplegado = false;
    }

    @FXML
    private void lanzarBuscar(ActionEvent event) throws IOException {
        gridPanelPrincipal.getChildren().clear();
        Parent fxml = FXMLLoader.load(getClass().getResource("/orangemusic/vistas/BuscarGUI.fxml"));
        gridPanelPrincipal.getChildren().addAll(fxml.getChildrenUnmodifiable());
    }

    public void setMain(OrangeMusic main) {
        this.main = main;
    }

    public void setUsuario(Usuario usr) {
        this.usr = usr;
    }
}
