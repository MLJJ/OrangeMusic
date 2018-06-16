package orangemusic.modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.json.JSONObject;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 16/06/2018
 * @time 12:14:01 AM
 */
public class Cancion {
    private int idCancion;
    private String nombreCancion;
    private String rutaCancion;
    
    public Cancion(){
        
    }
    
    public Cancion(JSONObject cancionJSON){
        this.idCancion = cancionJSON.getInt("idCancion");
        this.nombreCancion = cancionJSON.getString("nombreCancion");
        this.rutaCancion = cancionJSON.getString("rutaCancion");
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
    
    public String crearCancionJSON(){
        String cancionJSON = "{";
        cancionJSON += "\"idCancion\":\""+this.idCancion+"\",";
        cancionJSON += "\"rutaCancion\":\""+this.rutaCancion+"\",";
        cancionJSON += "\"nombreCancion\":\""+this.nombreCancion+"\"";
        cancionJSON += "}";
        
        return cancionJSON;
    }
    
    public List<Cancion> sacarCancionesDeAlbum(String ruta){
        List<Cancion> canciones = new ArrayList();
        
        File carpetaExtraer = new File(ruta);
        ZipInputStream zip = null;
        if (carpetaExtraer.exists()) {
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
                
            }finally{
                if(zip != null){
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
    
    @Override
    public String toString(){
        return this.nombreCancion;
    }
}
