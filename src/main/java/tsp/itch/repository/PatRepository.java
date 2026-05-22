package tsp.itch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import tsp.itch.model.Pat;

public interface PatRepository extends JpaRepository<Pat, Integer> {
	List<Pat> findByEstatus(Integer estatus);
}