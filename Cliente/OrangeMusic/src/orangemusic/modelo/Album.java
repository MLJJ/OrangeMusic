package orangemusic.modelo;

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
        this.idAlbum = albumJSON.getInt("idGenero");
        this.disquera = albumJSON.getString("disquera");
        this.nombreAlbum = albumJSON.getString("nombreAlumno");
        this.nombreImagen = albumJSON.getString("nombreImagen");
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
}
