package tsp.itch.service;

import java.util.List;
import tsp.itch.model.Carrera;

public interface ICarreraService {
    List<Carrera> buscarTodas();
    void guardar(Carrera carrera);
    Carrera buscarPorId(Integer id);
    void eliminar(Integer id);
    List<Carrera> buscarActivas();
}