package tsp.itch.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;
import tsp.itch.model.Constancia;

@Service
public class ConstanciaServiceImp implements IConstanciaService {

    List<Constancia> lista = new LinkedList<>();

    @Override
    public List<Constancia> buscarTodos() { return lista; }

    @Override
    public void guardar(Constancia constancia) { lista.add(constancia); }

    @Override
    public Constancia buscarPorId(Integer id) {
        for (Constancia c : lista)
            if (c.getIdConstancia() == id) return c;
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        for (Constancia c : lista) {
            if (c.getIdConstancia() == id) { lista.remove(c); break; }
        }
    }
}