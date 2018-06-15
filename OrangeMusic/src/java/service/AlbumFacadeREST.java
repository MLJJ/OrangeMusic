package service;

import java.io.FileOutputStream;
import java.util.Base64;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.Album;
import modelo.Cancion;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 15/06/2018
 * @time 10:56:03 PM
 */
@Stateless
@Path("modelo.album")
public class AlbumFacadeREST extends AbstractFacade<Album> {

    @PersistenceContext(unitName = "OrangeMusicPU")
    private EntityManager em;

    public AlbumFacadeREST() {
        super(Album.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Album entity) {
        super.create(entity);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public List<Cancion> registrarAlbum(Album nuevoAlbum) {
        List<Cancion> cancionesRegistradas = null;

        try {

        } catch (Exception e) {

        }

        return cancionesRegistradas;
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Album entity) {
        super.edit(entity);
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Album find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Album> findAll() {
        return super.findAll();
    }

    @POST
    @Path("/imagenesalbum")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response subirImagenAlbum(Album album) {
        String salida = "";

        try {
            byte arr[] = Base64.getDecoder().decode(album.getNombreImagen());
            int tamaño = arr.length;
            FileOutputStream arch = new FileOutputStream("C:\\Users\\Leonardo\\Documents\\GitHub\\OrangeMusic\\OrangeMusic\\web\\imagenesAlbum\\" + album.getIdAlbum());
            arch.write(arr, 0, tamaño);
            arch.close();
            salida = "{\"respuesta\": \"OK\"}";

        } catch (Exception exception) {
            salida = "{\"respuesta\": \"" + exception.toString() + "\"}";
        }

        return Response.status(200).entity(salida).build();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
