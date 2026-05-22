package tsp.itch.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;
import tsp.itch.model.Carrera;

@Service
public class CarreraServiceImp implements ICarreraService {

    List<Carrera> lista = new LinkedList<>();

    @Override
    public List<Carrera> buscarTodas() { return lista; }

    @Override
    public void guardar(Carrera carrera) { lista.add(carrera); }

    @Override
    public Carrera buscarPorId(Integer id) {
        for(Carrera c : lista)
            if(c.getIdCarrera() == id) return c;
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        for(Carrera c : lista) {
            if(c.getIdCarrera() == id) { lista.remove(c); break; }
        }
    }
    @Override
    public List<Carrera> buscarActivas() {
        List<Carrera> activas = new LinkedList<>();
        for (Carrera c : lista) {
            if (c.getEstatus() == 1) activas.add(c);
        }
        return activas;
    }
}