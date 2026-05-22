package tsp.itch.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;
import tsp.itch.model.Rol;

@Service
public class RolServiceImp implements IRolService {

    List<Rol> lista = new LinkedList<>();

    @Override
    public List<Rol> buscarTodos() { return lista; }

    @Override
    public void guardar(Rol rol) { lista.add(rol); }

    @Override
    public Rol buscarPorId(Integer id) {
        for(Rol r : lista)
            if(r.getIdRol() == id) return r;
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        for(Rol r : lista) {
            if(r.getIdRol() == id) { lista.remove(r); break; }
        }
    }
}