package tsp.itch.service.implementJPA;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tsp.itch.model.CoordinadorCarrera;
import tsp.itch.repository.CoordinadorCarreraRepository;
import tsp.itch.service.ICoordinadorCarreraService;

@Primary
@Service
public class CoordinadorCarreraServiceJpa implements ICoordinadorCarreraService {

    @Autowired
    private CoordinadorCarreraRepository repoCoordinador;

    @Override
    public List<CoordinadorCarrera> buscarTodos() {
        return repoCoordinador.findAll();
    }

    @Override
    public void guardar(CoordinadorCarrera coordinador) {
        repoCoordinador.save(coordinador);
    }

    @Override
    public CoordinadorCarrera buscarPorId(Integer id) {
        Optional<CoordinadorCarrera> optional = repoCoordinador.findById(id);
        if (optional.isPresent()) return optional.get();
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        repoCoordinador.deleteById(id);
    }
    
    @Override
    public CoordinadorCarrera buscarPorUsername(String username) {
        return repoCoordinador.findByUsuario_Username(username);
    }
}