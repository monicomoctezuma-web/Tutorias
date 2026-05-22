package tsp.itch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tsp.itch.model.Constancia;

public interface ConstanciaRepository extends JpaRepository<Constancia, Integer> {
	
	
}