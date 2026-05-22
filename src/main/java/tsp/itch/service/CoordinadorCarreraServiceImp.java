package tsp.itch.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;
import tsp.itch.model.CoordinadorCarrera;

@Service
public class CoordinadorCarreraServiceImp implements ICoordinadorCarreraService {

    List<CoordinadorCarrera> lista = new LinkedList<>();

    @Override
    public List<CoordinadorCarrera> buscarTodos() { return lista; }

    @Override
    public void guardar(CoordinadorCarrera coordinador) { lista.add(coordinador); }

    @Override
    public CoordinadorCarrera buscarPorId(Integer id) {
        for (CoordinadorCarrera c : lista)
            if (c.getIdCoordinador() == id) return c;
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        for (CoordinadorCarrera c : lista) {
            if (c.getIdCoordinador() == id) { lista.remove(c); break; }
        }
    }
    
    @Override
    public CoordinadorCarrera buscarPorUsername(String username) {
        return null;
    }
}