package orangemusic.modelo;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class CancionTest {
    
    public CancionTest() {
        String ipArchivada = System.getProperty("servicio");
        if (ipArchivada == null) {
            System.getProperties().put("servicio", "http://localhost:8080/OrangeMusic/");
        }
    }

    /**
     * Test of buscarCancion method, of class Cancion.
     */
    @Test
    public void testBuscarCancion() {
        System.out.println("buscarCancion");
        String nombreCancion = "Roar";
        Cancion instance = new Cancion();
        boolean expResult = true;
        List<Cancion> result = instance.buscarCancion(nombreCancion);
        assertEquals(expResult, !result.isEmpty());
    }

    /**
     * Test of crearListaDeEstacion method, of class Cancion.
     */
    @Test
    public void testCrearListaDeEstacion() {
        System.out.println("crearListaDeEstacion");
        int idGenero = 1;
        String correo = "cris@hotmail.com";
        Cancion instance = new Cancion();
        boolean expResult = true;
        List<Cancion> result = instance.crearListaDeEstacion(idGenero, correo);
        assertEquals(expResult, !result.isEmpty());
    }
    
}
