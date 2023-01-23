package com.mpm.springprojects.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpm.springprojects.tienda.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{
    
} 