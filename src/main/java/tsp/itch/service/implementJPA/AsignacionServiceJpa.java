package tsp.itch.service.implementJPA;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tsp.itch.model.Asignacion;
import tsp.itch.repository.AsignacionRepository;
import tsp.itch.service.IAsignacionService;

@Primary
@Service
public class AsignacionServiceJpa implements IAsignacionService {

    @Autowired
    private AsignacionRepository repoAsignacion;

    @Override
    public List<Asignacion> buscarTodas() {
        return repoAsignacion.findAll();
    }

    @Override
    public void guardar(Asignacion asignacion) {
        repoAsignacion.save(asignacion);
    }

    @Override
    public Asignacion buscarPorId(Integer id) {
        Optional<Asignacion> optional = repoAsignacion.findById(id);
        if (optional.isPresent()) return optional.get();
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        repoAsignacion.deleteById(id);
    }

    @Override
    public List<Asignacion> buscarPorCarrera(Integer idCarrera) {
        return repoAsignacion.findByCarreraIdCarrera(idCarrera);
    }

    @Override
    public List<Asignacion> buscarPorSemestre(Integer idSemestre) {
        return repoAsignacion.findBySemestreIdSemestre(idSemestre);
    }

    @Override
    /*public List<Asignacion> buscarPorTutor(Integer idUsuario) {
        return repoAsignacion.findByTutorIdUsuario(idUsuario);
    }*/
    public List<Asignacion> buscarPorTutor(Integer idTutor) {
        return repoAsignacion.findByTutorIdTutor(idTutor);
    }
    @Override
    public List<Asignacion> buscarActivas() {
        return repoAsignacion.findByEstatus(1);
    }
    
    @Override
    public List<Asignacion> buscarPorTutorUsername(String username) {
        return repoAsignacion.findByTutor_Username(username);
    }
}