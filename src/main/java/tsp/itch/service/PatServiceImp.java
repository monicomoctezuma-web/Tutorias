package tsp.itch.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;
import tsp.itch.model.Pat;

@Service
public class PatServiceImp implements IPatService {

    List<Pat> lista = new LinkedList<>();

    @Override
    public List<Pat> buscarTodas() { return lista; }

    @Override
    public void guardar(Pat pat) { lista.add(pat); }

    @Override
    public Pat buscarPorId(Integer id) {
        for (Pat p : lista)
            if (p.getIdPat() == id) return p;
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        for (Pat p : lista) {
            if (p.getIdPat() == id) { lista.remove(p); break; }
        }
    }
    @Override
    public List<Pat> buscarActivas() {
        List<Pat> activas = new LinkedList<>();
        for (Pat p : lista) {
            if (p.getEstatus() == 1) activas.add(p);
        }
        return activas;
    }
}