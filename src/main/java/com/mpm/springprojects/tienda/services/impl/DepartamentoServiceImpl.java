package com.mpm.springprojects.tienda.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mpm.springprojects.tienda.model.Departamento;
import com.mpm.springprojects.tienda.repository.DepartamentoRepository;
import com.mpm.springprojects.tienda.services.DepartamentoService;

@Service
public class DepartamentoServiceImpl implements DepartamentoService{
    
    @Autowired
    DepartamentoRepository departamentoRepository;

    @Override
    public Page<Departamento> findAll(Pageable page) {
        return departamentoRepository.findAll(page);
    }

    @Override
    public Departamento findById(int codigo) {
        Optional<Departamento> findById = departamentoRepository.findById(codigo);
        if(findById != null){
            return findById.get();
        }
        return null;
    }

    @Override
    public void insert(Departamento departamento) {
        departamentoRepository.save(departamento);
    }

    @Override
    public void update(Departamento departamento) {
        departamentoRepository.save(departamento);
    }

    @Override
    public void delete(int codigo) {
        departamentoRepository.deleteById(codigo);
    }
}
