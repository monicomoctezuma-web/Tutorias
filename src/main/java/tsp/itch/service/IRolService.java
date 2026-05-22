package tsp.itch.service;

import java.util.List;
import tsp.itch.model.Rol;

public interface IRolService {
    List<Rol> buscarTodos();
    void guardar(Rol rol);
    Rol buscarPorId(Integer id);
    void eliminar(Integer id);
}