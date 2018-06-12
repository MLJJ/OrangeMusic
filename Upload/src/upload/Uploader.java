package upload;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class Uploader {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ServerSocket server = null;

        try {
            server = new ServerSocket(1234);
            while (true) {
                Socket cliente = server.accept();

                Despachador despachador = new Despachador(cliente);
                
                new Thread(despachador).start();
            }
        } catch (Exception e) {
            System.err.println(e);
        }finally{
            if(server != null){
                try {
                    server.close();
                } catch (IOException ex) {
                    Logger.getLogger(Uploader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        
    }
}
