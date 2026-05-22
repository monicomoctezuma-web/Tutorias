package tsp.itch.service.implementJPA;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tsp.itch.model.Actividad;
import tsp.itch.repository.ActividadRepository;
import tsp.itch.service.IActividadService;

@Primary
@Service
public class ActividadServiceJpa implements IActividadService {

    @Autowired
    private ActividadRepository repoActividad;

    @Override
    public List<Actividad> buscarTodas() { return repoActividad.findAll(); }

    @Override
    public void guardar(Actividad actividad) { repoActividad.save(actividad); }

    @Override
    public Actividad buscarPorId(Integer id) {
        Optional<Actividad> optional = repoActividad.findById(id);
        if (optional.isPresent()) return optional.get();
        return null;
    }

    @Override
    public void eliminar(Integer id) { repoActividad.deleteById(id); }

    @Override
    public List<Actividad> buscarPorPat(Integer idPat) { // NUEVO
    	return repoActividad.findByPatIdPat(idPat); // ← cambiar esto
    }
    @Override
    public List<Actividad> buscarPorPatCarrera(Integer idPatCarrera) {
        return repoActividad.findByPatCarreraIdPatCarrera(idPatCarrera);
    }
    
    @Override
    public List<Actividad> buscarPorFecha(LocalDate fecha) {
        return repoActividad.findByFechaAndPatIsNotNull(fecha);
    }

}