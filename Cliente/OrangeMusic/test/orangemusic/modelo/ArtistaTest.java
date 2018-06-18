package orangemusic.modelo;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class ArtistaTest {
    
    public ArtistaTest() {
        String ipArchivada = System.getProperty("servicio");
        if (ipArchivada == null) {
            System.getProperties().put("servicio", "http://localhost:8080/OrangeMusic/");
        }
    }
    

    /**
     * Test of guardarArtista method, of class Artista.
     */
    @Test
    public void testGuardarArtista() {
        System.out.println("guardarArtista");
        Artista artista = null;
        Artista instance = new Artista();
        instance.setNombreArtista("Maluma baby");
        String expResult = "Se ha registrado el artista";
        String result = instance.guardarArtista(artista);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of guardarArtista method, of class Artista.
     */
    @Test
    public void testGuardarArtistaYaExistente() {
        System.out.println("guardarArtista");
        Artista artista = null;
        Artista instance = new Artista();
        instance.setNombreArtista("Katy Perri");
        String expResult = "El artista ya se encuentra registrado";
        String result = instance.guardarArtista(artista);
        assertEquals(expResult, result);
    }

    /**
     * Test of sacarArtista method, of class Artista.
     */
    @Test
    public void testSacarArtista() {
        System.out.println("sacarArtista");
        Artista instance = new Artista();
        boolean expResult = true;
        List<Artista> result = instance.sacarArtista();
        assertEquals(expResult, !result.isEmpty());
    }

    /**
     * Test of buscarArtista method, of class Artista.
     */
    @Test
    public void testBuscarArtista() {
        System.out.println("buscarArtista");
        String nombreArtista = "Katy";
        Artista instance = new Artista();
        boolean expResult = true;
        List<Artista> result = instance.buscarArtista(nombreArtista);
        assertEquals(true, !result.isEmpty());
    }
    
}
