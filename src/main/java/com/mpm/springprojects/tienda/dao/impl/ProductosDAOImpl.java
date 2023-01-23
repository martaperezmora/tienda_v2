package com.mpm.springprojects.tienda.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.mpm.springprojects.tienda.dao.ProductosDAO;
import com.mpm.springprojects.tienda.model.Producto;

@Repository
public class ProductosDAOImpl extends JdbcDaoSupport implements ProductosDAO {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    /*
     * @Override
     * public List<Producto> findAll() {
     * String query = "select * from Productos";
     * List<Producto> productos = getJdbcTemplate().query(query, new
     * BeanPropertyRowMapper(Producto.class));
     * return productos;
     * }
     */

    @Override
    public Page<Producto> findAll(Pageable page) {

        String queryCount = "select count(1) from Productos";
        Integer total = getJdbcTemplate().queryForObject(queryCount, Integer.class);

        Order order = !page.getSort().isEmpty() ? page.getSort().toList().get(0) : Order.by("codigo");

        String query = "SELECT * FROM Productos ORDER BY " + order.getProperty() + " "
                + order.getDirection().name() + " LIMIT " + page.getPageSize() + " OFFSET " + page.getOffset();

        final List<Producto> productos = getJdbcTemplate().query(query, new RowMapper<Producto>() {

            @Override
            @Nullable
            public Producto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Producto producto = new Producto();
                producto.setCodigo(rs.getInt("codigo"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getFloat("precio"));
                producto.setImagen(rs.getBytes("imagen"));

                return producto;
            }

        });

        return new PageImpl<Producto>(productos, page, total);
    }

    @Override
    public Producto findById(int codigo) {
        String query = "select * from Productos where codigo = ?";
        Object params[] = { codigo };
        int types[] = { Types.INTEGER };
        Producto producto = (Producto) getJdbcTemplate().queryForObject(query, params, types,
                new BeanPropertyRowMapper(Producto.class));
        return producto;
    }
    /*
     * @Override
     * public void insert(Producto producto){
     * String query =
     * "insert into Productos (nombre,descripcion,precio,imagen) values (?,?,?,?)";
     * 
     * Object[] params = {
     * producto.getNombre(),
     * producto.getDescripcion(),
     * producto.getPrecio(),
     * producto.getImagen()
     * };
     * 
     * int[] types = {
     * Types.VARCHAR,
     * Types.VARCHAR,
     * Types.FLOAT,
     * Types.BLOB
     * };
     * 
     * int update = getJdbcTemplate().update(query, params, types);
     * }
     */

    @Override
    public void insert(Producto producto) {

        String query = "insert into Productos (nombre," +
                " descripcion," +
                " precio," +
                " imagen)" +
                " values (?, ?, ?, ?)";
        // Object[] params = {
        // producto.getNombre(),
        // producto.getDescripcion(),
        // producto.getPrecio(),
        // producto.getImage()
        // };

        // final int[] types = {
        // Types.VARCHAR,
        // Types.VARCHAR,
        // Types.FLOAT,
        // Types.BLOB
        // };

        // int update = getJdbcTemplate().update(query, params, types);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        getJdbcTemplate().update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, producto.getNombre());
                ps.setString(2, producto.getDescripcion());
                ps.setFloat(3, producto.getPrecio());
                InputStream is = new ByteArrayInputStream(producto.getImagen());

                ps.setBlob(4, is);
                return ps;
            }
        }, keyHolder);

        producto.setCodigo(keyHolder.getKey().intValue());
    }

    @Override
    public void update(Producto producto) {
        String query = "update Productos set nombre = ?, descripcion = ?, precio = ? where codigo = ?";

        Object[] params = {
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getCodigo()
        };

        int[] types = {
                Types.VARCHAR,
                Types.VARCHAR,
                Types.FLOAT,
                Types.INTEGER
        };

        int update = getJdbcTemplate().update(query, params, types);

    }

    @Override
    public void delete(int codigo) {
        String query = "delete from Productos where codigo = ?";

        Object params[] = { codigo };
        int types[] = { Types.INTEGER };

        int update = getJdbcTemplate().update(query, params, types);
    }

    @Override
    public void updateImage(Producto producto) {
        String query = "update Productos set imagen = ? where codigo = ?";

        Object[] params = {
                producto.getImagen(),
                producto.getCodigo()
        };

        int[] types = {
                Types.BLOB,
                Types.INTEGER
        };

        int update = getJdbcTemplate().update(query, params, types);

    }

}
