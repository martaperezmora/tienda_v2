package com.mpm.springprojects.tienda.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mpm.springprojects.tienda.model.Empleado;

public interface EmpleadoService {
    public Page<Empleado> findAll(Pageable page);

    public Empleado findById(int codigo);

    public void insert(Empleado empleado);

    public void update(Empleado empleado);

    public void delete(int codigo);
}
