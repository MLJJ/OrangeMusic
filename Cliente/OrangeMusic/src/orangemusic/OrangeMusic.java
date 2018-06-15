/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orangemusic;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import orangemusic.controladores.InicioSesionGUIController;
import orangemusic.controladores.MenuPrincipalGUIController;
import orangemusic.controladores.RegistrarUsuarioGUIController;
import orangemusic.modelo.Usuario;

/**
 *
 * @author Cristhian Ubaldo Promotor
 */
public class OrangeMusic extends Application {

    private Stage ventana;

    public Stage getVentana() {
        return ventana;
    }

    public void setVentana(Stage ventana) {
        this.ventana = ventana;
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader cargador = new FXMLLoader(getClass().getResource("/orangemusic/vistas/InicioSesionGUI.fxml"));
        Parent root = (Parent) cargador.load();
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("/recursos/imagenes/logo.png"));

        InicioSesionGUIController controlador = cargador.getController();
        controlador.setMain(this);

        stage.setScene(scene);
        stage.show();
        this.ventana = stage;
    }

    public void desplegarRegistrarSesion() throws IOException {
        FXMLLoader cargador = new FXMLLoader(getClass().getResource("/orangemusic/vistas/RegistrarUsuarioGUI.fxml"));
        Parent root = (Parent) cargador.load();
        Scene scene = new Scene(root);

        RegistrarUsuarioGUIController controlador = cargador.getController();
        controlador.setMain(this);
        ventana.setScene(scene);
    }

    public void desplegarMenuPrincipal(Usuario usr) throws IOException {
        FXMLLoader cargador = new FXMLLoader(getClass().getResource("/orangemusic/vistas/MenuPrincipalGUI.fxml"));
        Parent root = (Parent) cargador.load();
        Scene scene = new Scene(root);

        MenuPrincipalGUIController controlador = cargador.getController();
        controlador.setMain(this);
        controlador.setUsuario(usr);
        ventana.setScene(scene);
    }

    public void desplegarInicioSesion() throws IOException {
        FXMLLoader cargador = new FXMLLoader(getClass().getResource("/orangemusic/vistas/InicioSesionGUI.fxml"));
        Parent root = (Parent) cargador.load();
        Scene scene = new Scene(root);

        InicioSesionGUIController controlador = cargador.getController();
        controlador.setMain(this);
        ventana.setScene(scene);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
