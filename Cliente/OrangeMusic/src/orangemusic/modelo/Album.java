package orangemusic.modelo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private int anoLanzamiento;
    private String nombreImagen;
    private String disquera;
    private String canciones;
    private Genero genero;
    private Artista artista;

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

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

    public int getAnoLanzamiento() {
        return anoLanzamiento;
    }

    public void setAnoLanzamiento(int anoLanzamiento) {
        this.anoLanzamiento = anoLanzamiento;
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

    public Album() {

    }

    public Album(JSONObject albumJSON) {
        this.anoLanzamiento = albumJSON.getInt("anoLanzamiento");
        this.idAlbum = albumJSON.getInt("idAlbum");
        this.disquera = albumJSON.getString("disquera");
        this.nombreAlbum = albumJSON.getString("nombreAlbum");
        this.nombreImagen = albumJSON.getString("nombreImagen");
        this.genero = new Genero(albumJSON.getJSONObject("generoidGenero"));
        this.artista = new Artista(albumJSON.getJSONObject("artistaidArtista"));
        this.canciones = "[]";
    }

    public String crearAlbumJSON() {
        String albumJSON = "{";
        albumJSON += "\"idAlbum\":\"" + this.idAlbum + "\",";
        albumJSON += "\"nombreAlbum\":\"" + this.nombreAlbum + "\",";
        albumJSON += "\"anoLanzamiento\":\"" + this.anoLanzamiento + "\",";
        albumJSON += "\"disquera\":\"" + this.disquera + "\",";
        albumJSON += "\"Artista_idArtista\":\"" + this.artista.crearArtistaJSON() + "\",";
        albumJSON += "\"Genero_idGenero\":\"" + this.genero.generoJSON() + "\",";
        albumJSON += "\"nombreImagen\":\"" + this.nombreImagen + "\"";
        albumJSON += "}";

        return albumJSON;
    }

    @Override
    public String toString() {
        return this.nombreAlbum;
    }

    public String subirAlbum(Album album, boolean disponibilidad, File canciones, File imagen) {
        String cancionesRegistradas = null;
        Cancion cancion = new Cancion();
        List<Cancion> listaCanciones = null;
        listaCanciones = cancion.sacarCancionesDeAlbum(canciones);

        JSONArray lista = cancion.darFormatoCanciones(listaCanciones);
        album.setNombreImagen(String.valueOf(lista));

        short numero = 0;
        if (disponibilidad) {
            numero = 1;
        }

        HttpURLConnection conexion = null;
        try {
            URL url = new URL(System.getProperty("servicio") + "webresources/modelo.album/" + numero + "/" + System.getProperty("correo") + "/" + album.getArtista().getIdArtista() + "/" + album.getGenero().getIdGenero());
            conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestProperty("Content-Type", "application/json");
            conexion.setRequestProperty("Accept", "application/json");
            conexion.setDoInput(true);
            conexion.setDoOutput(true);
            conexion.setRequestMethod("POST");
            conexion.connect();

            JSONObject albumJSON = new JSONObject(album);

            OutputStream outputStream = conexion.getOutputStream();
            BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(outputStream));
            escritor.write(String.valueOf(albumJSON));
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
            cancionesRegistradas = null;
        } catch (IOException e) {
            cancionesRegistradas = null;
        } catch (JSONException e) {
            cancionesRegistradas = null;
        } finally {
            if (conexion != null) {
                conexion.disconnect();
            }
        }

        try {
            JSONArray datos = new JSONArray(cancionesRegistradas);
            JSONObject objeto = datos.getJSONObject(0);
            JSONObject dato = objeto.getJSONObject("albumidAlbum");
            int id = dato.getInt("idAlbum");
            subirImagenAlbum(id, imagen);
        } catch (Exception e) {

        }
        
        return cancionesRegistradas;

    }

    public List<Album> buscarAlbum(String nombreAlbum) {
        List<Album> albums = null;
        try {
            URL url = new URL(System.getProperty("servicio") + "webresources/modelo.album/buscarPorNombre/" + nombreAlbum);
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

    public boolean descargarCancion(int idCancion, String nombreCancion) {
        boolean validacion = true;
        File rutaDescarga = new File(Constante.RUTADESCARGA);
        if (!rutaDescarga.exists()) {
            rutaDescarga.mkdirs();
        }
        BufferedInputStream bufferEntrada = null;
        BufferedOutputStream bufferEscritura = null;
        try {
            URL url = new URL(System.getProperty("servicio") + "/canciones/" + idCancion + "-1.mp3");
            byte[] receivedData = new byte[1024];
            bufferEntrada = new BufferedInputStream(url.openConnection().getInputStream());
            int tamaño;
            bufferEscritura = new BufferedOutputStream(new FileOutputStream(Constante.RUTADESCARGA + nombreCancion));
            while ((tamaño = bufferEntrada.read(receivedData)) != -1) {
                bufferEscritura.write(receivedData, 0, tamaño);
            }
        } catch (MalformedURLException e) {
            validacion = false;
        } catch (IOException e) {
            validacion = false;
        } finally {
            if (bufferEntrada != null) {
                try {
                    bufferEntrada.close();
                } catch (IOException ex) {
                    Logger.getLogger(Cancion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (bufferEscritura != null) {
                try {
                    bufferEscritura.close();
                } catch (IOException ex) {
                    Logger.getLogger(Cancion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return validacion;
    }

    public boolean descargarListaReproduccion(List<Cancion> canciones) {
        boolean validacion = true;

        File rutaDescarga = new File(Constante.RUTADESCARGA);
        if (!rutaDescarga.exists()) {
            rutaDescarga.mkdirs();
        }
        BufferedInputStream bufferEntrada = null;
        BufferedOutputStream bufferEscritura = null;
        try {
            for (Cancion cancion : canciones) {
                URL url = new URL(System.getProperty("servicio") + "canciones/" + cancion.getIdCancion() + "-1" + ".mp3");
                byte[] receivedData = new byte[1024];
                bufferEntrada = new BufferedInputStream(url.openConnection().getInputStream());
                int tamaño;
                bufferEscritura = new BufferedOutputStream(new FileOutputStream(Constante.RUTADESCARGA + cancion.getNombreCancion()));
                while ((tamaño = bufferEntrada.read(receivedData)) != -1) {
                    bufferEscritura.write(receivedData, 0, tamaño);
                }
                bufferEntrada.close();
                bufferEscritura.close();
            }
        } catch (MalformedURLException e) {
            validacion = false;
        } catch (IOException e) {
            validacion = false;
        } finally {
            if (bufferEntrada != null) {
                try {
                    bufferEntrada.close();
                } catch (IOException ex) {
                    Logger.getLogger(Cancion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (bufferEscritura != null) {
                try {
                    bufferEscritura.close();
                } catch (IOException ex) {
                    Logger.getLogger(Cancion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return validacion;
    }

    private String convertirImagen64(File file) {

        String base64Image = "";
        try (FileInputStream imageInFile = new FileInputStream(file)) {
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            base64Image = Base64.getEncoder().encodeToString(imageData);
            imageInFile.close();
        } catch (FileNotFoundException e) {

        } catch (IOException ioe) {

        }
        return base64Image;

    }

    public boolean subirImagenAlbum(int album, File imagen) {
        boolean validacion = true;
        HttpURLConnection conexion = null;
        String base64 = this.convertirImagen64(imagen);
        try {
            URL url = new URL(System.getProperty("servicio") + "webresources/modelo.album/imagenesalbum");
            conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestProperty("Content-Type", "application/json");
            conexion.setRequestProperty("Accept", "application/json");
            conexion.setDoInput(true);
            conexion.setDoOutput(true);
            conexion.setRequestMethod("POST");
            conexion.connect();

            String albumJSON = "\"idAlbum\":\"" + album + "\",";
            albumJSON += "\"nombreImagen\":\"" + base64 + "\"";
            albumJSON += "}";

            OutputStream outputStream = conexion.getOutputStream();
            BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(outputStream));
            escritor.write(String.valueOf(albumJSON));
            escritor.flush();

            InputStream input;
            if (conexion.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                input = conexion.getInputStream();
            } else {
                input = conexion.getErrorStream();
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
            String resultado = bufferedReader.readLine();
        } catch (MalformedURLException e) {
            validacion = false;
        } catch (IOException e) {
            validacion = false;
        } catch (JSONException e) {
            validacion = false;
        } finally {
            if (conexion != null) {
                conexion.disconnect();
            }
        }
        return validacion;
    }
}
