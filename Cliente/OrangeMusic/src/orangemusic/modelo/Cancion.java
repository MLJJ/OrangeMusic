package orangemusic.modelo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import orangemusic.utilerias.Constante;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 16/06/2018
 * @time 12:14:01 AM
 */
public class Cancion extends RecursiveTreeObject<Cancion> {

    private int idCancion;
    private String nombreCancion;
    private String rutaCancion;
    private Album album;
    private ImageView imagen;
    private String nombreArtista;

    public Cancion() {
        this.idCancion = 0;
        this.nombreCancion = "";
        this.rutaCancion = "";
    }

    public Cancion(JSONObject cancionJSON) {
        this.idCancion = cancionJSON.getInt("idCancion");
        this.nombreCancion = cancionJSON.getString("nombreCancion");
        this.rutaCancion = cancionJSON.getString("rutaCancion");
        this.album = new Album(cancionJSON.getJSONObject("albumidAlbum"));
        this.nombreArtista = this.album.getArtista().getNombreArtista();
    }

    public int getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(int idCancion) {
        this.idCancion = idCancion;
    }

    public String getNombreCancion() {
        return nombreCancion;
    }

    public void setNombreCancion(String nombreCancion) {
        this.nombreCancion = nombreCancion;
    }

    public String getRutaCancion() {
        return rutaCancion;
    }

    public void setRutaCancion(String rutaCancion) {
        this.rutaCancion = rutaCancion;
    }

    public ImageView getImagen() {
        return imagen;
    }

    public String crearCancionJSON() {
        String cancionJSON = "{";
        cancionJSON += "\"idCancion\":\"" + this.idCancion + "\",";
        cancionJSON += "\"rutaCancion\":\"" + this.rutaCancion + "\",";
        cancionJSON += "\"nombreCancion\":\"" + this.nombreCancion + "\"";
        cancionJSON += "}";

        return cancionJSON;
    }

    public void buscarImagenCancion() {
        String ruta = System.getProperty("servicio")+"imagenesCanciones/" + this.idCancion+".jpg";
        Image image = new Image(ruta, 50, 50, true, true);
        imagen = new ImageView(image);
    }

    public List<Cancion> sacarCancionesDeAlbum(File ruta) {
        List<Cancion> canciones = new ArrayList();

        ZipInputStream zip = null;
        if (ruta.exists()) {
            try {
                zip = new ZipInputStream(new FileInputStream(ruta), Charset.forName("Cp437"));

                ZipEntry salida = null;
                while (null != (salida = zip.getNextEntry())) {
                    Cancion cancion = new Cancion();
                    cancion.setNombreCancion(salida.getName());
                    canciones.add(cancion);

                    zip.closeEntry();
                }
            } catch (FileNotFoundException e) {

            } catch (IOException e) {

            } finally {
                if (zip != null) {
                    try {
                        zip.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Cancion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } else {
            System.out.println("No se encontró el directorio..");
        }

        return canciones;
    }

    public List<Cancion> buscarCancion(String nombreCancion) {
        List<Cancion> canciones = null;
        try {
            URL url = new URL(Constante.URLSERVICIOS+ "webresources/modelo.cancion/buscarPorNombre/" + nombreCancion);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();

            InputStream input;
            if (conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                input = conn.getInputStream();
            } else {
                input = conn.getErrorStream();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
            String cad = bufferedReader.readLine();
            JSONArray jsonArr = new JSONArray(cad);
            canciones = new ArrayList<>();
            for (int i = 0; i < jsonArr.length(); i++) {
                Cancion cancion = new Cancion(jsonArr.getJSONObject(i));
                canciones.add(cancion);
            }

            if (canciones.isEmpty()) {
                canciones = null;
            }
        } catch (MalformedURLException ex) {
            System.out.println("Error en URL");
        } catch (ProtocolException ex) {
            System.out.println("Error en RequesMethod: GET");
        } catch (IOException ex) {
            System.out.println("Error en HttpUrlConnection");
        } catch (Exception ex) {
            System.out.println("Error al crear el JSON");
        }
        return canciones;
    }

    @Override
    public String toString() {
        return this.nombreCancion;
    }

    public Album getAlbum() {
        return this.album;
    }
    
    public JSONArray darFormatoCanciones(List<Cancion> canciones){
        JSONArray lista = new JSONArray();
        
        for(Cancion cancion:canciones){
            lista.put(new JSONObject(cancion.crearCancionJSON()));
        }
        
        return lista;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
    
    
    
        
    public List<Cancion> crearListaDeEstacion(int idGenero,String correo){
        List<Cancion> listaEstacion = null;
        URL url = null;
        try {
            url = new URL(System.getProperty("servicio")+"/webresources/modelo.genero/estacionDeRadio/"+idGenero+"/"+correo);
            HttpURLConnection conexion = (HttpURLConnection)url.openConnection();
            conexion.setRequestProperty("Content-Type", "application/json");
            conexion.setRequestProperty("Accept", "application/json");
            conexion.connect();
            InputStream input;
            if (conexion.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                input = conexion.getInputStream();
            } else {
                input = conexion.getErrorStream();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
            String cad = bufferedReader.readLine();
            JSONArray jsonArr = new JSONArray(cad);
            listaEstacion = new ArrayList<>();
            for (int i = 0; i < jsonArr.length(); i++) {
                Cancion cancion = new Cancion(jsonArr.getJSONObject(i));
                listaEstacion.add(cancion);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Cancion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Cancion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaEstacion;
    }
    
}
