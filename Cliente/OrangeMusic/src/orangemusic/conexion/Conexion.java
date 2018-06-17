package orangemusic.conexion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import orangemusic.controladores.IpConfiguracionGUIController;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 17/06/2018
 * @time 11:36:10 AM
 */
public class Conexion {
    
    public void guardarArchivo(String ip) {
        FileWriter abridor = null;
        BufferedWriter bufferEscritor = null;
        PrintWriter escritor = null;
        try {
            File archivo = new File(System.getProperty("user.home") + "/orangeMusic/ip.txt");
            abridor = new FileWriter(archivo);
            bufferEscritor = new BufferedWriter(abridor);
            escritor = new PrintWriter(bufferEscritor,true);
            escritor.println(ip);
        } catch (IOException ex) {
            System.out.println("Error al guardarlo");
        } finally {
            try {
                if (abridor != null) {
                    abridor.close();
                }
            } catch (IOException ex) {

            }
            if (bufferEscritor != null) {
                try {
                    bufferEscritor.close();
                } catch (IOException ex) {
                    Logger.getLogger(IpConfiguracionGUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (escritor != null) {
                escritor.close();
            }
        }
    }
    
    public boolean validarConfiguracion(){
        boolean validacion = false;
        
        String ip = sacarIP();
        
        if(!ip.isEmpty()){
            validacion = this.provarConexion(ip);
        }
        
        return validacion;
    }

    private String sacarIP() {
        String ip = "";

        File archivo = new File(System.getProperty("user.home") + "/orangeMusic/ip.txt");

        if (archivo.isFile()) {
            FileReader abrirArchivo = null;
            BufferedReader bufferLector = null;
            try {
                abrirArchivo = new FileReader(archivo);
                bufferLector = new BufferedReader(abrirArchivo);
                String mensaje = bufferLector.readLine();
                if(mensaje != null){
                    ip = mensaje;
                }
                bufferLector.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(IpConfiguracionGUIController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(IpConfiguracionGUIController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if(abrirArchivo != null){
                       abrirArchivo.close(); 
                    }
                } catch (IOException ex) {
                    Logger.getLogger(IpConfiguracionGUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(bufferLector != null){
                    try {
                        bufferLector.close();
                    } catch (IOException ex) {
                        Logger.getLogger(IpConfiguracionGUIController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }else{
            System.out.println("No se encontro el archivo");
        }
        return ip;
    }

    public boolean provarConexion(String ip) {
        boolean validacion = true;
        Socket socket = null;
        try {
            socket = new Socket(ip, 8080);
        } catch (IOException ex) {
            validacion = false;
        } finally {
            try {
                if(socket != null)
                    socket.close();
            } catch (IOException ex) {

            }
        }

        return validacion;
    }
}
