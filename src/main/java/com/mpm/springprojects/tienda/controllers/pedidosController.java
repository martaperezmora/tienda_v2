package com.mpm.springprojects.tienda.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mpm.springprojects.tienda.model.Pedido;
import com.mpm.springprojects.tienda.services.PedidosService;

// lista historial de pedidos
@Controller
@Secured({"admin", "pedidos"})
@RequestMapping("/pedidos")
public class pedidosController {
    @Autowired
    PedidosService pedidosService;

    @Value("${pagination.size}")
    int sizePage;


    @GetMapping(value = "/lista")  // lista los pedidos con la paginacion
    public ModelAndView list(Model model){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:lista/1/codigo/desc");
        return modelAndView;
    }
  
    @GetMapping(value = "/lista/{numPage}/{fieldSort}/{directionSort}")
    public ModelAndView listPage(Model model,
            @PathVariable("numPage") Integer numPage,
            @PathVariable("fieldSort") String fieldSort,
            @PathVariable("directionSort") String directionSort) {


        Pageable pageable = PageRequest.of(numPage - 1, sizePage,
            directionSort.equals("asc") ? Sort.by(fieldSort).ascending() : Sort.by(fieldSort).descending());

        Page<Pedido> page = pedidosService.findAll(pageable);

        List<Pedido> pedidos = page.getContent();

        ModelAndView modelAndView = new ModelAndView("pedidos/lista");
        modelAndView.addObject("pedidos", pedidos);


		modelAndView.addObject("numPage", numPage);
		modelAndView.addObject("totalPages", page.getTotalPages());
		modelAndView.addObject("totalElements", page.getTotalElements());

		modelAndView.addObject("fieldSort", fieldSort);
		modelAndView.addObject("directionSort", directionSort.equals("asc") ? "asc" : "desc");

        return modelAndView;
    }
    // fin de la lista con paginacion


    @GetMapping(path = { "/editar/{codigo}" })  // para editar la informacion de un pedido
    public ModelAndView editar(
            @PathVariable(name = "codigo", required = true) int codigo, final Locale locale) {

        Pedido pedido = pedidosService.findById(codigo);   // se extrae el pedido de la base de datos
                                                       // usando el codigo recibido

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pedido", pedido);

        modelAndView.setViewName("pedidos/editar");  // se carga la pagina de edicion
                                                               // con los datos del pedido
        return modelAndView;
    }

    @GetMapping(path = { "/guardar" })   // para guardar los cambios realizados en el pedido despues de editar
    public ModelAndView guardar(HttpSession session)
            throws IOException {

        Pedido pedido = (Pedido) session.getAttribute("pedido");  // se extrae el pedido de la sesion

        pedidosService.insert(pedido);   // se guarda en la base de datos con el metodo correspondiente (ver PedidosDAOImpl.java)

        session.removeAttribute("pedido");  // se elimina el pedido editado de la sesion; ya no hace falta

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:lista");  // volvemos al listado de pedidos

        return modelAndView;
    }

    @GetMapping(path = { "/borrar/{codigo}" })  // para borar un pedido
    public ModelAndView borrar(
            @PathVariable(name = "codigo", required = true) int codigo, final Locale locale) {

            pedidosService.delete(codigo);

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("redirect:../lista");  // volvemos al listado de pedidos

            return modelAndView;
        }
}
