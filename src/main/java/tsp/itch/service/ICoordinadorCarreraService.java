package tsp.itch.service;

import java.util.List;
import tsp.itch.model.CoordinadorCarrera;

public interface ICoordinadorCarreraService {
    List<CoordinadorCarrera> buscarTodos();
    void guardar(CoordinadorCarrera coordinador);
    CoordinadorCarrera buscarPorId(Integer id);
    void eliminar(Integer id);
    
    CoordinadorCarrera buscarPorUsername(String username);
}