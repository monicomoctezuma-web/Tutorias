package tsp.itch.service;

import java.time.LocalDate;
import java.util.List;
import tsp.itch.model.SesionTutoria;

public interface ISesionTutoriaService {
    List<SesionTutoria> buscarTodos();
    void guardar(SesionTutoria sesion);
    SesionTutoria buscarPorId(Integer id);
    void eliminar(Integer id);
    List<SesionTutoria> buscarPorAsignacion(Integer idAsignacion);
   // boolean existeFecha(LocalDate fecha);
    //boolean existeFechaEnOtra(LocalDate fecha, Integer idSesion);
    boolean existeFecha(LocalDate fecha, Integer idAsignacion);
    boolean existeFechaEnOtra(LocalDate fecha, Integer idAsignacion, Integer idSesion);
    List<SesionTutoria> buscarPorActividad(Integer idActividad);
}