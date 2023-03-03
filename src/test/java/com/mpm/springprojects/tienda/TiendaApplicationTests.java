package com.mpm.springprojects.tienda;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

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

	@Autowired
	PasswordEncoder encoder;

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
		producto.setNombre("producto");
		producto.setCodigo(5);

		Permiso proveedor = new Permiso();
		proveedor.setNombre("proveedor");
		proveedor.setCodigo(6);

		Permiso vendedor = new Permiso();
		vendedor.setNombre("vendedor");
		vendedor.setCodigo(7);

		Permiso cesta = new Permiso();
		cesta.setNombre("cesta");
		cesta.setCodigo(8);

		Permiso empleado = new Permiso();
		empleado.setNombre("empleado");
		empleado.setCodigo(9);

		Permiso departamento = new Permiso();
		departamento.setNombre("departamento");
		departamento.setCodigo(10);

		Permiso nota = new Permiso();
		nota.setNombre("nota");
		nota.setCodigo(11);

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
		permisoService.createPermiso(nota);

		Usuario usuario1 = new Usuario();
		usuario1.setCodigo(1);
		usuario1.setNombre("usuario1");
		usuario1.setPassword(encoder.encode("3333"));
		usuario1.setEmail("usu1@email.com");

		Usuario usuario2 = new Usuario();
		usuario2.setCodigo(2);
		usuario2.setNombre("usuario2");
		usuario2.setPassword(encoder.encode("5555"));
		usuario2.setEmail("usu2@email.com");

		Usuario usuario3 = new Usuario();
		usuario3.setCodigo(3);
		usuario3.setNombre("usuario3");
		usuario3.setPassword(encoder.encode("8888"));
		usuario3.setEmail("usu3@email.com");

		Usuario usuario4 = new Usuario();
		usuario4.setCodigo(4);
		usuario4.setNombre("usuario4");
		usuario4.setPassword(encoder.encode("2222"));
		usuario4.setEmail("usu4@email.com");


		List<Permiso> permisos1 = new ArrayList<Permiso>();
		permisos1.add(admin);
		permisos1.add(login);
		usuario1.setPermisos(permisos1);
		usuarioService.createUsuario(usuario1);

		List<Permiso> permisos = new ArrayList<Permiso>();

		permisos.add(login);
		usuario4.setPermisos(permisos);
		usuarioService.createUsuario(usuario4);

		permisos.add(pedidos);
		usuario2.setPermisos(permisos);
		usuarioService.createUsuario(usuario2);	

		permisos.add(cliente);
		permisos.add(nota);
		usuario3.setPermisos(permisos);
		usuarioService.createUsuario(usuario3);



	}

}
