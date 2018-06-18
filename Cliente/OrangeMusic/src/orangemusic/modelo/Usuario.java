/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orangemusic.modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import orangemusic.utilerias.Constante;
import org.json.JSONObject;

/**
 *
 * @author arkadwn
 */
public class Usuario {

    private String nombre;
    private String correo;
    private String contraseña;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String toJSONfy() {
        String usuarioJSON = "{";
        usuarioJSON += "\"nombreUsuario\":\"" + this.nombre + "\",";
        usuarioJSON += "\"correo\":\"" + this.correo + "\",";
        usuarioJSON += "\"password\":\"" + this.contraseña + "\"";
        usuarioJSON += "}";
        return usuarioJSON;
    }

    public boolean registrarUsuario(Usuario usr) {
        boolean validacion = false;
        try {
            URL url = new URL(Constante.URLSERVICIOS + "modelo.usuario/");
            System.out.println(Constante.URLSERVICIOS);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.connect();

            JSONObject json = new JSONObject();
            json.accumulate("nombreUsuario", usr.getNombre());
            json.accumulate("correo", usr.getCorreo());
            json.accumulate("password", usr.getContraseña());
            json.accumulate("nombreImagen", " ");
            OutputStream outputStream = conn.getOutputStream();

            BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(outputStream));
            escritor.write(String.valueOf(json));
            escritor.flush();

            InputStream input;
            if (conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                input = conn.getInputStream();
            } else {
                input = conn.getErrorStream();
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
            String cadena = bufferedReader.readLine();
            JSONObject respuesta = new JSONObject(cadena);

            validacion = respuesta.getString("respuesta").equals("OK");

        } catch (MalformedURLException ex) {
            System.out.println("Error en URL");
        } catch (IOException ex) {
            System.out.println("Error en HttpURLConnection");
        }
        return validacion;
    }

    public Usuario autenticar(Usuario usr) {
        try {
            URL url = new URL(Constante.URLSERVICIOS + "modelo.usuario/" + usr.getCorreo());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();

            InputStream input;
            if (conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                input = conn.getInputStream();
            } else {
                input = conn.getErrorStream();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
            String cad = bufferedReader.readLine();
            JSONObject json = new JSONObject(cad);
            boolean validacion = json.getString("password").equals(usr.getContraseña());
            if(validacion){
                usr.setNombre(json.getString("nombreUsuario"));
            }else{
                usr = null;
            }

        } catch (MalformedURLException ex) {
            System.out.println("Error en URL");
        } catch (ProtocolException ex) {
            System.out.println("Error en RequesMethod: GET");
        } catch (IOException ex) {
            System.out.println("Error en HttpUrlConnection");
        } catch (Exception ex){
            System.out.println("Error al crear el JSON");
            usr = null;
        }
        return usr;
    }
}
