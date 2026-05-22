package tsp.itch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tsp.itch.model.Tutorado;

public interface TutoradoRepository extends JpaRepository<Tutorado, Integer>{
   
	List<Tutorado> findByCarreraIdCarrera(Integer idCarrera);
	
}
