package com.mpm.springprojects.tienda.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mpm.springprojects.tienda.model.Departamento;

public interface DepartamentoService {
    public Page<Departamento> findAll(Pageable page);

    public Departamento findById(int codigo);

    public void insert(Departamento departamento);

    public void update(Departamento departamento);

    public void delete(int codigo);
}
