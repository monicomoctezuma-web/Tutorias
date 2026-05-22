package tsp.itch.service;

import java.util.List;
import tsp.itch.model.Pat;

public interface IPatService {
    List<Pat> buscarTodas();
    void guardar(Pat pat);
    Pat buscarPorId(Integer id);
    void eliminar(Integer id);
    List<Pat> buscarActivas();
}