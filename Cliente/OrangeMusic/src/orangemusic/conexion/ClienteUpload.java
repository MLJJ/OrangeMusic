package orangemusic.conexion;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 13/06/2018
 * @time 01:53:04 AM
 */
public class ClienteUpload implements Runnable{

    private File canciones;
    private String archivos;
    
    public ClienteUpload(){
        
    }

    public ClienteUpload(File canciones, String archivos) {
        this.canciones = canciones;
        this.archivos = archivos;
    }
    
    @Override
    public void run() {
        BufferedInputStream bufferEntrada;
        BufferedOutputStream bufferSalida;
        int in;
        byte[] byteArray;

        try {
            Socket client = new Socket("127.0.0.1", 1234);
            bufferEntrada = new BufferedInputStream(new FileInputStream(canciones));
            bufferSalida = new BufferedOutputStream(client.getOutputStream());
          
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            dos.writeUTF(archivos);

            byteArray = new byte[8192];
            while ((in = bufferEntrada.read(byteArray)) != -1) {
                bufferSalida.write(byteArray, 0, in);
            }

            bufferEntrada.close();
            bufferSalida.close();

        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
}
