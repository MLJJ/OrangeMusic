package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Cancion;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-14T14:03:06")
@StaticMetamodel(Artista.class)
public class Artista_ { 

    public static volatile SingularAttribute<Artista, Integer> idArtista;
    public static volatile ListAttribute<Artista, Cancion> cancionList;
    public static volatile SingularAttribute<Artista, String> nombre;

}