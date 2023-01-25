package com.mpm.springprojects.tienda.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mpm.springprojects.tienda.dao.ClientesDAO;
import com.mpm.springprojects.tienda.dao.DetallePedidoDAO;
import com.mpm.springprojects.tienda.dao.PedidosDAO;
import com.mpm.springprojects.tienda.model.Cliente;
import com.mpm.springprojects.tienda.model.DetallePedido;
import com.mpm.springprojects.tienda.model.DetallePedidoId;
import com.mpm.springprojects.tienda.model.Pedido;
import com.mpm.springprojects.tienda.repository.ClienteRepository;
import com.mpm.springprojects.tienda.repository.DetallePedidoRepository;
import com.mpm.springprojects.tienda.repository.PedidoRepository;
import com.mpm.springprojects.tienda.services.PedidosService;

@Service
public class PedidosServiceImpl implements PedidosService{

    @Autowired
    PedidoRepository pedidoRepository;
 
    @Autowired
    DetallePedidoRepository detallePedidoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public Page<Pedido> findAll(Pageable pageable) {
        return pedidoRepository.findAll(pageable);
    }

    /* 
    @Override
    public Pedido findById(int codigo) {
        Optional<Pedido> pedido = pedidoRepository.findById(codigo);

        Cliente cliente = clienteRepository.findById(pedido.getCliente().getCodigo());

        pedido.setCliente(cliente);

        List<DetallePedido> detalle = detallePedidoDAO.findDetalle(pedido);
        pedido.setDetallePedidos(detalle);
        
        return pedido;
    }*/
    @Override
    public Pedido findById(int codigo) {
        Optional<Pedido> findById = pedidoRepository.findById(codigo);
        if(findById != null){
            Pedido pedido = findById.get();
            pedido.setDetallePedidos(detallePedidoRepository.findByPedidoCodigo(pedido.getCodigo()));
            return pedido;
        }
        return null;
    }

    @Override
    public void insert(Pedido pedido) {
        
        pedidoRepository.save(pedido);
        
        List<DetallePedido> detallePedidos = pedido.getDetallePedidos();
        for (DetallePedido detallePedido : detallePedidos) {
            DetallePedidoId id = new DetallePedidoId(pedido.getCodigo(), )

        }
        
    }

    @Override
    public void update(Pedido pedido) {
        pedidoRepository.save(pedido);
     }

    @Override
    public void delete(int codigo) {
        pedidoRepository.deleteById(codigo);        
    }
}




 /* 
@Service
public class PedidosServiceImpl implements PedidosService{

    @Autowired
    PedidosDAO pedidosDAO;

    @Autowired
    DetallePedidoDAO detallePedidoDAO;

    @Autowired
    ClientesDAO clientesDAO;

    @Override
    public Page<Pedido> findAll(Pageable pageable) {
        return pedidosDAO.findAll(pageable);
    }

    @Override
    public Pedido findById(int codigo) {
        Pedido pedido = pedidosDAO.findById(codigo);

        Cliente cliente = clientesDAO.findById(pedido.getCliente().getCodigo());

        pedido.setCliente(cliente);

        List<DetallePedido> detalle = detallePedidoDAO.findDetalle(pedido);
        pedido.setDetallePedidos(detalle);
        
        return pedido;
    }

    @Override
    public void insert(Pedido pedido) {
        
        pedidosDAO.insert(pedido);

        List<DetallePedido> detallePedidos = pedido.getDetallePedidos();
        for (DetallePedido detallePedido : detallePedidos) {
            detallePedidoDAO.insert(pedido, detallePedido);
        }

    }

    @Override
    public void update(Pedido pedido) {
        pedidosDAO.update(pedido);
     }

    @Override
    public void delete(int codigo) {
        pedidosDAO.delete(codigo);        
    }
}

*/