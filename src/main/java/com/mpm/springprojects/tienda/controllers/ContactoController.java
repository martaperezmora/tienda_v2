package com.mpm.springprojects.tienda.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mpm.springprojects.tienda.model.Contacto;
import com.mpm.springprojects.tienda.services.ContactoService;

@Controller
@RequestMapping("/contactos")
public class ContactoController {
    
    @Autowired
    ContactoService contactoService;

    @Value("${pagination.size}")
    int sizePage;


    @GetMapping(value = "/lista")
    public ModelAndView listPage(Model model) {

        List<Contacto> contactos = contactoService.findAll();

        ModelAndView modelAndView = new ModelAndView("contactos/list");
        modelAndView.addObject("contactos", contactos);

        return modelAndView;
    }

    @RequestMapping(value= {"/nuevo"})
    public ModelAndView nuevo(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("contactos/nuevo");

        return modelAndView;
    }

    @PostMapping(path = {"/guardar"})
    public ModelAndView guardar(Contacto contacto){
        ModelAndView modelAndView = new ModelAndView();
        contactoService.insert(contacto);
        modelAndView.setViewName("redirect:editar/" + contacto.getId());

        return modelAndView;
    }

    @GetMapping(path = {"/editar/{id}"})
    public ModelAndView editar(@PathVariable(name="id", required=true) int id){
        ModelAndView modelAndView = new ModelAndView();
        Contacto contacto = contactoService.findById(id);
        modelAndView.addObject("contacto", contacto);
        modelAndView.setViewName("contactos/editar");

        return modelAndView;
    }

    @PostMapping(path = {"/modificar"})
    public ModelAndView modificar(Contacto contacto){

        ModelAndView modelAndView = new ModelAndView();
        contactoService.update(contacto);
        modelAndView.setViewName("redirect:editar/" + contacto.getId());

        return modelAndView;
    }

    @GetMapping(path = {"/borrar/{id}"})
    public ModelAndView borrar(@PathVariable(name="id", required=true) int id){
        ModelAndView modelAndView = new ModelAndView();

        contactoService.delete(id);
        modelAndView.setViewName("redirect:../lista");
        
        return modelAndView;
    }

}
