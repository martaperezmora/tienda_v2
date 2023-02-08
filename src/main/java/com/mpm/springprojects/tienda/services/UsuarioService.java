package com.mpm.springprojects.tienda.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mpm.springprojects.tienda.model.Permiso;
import com.mpm.springprojects.tienda.model.Usuario;
import com.mpm.springprojects.tienda.repository.UsuarioRepository;


@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario createUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deleteUsuario(Long usuarioId) {
        usuarioRepository.deleteById(usuarioId);
    }

    public Usuario getUsuario(Long usuarioId) {
        return usuarioRepository.findById(usuarioId).orElse(null);
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<GrantedAuthority> permisosAut = new ArrayList<GrantedAuthority>();
        Usuario usuarioEncontrado = usuarioRepository.findByName(username);
        List<Permiso> permisos = usuarioEncontrado.getPermisos();

        for (Permiso permiso : permisos) {
            permisosAut.add(new SimpleGrantedAuthority(permiso.getNombre()));
        }

        UserDetails user = org.springframework.security.core.userdetails.User.builder()
                .username(usuarioEncontrado.getNombre())
                .password(usuarioEncontrado.getPassword())
                .authorities(permisosAut)
                .build();

        return user;
    }
}
