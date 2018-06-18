/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orangemusic.modelo;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import orangemusic.utilerias.UtileriaSHA2;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author arkadwn
 */
public class UsuarioTest {

    public UsuarioTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testToJSONfy() {
        System.out.println("toJSONfy");
        Usuario instance = new Usuario();
        try {
            instance.setContraseña(UtileriaSHA2.encriptarContrasena("1234"));
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Error al encriptar");
        }
        instance.setCorreo("arkadwn@gmail.com");
        instance.setNombre("Adrián Bustamante");
        String expResult = "{\"correo\":\"arkadwn@gmail.com\",\"nombreUsuario\":\"Adrián Bustamante\",\"password\":\"03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4\"}";
        JSONObject json = new JSONObject(expResult);
        String result = instance.toJSONfy();
        JSONObject json2 = new JSONObject(result);
        assertEquals(json.get("nombreUsuario"), json2.get("nombreUsuario"));
    }

    @Test
    public void testRegistrarUsuario() {
        System.out.println("registrarUsuario");
        Usuario usr = new Usuario();
        try {
            usr.setContraseña(UtileriaSHA2.encriptarContrasena("1234"));
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Error al encriptar");
        }
        usr.setCorreo("chinawn@gmail.com");
        usr.setNombre("Sinaí Hernandez");
        boolean expResult = true;
        boolean result = new Usuario().registrarUsuario(usr);
        assertEquals(expResult, result);
    }

    @Test
    public void testRegistrarUsuario2() {
        System.out.println("registrarUsuario fallido user registrado");
        Usuario usr = new Usuario();
        try {
            usr.setContraseña(UtileriaSHA2.encriptarContrasena("1234"));
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Error al encriptar");
        }
        usr.setCorreo("arkadwn@gmail.com");
        usr.setNombre("Adrián Bustamante");
        boolean expResult = false;
        
        boolean result;
        try{
            result = new Usuario().registrarUsuario(usr);
            result = true;
        }catch(Exception ex){
            result = false;
        }
        assertEquals(expResult, result);
    }

    @Test
    public void testAutenticar() {
        System.out.println("autenticar");
        Usuario usr = new Usuario();
        usr.setCorreo("arkadwn@gmail.com");
        try {
            usr.setContraseña(UtileriaSHA2.encriptarContrasena("1234"));
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Error al encriptar contraseña");
        }
        boolean expResult = true;
        boolean result = false;
        Usuario retorno = new Usuario().autenticar(usr);
        if (retorno.getContraseña().equals(usr.getContraseña())) {
            result = true;
        }
        assertEquals(expResult, result);
    }

    @Test
    public void testAutenticar2() {
        System.out.println("autenticar contraseña incorrecta");
        Usuario usr = new Usuario();
        usr.setCorreo("arkadwn@gmail.com");
        try {
            usr.setContraseña(UtileriaSHA2.encriptarContrasena("12345"));
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Error al encriptar contraseña");
        }
        boolean expResult = false;
        boolean result = false;
        Usuario retorno = new Usuario().autenticar(usr);
        if (retorno != null && retorno.getContraseña().equals(usr.getContraseña())) {
            result = true;
        }
        assertEquals(expResult, result);
    }

}
