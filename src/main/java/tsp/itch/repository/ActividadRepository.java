package tsp.itch.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tsp.itch.model.Actividad;

public interface ActividadRepository extends JpaRepository<Actividad, Integer> {
    List<Actividad> findByPatCarreraIdPatCarrera(Integer idPatCarrera);
    
    
    //
    List<Actividad> findByPatCarrera_Pat_IdPat(Integer idPat);
    List<Actividad> findByPatIdPat(Integer idPat); // ← AGREGAR ESTO
    
    List<Actividad> findByFecha(LocalDate fecha);
    List<Actividad> findByFechaAndPatIsNotNull(java.time.LocalDate fecha);
}
