package com.mpm.springprojects.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpm.springprojects.tienda.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
    
}
