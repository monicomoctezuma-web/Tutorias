package tsp.itch.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tsp.itch.model.Asignacion;

public interface AsignacionRepository extends JpaRepository<Asignacion, Integer> {
    List<Asignacion> findByCarreraIdCarrera(Integer idCarrera);
    List<Asignacion> findBySemestreIdSemestre(Integer idSemestre);
   // List<Asignacion> findByTutorIdUsuario(Integer idUsuario);
    List<Asignacion> findByTutorIdTutor(Integer idTutor);
    List<Asignacion> findByEstatus(Integer estatus);
    List<Asignacion> findByTutor_Username(String username);
}