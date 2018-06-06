package service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 7/06/2018
 * @time 12:32:07 AM
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(service.AlbumFacadeREST.class);
        resources.add(service.ArtistaFacadeREST.class);
        resources.add(service.CancionFacadeREST.class);
        resources.add(service.GeneroFacadeREST.class);
        resources.add(service.UsuarioFacadeREST.class);
        resources.add(service.VersionFacadeREST.class);
    }

}
