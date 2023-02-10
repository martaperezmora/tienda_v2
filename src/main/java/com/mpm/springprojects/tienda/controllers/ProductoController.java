package com.mpm.springprojects.tienda.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mpm.springprojects.tienda.model.DetallePedido;
import com.mpm.springprojects.tienda.model.Pedido;
import com.mpm.springprojects.tienda.model.Producto;
import com.mpm.springprojects.tienda.services.ProductosService;

@Controller
@RequestMapping("/productos")
@PreAuthorize("hasAnyAuthority('admin','producto')")
public class ProductoController {

    @Autowired
    ProductosService productosService;

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

        Page<Producto> page = productosService.findAll(pageable);

        List<Producto> productos = page.getContent();

        ModelAndView modelAndView = new ModelAndView("productos/list");
        modelAndView.addObject("productos", productos);

        modelAndView.addObject("numPage", numPage);
        modelAndView.addObject("totalPages", page.getTotalPages());
        modelAndView.addObject("totalElements", page.getTotalElements());

        modelAndView.addObject("fieldSort", fieldSort);
        modelAndView.addObject("directionSort", directionSort.equals("asc") ? "asc" : "desc");

        return modelAndView;
    }

    @RequestMapping(value = { "/nuevo" })
    public ModelAndView nuevo() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("productos/nuevo");

        return modelAndView;
    }


    @PostMapping(path = { "/guardar" })
    public ModelAndView guardar(Producto producto, @RequestParam("imagenForm") MultipartFile multipartFile)
            throws IOException {

        ModelAndView modelAndView = new ModelAndView();

        byte[] imagen = multipartFile.getBytes();
        producto.setImagen(imagen);

        productosService.insert(producto);

        modelAndView.setViewName("redirect:edit/" + producto.getCodigo() + "/false");

        return modelAndView;
    }


    @GetMapping(path = { "/edit/{codigo}/{cesta}" })
    public ModelAndView editar(
        @PathVariable(name = "codigo", required = true) int codigo, @PathVariable(name = "cesta", required = false) boolean cesta) {

    Producto producto = productosService.findById(codigo);

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("producto", producto);
    modelAndView.addObject("cesta", cesta);

    modelAndView.setViewName("productos/edit");
    return modelAndView;
}


    @PostMapping(path = { "/modificar" })
    public ModelAndView modificar(Producto producto, @RequestParam("imagenForm") MultipartFile multipartFile)
            throws IOException {

        ModelAndView modelAndView = new ModelAndView();
        byte[] imagen = multipartFile.getBytes();
        producto.setImagen(imagen);
        productosService.update(producto);

        modelAndView.setViewName("redirect:edit/" + producto.getCodigo());

        return modelAndView;

    }

    @GetMapping(path = { "/borrar/{codigo}" })
    public ModelAndView borrar(@PathVariable(name = "codigo", required = true) int codigo) {
        ModelAndView modelAndView = new ModelAndView();

        productosService.delete(codigo);
        // List<Producto> productos = productosService.findAll();
        // modelAndView.addObject("productos", productos);
        modelAndView.setViewName("redirect:/productos/list");

        return modelAndView;
    }


    @GetMapping(value = "/añadirCesta/{codigo}/{cantidad}")
    public ModelAndView añadirCesta(
        @PathVariable(name = "codigo", required = true) int codigo, @PathVariable(name = "cantidad", required = true) int cantidad,HttpSession session) {

            Producto producto = productosService.findById(codigo);

            Pedido pedido = (Pedido) session.getAttribute("pedido");

            if(pedido == null){
                pedido = new Pedido();
            }

            if(pedido.getDetallePedidos() == null){
                List<DetallePedido> detallePedidos = new ArrayList<DetallePedido>();
                pedido.setDetallePedidos(detallePedidos);
            }

            DetallePedido detalle = new DetallePedido();
            detalle.setProducto(producto);
            detalle.setCantidad(cantidad);
            detalle.setTotal(cantidad*producto.getPrecio());
            pedido.getDetallePedidos().add(detalle);

            session.setAttribute("pedido", pedido);

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("redirect:/cesta/editar");
            return modelAndView;
    }

}
