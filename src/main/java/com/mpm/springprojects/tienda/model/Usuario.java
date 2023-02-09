package com.mpm.springprojects.tienda.model;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long codigo;

    private String nombre;

    private String email;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER) // para que traiga las dependencias
    @JoinTable(name = "usuario_permiso", joinColumns = @JoinColumn(name = "usuario_codigo"), inverseJoinColumns = @JoinColumn(name = "permiso_codigo"))
    private List<Permiso> permisos;

    public Usuario() {
    }

    
    public Usuario(long codigo) {
        this.codigo = codigo;
    }

    public Usuario(long codigo, String nombre, String email, String password, List<Permiso> permisos) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.permisos = permisos;
    }


    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // public List<Group> getGroups() {
    // return groups;
    // }

    // public void setGroups(List<Group> groups) {
    // this.groups = groups;
    // }

    public List<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (codigo ^ (codigo >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Usuario other = (Usuario) obj;
        if (codigo != other.codigo)
            return false;
        return true;
    }
}