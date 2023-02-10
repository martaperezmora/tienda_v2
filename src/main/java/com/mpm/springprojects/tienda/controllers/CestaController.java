package com.mpm.springprojects.tienda.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mpm.springprojects.tienda.model.Cliente;
import com.mpm.springprojects.tienda.model.DetallePedido;
import com.mpm.springprojects.tienda.model.Pedido;
import com.mpm.springprojects.tienda.services.ClientesService;

// cesta de la compra
@Controller
@Secured({"admin", "pedidos", "clientes"})
@RequestMapping("/cesta")
public class CestaController {
    
    @Autowired
    ClientesService clientesService;

    @GetMapping(value = { "/editar" })
    public ModelAndView cesta(HttpSession session) {   // se crea el objeto sesion
        ModelAndView modelAndView = new ModelAndView();

        Pedido pedido = (Pedido) session.getAttribute("pedido");   // se extrae el atributo pedido que haya almacenado 
                                                                   // en el objeto de la sesion

        if(pedido == null) {
            pedido = new Pedido();  // en caso de que no exista ningun pedido en sesion se inicializa uno vacio
        }

        Cliente cliente = pedido.getCliente();  // se extrae el cliente del pedido, si el pedido esta vacio el cliente tambien


        modelAndView.addObject("cliente", cliente);  
        modelAndView.addObject("pedido", pedido);
        modelAndView.setViewName("cesta/editar");  // se carga la pagina de la cesta con los datos del cliente y el pedido
        return modelAndView;    
    }


    @GetMapping(value = { "/borrar" })         // controller para limpiar toda la cesta de la compra (el pedido entero)
    public ModelAndView borrar(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        session.setAttribute("pedido", null);  // para ello se establece el valor del pedido que habia en sesion a null
                                               // esto elimina todos los datos que tenia el objeto pedido

        modelAndView.setViewName("redirect:editar");  // seguimos en la pantalla de la cesta
        return modelAndView;    
    }


    @GetMapping(value = "/borrarproducto/{codigo}")   // controller para eliminar un producto que ya no queramos comprar
    public ModelAndView borrarProducto(
        @PathVariable(name = "codigo", required = true) int codigo, HttpSession session) {  // recibe el codigo del producto y la sesion como parametros

            Pedido pedido = (Pedido) session.getAttribute("pedido");      // se extrae el pedido de la sesion, en este caso no puede ser 
                                                                          // que no exista el pedido

            List<DetallePedido> detallePedidos = pedido.getDetallePedidos();// del pedido se extrae la lista de productos que tenga almacenados

            for (DetallePedido detallePedido : detallePedidos) {            // con un bucle tipo foreach por ejemplo se mira    
                if(detallePedido.getProducto().getCodigo() == codigo){      // el codigo de cada producto hasta encontrar el deseado.
                    detallePedidos.remove(detallePedido);
                    break;                                                  // entonces se elimina de la lista
                }
                
            }

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("redirect:/cesta/editar");   // otra vez seguimos en la pantalla de la cesta
            return modelAndView;
    }


}
