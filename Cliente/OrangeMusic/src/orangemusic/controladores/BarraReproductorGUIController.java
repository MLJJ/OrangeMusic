/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orangemusic.controladores;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.net.URL;
import javafx.util.Duration;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;

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
    private JFXButton buttonPausa;
    @FXML
    private MediaView mediaViewReproduccion;
    @FXML
    private Label labelTiempoRep;

//Elementos para la reproduccion
    private Media media;
    private MediaPlayer mediaPlayer;
    private Duration duracion;
    private boolean stopRequested = false;
    private boolean terminado = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String uriArchivo = "C:/Users/Cris_/Music/musik/Dog_Days.mp3";
        media = new Media(new File(uriArchivo).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaViewReproduccion.setMediaPlayer(mediaPlayer);

        sliderReproducion.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (sliderReproducion.isValueChanging()) {
                    // multiply duration by percentage calculated by slider position
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
                System.out.println("onPaused");
                buttonReproducir.setStyle("-fx-background-image: url('/recursos/imagenes/playIcon.png')");
            }
        });

        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                stopRequested = true;
                terminado = true;
            }
        });
    }

    @FXML
    private void cambiarAnterior(ActionEvent event) {
    }

    @FXML
    private void cambiarsiguiente(ActionEvent event) {
    }

    @FXML
    private void reproducir(ActionEvent event) {
        Status status = mediaPlayer.getStatus();
        if (status == MediaPlayer.Status.UNKNOWN || status == MediaPlayer.Status.HALTED) {
            return;
        }
        if (status == Status.READY || status == Status.STOPPED || status == Status.PAUSED) {
            mediaPlayer.play();

            //En lugar de decirle que regrese al inicio le digo que cambie a otra cancion
            if (terminado) {
                mediaPlayer.seek(mediaPlayer.getStartTime());
                terminado = false;
            }
        } else {
            mediaPlayer.pause();
        }
    }

    @FXML
    private void PausarCancion(ActionEvent event) {
        Status status = mediaPlayer.getStatus();
        if (status == MediaPlayer.Status.UNKNOWN || status == MediaPlayer.Status.HALTED) {
            return;
        }
        if (status == Status.PLAYING) {
            mediaPlayer.pause();
        }
    }

    protected void actualizarValores() {
        if (labelTiempoRep != null && sliderReproducion != null) {
            Platform.runLater(new Runnable() {
                public void run() {
                    Duration currentTime = mediaPlayer.getCurrentTime();
                    labelTiempoRep.setText(darFormatoAlTiempo(currentTime, duracion));
                    sliderReproducion.setDisable(duracion.isUnknown());
                    if (!sliderReproducion.isDisabled()
                            && duracion.greaterThan(Duration.ZERO)
                            && !sliderReproducion.isValueChanging()) {
                        sliderReproducion.setValue(currentTime.divide(duracion).toMillis()
                                * 100.0);
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

}
