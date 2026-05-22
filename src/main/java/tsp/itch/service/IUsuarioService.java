package tsp.itch.service;

import java.util.List;
import tsp.itch.model.Usuario;

public interface IUsuarioService {
    List<Usuario> buscarTodos();
    void guardar(Usuario usuario);
    Usuario buscarPorId(Integer id);
    void eliminar(Integer id);
    List<Usuario> buscarPorRol(String nombreRol);
    List<Usuario> buscarActivos();
    List<Usuario> buscarActivosPorRol(String rol);
    Usuario buscarPorNumeroTrabajador(String numeroTrabajador);
}