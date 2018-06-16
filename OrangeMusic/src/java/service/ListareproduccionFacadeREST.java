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
import modelo.Listareproduccion;

/**
 *
 * @author enriq
 */
@Stateless
@Path("modelo.listareproduccion")
public class ListareproduccionFacadeREST extends AbstractFacade<Listareproduccion> {

    @PersistenceContext(unitName = "OrangeMusicPU")
    private EntityManager em;

    public ListareproduccionFacadeREST() {
        super(Listareproduccion.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Listareproduccion entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Listareproduccion entity) {
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
    public Listareproduccion find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Listareproduccion> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Listareproduccion> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public Listareproduccion find(@PathParam("correo") String correo) {
        Listareproduccion lista=null;
        EntityManager conexion = getEntityManager();
        lista = (Listareproduccion) conexion.createQuery("Select l FROM ListaReproduccion l WHERE l.nombreLista='historial' AND l.correoUsuario: =email").setParameter("email", correo).getResultList();
        return lista;
    }
    
    @GET
    @Path("/cancionesLista/{idLista}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Cancion> sacarCancionesLista(@PathParam("idLista")Integer idLista){
        EntityManager conexion = getEntityManager();
        List<Cancion> canciones = null;
        try{
            Listareproduccion lista = conexion.find(Listareproduccion.class, idLista);
            canciones = lista.getCancionList();
        }catch(Exception e){
            canciones = new ArrayList();
        }
        
        return canciones;
    }
    
}
