package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Cancion;
import modelo.Sube;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-14T14:03:06")
@StaticMetamodel(Album.class)
public class Album_ { 

    public static volatile SingularAttribute<Album, String> disquera;
    public static volatile ListAttribute<Album, Cancion> cancionList;
    public static volatile SingularAttribute<Album, Integer> idAlbum;
    public static volatile SingularAttribute<Album, String> nombreImagen;
    public static volatile ListAttribute<Album, Sube> subeList;
    public static volatile SingularAttribute<Album, Integer> anoLanzamiento;
    public static volatile SingularAttribute<Album, String> nombreAlbum;

}