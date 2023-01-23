package com.mpm.springprojects.tienda.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.mpm.springprojects.tienda.model.Cliente;
import com.mpm.springprojects.tienda.model.Pedido;

public class PedidoMapper implements RowMapper<Pedido>{
    @Override
    @Nullable
    public Pedido mapRow(ResultSet rs, int rowNum) throws SQLException {
        Pedido pedido = new Pedido();
        pedido.setCodigo(rs.getInt("codigo"));
        pedido.setCliente(new Cliente(rs.getInt("codigo_cliente")));
        pedido.setFecha(new java.util.Date(rs.getDate("fecha").getTime()));
        pedido.setTotal(rs.getDouble("total"));
        return pedido;
    }
}
