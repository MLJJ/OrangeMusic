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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import orangemusic.controladores.VisualizarHistorialGUIController;
import static orangemusic.utilerias.Constante.URLSERVICIOS;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Descripcion
 *
 * @author Cristhian Ubaldo Promotor
 * @version fecha
 */
public class ListaReproduccion {

    private Integer idListaReproduccion;
    private String nombreLista;
    private String visibilidad;
    private List<Cancion> canciones;

    public ListaReproduccion() {
        this.canciones = new ArrayList<>();
    }

    public ListaReproduccion(JSONObject jsonLista) {
        this.canciones = new ArrayList<>();
        this.idListaReproduccion = jsonLista.getInt("idListaReproduccion");
        this.nombreLista = jsonLista.getString("nombreLista");
        this.visibilidad = jsonLista.getString("visibilidad");
        JSONArray listaCanciones = jsonLista.getJSONArray("cancionList");
        for (int i = 0; i < listaCanciones.length(); i++) {
            Cancion cancion = new Cancion(listaCanciones.getJSONObject(i));
            canciones.add(cancion);
        }
    }

    public Integer getIdListaReproduccion() {
        return idListaReproduccion;
    }

    public void setIdListaReproduccion(Integer idListaReproduccion) {
        this.idListaReproduccion = idListaReproduccion;
    }

    public String getNombreLista() {
        return nombreLista;
    }

    public void setNombreLista(String nombreLista) {
        this.nombreLista = nombreLista;
    }

    public String getVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(String visibilidad) {
        this.visibilidad = visibilidad;
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }

    public ListaReproduccion buscarHistorial(String correo) {
        ListaReproduccion lista = null;
        URL url = null;
        try {
            //cambiar url
            url = new URL(System.getProperty("servicio") +"/webresources/modelo.listareproduccion/historial/" + correo);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestProperty("Content-Type", "application/json");
            conexion.setRequestProperty("Accept", "application/json");
            conexion.connect();
            InputStream input;
            if (conexion.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                input = conexion.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));

                String cad = bufferedReader.readLine();
                System.out.println("json" + cad);
                JSONObject jsonObject = new JSONObject(cad);
                lista = (ListaReproduccion) jsonObject.get("listareproduccion");
            } else {
                input = conexion.getErrorStream();
            }

