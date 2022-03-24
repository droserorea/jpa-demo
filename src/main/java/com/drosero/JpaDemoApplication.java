package com.drosero;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.drosero.model.Categoria;
import com.drosero.model.Perfil;
import com.drosero.model.Usuario;
import com.drosero.model.Vacante;
import com.drosero.repository.CategoriasRepository;
import com.drosero.repository.PerfilesRepository;
import com.drosero.repository.UsuariosRepository;
import com.drosero.repository.VacantesRepository;

@SpringBootApplication
public class JpaDemoApplication implements CommandLineRunner {

	@Autowired
	private CategoriasRepository repoCategorias;
	@Autowired
	private VacantesRepository repoVacantes;
	@Autowired
	private UsuariosRepository repoUsuarios;
	@Autowired
	private PerfilesRepository repoPerfiles;

	public static void main(String[] args) {
		SpringApplication.run(JpaDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		buscarUsuario();
		// conteo();
	}
	
	//Metodo para buscar un usuario y desplegar perfiles asociados
	public void buscarUsuario() {
		Optional<Usuario> optional = repoUsuarios.findById(1);
		if (optional.isPresent()) {
			Usuario u = optional.get();
			System.out.println("Usuario: "+u.getNombre());
			System.out.println("Perfiles: ");
			for (Perfil p : u.getPerfiles()) {
				System.out.println(p.getPerfil());
			}
		}else {
			System.out.println("Usuario no encontrado");
		}
	}
	
	//Crear usuario con 2 perfiles ADMINISTRADOR/USUARIO
	private void crearUsuarioConDosPerfiles() {
		Usuario user = new Usuario();
		user.setNombre("Ivar");
		user.setEmail("example@example.com");
		user.setFechaRegistro(new Date());
		user.setUsername("ivaruser");
		user.setPassword("12345");
		user.setEstatus(1);
		
		Perfil p1=new Perfil();
		p1.setId(2);
		Perfil p2=new Perfil();
		p2.setId(3);
		
		user.agregar(p1);
		user.agregar(p2);
		
		repoUsuarios.save(user);
	}
	
	// Metodo para crear PERFILES / ROLES
	private void crearPerfilesAplicacion() {
		repoPerfiles.saveAll(getPerfilesAplicacion());

	}

	// Guardar una vacante con el met. save
	private void guardarVacante() {
		Vacante v1 = new Vacante();
		v1.setNombre("Porf. de Matematica");
		v1.setDescripcion("Escuela primaria");
		v1.setFecha(new Date());
		v1.setSalario(800.0);
		v1.setEstatus("Aprobada");
		v1.setDestacado(0);
		v1.setImagen("escuela.png");
		v1.setDetalles("<h1>Req. profesor de matematica</h1>");
		Categoria cat = new Categoria();
		cat.setId(15);
		v1.setCategoria(cat);
		repoVacantes.save(v1);
	}

	private void buscarVacantes() {
		List<Vacante> lista = repoVacantes.findAll();
		for (Vacante v : lista) {
			System.out.println(v.getId() + " " + v.getNombre() + " ->" + v.getCategoria().getNombre());
		}
	}

	// Metodo findAll [Con paginacion] - Interfaz CrudRepository
	private void buscarTodosPaginacionOrdenado() {
		Page<Categoria> page = repoCategorias.findAll(PageRequest.of(0, 5, Sort.by("nombre").descending()));
		System.out.println("Tot. Registros: " + page.getTotalElements());
		System.out.println("Tot. Paginas: " + page.getTotalPages());
		for (Categoria c : page) {
			System.out.println(c.getId() + " " + c.getNombre());
		}
	}

	// Metodo findAll [Con paginacion] - Interfaz CrudRepository
	private void buscarTodosPaginacion() {
		Page<Categoria> page = repoCategorias.findAll(PageRequest.of(0, 5));
		System.out.println("Tot. Registros: " + page.getTotalElements());
		System.out.println("Tot. Paginas: " + page.getTotalPages());
		for (Categoria c : page) {
			System.out.println(c.getId() + " " + c.getNombre());
		}
	}

	// Metodo findAll [Ordenar por un campo] - Interfaz CrudRepository
	private void buscarTodosOrdenados() {
		List<Categoria> categorias = repoCategorias.findAll(Sort.by("nombre").descending());
		for (Categoria c : categorias) {
			System.out.println(c.getId() + " " + c.getNombre());
		}
	}

	// Metodo deleteAllInBatch[usar con precaucion] - Interfaz CrudRepository
	private void borrarTodoEnBloque() {
		repoCategorias.deleteAllInBatch();
	}

	// Metodo findAll - Interfaz CrudRepository
	private void buscarTodosJpa() {
		List<Categoria> categorias = repoCategorias.findAll();
		for (Categoria c : categorias) {
			System.out.println(c.getId() + " " + c.getNombre());
		}
	}

	// Metodo deleteAll - Interfaz CrudRepository
	private void guardarTodas() {
		List<Categoria> categorias = getListaCategorias();
		repoCategorias.saveAll(categorias);
	}

	// Metodo deleteAll - Interfaz CrudRepository
	private void existeId() {
		boolean existe = repoCategorias.existsById(5);
		System.out.println("La categoria existe: " + existe);
	}

	// Metodo deleteAll - Interfaz CrudRepository
	private void buscarTodos() {
		Iterable<Categoria> categorias = repoCategorias.findAll();
		for (Categoria cat : categorias) {
			System.out.println(cat);
		}
	}

	// Metodo findAllById - Interfaz CrudRepository
	private void encontrarPorIds() {
		List<Integer> ids = new LinkedList<Integer>();
		ids.add(1);
		ids.add(4);
		ids.add(10);
		Iterable<Categoria> categorias = repoCategorias.findAllById(ids);
		for (Categoria cat : categorias) {
			System.out.println(cat);
		}

	}

	// Metodo deleteAll - Interfaz CrudRepository
	private void eliminarTodos() {
		repoCategorias.deleteAll();
	}

//	Metodo count -   Interfaz CrudRepository
	private void conteo() {
		long count = repoCategorias.count();
		System.out.println("Total de Categorias: " + count);
	}

//	Metodo deleteById -   Interfaz CrudRepository
	private void eliminar() {
		int idCategoria = 19;
		repoCategorias.deleteById(idCategoria);
	}

//	Metodo save(update) -   Interfaz CrudRepository
	private void modificar() {
		Optional<Categoria> optional = repoCategorias.findById(2);
		if (optional.isPresent()) {
			Categoria catTmp = optional.get();
			catTmp.setNombre("Ing en Sistemas");
			catTmp.setDescripcion("Desarrollo de software");
			repoCategorias.save(catTmp);
			System.out.println(optional.get());
		} else {
			System.out.println("Categoria no encontrada");
		}
	}

//	 Metodo find by Id
	private void buscarPorId() {
		Optional<Categoria> optional = repoCategorias.findById(5);
		if (optional.isPresent()) {
			System.out.println(optional.get());
		} else {
			System.out.println("Categoria no encontrada");
		}
	}

	// Metodo save to make an insert
	public void guardar() {
		Categoria c1 = new Categoria();
		c1.setNombre("Finanzas");
		c1.setDescripcion("Trabajos de finanzas");
		repoCategorias.save(c1);
		System.out.println(c1);
	}

	// Metodo que regresa una lista de 3 categorias
	private List<Categoria> getListaCategorias() {
		List<Categoria> lista = new LinkedList<Categoria>();
		// Categoria 1
		Categoria c1 = new Categoria();
		c1.setNombre("Programador blockchain");
		c1.setDescripcion("Trabajos realizados con bitcoind");

		// Categoria 1
		Categoria c2 = new Categoria();
		c2.setNombre("Soldador/Pintura");
		c2.setDescripcion("Trabajos relacionados con soldadura y pintura");

		// Categoria 1
		Categoria c3 = new Categoria();
		c3.setNombre("Ingeniero Industrial");
		c3.setDescripcion("Trabajos realizados con ing industrial");

		lista.add(c1);
		lista.add(c2);
		lista.add(c3);

		return lista;
	}

	// Metodo que regresa una lista de objetos tipo Perfil
	// Representa los ROLES/PERFILES de la app
	private List<Perfil> getPerfilesAplicacion() {
		List<Perfil> lista = new LinkedList<Perfil>();
		Perfil p1 = new Perfil();
		p1.setPerfil("SUPERVISOR");

		Perfil p2 = new Perfil();
		p2.setPerfil("ADMINISTRADOR");

		Perfil p3 = new Perfil();
		p3.setPerfil("USUARIO");
		
		lista.add(p1);
		lista.add(p2);
		lista.add(p3);
		
		return lista;
	}

}
