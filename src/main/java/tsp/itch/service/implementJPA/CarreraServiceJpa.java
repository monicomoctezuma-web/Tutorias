package tsp.itch.service.implementJPA;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tsp.itch.model.Carrera;
import tsp.itch.repository.CarreraRepository;
import tsp.itch.service.ICarreraService;

@Primary
@Service
public class CarreraServiceJpa implements ICarreraService {

    @Autowired
    private CarreraRepository repoCarrera;

    @Override
    public List<Carrera> buscarTodas() {
        return repoCarrera.findAll();
    }

    @Override
    public void guardar(Carrera carrera) {
        repoCarrera.save(carrera);
    }

    @Override
    public Carrera buscarPorId(Integer id) {
        Optional<Carrera> optional = repoCarrera.findById(id);
        if(optional.isPresent()) return optional.get();
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        repoCarrera.deleteById(id);
    }
    @Override
    public List<Carrera> buscarActivas() {
        return repoCarrera.findByEstatus(1);
    }
}