package com.mpm.springprojects.tienda.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping(value = { "/login" })
    public String login() {
        return "login";
    }

    @PreAuthorize("hasAuthority('login')")
    @RequestMapping(value = { "/bienvenido" })
    public String bienvenido() {
        return "bienvenido";
    }

}

/*
 * package com.mpm.springprojects.tienda.controllers;
 * 
 * import javax.servlet.http.HttpSession;
 * 
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.context.MessageSource;
 * import org.springframework.context.i18n.LocaleContextHolder;
 * import org.springframework.stereotype.Controller;
 * import org.springframework.web.bind.annotation.RequestMapping;
 * import org.springframework.web.servlet.ModelAndView;
 * 
 * import com.mpm.springprojects.tienda.model.Usuario;
 * 
 * @Controller
 * 
 * @RequestMapping("/login")
 * public class LoginController {
 * 
 * @Autowired
 * private MessageSource messageSource;
 * 
 * @RequestMapping(value = { "/signin" })
 * public String signin() {
 * return "signin";
 * }
 * 
 * @RequestMapping(value = { "/logout" })
 * public String logout() {
 * return "signin";
 * }
 * 
 * @RequestMapping(value = { "/bienvenido" })
 * public String bienvenido() {
 * return "bienvenido";
 * }
 * 
 * @RequestMapping(path = { "/login" })
 * public ModelAndView login(Usuario usuario, HttpSession session) {
 * ModelAndView modelAndView = new ModelAndView();
 * 
 * usuario = new Usuario();
 * usuario.setNombre("a");
 * 
 * String mensaje = messageSource.getMessage("saludar.usuario", new String[] {
 * usuario.getNombre() },
 * LocaleContextHolder.getLocale());
 * 
 * session.setAttribute("usuario", usuario);
 * 
 * modelAndView.addObject("bienvenido", mensaje);
 * modelAndView.addObject("usuario", usuario);
 * modelAndView.setViewName("bienvenido");
 * 
 * return modelAndView;
 * }
 * }
 */

/*
 * @PostMapping(path = {"/login"})
 * public ModelAndView login(Usuario usuario, HttpSession session){
 * ModelAndView modelAndView = new ModelAndView();
 * 
 * String mensaje = messageSource.getMessage("saludar.usuario", new
 * String[]{usuario.getNombre()}, LocaleContextHolder.getLocale());
 * 
 * session.setAttribute("usuario", usuario);
 * 
 * modelAndView.addObject("bienvenido", mensaje);
 * //modelAndView.addObject("usuario", usuario);
 * modelAndView.setViewName("bienvenido");
 * 
 * return modelAndView;
 * }
 */
