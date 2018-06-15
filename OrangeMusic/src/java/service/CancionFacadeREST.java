/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.Cancion;
import java.util.Base64;
import modelo.Album;
import modelo.Usuario;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 15/06/2018
 * @time 10:56:08 PM
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

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @POST
    @Path("/imagenesalbum")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response subirImagenAlbum(Album album){
        String salida="";
        
        try{
                //String ruta = new File(".").getCanonicalPath() + "/fotos/" + usuario.getCorreo() + ".jpg";
                
                byte arr[] = Base64.getDecoder().decode(album.getNombreImagen());
                int tamaño = arr.length;
                FileOutputStream arch = new FileOutputStream("C:\\Users\\Leonardo\\Documents\\GitHub\\OrangeMusic\\OrangeMusic\\web\\imagenesAlbum\\"+album.getIdAlbum());
                arch.write(arr, 0, tamaño);
                arch.close();
                salida = "{\"respuesta\": \"OK\"}";
            
        }catch(Exception exception){
            salida = "{\"respuesta\": \"" + exception.toString() + "\"}";
}
        
        return Response.status(200).entity(salida).build();
    }
    
    @POST
    @Path("/imagenPerfil")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response subirImagenPerfil(Usuario usuario){
        String salida="";
        
        try{
                //String ruta = new File(".").getCanonicalPath() + "/fotos/" + usuario.getCorreo() + ".jpg";
                
                byte arr[] = Base64.getDecoder().decode(usuario.getNombreUsuario());
                int tamaño = arr.length;
                FileOutputStream arch = new FileOutputStream("C:\\Users\\Leonardo\\Documents\\GitHub\\OrangeMusic\\OrangeMusic\\web\\imagenesPerfil\\"+usuario.getCorreo());
                arch.write(arr, 0, tamaño);
                arch.close();
                salida = "{\"respuesta\": \"OK\"}";
            
        }catch(Exception exception){
            salida = "{\"respuesta\": \"" + exception.toString() + "\"}";
        }
        
        return Response.status(200).entity(salida).build();
    }

}
