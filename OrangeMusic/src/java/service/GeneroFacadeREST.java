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
import modelo.Genero;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 15/06/2018
 * @time 10:55:58 PM
 */
@Stateless
@Path("modelo.genero")
public class GeneroFacadeREST extends AbstractFacade<Genero> {

    @PersistenceContext(unitName = "OrangeMusicPU")
    private EntityManager em;

    public GeneroFacadeREST() {
        super(Genero.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Genero entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Genero entity) {
        super.edit(entity);
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Genero find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Genero> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Genero> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
    @GET
    @Path("buscarPorNombre/{nombre}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Genero> buscarGenero(@PathParam("nombre") String nombre) {
        List<Genero> generos = null;
        String palabraClave = "%" + nombre + "%";
        EntityManager conexion = getEntityManager();

        try {
            generos = conexion.createQuery("SELECT g FROM Genero g WHERE g.nombreGenero LIKE :palabraClave").setParameter("palabraClave", palabraClave).getResultList();
        } catch (Exception e) {
            generos = new ArrayList();
        }
        return generos;
    }

}
