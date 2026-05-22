package tsp.itch.service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;
import tsp.itch.model.SesionTutoria;

@Service
public class SesionTutoriaServiceImp implements ISesionTutoriaService {

    List<SesionTutoria> lista = new LinkedList<>();

    @Override
    public List<SesionTutoria> buscarTodos() { return lista; }

    @Override
    public void guardar(SesionTutoria sesion) { lista.add(sesion); }

    @Override
    public SesionTutoria buscarPorId(Integer id) {
        for (SesionTutoria s : lista)
            if (s.getIdSesion() == id) return s;
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        for (SesionTutoria s : lista) {
            if (s.getIdSesion() == id) { lista.remove(s); break; }
        }
    }
    @Override
    public List<SesionTutoria> buscarPorAsignacion(Integer idAsignacion) { return lista; }


/*@Override
public boolean existeFecha(LocalDate fecha) { return false; }

@Override
public boolean existeFechaEnOtra(LocalDate fecha, Integer idSesion) { return false; }}*/
    @Override
    public boolean existeFecha(LocalDate fecha, Integer idAsignacion) { return false; }

    @Override
    public boolean existeFechaEnOtra(LocalDate fecha, Integer idAsignacion, Integer idSesion) { return false; }
    
    @Override
    public List<SesionTutoria> buscarPorActividad(Integer idActividad) {
        return new java.util.ArrayList<>();
    }
}


    