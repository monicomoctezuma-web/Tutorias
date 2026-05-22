package tsp.itch.service;

import java.time.LocalDate;
import java.util.List;
import tsp.itch.model.Actividad;

public interface IActividadService {
    List<Actividad> buscarTodas();
    void guardar(Actividad actividad);
    Actividad buscarPorId(Integer id);
    void eliminar(Integer id);
    
    //
    List<Actividad> buscarPorPat(Integer idPat); 
    List<Actividad> buscarPorPatCarrera(Integer idPatCarrera);
    List<Actividad> buscarPorFecha(LocalDate fecha);
    
    
}