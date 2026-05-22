package tsp.itch.service.implementJPA;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tsp.itch.model.Pat;
import tsp.itch.repository.PatRepository;
import tsp.itch.service.IPatService;

@Primary
@Service
public class PatServiceJpa implements IPatService {

    @Autowired
    private PatRepository repoPat;

    @Override
    public List<Pat> buscarTodas() { return repoPat.findAll(); }

    @Override
    public void guardar(Pat pat) { repoPat.save(pat); }

    @Override
    public Pat buscarPorId(Integer id) {
        Optional<Pat> optional = repoPat.findById(id);
        if (optional.isPresent()) return optional.get();
        return null;
    }

    @Override
    public void eliminar(Integer id) { repoPat.deleteById(id); }
    @Override
    public List<Pat> buscarActivas() {
        return repoPat.findByEstatus(1);
    }
}