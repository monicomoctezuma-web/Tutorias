package tsp.itch.service.implementJPA;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tsp.itch.model.AsignacionTutorado;
import tsp.itch.repository.AsignacionTutoradoRepository;
import tsp.itch.service.IAsignacionTutoradoService;

@Primary
@Service
public class AsignacionTutoradoServiceJpa implements IAsignacionTutoradoService {

    @Autowired
    private AsignacionTutoradoRepository repoAsignacionTutorado;

    @Override
    public List<AsignacionTutorado> buscarTodas() {
        return repoAsignacionTutorado.findAll();
    }

    @Override
    public void guardar(AsignacionTutorado asignacionTutorado) {
        repoAsignacionTutorado.save(asignacionTutorado);
    }

    @Override
    public AsignacionTutorado buscarPorId(Integer id) {
        Optional<AsignacionTutorado> optional = repoAsignacionTutorado.findById(id);
        if (optional.isPresent()) return optional.get();
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        repoAsignacionTutorado.deleteById(id);
    }

    @Override
    public List<AsignacionTutorado> buscarPorAsignacion(Integer idAsignacion) {
        return repoAsignacionTutorado.findByAsignacionIdAsignacion(idAsignacion);
    }

    @Override
    public List<AsignacionTutorado> buscarPorTutorado(Integer idTutorado) {
        return repoAsignacionTutorado.findByTutoradoIdTutorado(idTutorado);
    }
}