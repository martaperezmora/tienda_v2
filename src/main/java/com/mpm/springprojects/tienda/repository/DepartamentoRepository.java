package com.mpm.springprojects.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpm.springprojects.tienda.model.Departamento;

public interface DepartamentoRepository extends JpaRepository<Departamento, Integer>{
    
}
