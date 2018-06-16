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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javax.swing.ImageIcon;
import orangemusic.modelo.ListaReproduccion;
import orangemusic.modelo.Usuario;

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

    ObservableList<Cancion> canciones = null;
    private String usuario;
    private List<Cancion> lista;

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
//
//        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
//
//            @Override
//            public void changed(ObservableValue observable, Object oldValue,
//                    Object newValue) {
//                TreeItem<Cancion> selectedItem = (TreeItem<Cancion>) newValue;
//                buscarCancion(selectedItem.getValue().getIdCancion());
//                System.out.println("Selected Text : " + selectedItem.getValue().getNombre());
//            }
//        });
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    TreeItem<String> item = (TreeItem<String>) tableView.getSelectionModel().getSelectedItem();
                    System.out.println("Selected Text : " + item.getValue());
                    //Mandar a reproducir la cancion
                }
            }

        });

        buscarHistorial();
    }

    public void buscarCancion(int idCancion) {

    }

    public void buscarHistorial() {
        ListaReproduccion listaReproduccion = new ListaReproduccion();
        listaReproduccion = listaReproduccion.buscarHistorial("enrique@gmail.com");
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
