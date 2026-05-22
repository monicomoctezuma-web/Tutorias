package tsp.itch.service;

import java.util.List;
import tsp.itch.model.Asistencia;

public interface IAsistenciaService {
    List<Asistencia> buscarTodos();
    void guardar(Asistencia asistencia);
    Asistencia buscarPorId(Integer id);
    void eliminar(Integer id);
    List<Asistencia> buscarPorSesion(Integer idSesion);
    List<Asistencia> buscarPorTutorado(Integer idTutorado);
    long contarPresencias(Integer idTutorado);
    long contarTotalSesiones(Integer idTutorado);
    double calcularPorcentaje(Integer idTutorado);
    
    
    
    long contarPresenciasPorAsignacion(Integer idTutorado, Integer idAsignacion);
    double calcularPorcentajePorAsignacion(Integer idTutorado, Integer idAsignacion);
}