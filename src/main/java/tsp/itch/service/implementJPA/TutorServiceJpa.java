package tsp.itch.service.implementJPA;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tsp.itch.model.Tutor;
import tsp.itch.repository.TutorRepository;
import tsp.itch.service.ITutorService;

@Primary
@Service
public class TutorServiceJpa implements ITutorService {

    @Autowired
    private TutorRepository repoTutor;

    @Override
    public List<Tutor> buscarTodos() {
        return repoTutor.findAll();
    }

    @Override
    public void guardar(Tutor tutor) {
        repoTutor.save(tutor);
    }

    @Override
    public Tutor buscarPorId(Integer id) {
        Optional<Tutor> optional = repoTutor.findById(id);
        if (optional.isPresent()) return optional.get();
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        repoTutor.deleteById(id);
    }

    @Override
    public List<Tutor> buscarPorCarrera(Integer idCarrera) {
        return repoTutor.findByCarreraIdCarrera(idCarrera);
    }
    
    @Override
    public Tutor buscarPorNumeroTrabajador(String numeroTrabajador) {
        return repoTutor.findByNumeroTrabajador(numeroTrabajador);
    }
    
    @Override
    public Tutor buscarPorUsername(String username) {
        return repoTutor.findByUsername(username);
    }
    
    @Override
    public List<Tutor> buscarActivos() {
        return repoTutor.findByEstatus(1);
    }
    @Override
    public List<Tutor> buscarActivosPorCarrera(Integer idCarrera) {
        return repoTutor.findByEstatusAndCarreraIdCarrera(1, idCarrera);
    }
}