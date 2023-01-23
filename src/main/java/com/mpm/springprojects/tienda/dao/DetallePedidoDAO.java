package com.mpm.springprojects.tienda.dao;

import java.util.List;

import com.mpm.springprojects.tienda.model.DetallePedido;
import com.mpm.springprojects.tienda.model.Pedido;

public interface DetallePedidoDAO {
    
    public void insert(Pedido pedido, DetallePedido detallePedido);

    public List<DetallePedido> findDetalle(Pedido pedido);

}