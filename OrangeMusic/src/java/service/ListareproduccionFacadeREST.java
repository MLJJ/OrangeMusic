/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import modelo.Cancion;
import modelo.ListaReproduccion;

/**
 *
 * @author enriq
 */
@Stateless
@Path("modelo.listareproduccion")
public class ListareproduccionFacadeREST extends AbstractFacade<ListaReproduccion> {

    @PersistenceContext(unitName = "OrangeMusicPU")
    private EntityManager em;

    public ListareproduccionFacadeREST() {
        super(ListaReproduccion.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(ListaReproduccion entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, ListaReproduccion entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public ListaReproduccion find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<ListaReproduccion> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<ListaReproduccion> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @GET
    @Path("/historial/{correo}")
    @Produces({MediaType.APPLICATION_JSON})
    public ListaReproduccion find(@PathParam("correo") String correo) {
        ListaReproduccion lista=null;
        EntityManager conexion = getEntityManager();
        lista = (ListaReproduccion) conexion.createQuery("Select l FROM ListaReproduccion l WHERE l.nombreLista='historial' AND l.correoUsuario: =email").setParameter("email", correo).getResultList();
        return lista;
    }
    
    @GET
    @Path("/cancionesLista/{idLista}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Cancion> sacarCancionesLista(@PathParam("idLista")Integer idLista){
        EntityManager conexion = getEntityManager();
        List<Cancion> canciones = null;
        try{
            ListaReproduccion lista = conexion.find(ListaReproduccion.class, idLista);
            canciones = lista.getCancionList();
        }catch(Exception e){
            canciones = new ArrayList();
        }
        
        return canciones;
    }
    
    @GET
    @Path("/buscarPorUsuario/{correo}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<ListaReproduccion> sacarCancionesLista(@PathParam("correo")String correoUsuario){
        EntityManager conexion = getEntityManager();
        List<ListaReproduccion> listas = null;
        try{
             listas = conexion.createQuery("SELECT l FROM Listareproduccion l WHERE l.correoUsuario.correo = :correoUsuario")
                     .setParameter("correoUsuario", correoUsuario).getResultList();
        }catch(Exception e){
            listas = new ArrayList();
        }
        
        return listas;
    }
    
}
