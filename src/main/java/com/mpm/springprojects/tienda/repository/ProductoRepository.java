package com.mpm.springprojects.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpm.springprojects.tienda.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer>{

}
