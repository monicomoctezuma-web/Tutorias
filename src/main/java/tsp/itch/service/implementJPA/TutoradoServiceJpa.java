package tsp.itch.service.implementJPA;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tsp.itch.model.Tutorado;
import tsp.itch.repository.TutoradoRepository;
import tsp.itch.service.ITutoradoService;

@Primary
@Service
public class TutoradoServiceJpa implements ITutoradoService {

    @Autowired
    private TutoradoRepository repoTutorado;

    @Override
    public List<Tutorado> buscarTodos() {
        return repoTutorado.findAll();
    }

    @Override
    public void guardar(Tutorado tutorado) {
        repoTutorado.save(tutorado);
    }

    @Override
    public Tutorado buscarPorId(Integer id) {
        Optional<Tutorado> optional = repoTutorado.findById(id);
        if(optional.isPresent()) return optional.get();
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        repoTutorado.deleteById(id);
    }
    
    @Override
    public List<Tutorado> buscarPorCarrera(Integer idCarrera) {
        return repoTutorado.findByCarreraIdCarrera(idCarrera);
    }
}