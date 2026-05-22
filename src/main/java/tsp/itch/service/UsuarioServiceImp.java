package tsp.itch.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;
import tsp.itch.model.Usuario;

@Service
public class UsuarioServiceImp implements IUsuarioService {

    List<Usuario> lista = new LinkedList<>();

    @Override
    public List<Usuario> buscarTodos() { return lista; }

    @Override
    public void guardar(Usuario usuario) { lista.add(usuario); }

    @Override
    public Usuario buscarPorId(Integer id) {
        for(Usuario u : lista)
            if(u.getIdUsuario() == id) return u;
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        for(Usuario u : lista) {
            if(u.getIdUsuario() == id) { lista.remove(u); break; }
        }
    }
    
    @Override
    public List<Usuario> buscarPorRol(String nombreRol) {
        List<Usuario> resultado = new LinkedList<>();
        for (Usuario u : lista) {
            if (u.getRol() != null && u.getRol().getNombre().equals(nombreRol)) {
                resultado.add(u);
            }
        }
        return resultado;
    }
    @Override
    public List<Usuario> buscarActivos() {
        List<Usuario> activos = new LinkedList<>();
        for (Usuario u : lista) {
            if (u.getEstatus() == 1) activos.add(u);
        }
        return activos;
    }
    
    @Override
    public List<Usuario> buscarActivosPorRol(String rol) {
        List<Usuario> activos = new LinkedList<>();
        for (Usuario u : lista) {
            if (u.getRol().getNombre().equals(rol) && u.getEstatus() == 1) activos.add(u);
        }
        return activos;
    }
    
    @Override
    public Usuario buscarPorNumeroTrabajador(String numeroTrabajador) { return null; }
}