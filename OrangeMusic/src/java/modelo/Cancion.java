/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 7/06/2018
 * @time 12:31:18 AM
 */
@Entity
@Table(name = "cancion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cancion.findAll", query = "SELECT c FROM Cancion c")
    , @NamedQuery(name = "Cancion.findByIdCancion", query = "SELECT c FROM Cancion c WHERE c.idCancion = :idCancion")
    , @NamedQuery(name = "Cancion.findByNombreCancion", query = "SELECT c FROM Cancion c WHERE c.nombreCancion = :nombreCancion")})
public class Cancion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCancion")
    private Integer idCancion;
    @Basic(optional = false)
    @Column(name = "nombreCancion")
    private String nombreCancion;
    @JoinColumn(name = "album", referencedColumnName = "idAlbum")
    @ManyToOne(optional = false)
    private Album album;
    @JoinColumn(name = "artista", referencedColumnName = "idArtista")
    @ManyToOne(optional = false)
    private Artista artista;
    @JoinColumn(name = "genero", referencedColumnName = "idGenero")
    @ManyToOne(optional = false)
    private Genero genero;
    @JoinColumn(name = "version", referencedColumnName = "idVersion")
    @ManyToOne(optional = false)
    private Version version;

    public Cancion() {
    }

    public Cancion(Integer idCancion) {
        this.idCancion = idCancion;
    }

    public Cancion(Integer idCancion, String nombreCancion) {
        this.idCancion = idCancion;
        this.nombreCancion = nombreCancion;
    }

    public Integer getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(Integer idCancion) {
        this.idCancion = idCancion;
    }

    public String getNombreCancion() {
        return nombreCancion;
    }

    public void setNombreCancion(String nombreCancion) {
        this.nombreCancion = nombreCancion;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCancion != null ? idCancion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cancion)) {
            return false;
        }
        Cancion other = (Cancion) object;
        if ((this.idCancion == null && other.idCancion != null) || (this.idCancion != null && !this.idCancion.equals(other.idCancion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Cancion[ idCancion=" + idCancion + " ]";
    }

}
