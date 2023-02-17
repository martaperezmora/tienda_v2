package com.mpm.springprojects.tienda.services;

import java.util.List;

import com.mpm.springprojects.tienda.model.Contacto;

public interface ContactoService {

    public Contacto findById(int id);

    public List<Contacto> findAll();

    public void insert(Contacto contacto);

    public void update(Contacto contacto);

    public void delete(int id);
}
