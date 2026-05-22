package tsp.itch.service.implementJPA;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tsp.itch.model.Rol;
import tsp.itch.repository.RolRepository;
import tsp.itch.service.IRolService;

@Primary
@Service
public class RolServiceJpa implements IRolService {

    @Autowired
    private RolRepository repoRol;

    @Override
    public List<Rol> buscarTodos() {
        return repoRol.findAll();
    }

    @Override
    public void guardar(Rol rol) {
        repoRol.save(rol);
    }

    @Override
    public Rol buscarPorId(Integer id) {
        Optional<Rol> optional = repoRol.findById(id);
        if(optional.isPresent()) return optional.get();
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        repoRol.deleteById(id);
    }
}