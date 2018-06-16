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
import javax.ws.rs.core.Response;
import modelo.Artista;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 15/06/2018
 * @time 10:56:19 PM
 */
@Stateless
@Path("modelo.artista")
public class ArtistaFacadeREST extends AbstractFacade<Artista> {

    @PersistenceContext(unitName = "OrangeMusicPU")
    private EntityManager em;

    public ArtistaFacadeREST() {
        super(Artista.class);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response registrarArtista(Artista entity) {
        String salida = "";
        try{
            super.create(entity);
            salida = "{\"respuesta\": \"Se ha registrado el artista\"}";
        }catch(Exception e){
            salida = "{\"respuesta\": \"El artista ya se encuentra registrado\"}";
        }
        return Response.status(200).entity(salida).build();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Artista entity) {
        super.edit(entity);
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Artista find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Artista> findAll() {
        return super.findAll();
    }

    @GET
    @Path("buscarPorNombre/{nombre}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Artista> buscarArtista(@PathParam("nombre") String nombre) {
        List<Artista> generos = null;
        String palabraClave = "%" + nombre + "%";
        EntityManager conexion = getEntityManager();

        try {
            generos = conexion.createQuery("SELECT a FROM Artista a WHERE a.nombre LIKE :palabraClave").setParameter("palabraClave", palabraClave).getResultList();
        } catch (Exception e) {
            generos = new ArrayList();
        }

        return generos;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
