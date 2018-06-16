package orangemusic.modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import orangemusic.utilerias.Constante;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 13/06/2018
 * @time 11:11:08 PM
 */
public class Artista {
    private int idArtista;
    private String nombreArtista;

    public int getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(int idArtista) {
        this.idArtista = idArtista;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }
    
    public Artista(){
        
    }
    
    public Artista(JSONObject artistaJSON){
        this.idArtista = artistaJSON.getInt("idArtista");
        this.nombreArtista = artistaJSON.getString("nombre");
    }
    
    public String crearArtistaJSON(){
        String artistaJSON = "{";
        artistaJSON += "\"idArtista\":\""+this.idArtista+"\",";
        artistaJSON += "\"nombre\":\""+this.nombreArtista+"\"";
        artistaJSON += "}";
        return artistaJSON;
    }
    
    @Override
    public String toString(){
        return this.nombreArtista;
    }
    
    public String guardarArtista(Artista artista){
        String validacion = "";
        HttpURLConnection conexion = null;
        
        try {
            URL url = new URL(Constante.URLSERVICIOS+"modelo.artista");
            conexion = (HttpURLConnection)url.openConnection();
            conexion.setRequestProperty("Content-Type","application/json");
            conexion.setRequestProperty("Accept","application/json");
            conexion.setDoInput(true);
            conexion.setDoOutput(true);
            conexion.setRequestMethod("POST");
            conexion.connect();

            JSONObject usuarioJSON = new JSONObject(artista.crearArtistaJSON());

            OutputStream outputStream = conexion.getOutputStream();
            BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(outputStream));
            escritor.write(String.valueOf(usuarioJSON));
            escritor.flush();

            InputStream input;
            if (conexion.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                input = conexion.getInputStream();
            } else {
                input = conexion.getErrorStream();
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
            String cadena = bufferedReader.readLine();
            JSONObject respuesta = new JSONObject(cadena);
            validacion = respuesta.getString("respuesta");
        } catch (MalformedURLException e) {
            validacion= "Error de conexion con el servidor";
        } catch (IOException e) {
            validacion = "Error de conexion con el servidor";
        } catch (JSONException e) {
            validacion = "Error de conexion con el servidor";
        }finally{
            if(conexion != null){
                conexion.disconnect();
            }
        }

        
        return validacion;
    }
    
    public List<Artista> sacarArtista(){
        List<Artista> artistas = new ArrayList();
        
        HttpURLConnection conexion = null;

        try {
            URL url = new URL(Constante.URLSERVICIOS + "modelo.genero");
            conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestProperty("Content-Type", "application/json");
            conexion.setRequestProperty("Accept", "application/json");
            conexion.connect();

            InputStream entrada;

            if (conexion.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                entrada = conexion.getInputStream();
            } else {
                entrada = conexion.getErrorStream();
            }

            BufferedReader lector = new BufferedReader(new InputStreamReader(entrada));
            String cadena = lector.readLine();


            try {
                JSONArray lista = new JSONArray(cadena);
                for(int i = 0; i < lista.length(); i++){
                    Artista artista = new Artista(lista.getJSONObject(i));
                    artistas.add(artista);
                }
            } catch (JSONException e) {
                
            }


        } catch (Exception e) {
                
        } finally {
            if (conexion != null) {
                conexion.disconnect();
            }
        }
        
        return artistas;
    }
}
