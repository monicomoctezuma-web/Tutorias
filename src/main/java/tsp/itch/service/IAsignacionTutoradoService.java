package tsp.itch.service;

import java.util.List;
import tsp.itch.model.AsignacionTutorado;

public interface IAsignacionTutoradoService {
    List<AsignacionTutorado> buscarTodas();
    AsignacionTutorado buscarPorId(Integer id);
    void guardar(AsignacionTutorado asignacionTutorado);
    void eliminar(Integer id);
    List<AsignacionTutorado> buscarPorAsignacion(Integer idAsignacion);
    List<AsignacionTutorado> buscarPorTutorado(Integer idTutorado);
}