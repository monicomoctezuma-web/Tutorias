package tsp.itch.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import tsp.itch.model.SesionTutoria;

public interface SesionTutoriaRepository extends JpaRepository<SesionTutoria, Integer> {
	List<SesionTutoria> findByAsignacionIdAsignacion(Integer idAsignacion);
	
	 // Verificar si ya existe una sesión con esa fecha
    //boolean existsByFecha(LocalDate fecha);
    // Al editar, verificar que no sea otra sesión con esa fecha
   // boolean existsByFechaAndIdSesionNot(LocalDate fecha, Integer idSesion);
	boolean existsByFechaAndAsignacionIdAsignacion(LocalDate fecha, Integer idAsignacion);
	boolean existsByFechaAndAsignacionIdAsignacionAndIdSesionNot(LocalDate fecha, Integer idAsignacion, Integer idSesion);
	List<SesionTutoria> findByActividadIdActividad(Integer idActividad);
}
