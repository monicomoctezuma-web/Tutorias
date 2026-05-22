package tsp.itch.service;

import java.util.List;
import tsp.itch.model.PatCarrera;

public interface IPatCarreraService {
    List<PatCarrera> buscarTodas();
    PatCarrera buscarPorId(Integer id);
    void guardar(PatCarrera patCarrera);
    void eliminar(Integer id);
    List<PatCarrera> buscarPorCarrera(Integer idCarrera);
    List<PatCarrera> buscarPorPat(Integer idPat);
    PatCarrera buscarPorCarreraYSemestre(Integer idCarrera, Integer idSemestre);
}