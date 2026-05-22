package tsp.itch.service.implementJPA;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tsp.itch.model.Semestre;
import tsp.itch.repository.SemestreRepository;
import tsp.itch.service.ISemestreService;

@Primary
@Service
public class SemestreServiceJpa implements ISemestreService {

    @Autowired
    private SemestreRepository repoSemestre;

    @Override
    public List<Semestre> buscarTodos() {
        return repoSemestre.findAll();
    }

    @Override
    public void guardar(Semestre semestre) {
        repoSemestre.save(semestre);
    }

    @Override
    public Semestre buscarPorId(Integer id) {
        Optional<Semestre> optional = repoSemestre.findById(id);
        if(optional.isPresent()) return optional.get();
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        repoSemestre.deleteById(id);
    }
    @Override
    public List<Semestre> buscarActivos() {
        return repoSemestre.findByActivo(1);
    }
}
