package com.mpm.springprojects.tienda.services.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mpm.springprojects.tienda.model.Contacto;
import com.mpm.springprojects.tienda.services.ContactoService;

@Service
public class ContactoServiceImpl implements ContactoService{

    @Value("${url.agenda.rest.service}")
    String urlAgenda;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public Contacto findById(int id) {
        return restTemplate.getForObject(urlAgenda + "contactos/{id}", Contacto.class, id);
    }

    @Override
    public List<Contacto> findAll() {
        Contacto[] ca = restTemplate.getForObject(urlAgenda + "contactos", Contacto[].class); // array json de contactos
        List<Contacto> contactos = Arrays.asList(ca);

        return contactos;
    }

    @Override
    public void insert(Contacto contacto) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update(Contacto contacto) {
        //restTemplate.put(urlAgenda + "contactos/{id}", Contacto.class, contacto.getId());
        restTemplate.put(urlAgenda + "contactos/{id}", contacto, contacto.getId());
    }

    @Override
    public void delete(int id) {
        restTemplate.delete(urlAgenda + "contactos/{id}", id);
    }
    
}
