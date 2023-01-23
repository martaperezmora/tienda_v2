package com.mpm.springprojects.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpm.springprojects.tienda.model.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer>{
    
}
