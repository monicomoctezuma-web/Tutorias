package tsp.itch.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;
import tsp.itch.model.Tutor;

@Service
public class TutorServiceImp implements ITutorService {

    List<Tutor> lista = new LinkedList<>();

    @Override
    public List<Tutor> buscarTodos() { return lista; }

    @Override
    public void guardar(Tutor tutor) { lista.add(tutor); }

    @Override
    public Tutor buscarPorId(Integer id) {
        for (Tutor t : lista)
            if (t.getIdTutor() == id) return t;
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        for (Tutor t : lista) {
            if (t.getIdTutor() == id) { lista.remove(t); break; }
        }
    }

    @Override
    public List<Tutor> buscarPorCarrera(Integer idCarrera) {
        List<Tutor> resultado = new LinkedList<>();
        for (Tutor t : lista) {
            if (t.getCarrera() != null && t.getCarrera().getIdCarrera().equals(idCarrera)) {
                resultado.add(t);
            }
        }
        return resultado;
    }
    
    @Override
    public Tutor buscarPorNumeroTrabajador(String numeroTrabajador) { return null; }
    
    @Override
    public Tutor buscarPorUsername(String username) { return null; }
    
    @Override
    public List<Tutor> buscarActivos() {
        List<Tutor> resultado = new LinkedList<>();
        for (Tutor t : lista) {
            if (t.getEstatus() == 1) resultado.add(t);
        }
        return resultado;
    }
    
    @Override
    public List<Tutor> buscarActivosPorCarrera(Integer idCarrera) {
        List<Tutor> resultado = new LinkedList<>();
        for (Tutor t : lista) {
            if (t.getEstatus() == 1 && t.getCarrera() != null 
                && t.getCarrera().getIdCarrera().equals(idCarrera)) {
                resultado.add(t);
            }
        }
        return resultado;
    }
    
    
}