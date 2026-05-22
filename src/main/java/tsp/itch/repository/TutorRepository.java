package tsp.itch.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tsp.itch.model.Tutor;

public interface TutorRepository extends JpaRepository<Tutor, Integer> {

    List<Tutor> findByCarreraIdCarrera(Integer idCarrera);
    
    Tutor findByNumeroTrabajador(String numeroTrabajador);
    
    Tutor findByUsername(String username);
    
    List<Tutor> findByEstatus(Integer estatus);
    
    List<Tutor> findByEstatusAndCarreraIdCarrera(Integer estatus, Integer idCarrera);
}