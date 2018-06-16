package orangemusic.modelo;

import java.io.BufferedReader;
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
}
