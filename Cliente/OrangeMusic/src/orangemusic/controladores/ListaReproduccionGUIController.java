/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orangemusic.controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import orangemusic.modelo.Cancion;
import orangemusic.modelo.ListaReproduccion;

/**
 * FXML Controller class
 *
 * @author Cristhian Ubaldo Promotor
 */
public class ListaReproduccionGUIController implements Initializable {

    @FXML
    private JFXTextField textFieldNombreLista;
    @FXML
    private JFXButton buttonCrearListaGuardar;
    @FXML
    private JFXButton buttonEditarListaGuardar;
    @FXML
    private JFXButton buttonEliminarCancion;
    @FXML
    private JFXButton buttonAgregarCancion;
    @FXML
    private JFXButton buttonDescargar;
    @FXML
    private JFXButton buttonCancelar;
    @FXML
    private TableView<Cancion> tableCanciones;
    private MenuPrincipalGUIController menuPrincipal;
    private ListaReproduccion listaReproduccion;
    @FXML
    private ComboBox<String> comboBoxPrivacidad;
    @FXML
    private JFXButton buttonEliminarLista;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableColumn<Cancion, String> nombreCancion = new TableColumn("Nombre Canción");
        nombreCancion.setMinWidth(700);
        nombreCancion.setCellValueFactory(new PropertyValueFactory<>("nombreCancion"));

        TableColumn<Cancion, String> artista = new TableColumn("Artista");
        artista.setMinWidth(320);
        artista.setCellValueFactory(new PropertyValueFactory<>("nombreArtista"));

        tableCanciones.getColumns().addAll(artista, nombreCancion);

