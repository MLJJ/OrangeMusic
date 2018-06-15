package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Album;
import modelo.Artista;
import modelo.Genero;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-14T14:03:06")
@StaticMetamodel(Cancion.class)
public class Cancion_ { 

    public static volatile SingularAttribute<Cancion, Integer> idCancion;
    public static volatile SingularAttribute<Cancion, Artista> artista;
    public static volatile SingularAttribute<Cancion, Album> album;
    public static volatile SingularAttribute<Cancion, Genero> genero;
    public static volatile SingularAttribute<Cancion, String> nombreCancion;

}