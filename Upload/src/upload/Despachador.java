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
import org.json.JSONArray;
import org.json.JSONObject;

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
                System.out.println(file);

                bufferEscritura = new BufferedOutputStream(new FileOutputStream(Constante.rutaCarpetasArchivosRecibidos + "cancion.zip"));
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
        JSONArray lista = new JSONArray(datos);
        int i = 0;
        if (!carpetaExtraer.exists()) {
            carpetaExtraer.mkdirs();
        }
        ZipInputStream zip = null;
        FileOutputStream entrada = null;
        if (carpetaExtraer.exists()) {
            String ruta = Constante.rutaCarpetasArchivosRecibidos + "archivo/";
            File carpeta = new File(ruta);
            if (!carpeta.exists()) {
                carpeta.mkdir();
            }

            try {
                zip = new ZipInputStream(new FileInputStream(Constante.rutaCarpetasArchivosRecibidos + "cancion.zip"), Charset.forName("Cp437"));

                ZipEntry salida = null;
                while (null != (salida = zip.getNextEntry())) {
                    JSONObject objeto = lista.getJSONObject(i);
                    entrada = new FileOutputStream(ruta + objeto.getInt("idCancion") + ".mp3");
                    int leer;
                    byte[] buffer = new byte[1024];
                    while (0 < (leer = zip.read(buffer))) {
                        entrada.write(buffer, 0, leer);
                    }
                    entrada.close();
                    zip.closeEntry();
                    i++;
                }
            } catch (FileNotFoundException e) {

            } catch (IOException e) {

            } finally {
                if (zip != null) {
                    try {
                        zip.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Despachador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            realizarFormatos(lista);
        } else {
            System.out.println("No se encontró el directorio..");
        }
    }

    private void realizarFormatos(JSONArray lista) {
        String file = "";
        String archivo = "";
        String path = "C:\\Users\\Leonardo\\Documents\\GitHub\\OrangeMusic\\OrangeMusic\\web\\canciones\\";
        String path2 = "C:\\Users\\Leonardo\\upload\\archivo\\";

        //los kbits por segundo, a mayor numero mejor es la calidad 
        //ya que no realiza mucha compresión del audio, 320 es la mayor para formato mp3
        String bitrate1 = "320";
        String bitrate2 = "128";
        String bitrate3 = "32";
        for (int i = 0; i < lista.length(); i++) {
            JSONObject objeto = lista.getJSONObject(i);
            file = objeto.getInt("idCancion")+".mp3";
            archivo = ""+objeto.getInt("idCancion");
            
            String cmd1 = "ffmpeg -i \"" + path2 + file + "\" -codec:a libmp3lame -b:a " + bitrate1 + "k \"" + path + archivo + "-1.mp3\"";
            String cmd2 = "ffmpeg -i \"" + path2 + file + "\" -codec:a libmp3lame -b:a " + bitrate2 + "k \"" + path + archivo + "-2.mp3\"";
            String cmd3 = "ffmpeg -i \"" + path2 + file + "\" -codec:a libmp3lame -b:a " + bitrate3 + "k \"" + path + archivo + "-3.mp3\"";

            Process p;
            try {
                p = Runtime.getRuntime().exec(cmd1);
                p = Runtime.getRuntime().exec(cmd2);
                p = Runtime.getRuntime().exec(cmd3);
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                System.out.println("listo");
            }
        }
    }
}
