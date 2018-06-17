/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orangemusic.controladores;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import javafx.util.Duration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import orangemusic.OrangeMusic;
import orangemusic.modelo.Cancion;
import orangemusic.modelo.ListaReproduccion;

/**
 * FXML Controller class
 *
 * @author Cristhian Ubaldo Promotor
 */
public class BarraReproductorGUIController implements Initializable {

    @FXML
    private JFXButton buttonReproducir;
    @FXML
    private JFXButton buttonAnterior;
    @FXML
    private JFXButton buttonSiguiente;
    @FXML
    private Slider sliderReproducion;
    @FXML
    private Label labelNombreCancion;
    @FXML
    private Label labelTiempoRep;

//Elementos para la reproduccion
    private Media media;
    private MediaPlayer mediaPlayer;
    private Duration duracion;
    private boolean stopRequested = false;
    private boolean terminado = false;
    private ListaReproduccion listaReproduccion;
    private Cancion cancionInicio;
    private MenuPrincipalGUIController menuPrincipal;
    @FXML
    private ImageView imageViewImagenAlbum;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Solo de prueba
        /*
            listaReproduccion = new ListaReproduccion();
            Cancion cancion1 = new Cancion();
            cancion1.setRutaCancion("C:/Users/Cris_/Music/musik/Dog_Days.mp3");
            //cancion1.setRutaCancion("http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv");
            cancion1.setIdCancion(1);
            Cancion cancion2 = new Cancion();
            cancion2.setRutaCancion("C:/Users/Cris_/Music/musik/this_is_america.mp3");
            //cancion2.setRutaCancion("https://www.rmp-streaming.com/media/bbb-360p.mp4");
            cancion2.setIdCancion(2);
            cancionInicio = cancion1;
            listaReproduccion.getCanciones().add(cancion1);
            listaReproduccion.getCanciones().add(cancion2);
         */
        // fin datos

