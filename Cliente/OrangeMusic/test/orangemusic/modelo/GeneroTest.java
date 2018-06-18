/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orangemusic.modelo;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class GeneroTest {
    
    public GeneroTest() {
        String ipArchivada = System.getProperty("servicio");
        if (ipArchivada == null) {
            System.getProperties().put("servicio", "http://localhost:8080/OrangeMusic/");
        }
    }

    /**
     * Test of sacarGeneros method, of class Genero.
     */
    @Test
    public void testSacarGeneros() {
        System.out.println("sacarGeneros");
        Genero instance = new Genero();
        boolean expResult = true;
        List<Genero> result = instance.sacarGeneros();
        assertEquals(expResult, !result.isEmpty());
    }

    /**
     * Test of buscarGenero method, of class Genero.
     */
    @Test
    public void testBuscarGenero() {
        System.out.println("buscarGenero");
        String nombreGenero = "Pop";
        Genero instance = new Genero();
        boolean expResult = true;
        List<Genero> result = instance.buscarGenero(nombreGenero);
        assertEquals(expResult, !result.isEmpty());
    }
    
    /**
     * Test of buscarGenero method, of class Genero.
     */
    @Test
    public void testBuscarGeneroQueNoExiste() {
        System.out.println("buscarGeneroQueNoExiste");
        String nombreGenero = "Cartel de santa";
        Genero instance = new Genero();
        boolean expResult = true;
        List<Genero> result = instance.buscarGenero(nombreGenero);
        assertEquals(expResult, result.isEmpty());
    }
    
}
