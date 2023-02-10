package com.mpm.springprojects.tienda.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mpm.springprojects.tienda.model.Empleado;
import com.mpm.springprojects.tienda.services.EmpleadoService;

@Controller
@RequestMapping("/empleados")
@PreAuthorize("hasAnyAuthority('admin','empleado')")
public class EmpleadoController {
    
    @Autowired
    EmpleadoService empleadoService;

    @Value("${pagination.size}")
    int sizePage;

    @GetMapping(value = "/list")
    public ModelAndView list(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:list/1/codigo/asc");
        return modelAndView;
    }

    @GetMapping(value = "/list/{numPage}/{fieldSort}/{directionSort}")
    public ModelAndView listPage(Model model,
            @PathVariable("numPage") Integer numPage,
            @PathVariable("fieldSort") String fieldSort,
            @PathVariable("directionSort") String directionSort) {

        Pageable pageable = PageRequest.of(numPage - 1, sizePage,
                directionSort.equals("asc") ? Sort.by(fieldSort).ascending() : Sort.by(fieldSort).descending());

        Page<Empleado> page = empleadoService.findAll(pageable);

        List<Empleado> empleados = page.getContent();

        ModelAndView modelAndView = new ModelAndView("empleados/list");
        modelAndView.addObject("empleados", empleados);

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
        modelAndView.setViewName("empleados/nuevo");

        return modelAndView;
    }

    @PostMapping(path = {"/guardar"})
    public ModelAndView guardar(Empleado empleado){
        ModelAndView modelAndView = new ModelAndView();
        empleadoService.insert(empleado);
        modelAndView.setViewName("redirect:editar/" + empleado.getCodigo());

        return modelAndView;
    }

    @GetMapping(path = {"/editar/{codigo}"})
    public ModelAndView editar(@PathVariable(name="codigo", required=true) int codigo){
        ModelAndView modelAndView = new ModelAndView();
        Empleado empleado = empleadoService.findById(codigo);
        modelAndView.addObject("empleado", empleado);
        modelAndView.setViewName("empleados/editar");

        return modelAndView;

    }

    @PostMapping(path = {"/modificar"})
    public ModelAndView modificar(Empleado empleado){

        ModelAndView modelAndView = new ModelAndView();
        empleadoService.update(empleado);
        modelAndView.setViewName("redirect:editar/" + empleado.getCodigo());

        return modelAndView;
    }

    @GetMapping(path = {"/borrar/{codigo}"})
    public ModelAndView borrar(@PathVariable(name="codigo", required=true) int codigo){
        ModelAndView modelAndView = new ModelAndView();

        empleadoService.delete(codigo);
        modelAndView.setViewName("redirect:../list");
        
        return modelAndView;
    }
/*
    @GetMapping(value = "/añadirEmpleado/{codigo}")
    public ModelAndView addEmpleado(
        @PathVariable(name = "codigo", required = true) int codigo, HttpSession session) {

            Empleado empleado = empleadoService.findById(codigo);

            Pedido pedido = (Pedido) session.getAttribute("pedido");

            if(pedido == null){
                pedido = new Pedido();
            }

            pedido.setEmpleado(empleado);

            session.setAttribute("pedido", pedido);

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("redirect:/cesta/editar");
            return modelAndView;
    }*/

}
