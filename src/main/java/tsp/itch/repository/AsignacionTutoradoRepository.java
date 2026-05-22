package tsp.itch.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tsp.itch.model.AsignacionTutorado;

public interface AsignacionTutoradoRepository extends JpaRepository<AsignacionTutorado, Integer> {
    List<AsignacionTutorado> findByAsignacionIdAsignacion(Integer idAsignacion);
    List<AsignacionTutorado> findByTutoradoIdTutorado(Integer idTutorado);
}