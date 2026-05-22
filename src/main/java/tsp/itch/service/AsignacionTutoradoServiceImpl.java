package tsp.itch.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;
import tsp.itch.model.AsignacionTutorado;

@Service
public class AsignacionTutoradoServiceImpl implements IAsignacionTutoradoService {

    List<AsignacionTutorado> lista = new LinkedList<>();

    @Override
    public List<AsignacionTutorado> buscarTodas() { return lista; }

    @Override
    public void guardar(AsignacionTutorado asignacionTutorado) { lista.add(asignacionTutorado); }

    @Override
    public AsignacionTutorado buscarPorId(Integer id) {
        for (AsignacionTutorado at : lista)
            if (at.getId() == id) return at;
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        for (AsignacionTutorado at : lista) {
            if (at.getId() == id) { lista.remove(at); break; }
        }
    }

    @Override
    public List<AsignacionTutorado> buscarPorAsignacion(Integer idAsignacion) { return lista; }

    @Override
    public List<AsignacionTutorado> buscarPorTutorado(Integer idTutorado) { return lista; }
}