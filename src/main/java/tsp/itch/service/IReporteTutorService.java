package tsp.itch.service;

import java.util.List;
import tsp.itch.model.ReporteTutor;

public interface IReporteTutorService {
    List<ReporteTutor> buscarTodos();
    void guardar(ReporteTutor reporte);
    ReporteTutor buscarPorId(Integer id);
    void eliminar(Integer id);
    List<ReporteTutor> buscarPorSesion(Integer idSesion);
    
    List<ReporteTutor> buscarPorTutorUsername(String username);
    List<ReporteTutor> buscarPorCarrera(Integer idCarrera);
}