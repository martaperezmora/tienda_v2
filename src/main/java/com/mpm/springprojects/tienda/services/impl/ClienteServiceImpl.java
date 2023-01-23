package com.mpm.springprojects.tienda.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mpm.springprojects.tienda.dao.ClientesDAO;
import com.mpm.springprojects.tienda.model.Cliente;
import com.mpm.springprojects.tienda.repository.ClienteRepository;
import com.mpm.springprojects.tienda.services.ClientesService;

// código para usar el repository
@Service
public class ClienteServiceImpl implements ClientesService {

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public Page<Cliente> findAll(Pageable page) {
        return clienteRepository.findAll(page);
    }

    @Override
    public Cliente findById(int codigo) {
        Optional<Cliente> findById = clienteRepository.findById(codigo);
        if(findById != null){
            return findById.get();
        }
        return null;
    }

    @Override
    public void insert(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    @Override
    public void update(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    @Override
    public void delete(int codigo) {
        clienteRepository.deleteById(codigo);
    }

}

/*
 * código para utilizar el DAO
 
 @Service
 public class ClienteServiceImpl implements ClientesService {

    @Autowired
    ClientesDAO clientesDAO;

    @Override
    public Page<Cliente> findAll(Pageable page) {
        return clientesDAO.findAll(page);
    }

    @Override
    public Cliente findById(int codigo) {
        return clientesDAO.findById(codigo);
    }

    @Override
    public void insert(Cliente cliente) {
        clientesDAO.insert(cliente);
    }

    @Override
    public void update(Cliente cliente) {
        clientesDAO.update(cliente);
    }

    @Override
    public void delete(int codigo) {
        clientesDAO.delete(codigo);
    }
}

*/