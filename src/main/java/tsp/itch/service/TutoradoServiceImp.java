package tsp.itch.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;
import tsp.itch.model.Tutorado;

@Service
public class TutoradoServiceImp implements ITutoradoService {

    List<Tutorado> lista = new LinkedList<>();

    @Override
    public List<Tutorado> buscarTodos() { return lista; }

    @Override
    public void guardar(Tutorado tutorado) { lista.add(tutorado); }

    @Override
    public Tutorado buscarPorId(Integer id) {
        for(Tutorado t : lista)
            if(t.getIdTutorado() == id) return t;
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        for(Tutorado t : lista) {
            if(t.getIdTutorado() == id) { lista.remove(t); break; }
        }
    }
    
    @Override
    public List<Tutorado> buscarPorCarrera(Integer idCarrera) {
        List<Tutorado> resultado = new LinkedList<>();
        for (Tutorado t : lista) {
            if (t.getCarrera() != null && t.getCarrera().getIdCarrera().equals(idCarrera)) {
                resultado.add(t);
            }
        }
        return resultado;
    }
}