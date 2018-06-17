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
 * @author arkadwn
 */
@Entity
@Table(name = "sube")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sube.findAll", query = "SELECT s FROM Sube s")
    , @NamedQuery(name = "Sube.findByPrivacidad", query = "SELECT s FROM Sube s WHERE s.privacidad = :privacidad")
    , @NamedQuery(name = "Sube.findByIdSube", query = "SELECT s FROM Sube s WHERE s.idSube = :idSube")})
public class Sube implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "privacidad")
    private short privacidad;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSube")
    private Integer idSube;
    @JoinColumn(name = "album", referencedColumnName = "idAlbum")
    @ManyToOne(optional = false)
    private Album album;
    @JoinColumn(name = "usuario", referencedColumnName = "correo")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Sube() {
    }

    public Sube(Integer idSube) {
        this.idSube = idSube;
    }

    public Sube(Integer idSube, short privacidad) {
        this.idSube = idSube;
        this.privacidad = privacidad;
    }

    public short getPrivacidad() {
        return privacidad;
    }

    public void setPrivacidad(short privacidad) {
        this.privacidad = privacidad;
    }

    public Integer getIdSube() {
        return idSube;
    }

    public void setIdSube(Integer idSube) {
        this.idSube = idSube;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSube != null ? idSube.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sube)) {
            return false;
        }
        Sube other = (Sube) object;
        if ((this.idSube == null && other.idSube != null) || (this.idSube != null && !this.idSube.equals(other.idSube))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Sube[ idSube=" + idSube + " ]";
    }
    
}
