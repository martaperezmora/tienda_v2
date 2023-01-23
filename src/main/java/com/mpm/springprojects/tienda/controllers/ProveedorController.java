package com.mpm.springprojects.tienda.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mpm.springprojects.tienda.model.Proveedor;
import com.mpm.springprojects.tienda.services.ProveedoresService;


@Controller
@RequestMapping("/proveedores")
public class ProveedorController {

    @Autowired
    ProveedoresService proveedoresService;

    @Value("${pagination.size}")
    int sizePage;

    @GetMapping(value = "/lista")
    public ModelAndView lista(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:lista/1/codigo/asc");
        return modelAndView;
    }

    @GetMapping(value = "/lista/{numPage}/{fieldSort}/{directionSort}")
    public ModelAndView listPage(Model model,
            @PathVariable("numPage") Integer numPage,
            @PathVariable("fieldSort") String fieldSort,
            @PathVariable("directionSort") String directionSort) {

        Pageable pageable = PageRequest.of(numPage - 1, sizePage,
                directionSort.equals("asc") ? Sort.by(fieldSort).ascending() : Sort.by(fieldSort).descending());

        Page<Proveedor> page = proveedoresService.findAll(pageable);

        List<Proveedor> proveedores = page.getContent();

        ModelAndView modelAndView = new ModelAndView("proveedores/lista");
        modelAndView.addObject("proveedores", proveedores);

        modelAndView.addObject("numPage", numPage);
        modelAndView.addObject("totalPages", page.getTotalPages());
        modelAndView.addObject("totalElements", page.getTotalElements());

        modelAndView.addObject("fieldSort", fieldSort);
        modelAndView.addObject("directionSort", directionSort.equals("asc") ? "asc" : "desc");

        return modelAndView;
    }

    @RequestMapping(value= {"/nuevo"})
    public ModelAndView nuevo(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("proveedores/nuevo");

        return modelAndView;
    }

    @PostMapping(path = {"/guardar"})
    public ModelAndView guardar(Proveedor proveedor){
        ModelAndView modelAndView = new ModelAndView();
        proveedoresService.insert(proveedor);
        modelAndView.setViewName("redirect:editar/" + proveedor.getCodigo());
        return modelAndView;
    }

    @GetMapping(path = {"/editar/{codigo}"})
    public ModelAndView editar(@PathVariable(name="codigo", required=true) int codigo){
        ModelAndView modelAndView = new ModelAndView();
        Proveedor proveedor = proveedoresService.findById(codigo);
        modelAndView.addObject("proveedor", proveedor);
        modelAndView.setViewName("proveedores/editar");

        return modelAndView;

    }

    @PostMapping(path = {"/modificar"})
    public ModelAndView modificar(Proveedor proveedor){
        ModelAndView modelAndView = new ModelAndView();
        proveedoresService.update(proveedor);
        modelAndView.setViewName("redirect:editar/" + proveedor.getCodigo());

        return modelAndView;
    }

    @GetMapping(path = {"/borrar/{codigo}"})
    public ModelAndView borrar(@PathVariable(name="codigo", required=true) int codigo){
        ModelAndView modelAndView = new ModelAndView();
        proveedoresService.delete(codigo);
        modelAndView.setViewName("redirect:/proveedores/lista");
        
        return modelAndView;
    }
   
}
