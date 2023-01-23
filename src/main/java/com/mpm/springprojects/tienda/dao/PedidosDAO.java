package com.mpm.springprojects.tienda.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mpm.springprojects.tienda.model.Pedido;

public interface PedidosDAO {
    public Page<Pedido> findAll(Pageable page);
    public Pedido findById(int codigo);
    public void insert(Pedido Pedido);
    public void update(Pedido Pedido);
    public void delete(int codigo);
}
