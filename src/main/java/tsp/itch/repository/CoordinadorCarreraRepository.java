package tsp.itch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tsp.itch.model.CoordinadorCarrera;

public interface CoordinadorCarreraRepository extends JpaRepository<CoordinadorCarrera, Integer> {
	
	CoordinadorCarrera findByUsuario_Username(String username);	
}