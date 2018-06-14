package orangemusic.modelo;

import org.json.JSONObject;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 13/06/2018
 * @time 11:10:44 PM
 */
public class Genero {
    private int idGenero;
    private String nombreAlbum;
    private int añoLanzamiento;
    private String nombreImagen;
    private String disquera;
    
    public Genero(){
        
    }
    
    public Genero(JSONObject generoJSON){
        this.añoLanzamiento = generoJSON.getInt("anoLanzamiento");
        this.idGenero = generoJSON.getInt("idGenero");
        this.disquera = generoJSON.getString("disquera");
        this.nombreAlbum = generoJSON.getString("nombreAlumno");
        this.nombreImagen = generoJSON.getString("nombreImagen");
    }
    
    public String crearGeneroJSON(){
        String generoJSON = "{";
        generoJSON += "\"idGenero\":\""+this.idGenero+"\",";
        generoJSON += "\"nombreAlbum\":\""+this.nombreAlbum+"\",";
        generoJSON += "\"anoLanzamiento\":\""+this.añoLanzamiento+"\",";
        generoJSON += "\"disquera\":\""+this.disquera+"\",";
        generoJSON += "\"nombreImagen\":\""+this.nombreImagen+"\"";
        generoJSON += "}";
        
        return generoJSON;
    }
    
    @Override
    public String toString(){
        return this.nombreAlbum;
    }
}
