package tsp.itch.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;
import tsp.itch.model.Necesidad;

@Service
public class NecesidadServiceImp implements INecesidadService {

    List<Necesidad> lista = new LinkedList<>();

    @Override
    public List<Necesidad> buscarTodos() { return lista; }

    @Override
    public void guardar(Necesidad necesidad) { lista.add(necesidad); }

    @Override
    public Necesidad buscarPorId(Integer id) {
        for (Necesidad n : lista)
            if (n.getIdNecesidad() == id) return n;
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        for (Necesidad n : lista) {
            if (n.getIdNecesidad() == id) { lista.remove(n); break; }
        }
    }
    
    @Override
    public List<Necesidad> buscarPorTutorUsername(String username) { return null; }
    @Override
    public List<Necesidad> buscarPorCarrera(Integer idCarrera) { return null; }
}