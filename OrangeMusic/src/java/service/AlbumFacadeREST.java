package service;

import java.io.FileOutputStream;
import java.util.ArrayList;
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
import modelo.Artista;
import modelo.Cancion;
import modelo.Genero;
import modelo.Sube;
import modelo.Usuario;
import org.json.JSONArray;
import org.json.JSONObject;

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
    @Path("{disponibilidad}/{correo}/{idArtista}/{idGenero}")
    @Consumes({MediaType.APPLICATION_JSON})
    public List<Cancion> registrarAlbum(@PathParam("disponibilidad") Short disponibilidad, @PathParam("correo") String correo,@PathParam("idArtista") Integer idArtista ,@PathParam("idGenero") Integer idGenero,Album nuevoAlbum) {
        List<Cancion> cancionesRegistradas = new ArrayList();
        EntityManager conexion = getEntityManager();
        String canciones = nuevoAlbum.getNombreImagen();
        System.out.println(canciones);
        Usuario usuario = null;
        try{
            usuario = conexion.find(Usuario.class, correo);
            nuevoAlbum.setArtistaidArtista(conexion.find(Artista.class, idArtista));
            nuevoAlbum.setGeneroidGenero(conexion.find(Genero.class, idGenero));
            
            conexion.persist(nuevoAlbum);
            nuevoAlbum.setNombreImagen(nuevoAlbum.getIdAlbum()+".jpg");
            conexion.merge(nuevoAlbum);
            Sube sube = new Sube();
            sube.setAlbum(nuevoAlbum);
            sube.setPrivacidad(disponibilidad);
            sube.setUsuario(usuario);
            conexion.persist(sube);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        try{
            JSONArray lista = new JSONArray(canciones);
            for(int i = 0; i < lista.length(); i++){
                JSONObject objeto = lista.getJSONObject(i);
                Cancion cancion = new Cancion();
                cancion.setAlbumidAlbum(nuevoAlbum);
                cancion.setRutaCancion(objeto.getString("nombreCancion"));
                cancion.setNombreCancion(objeto.getString("nombreCancion"));
                conexion.persist(cancion);
                cancionesRegistradas.add(cancion);
            }
            
        }catch(Exception e){
            e.printStackTrace();
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
            FileOutputStream arch = new FileOutputStream("C:\\Users\\Leonardo\\Documents\\GitHub\\OrangeMusic\\OrangeMusic\\web\\imagenesAlbum\\" + album.getIdAlbum()+".jpg");
            arch.write(arr, 0, tamaño);
            arch.close();
            salida = "{\"respuesta\": \"OK\"}";

        } catch (Exception exception) {
            salida = "{\"respuesta\": \"" + exception.toString() + "\"}";
        }

        return Response.status(200).entity(salida).build();
    }
    
    @GET
    @Path("buscarPorNombre/{nombre}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Album> buscarAlbum(@PathParam("nombre") String nombre) {
        List<Album> albumnes = null;
        String palabraClave = "%" + nombre + "%";
        EntityManager conexion = getEntityManager();

        try {
            albumnes = conexion.createQuery("SELECT a FROM Album a WHERE a.nombreAlbum LIKE :palabraClave").setParameter("palabraClave", palabraClave).getResultList();
        } catch (Exception e) {
            albumnes = new ArrayList();
        }

        return albumnes;
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
