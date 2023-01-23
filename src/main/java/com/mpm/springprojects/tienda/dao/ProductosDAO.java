package com.mpm.springprojects.tienda.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mpm.springprojects.tienda.model.Producto;

public interface ProductosDAO {
    
    public Page<Producto> findAll(Pageable page);
    public Producto findById(int codigo);
    public void insert(Producto Producto);
    public void update(Producto Producto);
    public void delete(int codigo);
    public void updateImage(Producto producto);

}
