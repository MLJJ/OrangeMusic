package orangemusic.modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
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
 * @time 11:10:44 PM
 */
public class Album {
    private int idAlbum;
    private String nombreAlbum;
    private int añoLanzamiento;
    private String nombreImagen;
    private String disquera;
    private String canciones;
    private Genero genero;
    private Artista artista;

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getNombreAlbum() {
        return nombreAlbum;
    }

    public void setNombreAlbum(String nombreAlbum) {
        this.nombreAlbum = nombreAlbum;
    }

    public int getAñoLanzamiento() {
        return añoLanzamiento;
    }

    public void setAñoLanzamiento(int añoLanzamiento) {
        this.añoLanzamiento = añoLanzamiento;
    }

    public String getNombreImagen() {
        return nombreImagen;
    }

    public void setNombreImagen(String nombreImagen) {
        this.nombreImagen = nombreImagen;
    }

    public String getDisquera() {
        return disquera;
    }

    public void setDisquera(String disquera) {
        this.disquera = disquera;
    }

    public String getCanciones() {
        return canciones;
    }

    public void setCanciones(String canciones) {
        this.canciones = canciones;
    }
    
    public Album(){
        
    }
    
    public Album(JSONObject albumJSON){
        this.añoLanzamiento = albumJSON.getInt("anoLanzamiento");
        this.idAlbum = albumJSON.getInt("idAlbum");
        this.disquera = albumJSON.getString("disquera");
        this.nombreAlbum = albumJSON.getString("nombreAlbum");
        this.nombreImagen = albumJSON.getString("nombreImagen");
        this.genero = new Genero(albumJSON.getJSONObject("generoidGenero"));
        this.artista = new Artista(albumJSON.getJSONObject("artistaidArtista"));
        this.canciones = "[]";
    }
    
    public String crearAlbumJSON(){
        String albumJSON = "{";
        albumJSON += "\"idAlbum\":\""+this.idAlbum+"\",";
        albumJSON += "\"nombreAlbum\":\""+this.nombreAlbum+"\",";
        albumJSON += "\"anoLanzamiento\":\""+this.añoLanzamiento+"\",";
        albumJSON += "\"albumidAlbum\":\""+this.canciones+"\",";
        albumJSON += "\"disquera\":\""+this.disquera+"\",";
        albumJSON += "\"Artista_idArtista\":\""+this.artista.toString()+"\",";
        albumJSON += "\"Genero_idGenero\":\""+this.genero.toString()+"\",";
        albumJSON += "\"nombreImagen\":\""+this.nombreImagen+"\"";
        albumJSON += "}";
        
        return albumJSON;
    }
    
    @Override
    public String toString(){
        return this.nombreAlbum;
    }
    
    public String subirAlbum(Album album, boolean disponibilidad, File canciones, File imagen){
        String cancionesRegistradas = null;
        
        HttpURLConnection conexion = null;
        try {
            URL url = new URL(Constante.URLSERVICIOS+"persistencia.usuarios");
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
            cancionesRegistradas = bufferedReader.readLine();
        } catch (MalformedURLException e) {
            cancionesRegistradas= null;
        } catch (IOException e) {
            cancionesRegistradas = null;
        } catch (JSONException e) {
            cancionesRegistradas = null;
        }finally{
            if(conexion != null){
                conexion.disconnect();
            }
        }
        
        return cancionesRegistradas;
    }

        public List<Album> buscarAlbum(String nombreAlbum) {
        List<Album> albums = null;
        try {
            URL url = new URL("http://localhost:8080/OrangeMusic/webresources/modelo.album/buscarPorNombre/" + nombreAlbum);
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
            albums = new ArrayList<>();
            for (int i = 0; i < jsonArr.length(); i++) {
                Album album = new Album(jsonArr.getJSONObject(i));
                albums.add(album);
            }

            if (albums.isEmpty()) {
                albums = null;
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
        return albums;
    }

    public Artista getArtista() {
        return artista;
    }

    public Genero getGenero() {
        return genero;
    }

}
