package tsp.itch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tsp.itch.model.Semestre;

public interface SemestreRepository extends JpaRepository<Semestre, Integer> {
	List<Semestre> findByActivo(Integer activo);
}
