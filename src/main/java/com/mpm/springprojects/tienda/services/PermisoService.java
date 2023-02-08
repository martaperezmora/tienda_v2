package com.mpm.springprojects.tienda.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpm.springprojects.tienda.model.Permiso;
import com.mpm.springprojects.tienda.repository.PermisoRepository;


@Service
public class PermisoService {

    @Autowired
    private PermisoRepository permisoRepository;

    public Permiso createPermiso(Permiso permiso) {
        return permisoRepository.save(permiso);
    }

    public Permiso updatePermiso(Permiso permiso) {
        return permisoRepository.save(permiso);
    }

    public void deletePermiso(Long permisoId) {
        permisoRepository.deleteById(permisoId);
    }

    public Permiso getPermiso(Long permisoId) {
        return permisoRepository.findById(permisoId).orElse(null);
    }

    public List<Permiso> getAllPermisos() {
        return permisoRepository.findAll();
    }
}
