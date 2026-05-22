package tsp.itch.service.implementJPA;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tsp.itch.model.SesionTutoria;
import tsp.itch.repository.SesionTutoriaRepository;
import tsp.itch.service.ISesionTutoriaService;

@Primary
@Service
public class SesionTutoriaServiceJpa implements ISesionTutoriaService {

    @Autowired
    private SesionTutoriaRepository repoSesion;

    @Override
    public List<SesionTutoria> buscarTodos() {
        return repoSesion.findAll();
    }

    @Override
    public void guardar(SesionTutoria sesion) {
        repoSesion.save(sesion);
    }

    @Override
    public SesionTutoria buscarPorId(Integer id) {
        Optional<SesionTutoria> optional = repoSesion.findById(id);
        if (optional.isPresent()) return optional.get();
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        repoSesion.deleteById(id);
    }
    
    @Override
    public List<SesionTutoria> buscarPorAsignacion(Integer idAsignacion) {
        return repoSesion.findByAsignacionIdAsignacion(idAsignacion);
    }
    
   /* @Override
    public boolean existeFecha(LocalDate fecha) {
        return repoSesion.existsByFecha(fecha);
    }

    @Override
    public boolean existeFechaEnOtra(LocalDate fecha, Integer idSesion) {
        return repoSesion.existsByFechaAndIdSesionNot(fecha, idSesion);
    }*/
    
    @Override
    public boolean existeFecha(LocalDate fecha, Integer idAsignacion) {
        return repoSesion.existsByFechaAndAsignacionIdAsignacion(fecha, idAsignacion);
    }

    @Override
    public boolean existeFechaEnOtra(LocalDate fecha, Integer idAsignacion, Integer idSesion) {
        return repoSesion.existsByFechaAndAsignacionIdAsignacionAndIdSesionNot(fecha, idAsignacion, idSesion);
    }
    @Override
    public List<SesionTutoria> buscarPorActividad(Integer idActividad) {
        return repoSesion.findByActividadIdActividad(idActividad);
    }
}
