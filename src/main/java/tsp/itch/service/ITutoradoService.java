package tsp.itch.service;

import java.util.List;
import tsp.itch.model.Tutorado;

public interface ITutoradoService {
    List<Tutorado> buscarTodos();
    void guardar(Tutorado tutorado);
    Tutorado buscarPorId(Integer id);
    void eliminar(Integer id);
    List<Tutorado> buscarPorCarrera(Integer idCarrera);
}