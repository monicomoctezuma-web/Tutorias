package tsp.itch.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;
import tsp.itch.model.ReporteTutor;

@Service
public class ReporteTutorServiceImp implements IReporteTutorService {

    List<ReporteTutor> lista = new LinkedList<>();

    @Override
    public List<ReporteTutor> buscarTodos() { return lista; }

    @Override
    public void guardar(ReporteTutor reporte) { lista.add(reporte); }

    @Override
    public ReporteTutor buscarPorId(Integer id) {
        for (ReporteTutor r : lista)
            if (r.getIdReporte() == id) return r;
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        for (ReporteTutor r : lista) {
            if (r.getIdReporte() == id) { lista.remove(r); break; }
        }
    }
    
    @Override
    public List<ReporteTutor> buscarPorSesion(Integer idSesion) { return lista; }
    
    @Override
    public List<ReporteTutor> buscarPorTutorUsername(String username) { return null; }
    @Override
    public List<ReporteTutor> buscarPorCarrera(Integer idCarrera) { return null; }
}
