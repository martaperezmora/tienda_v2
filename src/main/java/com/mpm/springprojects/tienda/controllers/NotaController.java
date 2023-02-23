package com.mpm.springprojects.tienda.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.mpm.springprojects.tienda.model.Nota;
import com.mpm.springprojects.tienda.services.NotaService;

@Controller
@RequestMapping("/notas")
public class NotaController {
    
    @Autowired
    NotaService notaService;

    @Value("${pagination.size}")
    int sizePage;


    @GetMapping(value = "/lista")
    public ModelAndView listPage(Model model) {

        List<Nota> notas = notaService.findAll();

        ModelAndView modelAndView = new ModelAndView("notas/list");
        modelAndView.addObject("notas", notas);

        return modelAndView;
    }
/* 
    @PostMapping(value = "/busqueda")
    public ModelAndView busqueda(Nota nota) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
        Date fechaDate = formato.parse(nota.getFecha()); 

        List<Nota> notas = notaService.busqueda(nota.getTitulo(), nota.getFecha());

        ModelAndView modelAndView = new ModelAndView("notas/busqueda");
        modelAndView.addObject("notas", notas);

        return modelAndView;
    }*/

    @RequestMapping(value= {"/nuevo"})
    public ModelAndView nuevo(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("notas/nuevo");

        return modelAndView;
    }

    @PostMapping(path = {"/guardar"})
    public ModelAndView guardar(Nota nota){
        ModelAndView modelAndView = new ModelAndView();
        notaService.insert(nota);
        modelAndView.setViewName("redirect:editar/" + nota.getCodigo());

        return modelAndView;
    }

    @GetMapping(path = {"/editar/{codigo}"})
    public ModelAndView editar(@PathVariable(name="codigo", required=true) int codigo){
        ModelAndView modelAndView = new ModelAndView();
        Nota nota = notaService.findById(codigo);
        modelAndView.addObject("nota", nota);
        modelAndView.setViewName("notas/editar");

        return modelAndView;
    }

    @PostMapping(path = {"/modificar"})
    public ModelAndView modificar(Nota nota){

        ModelAndView modelAndView = new ModelAndView();
        notaService.update(nota);
        modelAndView.setViewName("redirect:editar/" + nota.getCodigo());

        return modelAndView;
    }

    @GetMapping(path = {"/borrar/{codigo}"})
    public ModelAndView borrar(@PathVariable(name="codigo", required=true) int codigo){
        ModelAndView modelAndView = new ModelAndView();

        notaService.delete(codigo);
        modelAndView.setViewName("redirect:../lista");
        
        return modelAndView;
    }

}
