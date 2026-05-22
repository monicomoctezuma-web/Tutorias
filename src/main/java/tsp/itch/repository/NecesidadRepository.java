package tsp.itch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import tsp.itch.model.Necesidad;

public interface NecesidadRepository extends JpaRepository<Necesidad, Integer> {
	List<Necesidad> findByAsignacion_Tutor_Username(String username);
	List<Necesidad> findByAsignacion_Carrera_IdCarrera(Integer idCarrera);
	
}