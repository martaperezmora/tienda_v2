package com.mpm.springprojects.tienda.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.mpm.springprojects.tienda.dao.PedidosDAO;
import com.mpm.springprojects.tienda.model.Cliente;
import com.mpm.springprojects.tienda.model.Pedido;

@Repository
public class PedidosDAOImpl extends JdbcDaoSupport implements PedidosDAO {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public Page<Pedido> findAll(Pageable page) {
        String queryCount = "select count(1) from pedidos";
        Integer total = getJdbcTemplate().queryForObject(queryCount, Integer.class);

        Order order = !page.getSort().isEmpty() ? page.getSort().toList().get(0) : Order.by("codigo");

        String query = "SELECT p.*, c.nombre FROM pedidos p, Clientes c where p.codigo_cliente = c.codigo ORDER BY "
                + order.getProperty() + " "
                + order.getDirection().name() + " LIMIT " + page.getPageSize() + " OFFSET " + page.getOffset();

        final List<Pedido> pedidos = getJdbcTemplate().query(query, new RowMapper<Pedido>() {

            @Override
            @Nullable
            public Pedido mapRow(ResultSet rs, int rowNum) throws SQLException {
                Pedido pedido = new Pedido();
                pedido.setCodigo(rs.getInt("codigo"));
                pedido.setCliente(new Cliente(rs.getInt("codigo_cliente")));
                pedido.getCliente().setNombre(rs.getString("nombre"));
                pedido.setFecha(new java.util.Date(rs.getDate("fecha").getTime()));
                pedido.setTotal(rs.getDouble("total"));
                return pedido;
            }

        });

        return new PageImpl<Pedido>(pedidos, page, total);
    }

    @Override
    public Pedido findById(int codigo) {
        String query = "select p.*, c.nombre from pedidos p, Clientes c where p.codigo = ?";
        Object params[] = { codigo };
        int types[] = { Types.INTEGER };
        Pedido pedido = (Pedido) getJdbcTemplate().queryForObject(query, params, types, new RowMapper<Pedido>() {
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

        });

        return pedido;
    }

    @Override
    public void insert(Pedido pedido) {
        String query = "insert into pedidos (codigo_cliente,total,fecha) values (?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        getJdbcTemplate().update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

                ps.setInt(1, pedido.getCliente().getCodigo());
                ps.setDouble(2, pedido.getTotal());
                java.util.Date fecha = new Date();
                ps.setDate(3, new java.sql.Date(fecha.getTime()));

                return ps;
            }
        }, keyHolder);

        pedido.setCodigo(keyHolder.getKey().intValue());

    }

    @Override
    public void update(Pedido pedido) {
        String query = "update pedidos set codigo_cliente = ?, total = ?, fecha = ? where codigo = ?";

        Date fecha = new java.sql.Date(pedido.getFecha().getTime());
        Object[] params = {
                pedido.getCliente().getCodigo(),
                pedido.getTotal(),
                fecha,
                pedido.getFecha(),
                pedido.getCodigo(),
        };

        int[] types = {
                Types.INTEGER,
                Types.DOUBLE,
                Types.DATE,
                Types.INTEGER,
        };

        int update = getJdbcTemplate().update(query, params, types);

    }

    @Override
    public void delete(int codigo) {
        String query = "delete from pedidos where codigo = ?";
        String queryDetalle = "delete from detalle_pedido where codigo_pedido = ?";

        Object params[] = { codigo };
        int types[] = { Types.INTEGER };

        
        int updateDetalle = getJdbcTemplate().update(queryDetalle, params, types);
        int update = getJdbcTemplate().update(query, params, types);
    }

}