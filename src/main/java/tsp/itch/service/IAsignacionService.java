package tsp.itch.service;

import java.util.List;
import tsp.itch.model.Asignacion;

public interface IAsignacionService {
    List<Asignacion> buscarTodas();
    Asignacion buscarPorId(Integer id);
    void guardar(Asignacion asignacion);
    void eliminar(Integer id);
    List<Asignacion> buscarPorCarrera(Integer idCarrera);
    List<Asignacion> buscarPorSemestre(Integer idSemestre);
   // List<Asignacion> buscarPorTutor(Integer idUsuario);
    List<Asignacion> buscarPorTutor(Integer idTutor);
    List<Asignacion> buscarActivas();
    List<Asignacion> buscarPorTutorUsername(String username);
}