package tsp.itch.service;

import java.util.List;
import tsp.itch.model.Tutor;

public interface ITutorService {
    List<Tutor> buscarTodos();
    void guardar(Tutor tutor);
    Tutor buscarPorId(Integer id);
    void eliminar(Integer id);
    List<Tutor> buscarPorCarrera(Integer idCarrera);
    
    Tutor buscarPorNumeroTrabajador(String numeroTrabajador);
    
    Tutor buscarPorUsername(String username);
    List<Tutor> buscarActivos();
    List<Tutor> buscarActivosPorCarrera(Integer idCarrera);
}