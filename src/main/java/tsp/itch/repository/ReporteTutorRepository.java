package tsp.itch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import tsp.itch.model.ReporteTutor;

public interface ReporteTutorRepository extends JpaRepository<ReporteTutor, Integer> {
	List<ReporteTutor> findBySesionIdSesion(Integer idSesion);
	
	List<ReporteTutor> findBySesion_Asignacion_Tutor_Username(String username);
	List<ReporteTutor> findBySesion_Asignacion_Carrera_IdCarrera(Integer idCarrera);
}