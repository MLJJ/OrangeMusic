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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 7/06/2018
 * @time 12:31:18 AM
 */
@Entity
@Table(name = "version")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Version.findAll", query = "SELECT v FROM Version v")
    , @NamedQuery(name = "Version.findByIdVersion", query = "SELECT v FROM Version v WHERE v.idVersion = :idVersion")
    , @NamedQuery(name = "Version.findByNombreArchivo", query = "SELECT v FROM Version v WHERE v.nombreArchivo = :nombreArchivo")
    , @NamedQuery(name = "Version.findByCalidad", query = "SELECT v FROM Version v WHERE v.calidad = :calidad")})
public class Version implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idVersion")
    private Integer idVersion;
    @Basic(optional = false)
    @Column(name = "nombreArchivo")
    private String nombreArchivo;
    @Basic(optional = false)
    @Column(name = "calidad")
    private String calidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "version")
    private List<Cancion> cancionList;

    public Version() {
    }

    public Version(Integer idVersion) {
        this.idVersion = idVersion;
    }

    public Version(Integer idVersion, String nombreArchivo, String calidad) {
        this.idVersion = idVersion;
        this.nombreArchivo = nombreArchivo;
        this.calidad = calidad;
    }

    public Integer getIdVersion() {
        return idVersion;
    }

    public void setIdVersion(Integer idVersion) {
        this.idVersion = idVersion;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getCalidad() {
        return calidad;
    }

    public void setCalidad(String calidad) {
        this.calidad = calidad;
    }

    @XmlTransient
    public List<Cancion> getCancionList() {
        return cancionList;
    }

    public void setCancionList(List<Cancion> cancionList) {
        this.cancionList = cancionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVersion != null ? idVersion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Version)) {
            return false;
        }
        Version other = (Version) object;
        if ((this.idVersion == null && other.idVersion != null) || (this.idVersion != null && !this.idVersion.equals(other.idVersion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Version[ idVersion=" + idVersion + " ]";
    }

}
