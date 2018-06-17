/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orangemusic.controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import orangemusic.modelo.Album;
import orangemusic.modelo.Artista;
import orangemusic.modelo.Cancion;
import orangemusic.modelo.Genero;

/**
 * FXML Controller class
 *
 * @author arkadwn
 */
public class BuscarGUIController implements Initializable {

    @FXML
    private JFXButton btnBuscar;
    @FXML
    private ImageView imgResultado;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbInformacion;
    @FXML
    private Label lbExtras;
    private String busqueda;
    @FXML
    private TextField txtBusqueda;
    @FXML
    private RowConstraints row0;
    @FXML
    private RowConstraints row1;
    @FXML
    private RowConstraints row2;
    @FXML
    private RowConstraints row3;
    @FXML
    private RowConstraints row4;
    @FXML
    private RowConstraints row5;
    @FXML
    private RowConstraints row6;
    @FXML
    private RowConstraints row7;
    @FXML
    private Label lbCancion;
    @FXML
    private JFXListView<Cancion> listCancion;
    @FXML
    private Label lbAlbum;
    @FXML
    private JFXListView<Album> listAlbum;
    @FXML
    private Label lbArtista;
    @FXML
    private JFXListView<Artista> listArtista;
    @FXML
    private Label lbGenero;
    @FXML
    private JFXListView<Genero> listGenero;
    @FXML
    private Label lbNoCoincidencias;
    @FXML
    private JFXButton btnPlay;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void buscar(ActionEvent event) {
        if (event != null) {
            busqueda = txtBusqueda.getText();
            pintarResultados(busqueda);
        } else {
            pintarResultados(busqueda);
        }
    }

    public void setBusqueda(String text) {
        this.busqueda = text;
        buscar(null);
    }

    private void pintarResultados(String search) {
        List<Genero> gnros = new Genero().buscarGenero(search);
        List<Artista> artistas = new Artista().buscarArtista(search);
        List<Album> albumns = new Album().buscarAlbum(search);
        List<Cancion> cancions = new Cancion().buscarCancion(search);
        boolean noResultados = false;

        if (cancions == null) {
            row0.setPrefHeight(0);
            row0.setMaxHeight(0);
            row0.setMinHeight(0);
            row1.setPrefHeight(0);
            row1.setMaxHeight(0);
            row1.setMinHeight(0);
            lbCancion.setVisible(false);
            listCancion.getItems().clear();
        } else {
            row0.setPrefHeight(33);
            row0.setMaxHeight(33);
            row0.setMinHeight(33);
            row1.setMaxHeight(377);
            row1.setMinHeight(377);
            row1.setPrefHeight(377);
            lbCancion.setVisible(true);
            lbCancion.setText("Canciones: ");
            listCancion.setItems(FXCollections.observableArrayList(cancions));
            noResultados = true;
        }
        if (albumns == null) {
            row2.setPrefHeight(0);
            row2.setMaxHeight(0);
            row2.setMinHeight(0);
            row3.setPrefHeight(0);
            row3.setMaxHeight(0);
            row3.setMinHeight(0);
            lbAlbum.setVisible(false);
            listAlbum.getItems().clear();
        } else {
            row2.setPrefHeight(33);
            row2.setMaxHeight(33);
            row2.setMinHeight(33);
            row3.setMaxHeight(377);
            row3.setMinHeight(377);
            row3.setPrefHeight(377);
            lbAlbum.setText("Albumnes: ");
            lbAlbum.setVisible(true);
            listAlbum.setItems(FXCollections.observableArrayList(albumns));
            noResultados = true;
        }
        if (artistas == null) {
            row4.setPrefHeight(0);
            row4.setMaxHeight(0);
            row4.setMinHeight(0);
            row5.setPrefHeight(0);
            row5.setMaxHeight(0);
            row5.setMinHeight(0);
            lbArtista.setVisible(false);
            listArtista.getItems().clear();
        } else {
            row4.setPrefHeight(33);
            row4.setMaxHeight(33);
            row4.setMinHeight(33);
            row5.setMaxHeight(377);
            row5.setMinHeight(377);
            row5.setPrefHeight(377);
            lbArtista.setText("Artistas: ");
            lbArtista.setVisible(true);
            listArtista.setItems(FXCollections.observableArrayList(artistas));
            noResultados = true;
        }
        if (gnros == null) {
            row6.setPrefHeight(0);
            row6.setMaxHeight(0);
            row6.setMinHeight(0);
            row7.setPrefHeight(0);
            row7.setMaxHeight(0);
            row7.setMinHeight(0);
            lbGenero.setVisible(false);
            listGenero.getItems().clear();
        } else {
            row6.setPrefHeight(33);
            row6.setMaxHeight(33);
            row6.setMinHeight(33);
            row7.setMaxHeight(377);
            row7.setMinHeight(377);
            row7.setPrefHeight(377);
            lbGenero.setText("Generos: ");
            lbGenero.setVisible(true);
            listGenero.setItems(FXCollections.observableArrayList(gnros));
            noResultados = true;
        }
        if (!noResultados) {
            row0.setPrefHeight(33);
            row0.setMaxHeight(33);
            row0.setMinHeight(33);
            lbNoCoincidencias.setVisible(true);
            lbCancion.setVisible(false);
            lbAlbum.setVisible(false);
            lbArtista.setVisible(false);
            lbGenero.setVisible(false);
        } else {
            lbNoCoincidencias.setVisible(false);
        }
    }

    @FXML
    private void seleccionoCancion(MouseEvent event) {
        Cancion cancion = listCancion.getSelectionModel().getSelectedItem();
        if (cancion != null) {
            //Poner imagen
            lbNombre.setText(cancion.getNombreCancion());
            lbInformacion.setText(cancion.getAlbum().getArtista().toString() + " - " + cancion.getAlbum().getNombreAlbum());
            lbExtras.setText("Año de lanzamiento: " + cancion.getAlbum().getAnoLanzamiento()
                    + "\nDe la disquera: " + cancion.getAlbum().getDisquera()
                    + "\nGenero: " + cancion.getAlbum().getGenero().toString());
            btnPlay.setVisible(true);
        }
    }

    @FXML
    private void seleccionoAlbum(MouseEvent event) {
        Album album = listAlbum.getSelectionModel().getSelectedItem();
        if(album != null){
            //Poner imagen
            lbNombre.setText(album.getNombreAlbum());
            lbInformacion.setText("Lanzado por: " + album.getArtista().toString());
            lbExtras.setText("Año de lanzamiento: " + album.getAnoLanzamiento()
                    + "\nDe la disquera: " + album.getDisquera()
                    + "\nGenero: " + album.getGenero().toString());
            btnPlay.setVisible(true);
            btnPlay.setText("Ir");
        }
    }

    @FXML
    private void seleccionoArtista(MouseEvent event) {
        Artista artista = listArtista.getSelectionModel().getSelectedItem();
        if(artista != null){
            lbNombre.setText(artista.toString());
            lbInformacion.setText("No hay más información que mostrar");
            lbExtras.setText("");
        }
        btnPlay.setVisible(false);
    }

    @FXML
    private void seleccionoGenero(MouseEvent event) {
        Genero gnro = listGenero.getSelectionModel().getSelectedItem();
        if(gnro != null){
            lbNombre.setText(gnro.toString());
            lbInformacion.setText("No hay más información que mostrar");
            lbExtras.setText("");
        }
        btnPlay.setVisible(false);
    }

    @FXML
    private void irReproducir(ActionEvent event) {
        
    }

}
