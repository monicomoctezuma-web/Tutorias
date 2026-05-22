package tsp.itch.service;

import java.util.List;
import tsp.itch.model.Necesidad;

public interface INecesidadService {
    List<Necesidad> buscarTodos();
    void guardar(Necesidad necesidad);
    Necesidad buscarPorId(Integer id);
    void eliminar(Integer id);
    
    List<Necesidad> buscarPorTutorUsername(String username);
    List<Necesidad> buscarPorCarrera(Integer idCarrera);
}