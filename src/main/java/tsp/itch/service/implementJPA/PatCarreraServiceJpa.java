package tsp.itch.service.implementJPA;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tsp.itch.model.PatCarrera;
import tsp.itch.repository.PatCarreraRepository;
import tsp.itch.service.IPatCarreraService;

@Primary
@Service
public class PatCarreraServiceJpa implements IPatCarreraService {

    @Autowired
    private PatCarreraRepository repoPatCarrera;

    @Override
    public List<PatCarrera> buscarTodas() {
        return repoPatCarrera.findAll();
    }

    @Override
    public PatCarrera buscarPorId(Integer id) {
        Optional<PatCarrera> optional = repoPatCarrera.findById(id);
        if (optional.isPresent()) return optional.get();
        return null;
    }

    @Override
    public void guardar(PatCarrera patCarrera) {
        repoPatCarrera.save(patCarrera);
    }

    @Override
    public void eliminar(Integer id) {
        repoPatCarrera.deleteById(id);
    }

    @Override
    public List<PatCarrera> buscarPorCarrera(Integer idCarrera) {
        return repoPatCarrera.findByCarreraIdCarrera(idCarrera);
    }

    @Override
    public List<PatCarrera> buscarPorPat(Integer idPat) {
        return repoPatCarrera.findByPatIdPat(idPat);
    }
    @Override
    public PatCarrera buscarPorCarreraYSemestre(Integer idCarrera, Integer idSemestre) {
        PatCarrera result = repoPatCarrera.findByCarreraIdCarreraAndSemestreIdSemestre(idCarrera, idSemestre);
        return result;
    }
}