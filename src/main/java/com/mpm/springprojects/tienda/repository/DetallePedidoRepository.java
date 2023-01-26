package com.mpm.springprojects.tienda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpm.springprojects.tienda.model.DetallePedido;
import com.mpm.springprojects.tienda.model.DetallePedidoId;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, DetallePedidoId>{
    public void deleteByPedidoCodigo(int codigo_pedido);
    public List<DetallePedido> findByPedidoCodigo(int codigo_pedido);
}
