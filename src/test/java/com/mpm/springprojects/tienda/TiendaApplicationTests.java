package com.mpm.springprojects.tienda;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mpm.springprojects.tienda.model.Permiso;
import com.mpm.springprojects.tienda.model.Usuario;
import com.mpm.springprojects.tienda.services.PermisoService;
import com.mpm.springprojects.tienda.services.UsuarioService;

@SpringBootTest
class TiendaApplicationTests {

	@Autowired
	PermisoService permisoService;

	@Autowired
	UsuarioService usuarioService;

	@Test
	void crearUsuarios() {
		Permiso admin = new Permiso();
		admin.setNombre("admin"); // acceso a todo
		admin.setCodigo(1);

		Permiso pedidos = new Permiso();
		pedidos.setNombre("pedidos");  // acceso a pedidos
		pedidos.setCodigo(2);

		Permiso cliente = new Permiso();
		cliente.setNombre("cliente");  // acceso a clientes
		cliente.setCodigo(3);

		Permiso login = new Permiso();
		login.setNombre("login");  // permiso para logear y entrar en la app, acceso solo a la pantalla de inicio
		login.setCodigo(4);

		Permiso producto = new Permiso();
		login.setNombre("producto");
		login.setCodigo(5);

		Permiso proveedor = new Permiso();
		login.setNombre("proveedor");
		login.setCodigo(6);

		Permiso vendedor = new Permiso();
		login.setNombre("vendedor");
		login.setCodigo(7);

		Permiso cesta = new Permiso();
		login.setNombre("cesta");
		login.setCodigo(8);

		Permiso empleado = new Permiso();
		login.setNombre("empleado");
		login.setCodigo(9);

		Permiso departamento = new Permiso();
		login.setNombre("departamento");
		login.setCodigo(10);

		permisoService.createPermiso(admin);
		permisoService.createPermiso(pedidos);
		permisoService.createPermiso(cliente);
		permisoService.createPermiso(login);
		permisoService.createPermiso(producto);
		permisoService.createPermiso(proveedor);
		permisoService.createPermiso(vendedor);
		permisoService.createPermiso(cesta);
		permisoService.createPermiso(empleado);
		permisoService.createPermiso(departamento);

		Usuario usuario1 = new Usuario();
		usuario1.setCodigo(1);
		usuario1.setNombre("usuario1");
		usuario1.setPassword("{noop}3333");
		usuario1.setEmail("usu1@email.com");

		Usuario usuario2 = new Usuario();
		usuario2.setCodigo(2);
		usuario2.setNombre("usuario2");
		usuario2.setPassword("{noop}5555");
		usuario2.setEmail("usu2@email.com");

		Usuario usuario3 = new Usuario();
		usuario3.setCodigo(3);
		usuario3.setNombre("usuario3");
		usuario3.setPassword("{noop}8888");
		usuario3.setEmail("usu3@email.com");

		Usuario usuario4 = new Usuario();
		usuario4.setCodigo(4);
		usuario4.setNombre("usuario4");
		usuario4.setPassword("{noop}2222");
		usuario4.setEmail("usu4@email.com");


		List<Permiso> permisos1 = new ArrayList<Permiso>();
		permisos1.add(admin);
		permisos1.add(login);
		usuario1.setPermisos(permisos1);
		usuarioService.createUsuario(usuario1); // usuario1: admin, login

		List<Permiso> permisos = new ArrayList<Permiso>();

		permisos.add(login);
		usuario4.setPermisos(permisos);
		usuarioService.createUsuario(usuario4); // usuario4: login

		permisos.add(pedidos);
		usuario2.setPermisos(permisos);
		usuarioService.createUsuario(usuario2); // usuario2: login, pedidos

		permisos.add(cliente);
		usuario3.setPermisos(permisos);
		usuarioService.createUsuario(usuario3); // usuario3: login, pedidos, cliente

	}

}