        comboBoxPrivacidad.getItems().clear();
        comboBoxPrivacidad.getItems().addAll("Publica", "Privada");
        comboBoxPrivacidad.getSelectionModel().select(0);
    }

    @FXML
    private void crearListaReproduccion(ActionEvent event) {
        if (buttonCrearListaGuardar.getText().equalsIgnoreCase("Crear lista")) {
            // if (!listaReproduccion.getNombreLista().equalsIgnoreCase("subidas") || !listaReproduccion.getNombreLista().equalsIgnoreCase("descargadas")) {
            cancelarOperacion(null);
            buttonCrearListaGuardar.setText("Guardar");
            textFieldNombreLista.setEditable(true);
            textFieldNombreLista.clear();
            comboBoxPrivacidad.setDisable(false);
            //}
        } else {
            if (!textFieldNombreLista.getText().trim().isEmpty()) {
                if (!textFieldNombreLista.getText().trim().equalsIgnoreCase("subidas")
                        || !textFieldNombreLista.getText().trim().equalsIgnoreCase("descargadas")
                        || !textFieldNombreLista.getText().trim().equalsIgnoreCase("historial")) {

                    ListaReproduccion nuevaLista = new ListaReproduccion();
                    nuevaLista.setNombreLista(textFieldNombreLista.getText());
                    nuevaLista.setVisibilidad(comboBoxPrivacidad.getSelectionModel().getSelectedItem());
                    boolean confimacion;
                    confimacion = nuevaLista.crearListaReproduccion(menuPrincipal.getUsuario().getCorreo());
                    if (confimacion) {
                        MensajeController.mensajeInformacion("La lista se ha creado con éxito");
                    } else {
                        MensajeController.mensajeAdvertencia("Ha ocurrido un error en la creación de la lista");
                    }
                } else {
                    MensajeController.mensajeAdvertencia("No se pueden llamar como las listas del sistema");
                }

            } else {
                MensajeController.mensajeAdvertencia("No deje campos vacíos");
            }
            cancelarOperacion(null);
            menuPrincipal.setMisPlayList(null);
            //menuPrincipal.initialize(null, null);
        }
    }

    @FXML
    private void editarListaReproduccion(ActionEvent event) {
        if (buttonEditarListaGuardar.getText().equalsIgnoreCase("Editar lista")) {
            if (!listaReproduccion.getNombreLista().equalsIgnoreCase("subidas") 
                    || !listaReproduccion.getNombreLista().equalsIgnoreCase("descargadas")) {
                cancelarOperacion(null);
                buttonEditarListaGuardar.setText("Guardar");
                textFieldNombreLista.setEditable(true);
                textFieldNombreLista.clear();
                comboBoxPrivacidad.setDisable(false);
                buttonCancelar.setVisible(true);
            }
        } else {
            if (!textFieldNombreLista.getText().trim().isEmpty()) {
                if (!textFieldNombreLista.getText().trim().equalsIgnoreCase("subidas")
                        || !textFieldNombreLista.getText().trim().equalsIgnoreCase("descargadas")
                        || !textFieldNombreLista.getText().trim().equalsIgnoreCase("historial")) {

                    ListaReproduccion nuevaLista = new ListaReproduccion();
                    nuevaLista.setIdListaReproduccion(listaReproduccion.getIdListaReproduccion());
                    nuevaLista.setNombreLista(textFieldNombreLista.getText());
                    nuevaLista.setVisibilidad(comboBoxPrivacidad.getSelectionModel().getSelectedItem());
                    boolean confimacion;
                    confimacion = nuevaLista.editarListaReproduccion(menuPrincipal.getUsuario().getCorreo());
                    if (confimacion) {
                        MensajeController.mensajeInformacion("La lista se ha creado con éxito");
                    } else {
                        MensajeController.mensajeAdvertencia("Ha ocurrido un error en la creación de la lista");
                    }
                } else {
                    MensajeController.mensajeAdvertencia("No se pueden llamar como las listas del sistema");
                }

            } else {
                MensajeController.mensajeAdvertencia("No deje campos vacíos");
            }
            cancelarOperacion(null);
            menuPrincipal.setMisPlayList(null);
            //menuPrincipal.initialize(null, null);
        }
    }

    @FXML
    private void eliminarListaReproduccion(ActionEvent event) {
    }

    @FXML
    private void agregarCancion(ActionEvent event) {
    }

    @FXML
    private void descargar(ActionEvent event) {
    }

    @FXML
    private void eliminarCancion(ActionEvent event) {

    }

    /**
     * Cambia el estado de los botones y campos
     *
     * @param event
     */
    @FXML
    private void cancelarOperacion(ActionEvent event) {
        buttonCrearListaGuardar.setText("Crear lista");
        buttonEditarListaGuardar.setText("Editar lista");
        textFieldNombreLista.setEditable(false);
        textFieldNombreLista.setText(listaReproduccion.getNombreLista());
        comboBoxPrivacidad.setDisable(true);
        buttonCancelar.setVisible(false);
    }

    @FXML
    private void reproducirCancion(MouseEvent event) {
        if (tableCanciones.getSelectionModel().getSelectedItem() != null) {
            menuPrincipal.getControladorBarra().cargarCancion(listaReproduccion, tableCanciones.getSelectionModel().getSelectedItem());
        }
    }

    public void setMenuPrincipal(MenuPrincipalGUIController menuPrincipal) {
        this.menuPrincipal = menuPrincipal;
    }

    public void setListaReproduccion(ListaReproduccion listaReproduccion) {
        this.listaReproduccion = listaReproduccion;
    }

    public void cargarLista() {
        if (this.listaReproduccion != null) {
            textFieldNombreLista.setText(listaReproduccion.getNombreLista());
            comboBoxPrivacidad.getSelectionModel().select(listaReproduccion.getVisibilidad());
            tableCanciones.getItems().clear();
            tableCanciones.setItems(FXCollections.observableList(listaReproduccion.getCanciones()));
        }
    }

    @FXML
    private void limitarNombreLista(KeyEvent event) {
        if (textFieldNombreLista.getText().length() >= 80) {
            event.consume();
        }
    }
}
