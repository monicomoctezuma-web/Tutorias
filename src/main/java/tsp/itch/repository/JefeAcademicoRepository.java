package tsp.itch.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import tsp.itch.model.JefeAcademico;

public interface JefeAcademicoRepository extends JpaRepository<JefeAcademico, Integer> {
    JefeAcademico findByUsuario_Username(String username);
}