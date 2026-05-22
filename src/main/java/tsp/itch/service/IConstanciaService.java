package tsp.itch.service;

import java.util.List;
import tsp.itch.model.Constancia;

public interface IConstanciaService {
    List<Constancia> buscarTodos();
    void guardar(Constancia constancia);
    Constancia buscarPorId(Integer id);
    void eliminar(Integer id);
}