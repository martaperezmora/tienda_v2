package com.mpm.springprojects.tienda.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mpm.springprojects.tienda.model.Pedido;

public interface PedidosService {
    public Page<Pedido> findAll(Pageable page);
    public Pedido findById(int codigo);
    public void insert(Pedido pedido);
    public void update(Pedido producto);
    public void delete(int codigo);
}
