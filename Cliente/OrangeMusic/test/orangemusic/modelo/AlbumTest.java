package orangemusic.modelo;

import java.io.File;
import java.util.List;
import org.json.JSONArray;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class AlbumTest {

    public AlbumTest() {
        String ipArchivada = System.getProperty("servicio");
        if (ipArchivada == null) {
            System.getProperties().put("servicio", "http://localhost:8080/OrangeMusic/");
        }
    }

    /**
     * Test of subirAlbum method, of class Album.
     */
    @Test
    public void testSubirAlbum() {
        System.out.println("subirAlbum");
        boolean disponibilidad = false;
        //ruta de un zip con canciones
        File canciones = new File("");
        //ruta de una imagen
        File imagen = new File("");
        Artista artista = new Artista();
        artista.setIdArtista(1);
        Genero genero = new Genero();
        genero.setIdGenero(1);
        Album album = new Album();
        album.setAnoLanzamiento(2018);
        album.setArtista(artista);
        album.setDisquera("Mexican");
        album.setNombreAlbum("Lo nuevo de katy");

        boolean expResult = true;
        String result = album.subirAlbum(album, disponibilidad, canciones, imagen);
        boolean validacion = true;
        try {
            JSONArray lista = new JSONArray(result);
        } catch (Exception e) {
            validacion = false;
        }
        assertEquals(expResult, album);
    }

    /**
     * Test of buscarAlbum method, of class Album.
     */
    @Test
    public void testBuscarAlbum() {
        System.out.println("buscarAlbum");
        String nombreAlbum = "Prism";
        Album instance = new Album();
        boolean expResult = true;
        List<Album> result = instance.buscarAlbum(nombreAlbum);
        assertEquals(expResult, !result.isEmpty());
    }

    /**
     * Test of descargarCancion method, of class Album.
     */
    @Test
    public void testDescargarCancion() {
        System.out.println("descargarCancion");
        int idCancion = 1;
        String nombreCancion = "loca.mp3";
        Album instance = new Album();
        boolean expResult = false;
        boolean result = instance.descargarCancion(idCancion, nombreCancion);
        assertEquals(expResult, result);
    }

    /**
     * Test of subirImagenAlbum method, of class Album.
     */
    @Test
    public void testSubirImagenAlbum() {
        System.out.println("subirImagenAlbum");
        int album = 1;
        //ruta de una imagen
        File imagen = new File("");
        Album instance = new Album();
        boolean expResult = true;
        boolean result = instance.subirImagenAlbum(album, imagen);
        assertEquals(expResult, result);
    }

}
