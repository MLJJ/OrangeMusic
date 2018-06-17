package orangemusic.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import orangemusic.utilerias.Constante;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 15/06/2018
 * @time 11:51:29 PM
 */
public class Genero {
    private String nombreGenero;
    private int idGenero;
    
    public Genero(){
        
    }
    
    public Genero(JSONObject generoJSON){
        this.nombreGenero = generoJSON.getString("nombreGenero");
        this.idGenero = generoJSON.getInt("idGenero");
    }
    
    public String generoJSON(){
        String generoJSON = "{";
        generoJSON += "\"idGenero\":\""+this.idGenero+"\",";
        generoJSON += "\"nombreGenero\":\""+this.nombreGenero+"\"";
        generoJSON += "}";
        
        return generoJSON;
    }
    
    @Override
    public String toString(){
        return this.nombreGenero;
    }
    
    public List<Genero> sacarGeneros(){
        List<Genero> generos = new ArrayList();
        
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
                    Genero genero = new Genero(lista.getJSONObject(i));
                    generos.add(genero);
                }
            } catch (JSONException e) {
                
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conexion != null) {
                conexion.disconnect();
            }
        }
        
        return generos;
    }

    public List<Genero> buscarGenero(String nombreGenero) {
        List<Genero> gnros = null;
        try {
            URL url = new URL("http://localhost:8080/OrangeMusic/webresources/modelo.genero/buscarPorNombre/" + nombreGenero);
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
            gnros = new ArrayList<>();
            for (int i = 0; i < jsonArr.length(); i++) {
                Genero gn = new Genero(jsonArr.getJSONObject(i));
                gnros.add(gn);
            }
            
            if(gnros.isEmpty()){
                gnros = null;
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
        return gnros;
    }

    public String getNombreGenero() {
        return nombreGenero;
    }

    public void setNombreGenero(String nombreGenero) {
        this.nombreGenero = nombreGenero;
    }

    public int getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(int idGenero) {
        this.idGenero = idGenero;
    }
}
