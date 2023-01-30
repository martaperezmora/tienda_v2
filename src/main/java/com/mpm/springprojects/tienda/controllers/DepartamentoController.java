package com.mpm.springprojects.tienda.controllers;

import java.util.List;

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

import com.mpm.springprojects.tienda.model.Departamento;
import com.mpm.springprojects.tienda.model.Empleado;
import com.mpm.springprojects.tienda.services.DepartamentoService;
import com.mpm.springprojects.tienda.services.EmpleadoService;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {
    
    @Autowired
    DepartamentoService departamentoService;

    @Autowired
    EmpleadoService empleadosService;

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

        Page<Departamento> page = departamentoService.findAll(pageable);

        List<Departamento> departamentos = page.getContent();

        ModelAndView modelAndView = new ModelAndView("departamentos/list");
        modelAndView.addObject("departamentos", departamentos);

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
        modelAndView.setViewName("departamentos/nuevo");

        return modelAndView;
    }

    @PostMapping(path = {"/guardar"})
    public ModelAndView guardar(Departamento departamento){
        ModelAndView modelAndView = new ModelAndView();
        departamentoService.insert(departamento);
        modelAndView.setViewName("redirect:editar/" + departamento.getCodigo());

        return modelAndView;
    }

    @GetMapping(path = {"/editar/{codigo}"})
    public ModelAndView editar(@PathVariable(name="codigo", required=true) int codigo){
        ModelAndView modelAndView = new ModelAndView();
        Departamento departamento = departamentoService.findById(codigo);

        List<Empleado> empleados = empleadosService.findAll();
        List<Empleado> empleadosDep = departamento.getEmpleados();

        for (Empleado empTodos : empleados) {
            if (empleadosDep.contains(empTodos)) {
                empTodos.setChecked(true);
            }
        }
        
        modelAndView.addObject("departamento", departamento);
        modelAndView.addObject("empleados", empleados);
        modelAndView.setViewName("departamentos/editar");

        return modelAndView;

    }

    @PostMapping(path = {"/modificar"})
    public ModelAndView modificar(Departamento departamento){

        ModelAndView modelAndView = new ModelAndView();
        departamentoService.update(departamento);
        modelAndView.setViewName("redirect:editar/" + departamento.getCodigo());

        return modelAndView;
    }

    @GetMapping(path = {"/borrar/{codigo}"})
    public ModelAndView borrar(@PathVariable(name="codigo", required=true) int codigo){
        ModelAndView modelAndView = new ModelAndView();

        departamentoService.delete(codigo);
        modelAndView.setViewName("redirect:/departamentos/list");
        
        return modelAndView;
    }
}