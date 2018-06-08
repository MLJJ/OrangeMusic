package service;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.Cancion;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 7/06/2018
 * @time 12:32:18 AM
 */
@Stateless
@Path("modelo.cancion")
public class CancionFacadeREST extends AbstractFacade<Cancion> {

    @PersistenceContext(unitName = "OrangeMusicPU")
    private EntityManager em;

    public CancionFacadeREST() {
        super(Cancion.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Cancion entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Cancion entity) {
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
    public Cancion find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Cancion> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Cancion> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    @POST
    @Path("/fotos/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@Context HttpServletRequest request, @PathParam("id") String id){
        String output = "";
        try{
            if (request.getParts().size() > 0){
                Part parte = (Part) request.getParts().toArray()[0];
                String path = "C:\\Users\\Leonardo\\Documents\\GitHub\\ServiciosForeignCook\\ForeignCook\\web\\fotos\\"+id+".jpg";
                InputStream is = parte.getInputStream();
                System.out.println("pase");
                int tam = (int) parte.getSize();
                byte arr[] = new byte[tam];
                is.read(arr, 0, tam);
                FileOutputStream arch = new FileOutputStream(path);
                arch.write(arr, 0, tam);
                arch.close();
                output = "{\"respuesta\": \"OK\"}";
            }
        }catch(Exception exception){
            System.out.println(exception.getMessage());
            output = "{\"respuesta\": \"" + exception.toString() + "\"}";
        }
        return Response.status(200).entity(output).build();
    }

}
