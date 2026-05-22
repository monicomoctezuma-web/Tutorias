package tsp.itch.service.implementJPA;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tsp.itch.model.Constancia;
import tsp.itch.repository.ConstanciaRepository;
import tsp.itch.service.IConstanciaService;

@Primary
@Service
public class ConstanciaServiceJpa implements IConstanciaService {

    @Autowired
    private ConstanciaRepository repoConstancia;

    @Override
    public List<Constancia> buscarTodos() {
        return repoConstancia.findAll();
    }

    @Override
    public void guardar(Constancia constancia) {
        repoConstancia.save(constancia);
    }

    @Override
    public Constancia buscarPorId(Integer id) {
        Optional<Constancia> optional = repoConstancia.findById(id);
        if (optional.isPresent()) return optional.get();
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        repoConstancia.deleteById(id);
    }
}