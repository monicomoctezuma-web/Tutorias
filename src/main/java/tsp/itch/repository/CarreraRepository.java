package tsp.itch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tsp.itch.model.Carrera;

public interface CarreraRepository extends JpaRepository<Carrera, Integer> {
	List<Carrera> findByEstatus(Integer estatus);
}