            //lista.setCanciones(buscarCancionesDeHistorial(lista.getIdListaReproduccion()));
            conexion.disconnect();
        } catch (MalformedURLException ex) {
            Logger.getLogger(ListaReproduccion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ListaReproduccion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public boolean ingresarCancionAHistorial(Cancion cancion, ListaReproduccion lista) {
        boolean ingresada = false;        
        URL url = null;
        try {
            url = new URL(System.getProperty("servicio")+ "/webresources/modelo.listareproduccion/agregarCancionLista/"+lista.getIdListaReproduccion()+"/"+cancion.getIdCancion());
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestProperty("Content-Type", "application/json");
            conexion.setRequestProperty("Accept", "application/json");
            conexion.setRequestMethod("POST");
            conexion.setDoInput(true);
            conexion.setDoOutput(true);
            conexion.connect();

            InputStream input;
            if (conexion.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                input = conexion.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                String cad = bufferedReader.readLine();
                System.out.println(cad);
                System.out.println("Cancion ingresada al historial");
                ingresada = true;
            } else {
                input = conexion.getErrorStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                String cad = bufferedReader.readLine();
                System.out.println(cad);
            }

            conexion.disconnect();

        } catch (MalformedURLException ex) {
            Logger.getLogger(VisualizarHistorialGUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VisualizarHistorialGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ingresada;
    }

    public ListaReproduccion crearHistorial(String correo) {
        System.out.println("crear historial");
        ListaReproduccion lista = null;
        URL url = null;
        try {
            url = new URL(System.getProperty("servicio")+ "/webresources/modelo.listareproduccion");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestProperty("Content-Type", "application/json");
            conexion.setRequestProperty("Accept", "application/json");
            conexion.setRequestMethod("POST");
            conexion.setDoInput(true);
            conexion.setDoOutput(true);
            conexion.connect();
            JSONObject jsonObject = new JSONObject();
            JSONArray arr = new JSONArray();
            JSONObject usuario = new JSONObject();
            usuario.accumulate("correo", correo);
            jsonObject.accumulate("nombreLista", "historial");
            jsonObject.accumulate("visibilidad", "privado");
            jsonObject.accumulate("correoUsuario", usuario);
            OutputStream outputStream = conexion.getOutputStream();
            BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(outputStream));
            escritor.write(String.valueOf(jsonObject));
            System.out.println(String.valueOf(jsonObject));
            escritor.flush();

            InputStream input;
            if (conexion.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                input = conexion.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                String cad = bufferedReader.readLine();
                System.out.println(cad);
                JSONObject listaObjecto = new JSONObject(cad);
                lista = (ListaReproduccion) listaObjecto.get("listareproduccion");
            } else {
                input = conexion.getErrorStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                String cad = bufferedReader.readLine();
                System.out.println(cad);
            }

            conexion.disconnect();

        } catch (MalformedURLException ex) {
            Logger.getLogger(VisualizarHistorialGUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VisualizarHistorialGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public List<ListaReproduccion> obtenerMisListas(Usuario usr) {
        List<ListaReproduccion> misListas = null;
        try {
            URL url = new URL(URLSERVICIOS + "modelo.listareproduccion/buscarPorUsuario/" + usr.getCorreo());
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
            JSONArray jsonArr = new JSONArray(cad);
            misListas = new ArrayList<>();
            for (int i = 0; i < jsonArr.length(); i++) {
                ListaReproduccion lista = new ListaReproduccion(jsonArr.getJSONObject(i));
                misListas.add(lista);
            }

            if (misListas.isEmpty()) {
                misListas = null;
            }
        } catch (MalformedURLException ex) {
            System.out.println("Error en URL");
        } catch (ProtocolException ex) {
            System.out.println("Error en RequesMethod: GET");
        } catch (IOException ex) {
            System.out.println("Error en HttpUrlConnection");
        } catch (Exception ex) {
            System.out.println("Error al crear el JSON");
        }
        return misListas;
    }

    public boolean crearListaReproduccion(String correo) {
        System.out.println("creando lista reproducción");
        boolean confirmacion = false;
        URL url = null;
        try {
            //cambiar url
            url = new URL(URLSERVICIOS + "modelo.listareproduccion");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestProperty("Content-Type", "application/json");
            conexion.setRequestProperty("Accept", "application/json");
            conexion.setRequestMethod("POST");
            conexion.setDoInput(true);
            conexion.setDoOutput(true);
            conexion.connect();
            JSONObject jsonObject = new JSONObject();
            JSONArray arr = new JSONArray();
            JSONObject usuario = new JSONObject();
            usuario.accumulate("correo", correo);
            jsonObject.accumulate("nombreLista", this.nombreLista);
            jsonObject.accumulate("visibilidad", this.visibilidad);
            jsonObject.accumulate("correoUsuario", usuario);
            OutputStream outputStream = conexion.getOutputStream();
            BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(outputStream));
            escritor.write(String.valueOf(jsonObject));
            System.out.println(String.valueOf(jsonObject));
            escritor.flush();

            InputStream input;
            if (conexion.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                input = conexion.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                String cad = bufferedReader.readLine();
                confirmacion = true;
            } else {
                input = conexion.getErrorStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                String cad = bufferedReader.readLine();
                System.out.println(cad);
                confirmacion = false;
            }

            conexion.disconnect();

        } catch (MalformedURLException ex) {
            Logger.getLogger(VisualizarHistorialGUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VisualizarHistorialGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return confirmacion;
    }

    public boolean editarListaReproduccion(String correo) {
        System.out.println("editando lista reproducción");
        boolean confirmacion = false;
        URL url = null;
        try {
            //cambiar url
            url = new URL(URLSERVICIOS + "modelo.listareproduccion/" + this.idListaReproduccion);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestProperty("Content-Type", "application/json");
            conexion.setRequestProperty("Accept", "application/json");
            conexion.setRequestMethod("PUT");
            conexion.setDoInput(true);
            conexion.setDoOutput(true);
            conexion.connect();
            JSONObject jsonObject = new JSONObject();
            JSONArray arr = new JSONArray();
            JSONObject usuario = new JSONObject();
            usuario.accumulate("correo", correo);
            jsonObject.accumulate("idListaReproduccion", this.idListaReproduccion);
            jsonObject.accumulate("nombreLista", this.nombreLista);
            jsonObject.accumulate("visibilidad", this.visibilidad);
            jsonObject.accumulate("correoUsuario", usuario);
            OutputStream outputStream = conexion.getOutputStream();
            BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(outputStream));
            escritor.write(String.valueOf(jsonObject));
            System.out.println(String.valueOf(jsonObject));
            escritor.flush();

            InputStream input;
            if (conexion.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                input = conexion.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                String cad = bufferedReader.readLine();
                confirmacion = true;
            } else {
                input = conexion.getErrorStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                String cad = bufferedReader.readLine();
                System.out.println(cad);
                confirmacion = false;
            }

            conexion.disconnect();

        } catch (MalformedURLException ex) {
            Logger.getLogger(VisualizarHistorialGUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VisualizarHistorialGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return confirmacion;
    }

    public boolean eliminarCancionLista(Integer idListaReproduccion, int idCancion) {
        boolean confirmacion = false;
        URL url = null;
        try {
            //cambiar url
            url = new URL(URLSERVICIOS + "modelo.listareproduccion/eliminarCancionLista/"
                    + idListaReproduccion + "/" + idCancion);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestProperty("Content-Type", "application/json");
            conexion.setRequestProperty("Accept", "application/json");
            conexion.setRequestMethod("PUT");
            conexion.setDoInput(true);
            conexion.setDoOutput(true);
            conexion.connect();

            InputStream input;
            if (conexion.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                input = conexion.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                String cad = bufferedReader.readLine();
                confirmacion = true;
            } else {
                input = conexion.getErrorStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                String cad = bufferedReader.readLine();
                confirmacion = false;
            }

            conexion.disconnect();

        } catch (MalformedURLException ex) {
            Logger.getLogger(VisualizarHistorialGUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VisualizarHistorialGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return confirmacion;
    }

    public boolean agregarCancionLista(Integer idListaReproduccion, int idCancion) {
        boolean confirmacion = false;
        URL url = null;
        try {
            //cambiar url
            url = new URL(URLSERVICIOS + "modelo.listareproduccion/agregarCancionLista/"
                    + idListaReproduccion + "/" + idCancion);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestProperty("Content-Type", "application/json");
            conexion.setRequestProperty("Accept", "application/json");
            conexion.setRequestMethod("POST");
            conexion.setDoInput(true);
            conexion.setDoOutput(true);
            conexion.connect();

            InputStream input;
            if (conexion.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                input = conexion.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                String cad = bufferedReader.readLine();
                confirmacion = true;
            } else {
                input = conexion.getErrorStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                String cad = bufferedReader.readLine();
                confirmacion = false;
            }

            conexion.disconnect();

        } catch (MalformedURLException ex) {
            Logger.getLogger(VisualizarHistorialGUIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VisualizarHistorialGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return confirmacion;
    }

    public List<Cancion> obtenerSubidas(Usuario usr) {
        List<Cancion> subidas = null;
        try {
            URL url = new URL(URLSERVICIOS + "modelo.sube/cancionesSubidas/" + usr.getCorreo());
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
            JSONArray jsonArr = new JSONArray(cad);
            subidas = new ArrayList<>();
            for (int i = 0; i < jsonArr.length(); i++) {
                Cancion cancion = new Cancion(jsonArr.getJSONObject(i));
                subidas.add(cancion);
            }

        } catch (MalformedURLException ex) {
            System.out.println("Error en URL");
        } catch (ProtocolException ex) {
            System.out.println("Error en RequesMethod: GET");
        } catch (IOException ex) {
            System.out.println("Error en HttpUrlConnection");
        } catch (Exception ex) {
            System.out.println("Error al crear el JSON");
        }
        return subidas;
    }

}
