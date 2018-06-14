package orangemusic.modelo;

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
}
