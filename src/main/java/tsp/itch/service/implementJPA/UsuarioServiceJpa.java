package tsp.itch.service.implementJPA;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tsp.itch.model.Usuario;
import tsp.itch.repository.UsuarioRepository;
import tsp.itch.service.IUsuarioService;

@Primary
@Service
public class UsuarioServiceJpa implements IUsuarioService {

    @Autowired
    private UsuarioRepository repoUsuario;

    @Override
    public List<Usuario> buscarTodos() {
        return repoUsuario.findAll();
    }

    @Override
    public void guardar(Usuario usuario) {
        repoUsuario.save(usuario);
    }

    @Override
    public Usuario buscarPorId(Integer id) {
        Optional<Usuario> optional = repoUsuario.findById(id);
        if(optional.isPresent()) return optional.get();
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        repoUsuario.deleteById(id);
    }
    
    @Override
    public List<Usuario> buscarPorRol(String nombreRol) {
        return repoUsuario.findByRolNombre(nombreRol);
    }
    
    @Override
    public List<Usuario> buscarActivos() {
        return repoUsuario.findByEstatus(1);
    }
    
    @Override
    public List<Usuario> buscarActivosPorRol(String rol) {
        return repoUsuario.findByRolNombreAndEstatus(rol, 1);
    }
    
    @Override
    public Usuario buscarPorNumeroTrabajador(String numeroTrabajador) {
        return repoUsuario.findByNumeroTrabajador(numeroTrabajador);
    }
}