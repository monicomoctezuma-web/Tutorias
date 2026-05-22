package tsp.itch.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;
import tsp.itch.model.JefeAcademico;

@Service
public class JefeAcademicoServiceImp implements IJefeAcademicoService {

    List<JefeAcademico> lista = new LinkedList<>();

    @Override
    public List<JefeAcademico> buscarTodos() { return lista; }

    @Override
    public void guardar(JefeAcademico jefe) { lista.add(jefe); }

    @Override
    public JefeAcademico buscarPorId(Integer id) {
        for (JefeAcademico j : lista)
            if (j.getIdJefe() == id) return j;
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        for (JefeAcademico j : lista) {
            if (j.getIdJefe() == id) { lista.remove(j); break; }
        }
    }

    @Override
    public JefeAcademico buscarPorUsername(String username) {
        for (JefeAcademico j : lista)
            if (j.getUsuario().getUsername().equals(username)) return j;
        return null;
    }
}