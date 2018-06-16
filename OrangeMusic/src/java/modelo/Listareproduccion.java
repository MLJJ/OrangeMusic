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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author enriq
 */
@Entity
@Table(name = "listareproduccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Listareproduccion.findAll", query = "SELECT l FROM Listareproduccion l")
    , @NamedQuery(name = "Listareproduccion.findByIdListaReproduccion", query = "SELECT l FROM Listareproduccion l WHERE l.idListaReproduccion = :idListaReproduccion")
    , @NamedQuery(name = "Listareproduccion.findByNombreLista", query = "SELECT l FROM Listareproduccion l WHERE l.nombreLista = :nombreLista")
    , @NamedQuery(name = "Listareproduccion.findByVisibilidad", query = "SELECT l FROM Listareproduccion l WHERE l.visibilidad = :visibilidad")})
public class Listareproduccion implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idListaReproduccion")
    private Integer idListaReproduccion;
    @Basic(optional = false)
    @Column(name = "nombreLista")
    private String nombreLista;
    @Basic(optional = false)
    @Column(name = "visibilidad")
    private String visibilidad;
    @ManyToMany(mappedBy = "listareproduccionList")
    private List<Cancion> cancionList;
    @JoinColumn(name = "correoUsuario", referencedColumnName = "correo")
    @ManyToOne(optional = false)
    private Usuario correoUsuario;

    public Listareproduccion() {
    }

    public Listareproduccion(Integer idListaReproduccion) {
        this.idListaReproduccion = idListaReproduccion;
    }

    public Listareproduccion(Integer idListaReproduccion, String nombreLista, String visibilidad) {
        this.idListaReproduccion = idListaReproduccion;
        this.nombreLista = nombreLista;
        this.visibilidad = visibilidad;
    }

    public Integer getIdListaReproduccion() {
        return idListaReproduccion;
    }

    public void setIdListaReproduccion(Integer idListaReproduccion) {
        this.idListaReproduccion = idListaReproduccion;
    }

    public String getNombreLista() {
        return nombreLista;
    }

    public void setNombreLista(String nombreLista) {
        this.nombreLista = nombreLista;
    }

    public String getVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(String visibilidad) {
        this.visibilidad = visibilidad;
    }

    public List<Cancion> getCancionList() {
        return cancionList;
    }

    public void setCancionList(List<Cancion> cancionList) {
        this.cancionList = cancionList;
    }

    public Usuario getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(Usuario correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idListaReproduccion != null ? idListaReproduccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Listareproduccion)) {
            return false;
        }
        Listareproduccion other = (Listareproduccion) object;
        if ((this.idListaReproduccion == null && other.idListaReproduccion != null) || (this.idListaReproduccion != null && !this.idListaReproduccion.equals(other.idListaReproduccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Listareproduccion[ idListaReproduccion=" + idListaReproduccion + " ]";
    }
    
}
