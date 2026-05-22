package tsp.itch.service;

import java.util.List;
import tsp.itch.model.Semestre;

public interface ISemestreService {
    List<Semestre> buscarTodos();
    void guardar(Semestre semestre);
    Semestre buscarPorId(Integer id);
    void eliminar(Integer id);
    List<Semestre> buscarActivos();
}