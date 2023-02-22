package com.mpm.springprojects.tienda.services;

import java.util.List;

import com.mpm.springprojects.tienda.model.Nota;

public interface NotaService {

    public Nota findById(int id);

    public List<Nota> findAll();

    public void insert(Nota nota);

    public void update(Nota nota);

    public void delete(int id);
}
