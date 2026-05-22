package tsp.itch.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tsp.itch.model.PatCarrera;

public interface PatCarreraRepository extends JpaRepository<PatCarrera, Integer> {
    List<PatCarrera> findByCarreraIdCarrera(Integer idCarrera);
    List<PatCarrera> findByPatIdPat(Integer idPat);
    
    PatCarrera findByCarreraIdCarreraAndSemestreIdSemestre(Integer idCarrera, Integer idSemestre);
}