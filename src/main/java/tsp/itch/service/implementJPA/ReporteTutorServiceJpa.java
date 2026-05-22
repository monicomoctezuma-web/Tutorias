package tsp.itch.service.implementJPA;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tsp.itch.model.ReporteTutor;
import tsp.itch.repository.ReporteTutorRepository;
import tsp.itch.service.IReporteTutorService;

@Primary
@Service
public class ReporteTutorServiceJpa implements IReporteTutorService {

    @Autowired
    private ReporteTutorRepository repoReporte;

    @Override
    public List<ReporteTutor> buscarTodos() {
        return repoReporte.findAll();
    }

    @Override
    public void guardar(ReporteTutor reporte) {
        repoReporte.save(reporte);
    }

    @Override
    public ReporteTutor buscarPorId(Integer id) {
        Optional<ReporteTutor> optional = repoReporte.findById(id);
        if (optional.isPresent()) return optional.get();
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        repoReporte.deleteById(id);
    }
    
    @Override
    public List<ReporteTutor> buscarPorSesion(Integer idSesion) {
        return repoReporte.findBySesionIdSesion(idSesion);
    } 
    
    @Override
    public List<ReporteTutor> buscarPorTutorUsername(String username) {
        return repoReporte.findBySesion_Asignacion_Tutor_Username(username);
    }
    @Override
    public List<ReporteTutor> buscarPorCarrera(Integer idCarrera) {
        return repoReporte.findBySesion_Asignacion_Carrera_IdCarrera(idCarrera);
    }
}