/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package orangemusic.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Descripcion
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
    
    
    
}
