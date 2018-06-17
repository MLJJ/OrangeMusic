/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orangemusic.controladores;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import orangemusic.OrangeMusic;
import orangemusic.modelo.ListaReproduccion;
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
    @FXML
    private TitledPane titledPanePlayList;
    private List<ListaReproduccion> misPlayList;

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

    public Usuario getUsuario() {
        return usr;
    }

    public List<ListaReproduccion> getMisPlayList() {
        return misPlayList;
    }

    public void setMisPlayList(List<ListaReproduccion> misPlayList) {
        this.misPlayList = misPlayList;
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
        controlador.setControladorMenu(this);

        panelReproductor.getChildren().clear();
        panelReproductor.getChildren().addAll(fxml.getChildrenUnmodifiable());
    }

    @FXML
    private void lanzarBuscar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/orangemusic/vistas/BuscarGUI.fxml"));
        Parent fxml = (Parent) loader.load();
        BuscarGUIController controlador = loader.getController();
        controlador.setBusqueda(txtBusqueda.getText());
        txtBusqueda.setText("");
        gridPanelPrincipal.getChildren().clear();
        gridPanelPrincipal.getChildren().addAll(fxml.getChildrenUnmodifiable());
    }

    public void lanzarMusicaDetalles() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/orangemusic/vistas/ReproductorMusicaGUI.fxml"));
        Parent fxml = (Parent) loader.load();
        ReproductorMusicaGUIController controlador = loader.getController();
        this.controladorReproductor = controlador;
        controlador.setMenuPrincipal(this);

        gridPanelPrincipal.getChildren().clear();
        gridPanelPrincipal.getChildren().addAll(fxml.getChildrenUnmodifiable());
    }

    public void lanzarListasReproduccion() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/orangemusic/vistas/ListaReproduccionGUI.fxml"));
        Parent fxml = (Parent) loader.load();
        ListaReproduccionGUIController controlador = loader.getController();
        this.controladorListas = controlador;
        controlador.setMenuPrincipal(this);
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
        if (this.misPlayList == null) {
            misPlayList = new ListaReproduccion().obtenerMisListas(usr);
            VBox contenedorListas = new VBox();
            for (int i = 0; i < misPlayList.size(); i++) {
                
                JFXButton buttonLista = new JFXButton(misPlayList.get(i).getNombreLista());
                buttonLista.setId(misPlayList.get(i).getIdListaReproduccion().toString());
                buttonLista.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        try {
                            lanzarListasReproduccion();
                            controladorListas.setMenuPrincipal(MenuPrincipalGUIController.this);
                            int posLista = 0;
                            for (int i = 0; i < misPlayList.size(); i++) {
                                if(Objects.equals(misPlayList.get(i).getIdListaReproduccion(), Integer.valueOf(buttonLista.getId()))){
                                    posLista = i;
                                }
                            }
                            controladorListas.setListaReproduccion(misPlayList.get(posLista));
                            controladorListas.cargarLista();
                        }catch (IOException ex) {
                            Logger.getLogger(MenuPrincipalGUIController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                contenedorListas.getChildren().add(buttonLista);
            }
            contenedorListas.getChildren().add(0, btnBuscar);
            titledPanePlayList.setContent(contenedorListas);
        }
    }
}
