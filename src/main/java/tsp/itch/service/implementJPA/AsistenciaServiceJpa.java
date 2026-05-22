package tsp.itch.service.implementJPA;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tsp.itch.model.Asistencia;
import tsp.itch.repository.AsistenciaRepository;
import tsp.itch.service.IAsistenciaService;

@Primary
@Service
public class AsistenciaServiceJpa implements IAsistenciaService {

    @Autowired
    private AsistenciaRepository repoAsistencia;

    @Override
    public List<Asistencia> buscarTodos() {
        return repoAsistencia.findAll();
    }

    @Override
    public void guardar(Asistencia asistencia) {
        repoAsistencia.save(asistencia);
    }

    @Override
    public Asistencia buscarPorId(Integer id) {
        Optional<Asistencia> optional = repoAsistencia.findById(id);
        if (optional.isPresent()) return optional.get();
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        repoAsistencia.deleteById(id);
    }

    @Override
    public List<Asistencia> buscarPorSesion(Integer idSesion) {
        return repoAsistencia.findBySesionIdSesion(idSesion);
    }

    @Override
    public List<Asistencia> buscarPorTutorado(Integer idTutorado) {
        return repoAsistencia.findByTutoradoIdTutorado(idTutorado);
    }

    @Override
    public long contarPresencias(Integer idTutorado) {
        return repoAsistencia.contarPresencias(idTutorado);
    }

    @Override
    public long contarTotalSesiones(Integer idTutorado) {
        return repoAsistencia.contarTotalSesiones(idTutorado);
    }

    /*@Override
    public double calcularPorcentaje(Integer idTutorado) {
        long total = repoAsistencia.contarTotalSesiones(idTutorado);
        if (total == 0) return 0.0;
        long presencias = repoAsistencia.contarPresencias(idTutorado);
        return Math.round((presencias * 100.0 / total) * 10.0) / 10.0;
    }
}*/
    @Override
    public double calcularPorcentaje(Integer idTutorado) {
        long presencias = repoAsistencia.contarPresencias(idTutorado);
        // Siempre sobre 10 sesiones que dura el semestre
        return Math.round((presencias * 100.0 / 10) * 10.0) / 10.0;
    }
    
    @Override
    public long contarPresenciasPorAsignacion(Integer idTutorado, Integer idAsignacion) {
        return repoAsistencia.contarPresenciasPorAsignacion(idTutorado, idAsignacion);
    }

    @Override
    public double calcularPorcentajePorAsignacion(Integer idTutorado, Integer idAsignacion) {
        long presencias = repoAsistencia.contarPresenciasPorAsignacion(idTutorado, idAsignacion);
        return Math.round((presencias * 100.0 / 10) * 10.0) / 10.0;
    }
}
    	