        if (listaReproduccion != null) {
            System.getProperties().put("ip", "");
            System.getProperty("ip");
            String rutaInicio = listaReproduccion.getCanciones().get(listaReproduccion.getCanciones().indexOf(cancionInicio)).getRutaCancion();
            //media = new Media(new File(rutaInicio).toURI().toString()); //provar con rutas de pc
            media = new Media(rutaInicio);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            enlazarElemetos();
        }

    }

    public ListaReproduccion getListaReproduccion() {
        return listaReproduccion;
    }

    public void setListaReproduccion(ListaReproduccion listaReproduccion) {
        this.listaReproduccion = listaReproduccion;
    }

    public void setControladorMenu(MenuPrincipalGUIController menuPrincipal) {
        this.menuPrincipal = menuPrincipal;

    }

    public void enlazarElemetos() {
        sliderReproducion.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (sliderReproducion.isValueChanging()) {
                    mediaPlayer.seek(duracion.multiply(sliderReproducion.getValue() / 100.0));
                }
            }
        });

        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                actualizarValores();
            }
        });

        mediaPlayer.setOnReady(new Runnable() {
            public void run() {
                duracion = mediaPlayer.getMedia().getDuration();
                actualizarValores();
            }
        });

        mediaPlayer.setOnPlaying(new Runnable() {
            public void run() {
                if (stopRequested) {
                    mediaPlayer.pause();
                    stopRequested = false;
                } else {

                    buttonReproducir.setStyle("-fx-background-image: url('/recursos/imagenes/pausaIcon.png')");
                }
            }
        });

        mediaPlayer.setOnPaused(new Runnable() {
            public void run() {
                buttonReproducir.setStyle("-fx-background-image: url('/recursos/imagenes/playIcon.png')");
            }
        });

        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                cambiarSiguiente(null);
            }
        });

        labelNombreCancion.setText(cancionInicio.getNombreCancion());
    }

    public void cargarCancion(ListaReproduccion listaReproduccion, Cancion cancionInicio) {
        this.listaReproduccion = listaReproduccion;
        this.cancionInicio = cancionInicio;

    }

    @FXML
    private void cambiarAnterior(ActionEvent event) {
        mediaPlayer.dispose();
        String rutaCancion;
        if ((listaReproduccion.getCanciones().indexOf(cancionInicio) - 1) > 0) {
            rutaCancion = listaReproduccion.getCanciones().get(listaReproduccion.getCanciones().indexOf(cancionInicio) - 1).getRutaCancion() + cancionInicio.getIdCancion() + "-1.mp3";
            cancionInicio = listaReproduccion.getCanciones().get(listaReproduccion.getCanciones().indexOf(cancionInicio) - 1);
        } else {
            rutaCancion = listaReproduccion.getCanciones().get(0).getRutaCancion();
            cancionInicio = listaReproduccion.getCanciones().get(0);
        }
        //media = new Media(new File(rutaCancion).toURI().toString()); //solo si se quiere probar con rutas de la pc 
        media = new Media(rutaCancion);
        mediaPlayer = new MediaPlayer(media);
        enlazarElemetos();
        mediaPlayer.play();

    }

    @FXML
    private void cambiarSiguiente(ActionEvent event) {
        mediaPlayer.dispose();
        String rutaCancion;
        if ((listaReproduccion.getCanciones().indexOf(cancionInicio) + 1) < listaReproduccion.getCanciones().size()) {
            rutaCancion = listaReproduccion.getCanciones().get(listaReproduccion.getCanciones().indexOf(cancionInicio) + 1).getRutaCancion();
            cancionInicio = listaReproduccion.getCanciones().get(listaReproduccion.getCanciones().indexOf(cancionInicio) + 1);
        } else {
            rutaCancion = listaReproduccion.getCanciones().get(0).getRutaCancion();
            cancionInicio = listaReproduccion.getCanciones().get(0);
        }
        //media = new Media(new File(rutaCancion).toURI().toString()); //solo si se quiere probar con rutas de la pc 
        media = new Media(rutaCancion);
        mediaPlayer = new MediaPlayer(media);
        enlazarElemetos();
        mediaPlayer.play();
    }

    @FXML
    private void reproducir(ActionEvent event) {
        Status status = mediaPlayer.getStatus();
        if (status == MediaPlayer.Status.UNKNOWN || status == MediaPlayer.Status.HALTED) {
            return;
        }
        if (status == MediaPlayer.Status.UNKNOWN || status == Status.READY || status == Status.STOPPED || status == Status.PAUSED) {
            mediaPlayer.play();

        } else {
            mediaPlayer.pause();
        }
    }

    protected void actualizarValores() {
        if (labelTiempoRep != null && sliderReproducion != null) {
            Platform.runLater(new Runnable() {
                public void run() {
                    Duration tiempoActual = mediaPlayer.getCurrentTime();
                    labelTiempoRep.setText(darFormatoAlTiempo(tiempoActual, duracion));
                    sliderReproducion.setDisable(duracion.isUnknown());
                    if (!sliderReproducion.isDisabled() && duracion.greaterThan(Duration.ZERO)
                            && !sliderReproducion.isValueChanging()) {
                        sliderReproducion.setValue(tiempoActual.divide(duracion).toMillis() * 100.0);
                    }

                }
            });
        }
    }

    private static String darFormatoAlTiempo(Duration elapsed, Duration duration) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
                - elapsedMinutes * 60;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60
                    - durationMinutes * 60;
            if (durationHours > 0) {
                return String.format("%d:%02d:%02d/%d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds,
                        durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d",
                        elapsedMinutes, elapsedSeconds, durationMinutes,
                        durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d", elapsedHours,
                        elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d", elapsedMinutes,
                        elapsedSeconds);
            }
        }
    }

    @FXML
    private void mostrarDetallesCancion(MouseEvent event) {
        try {
            this.menuPrincipal.lanzarMusicaDetalles();
            this.menuPrincipal.getControladorReproductor().cargarIformacionCancion(this.cancionInicio);
        } catch (IOException ex) {
            Logger.getLogger(BarraReproductorGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cambiarCalidadCancion(String calidad) {
        //la calidad seria el -1 alta, -2 media y -3 baja para automática se calcularia o se dejaría la más alta por defecto
        String rutaCancion = cancionInicio.getRutaCancion() + cancionInicio.getIdCancion() + calidad + ".mp3";
        media = new Media(rutaCancion);
        mediaPlayer = new MediaPlayer(media);
        enlazarElemetos();
        mediaPlayer.play();
    }

}
