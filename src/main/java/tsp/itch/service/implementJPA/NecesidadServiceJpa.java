package tsp.itch.service.implementJPA;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tsp.itch.model.Necesidad;
import tsp.itch.repository.NecesidadRepository;
import tsp.itch.service.INecesidadService;

@Primary
@Service
public class NecesidadServiceJpa implements INecesidadService {

    @Autowired
    private NecesidadRepository repoNecesidad;

    @Override
    public List<Necesidad> buscarTodos() {
        return repoNecesidad.findAll();
    }

    @Override
    public void guardar(Necesidad necesidad) {
        repoNecesidad.save(necesidad);
    }

    @Override
    public Necesidad buscarPorId(Integer id) {
        Optional<Necesidad> optional = repoNecesidad.findById(id);
        if (optional.isPresent()) return optional.get();
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        repoNecesidad.deleteById(id);
    }
    
    @Override
    public List<Necesidad> buscarPorTutorUsername(String username) {
    	return repoNecesidad.findByAsignacion_Tutor_Username(username);
    }
    @Override
    public List<Necesidad> buscarPorCarrera(Integer idCarrera) {
        return repoNecesidad.findByAsignacion_Carrera_IdCarrera(idCarrera);
    }
}