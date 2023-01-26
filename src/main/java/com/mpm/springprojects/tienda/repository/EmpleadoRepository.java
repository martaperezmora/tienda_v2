package com.mpm.springprojects.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpm.springprojects.tienda.model.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer>{
    
}
