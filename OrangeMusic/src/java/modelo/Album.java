/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author arkadwn
 */
@Entity
@Table(name = "Album")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Album.findAll", query = "SELECT a FROM Album a")
    , @NamedQuery(name = "Album.findByIdAlbum", query = "SELECT a FROM Album a WHERE a.idAlbum = :idAlbum")
    , @NamedQuery(name = "Album.findByNombreAlbum", query = "SELECT a FROM Album a WHERE a.nombreAlbum = :nombreAlbum")
    , @NamedQuery(name = "Album.findByAnoLanzamiento", query = "SELECT a FROM Album a WHERE a.anoLanzamiento = :anoLanzamiento")
    , @NamedQuery(name = "Album.findByNombreImagen", query = "SELECT a FROM Album a WHERE a.nombreImagen = :nombreImagen")
    , @NamedQuery(name = "Album.findByDisquera", query = "SELECT a FROM Album a WHERE a.disquera = :disquera")})
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAlbum")
    private Integer idAlbum;
    @Column(name = "nombreAlbum")
    private String nombreAlbum;
    @Basic(optional = false)
    @Column(name = "anoLanzamiento")
    private int anoLanzamiento;
    @Column(name = "nombreImagen")
    private String nombreImagen;
    @Basic(optional = false)
    @Column(name = "disquera")
    private String disquera;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "albumidAlbum")
    private List<Cancion> cancionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "album")
    private List<Sube> subeList;
    @JoinColumn(name = "Artista_idArtista", referencedColumnName = "idArtista")
    @ManyToOne(optional = false)
    private Artista artistaidArtista;
    @JoinColumn(name = "Genero_idGenero", referencedColumnName = "idGenero")
    @ManyToOne(optional = false)
    private Genero generoidGenero;

    public Album() {
    }

    public Album(Integer idAlbum) {
        this.idAlbum = idAlbum;
    }

    public Album(Integer idAlbum, int anoLanzamiento, String disquera) {
        this.idAlbum = idAlbum;
        this.anoLanzamiento = anoLanzamiento;
        this.disquera = disquera;
    }

    public Integer getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(Integer idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getNombreAlbum() {
        return nombreAlbum;
    }

    public void setNombreAlbum(String nombreAlbum) {
        this.nombreAlbum = nombreAlbum;
    }

    public int getAnoLanzamiento() {
        return anoLanzamiento;
    }

    public void setAnoLanzamiento(int anoLanzamiento) {
        this.anoLanzamiento = anoLanzamiento;
    }

    public String getNombreImagen() {
        return nombreImagen;
    }

    public void setNombreImagen(String nombreImagen) {
        this.nombreImagen = nombreImagen;
    }

    public String getDisquera() {
        return disquera;
    }

    public void setDisquera(String disquera) {
        this.disquera = disquera;
    }

    @XmlTransient
    public List<Cancion> getCancionList() {
        return cancionList;
    }

    public void setCancionList(List<Cancion> cancionList) {
        this.cancionList = cancionList;
    }

    @XmlTransient
    public List<Sube> getSubeList() {
        return subeList;
    }

    public void setSubeList(List<Sube> subeList) {
        this.subeList = subeList;
    }

    public Artista getArtistaidArtista() {
        return artistaidArtista;
    }

    public void setArtistaidArtista(Artista artistaidArtista) {
        this.artistaidArtista = artistaidArtista;
    }

    public Genero getGeneroidGenero() {
        return generoidGenero;
    }

    public void setGeneroidGenero(Genero generoidGenero) {
        this.generoidGenero = generoidGenero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAlbum != null ? idAlbum.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Album)) {
            return false;
        }
        Album other = (Album) object;
        if ((this.idAlbum == null && other.idAlbum != null) || (this.idAlbum != null && !this.idAlbum.equals(other.idAlbum))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Album[ idAlbum=" + idAlbum + " ]";
    }
    
}
