package tsp.itch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tsp.itch.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	List<Usuario> findByRolNombre(String nombre);
	List<Usuario> findByEstatus(Integer estatus);
	List<Usuario> findByRolNombreAndEstatus(String nombre, Integer estatus);
	Usuario findByNumeroTrabajador(String numeroTrabajador);
	
	Usuario findByUsername(String username);
}
