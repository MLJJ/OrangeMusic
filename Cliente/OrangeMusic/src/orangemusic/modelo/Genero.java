package orangemusic.modelo;

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
}
