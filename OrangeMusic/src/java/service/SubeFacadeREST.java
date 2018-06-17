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
import modelo.Album;
import modelo.Cancion;
import modelo.Sube;
import modelo.Usuario;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 15/06/2018
 * @time 10:56:13 PM
 */
@Stateless
@Path("modelo.sube")
public class SubeFacadeREST extends AbstractFacade<Sube> {

    @PersistenceContext(unitName = "OrangeMusicPU")
    private EntityManager em;

    public SubeFacadeREST() {
        super(Sube.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Sube entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Sube entity) {
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
    public Sube find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Sube> findAll() {
        return super.findAll();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @GET
    @Path("cancionesSubidas/{correo}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Cancion> buscarCancionesSubidas(@PathParam("correo")String correo){
        List<Cancion> canciones = new ArrayList();
        EntityManager conexion = null;
        Usuario usuario = null;
        List<Sube> archivosSubidos = null;
        Album album = null;
        try{
            conexion = getEntityManager();
            usuario = conexion.find(Usuario.class, correo);
            archivosSubidos = usuario.getSubeList();
            
            for(Sube sube : archivosSubidos){
                album = sube.getAlbum();
                canciones.addAll(album.getCancionList());
            }
        }catch(Exception e){
            
        }
        
        return canciones;
    }

}
