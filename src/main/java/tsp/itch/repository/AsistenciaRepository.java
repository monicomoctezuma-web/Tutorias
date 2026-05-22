package tsp.itch.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tsp.itch.model.Asistencia;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Integer> {

    // Buscar asistencias por sesión
    List<Asistencia> findBySesionIdSesion(Integer idSesion);

    // Buscar asistencias por tutorado
    List<Asistencia> findByTutoradoIdTutorado(Integer idTutorado);

    // Contar cuántas veces estuvo presente un tutorado
    @Query("SELECT COUNT(a) FROM Asistencia a WHERE a.tutorado.idTutorado = :idTutorado AND a.presente = 1")
    long contarPresencias(@Param("idTutorado") Integer idTutorado);

    // Contar total de sesiones registradas para un tutorado
    @Query("SELECT COUNT(a) FROM Asistencia a WHERE a.tutorado.idTutorado = :idTutorado")
    long contarTotalSesiones(@Param("idTutorado") Integer idTutorado);
    
    //nuevos
    
    @Query("SELECT COUNT(a) FROM Asistencia a WHERE a.tutorado.idTutorado = :idTutorado AND a.presente = 1 AND a.sesion.asignacion.idAsignacion = :idAsignacion")
    long contarPresenciasPorAsignacion(@Param("idTutorado") Integer idTutorado, @Param("idAsignacion") Integer idAsignacion);

    @Query("SELECT COUNT(a) FROM Asistencia a WHERE a.tutorado.idTutorado = :idTutorado AND a.sesion.asignacion.idAsignacion = :idAsignacion")
    long contarTotalSesionesPorAsignacion(@Param("idTutorado") Integer idTutorado, @Param("idAsignacion") Integer idAsignacion);
}