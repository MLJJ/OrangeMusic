/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orangemusic.controladores;

import orangemusic.modelo.Cancion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseEvent;
import orangemusic.modelo.ListaReproduccion;

/**
 * FXML Controller class
 *
 * @author enriq
 */
public class VisualizarHistorialGUIController implements Initializable {

    @FXML
    private JFXTreeTableView tableView;

    @FXML
    private TreeTableColumn nombre;

    @FXML
    private TreeTableColumn artista;

    @FXML
    private TreeTableColumn album;

    @FXML
    private TreeTableColumn genero;
    @FXML
    private TreeTableColumn imagen;

    private ObservableList<Cancion> canciones = null;
    private String usuario;
    private List<Cancion> lista;
    private ListaReproduccion listaReproduccion;
    private MenuPrincipalGUIController menuPricipal;

    public MenuPrincipalGUIController getMenuPricipal() {
        return menuPricipal;
    }

    public void setMenuPricipal(MenuPrincipalGUIController menuPricipal) {
        this.menuPricipal = menuPricipal;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        canciones = FXCollections.observableArrayList();
        usuario = System.getProperty("correo");
        lista = new ArrayList<>();

        imagen.setCellValueFactory(new TreeItemPropertyValueFactory<>("imagen"));
        nombre.setCellValueFactory(new TreeItemPropertyValueFactory<>("nombre"));
        artista.setCellValueFactory(new TreeItemPropertyValueFactory<>("artista"));
        album.setCellValueFactory(new TreeItemPropertyValueFactory<>("album"));
        genero.setCellValueFactory(new TreeItemPropertyValueFactory<>("genero"));

        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    
                    TreeItem<Cancion> item = (TreeItem<Cancion>) tableView.getSelectionModel().getSelectedItem();
                    System.out.println("Selected Text : " + item.getValue().getNombreCancion());
                    ListaReproduccion listaActual = new ListaReproduccion();
                    List<Cancion> cancion = new ArrayList<>();
                    cancion.add(item.getValue());
                    listaActual.setCanciones(cancion);
                    listaActual.setNombreLista(item.getValue().getNombreCancion());
                    menuPricipal.getControladorBarra().cargarCancion(listaActual, listaActual.getCanciones().get(0));
      
                }
            }

        });

        buscarHistorial();
    }

    public void buscarCancion(int idCancion) {

    }

    public void buscarHistorial() {
        listaReproduccion = new ListaReproduccion();
        listaReproduccion = listaReproduccion.buscarHistorial(System.getProperty("correo"));
        if (listaReproduccion != null) {
            for (Cancion cancion : listaReproduccion.getCanciones()) {
                cancion.buscarImagenCancion();
                canciones.add(cancion);
            }
            TreeItem<Cancion> root = new RecursiveTreeItem<>(canciones, RecursiveTreeObject::getChildren);
            tableView.setRoot(root);
            tableView.setShowRoot(false);
        }
    }
}
