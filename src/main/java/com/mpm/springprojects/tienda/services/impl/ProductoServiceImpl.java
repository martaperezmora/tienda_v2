package com.mpm.springprojects.tienda.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mpm.springprojects.tienda.dao.ProductosDAO;
import com.mpm.springprojects.tienda.model.Producto;
import com.mpm.springprojects.tienda.repository.ProductoRepository;
import com.mpm.springprojects.tienda.services.ProductosService;

@Service
public class ProductoServiceImpl implements ProductosService {

    @Autowired
    ProductoRepository productoRepository;

    @Override
    public Page<Producto> findAll(Pageable page) {
        return productoRepository.findAll(page);
    }

    @Override
    public Producto findById(int codigo) {
        Optional<Producto> findById = productoRepository.findById(codigo);
        if(findById != null){
            return findById.get();
        }
        return null;
    }

    @Override
    public void insert(Producto Producto) {
        productoRepository.save(Producto);
    }

    @Override
    public void update(Producto producto) {

        if(producto.getImagen() == null){
            Producto productoBd = findById(producto.getCodigo());
            if(productoBd.getImagen() != null){
                producto.setImagen(productoBd.getImagen());
            }
        }
        productoRepository.save(producto);
    }

    @Override
    public void delete(int codigo) {
        productoRepository.deleteById(codigo);
    }

}


/* 
codigo para usar el dao

@Service
public class ProductoServiceImpl implements ProductosService {

    @Autowired
    ProductosDAO productosDAO;

    @Override
    public Page<Producto> findAll(Pageable page) {
        return productosDAO.findAll(page);
    }

    @Override
    public Producto findById(int codigo) {
        return productosDAO.findById(codigo);
    }

    @Override
    public void insert(Producto Producto) {
        productosDAO.insert(Producto);
    }

    @Override
    public void update(Producto producto) {
        productosDAO.update(producto);

        if (producto.getImagen() != null && producto.getImagen().length > 0) {
            productosDAO.updateImage(producto);
        }
    }

    @Override
    public void delete(int codigo) {
        productosDAO.delete(codigo);
    }

}
*/
