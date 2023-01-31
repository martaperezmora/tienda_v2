package com.mpm.springprojects.tienda.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mpm.springprojects.tienda.model.Empleado;
import com.mpm.springprojects.tienda.repository.EmpleadoRepository;
import com.mpm.springprojects.tienda.services.EmpleadoService;


@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    EmpleadoRepository empleadoRepository;

    @Override
    public Page<Empleado> findAll(Pageable page) {
        return empleadoRepository.findAll(page);
    }

    @Override
    public List<Empleado> findAll() {
        return empleadoRepository.findAll();
    }

    @Override
    public Empleado findById(int codigo) {
        Optional<Empleado> findById = empleadoRepository.findById(codigo);
        if(findById != null){
            return findById.get();
        }
        return null;
    }

    @Override
    public void insert(Empleado empleado) {
        empleadoRepository.save(empleado);
    }

    @Override
    public void update(Empleado empleado) {
        empleadoRepository.save(empleado);
    }

    @Override
    public void delete(int codigo) {
        empleadoRepository.deleteById(codigo);
    }

}