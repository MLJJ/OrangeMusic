/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 15/06/2018
 * @time 10:44:32 PM
 */
@Entity
@Table(name = "cancion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cancion.findAll", query = "SELECT c FROM Cancion c")
    , @NamedQuery(name = "Cancion.findByIdCancion", query = "SELECT c FROM Cancion c WHERE c.idCancion = :idCancion")
    , @NamedQuery(name = "Cancion.findByNombreCancion", query = "SELECT c FROM Cancion c WHERE c.nombreCancion = :nombreCancion")
    , @NamedQuery(name = "Cancion.findByRutaCancion", query = "SELECT c FROM Cancion c WHERE c.rutaCancion = :rutaCancion")})
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
    @Basic(optional = false)
    @Column(name = "rutaCancion")
    private String rutaCancion;
    @JoinTable(name = "listatienecancion", joinColumns = {
        @JoinColumn(name = "idCancion", referencedColumnName = "idCancion")}, inverseJoinColumns = {
        @JoinColumn(name = "idListaReproduccion", referencedColumnName = "idListaReproduccion")})
    @ManyToMany
    private List<Listareproduccion> listareproduccionList;
    @JoinColumn(name = "Album_idAlbum", referencedColumnName = "idAlbum")
    @ManyToOne(optional = false)
    private Album albumidAlbum;

    public Cancion() {
    }

    public Cancion(Integer idCancion) {
        this.idCancion = idCancion;
    }

    public Cancion(Integer idCancion, String nombreCancion, String rutaCancion) {
        this.idCancion = idCancion;
        this.nombreCancion = nombreCancion;
        this.rutaCancion = rutaCancion;
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

    public String getRutaCancion() {
        return rutaCancion;
    }

    public void setRutaCancion(String rutaCancion) {
        this.rutaCancion = rutaCancion;
    }

    @XmlTransient
    public List<Listareproduccion> getListareproduccionList() {
        return listareproduccionList;
    }

    public void setListareproduccionList(List<Listareproduccion> listareproduccionList) {
        this.listareproduccionList = listareproduccionList;
    }

    public Album getAlbumidAlbum() {
        return albumidAlbum;
    }

    public void setAlbumidAlbum(Album albumidAlbum) {
        this.albumidAlbum = albumidAlbum;
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
