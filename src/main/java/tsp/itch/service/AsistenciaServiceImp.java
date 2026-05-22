package tsp.itch.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;
import tsp.itch.model.Asistencia;

@Service
public class AsistenciaServiceImp implements IAsistenciaService {

    List<Asistencia> lista = new LinkedList<>();

    @Override
    public List<Asistencia> buscarTodos() { return lista; }

    @Override
    public void guardar(Asistencia asistencia) { lista.add(asistencia); }

    @Override
    public Asistencia buscarPorId(Integer id) {
        for (Asistencia a : lista)
            if (a.getIdAsistencia() == id) return a;
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        for (Asistencia a : lista) {
            if (a.getIdAsistencia() == id) { lista.remove(a); break; }
        }
    }
    
    @Override
    public List<Asistencia> buscarPorSesion(Integer idSesion) { return lista; }

    @Override
    public List<Asistencia> buscarPorTutorado(Integer idTutorado) { return lista; }

    @Override
    public long contarPresencias(Integer idTutorado) { return 0; }

    @Override
    public long contarTotalSesiones(Integer idTutorado) { return 0; }

    @Override
    public double calcularPorcentaje(Integer idTutorado) { return 0.0; }

    @Override
    public long contarPresenciasPorAsignacion(Integer idTutorado, Integer idAsignacion) { return 0; }

    @Override
    public double calcularPorcentajePorAsignacion(Integer idTutorado, Integer idAsignacion) { return 0.0; }
}