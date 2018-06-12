package upload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 13/06/2018
 * @time 01:04:35 AM
 */
public class Despachador implements Runnable {

    private Socket cliente;

    public Despachador(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        File ruta = new File(Constante.rutaCarpetasArchivosRecibidos);
        String file = null;
        if (ruta.exists()) {

            BufferedInputStream bufferEntrada = null;
            BufferedOutputStream bufferEscritura = null;
            DataInputStream datosEntrada = null;
            try {
                int tamaño;
                byte[] receivedData = new byte[1024];
                bufferEntrada = new BufferedInputStream(cliente.getInputStream());
                datosEntrada = new DataInputStream(cliente.getInputStream());

                file = datosEntrada.readUTF();

                bufferEscritura = new BufferedOutputStream(new FileOutputStream(System.getProperty("user.home") + "/aredEspacio/cancion.zip"));
                while ((tamaño = bufferEntrada.read(receivedData)) != -1) {
                    bufferEscritura.write(receivedData, 0, tamaño);
                }
            } catch (IOException ex) {
                Logger.getLogger(Despachador.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    bufferEscritura.close();
                    datosEntrada.close();
                    bufferEntrada.close();
                } catch (IOException ex) {
                    Logger.getLogger(Despachador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        descomprmirArchivo(file);
    }

    private void descomprmirArchivo(String datos) {
        File carpetaExtraer = new File(Constante.rutaCarpetasArchivosRecibidos);

        if (carpetaExtraer.exists()) {

            try {
                ZipInputStream zis = new ZipInputStream(new FileInputStream(Constante.rutaCarpetasArchivosRecibidos+"cancion.zip"), Charset.forName("Cp437"));

                ZipEntry salida = null;
                while (null != (salida = zis.getNextEntry())) {
                    FileOutputStream fos = new FileOutputStream(Constante.rutaCarpetasArchivosRecibidos + salida.getName());
                    int leer;
                    byte[] buffer = new byte[1024];
                    while (0 < (leer = zis.read(buffer))) {
                        fos.write(buffer, 0, leer);
                    }
                    fos.close();
                    zis.closeEntry();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Directorio de salida: " + Constante.rutaCarpetasArchivosRecibidos);
        } else {
            System.out.println("No se encontró el directorio..");
        }
    }
}
