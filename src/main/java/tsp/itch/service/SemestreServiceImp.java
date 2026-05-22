package tsp.itch.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;
import tsp.itch.model.Semestre;

@Service
public class SemestreServiceImp implements ISemestreService {

    List<Semestre> lista = new LinkedList<>();

    @Override
    public List<Semestre> buscarTodos() { return lista; }

    @Override
    public void guardar(Semestre semestre) { lista.add(semestre); }

    @Override
    public Semestre buscarPorId(Integer id) {
        for(Semestre s : lista)
            if(s.getIdSemestre() == id) return s;
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        for(Semestre s : lista) {
            if(s.getIdSemestre() == id) { lista.remove(s); break; }
        }
    }
    
    @Override
    public List<Semestre> buscarActivos() {
        List<Semestre> activos = new LinkedList<>();
        for (Semestre s : lista) {
            if (s.getActivo() == 1) activos.add(s);
        }
        return activos;
    }
}