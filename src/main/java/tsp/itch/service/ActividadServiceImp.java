package tsp.itch.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;
import tsp.itch.model.Actividad;

@Service
public class ActividadServiceImp implements IActividadService {

    List<Actividad> lista = new LinkedList<>();

    @Override
    public List<Actividad> buscarTodas() { return lista; }

    @Override
    public void guardar(Actividad actividad) { lista.add(actividad); }

    @Override
    public Actividad buscarPorId(Integer id) {
        for (Actividad a : lista)
            if (a.getIdActividad() == id) return a;
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        for (Actividad a : lista) {
            if (a.getIdActividad() == id) { lista.remove(a); break; }
        }
        
    }
    @Override
    public List<Actividad> buscarPorPat(Integer idPat) { // NUEVO
        return null; // no se usa, JPA tiene @Primary 
    
}
    @Override
    public List<Actividad> buscarPorPatCarrera(Integer idPatCarrera) { return null; }


@Override
public List<Actividad> buscarPorFecha(java.time.LocalDate fecha) { return null; }
}