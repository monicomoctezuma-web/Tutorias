package tsp.itch.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;
import tsp.itch.model.Asignacion;

@Service
public class AsignacionServiceImpl implements IAsignacionService {

    List<Asignacion> lista = new LinkedList<>();

    @Override
    public List<Asignacion> buscarTodas() { return lista; }

    @Override
    public void guardar(Asignacion asignacion) { lista.add(asignacion); }

    @Override
    public Asignacion buscarPorId(Integer id) {
        for (Asignacion a : lista)
            if (a.getIdAsignacion() == id) return a;
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        for (Asignacion a : lista) {
            if (a.getIdAsignacion() == id) { lista.remove(a); break; }
        }
    }

    @Override
    public List<Asignacion> buscarPorCarrera(Integer idCarrera) { return lista; }

    @Override
    public List<Asignacion> buscarPorSemestre(Integer idSemestre) { return lista; }

    @Override
   // public List<Asignacion> buscarPorTutor(Integer idUsuario) { return lista; }
    public List<Asignacion> buscarPorTutor(Integer idTutor) { return lista; }
    
    @Override
    public List<Asignacion> buscarActivas() {
        List<Asignacion> activas = new LinkedList<>();
        for (Asignacion a : lista) {
            if (a.getEstatus() == 1) activas.add(a);
        }
        return activas;
    }
    @Override
    public List<Asignacion> buscarPorTutorUsername(String username) { return null; }
}