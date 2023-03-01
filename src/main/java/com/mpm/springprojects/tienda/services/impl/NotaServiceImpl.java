package com.mpm.springprojects.tienda.services.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mpm.springprojects.tienda.model.Nota;
import com.mpm.springprojects.tienda.services.NotaService;

@Service
public class NotaServiceImpl implements NotaService{

    @Value("${url.notas.rest.service}")
    String urlNotas;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public Nota findById(int codigo) {
        return restTemplate.getForObject(urlNotas + "notas/{codigo}", Nota.class, codigo);
    }

    @Override
    public List<Nota> findAll() {
        Nota[] ca = restTemplate.getForObject(urlNotas + "notas", Nota[].class); // array json de notas
        List<Nota> notas = Arrays.asList(ca);

        return notas;
    }

    @Override
    public void insert(Nota nota) {
        Nota notaResponse = restTemplate.postForObject(urlNotas + "notas", nota, Nota.class);
        nota.setCodigo(notaResponse.getCodigo());
    }

    @Override
    public void update(Nota nota) {
        restTemplate.put(urlNotas + "notas/{codigo}", nota, nota.getCodigo());
    }

    @Override
    public void delete(int codigo) {
        restTemplate.delete(urlNotas + "notas/{codigo}", codigo);
    }

    @Override
    public List<Nota> busqueda(String titulo, String fecha) {
        Nota[] listNota = restTemplate.getForObject(urlNotas + "notas/buscar?titulo=" + titulo + "&fecha=" + fecha, Nota[].class);
        List<Nota> notas = Arrays.asList(listNota);
        return notas;
    }
    
}
