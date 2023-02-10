package com.mpm.springprojects.tienda.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mpm.springprojects.tienda.model.Vendedor;

@Controller
@Secured({"admin"})
@RequestMapping("/vendedores")
public class VendedorController {
    
    @RequestMapping(value= {"/lista"})
    public ModelAndView lista(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("vendedores", getVendedores());
        modelAndView.setViewName("vendedores/lista");

        return modelAndView;
    }

    @RequestMapping(value= {"/nuevo"})
    public ModelAndView nuevo(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("vendedores/nuevo");

        return modelAndView;
    }

    @PostMapping(path = {"/guardar"})
    public ModelAndView guardar(Vendedor vendedor){
        ModelAndView modelAndView = new ModelAndView();
        
        modelAndView.addObject("vendedores", addVendedor(vendedor));
        modelAndView.setViewName("vendedores/lista");

        return modelAndView;
    }

    @GetMapping(path = {"/editar/{codigo}"})
    public ModelAndView editar(@PathVariable(name="codigo", required=true) int codigo){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("vendedor", getVendedor(codigo));
        modelAndView.setViewName("vendedores/editar");

        return modelAndView;

    }

    @PostMapping(path = {"/modificar"})
    public ModelAndView modificar(Vendedor vendedor){
        ModelAndView modelAndView = new ModelAndView();

        List<Vendedor> vendedores = getVendedores();
        int indexOf = vendedores.indexOf(vendedor);
        vendedores.set(indexOf, vendedor);

        modelAndView.addObject("vendedores", vendedores);
        modelAndView.setViewName("vendedores/lista");

        return modelAndView;
    }

    @GetMapping(path = {"/borrar/{codigo}"})
    public ModelAndView borrar(@PathVariable(name="codigo", required=true) int codigo){
        ModelAndView modelAndView = new ModelAndView();
        List<Vendedor> vendedores = getVendedores();
        int indexOf = vendedores.indexOf(new Vendedor(codigo));
        vendedores.remove(indexOf);
        modelAndView.addObject("vendedores", vendedores);
        modelAndView.setViewName("vendedores/lista");
        
        return modelAndView;
    }

    private List<Vendedor> addVendedor(Vendedor vendedor){
        List<Vendedor> vendedores = getVendedores();
        vendedores.add(vendedor);
        return vendedores;
    }

    private Vendedor getVendedor(int codigo){
        List<Vendedor> Vendedores = getVendedores();
        int indexOf = Vendedores.indexOf(new Vendedor(codigo));
        
        return Vendedores.get(indexOf);
    }

    private List<Vendedor> getVendedores() {
        ArrayList<Vendedor> Vendedores = new ArrayList<Vendedor>();
        Vendedores.add(new Vendedor(1, "nombre1", "apellidos1", "puesto1"));
        Vendedores.add(new Vendedor(2, "nombre2", "apellidos2", "puesto2"));

        return Vendedores;
    }

}
