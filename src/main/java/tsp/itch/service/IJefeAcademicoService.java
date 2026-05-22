package tsp.itch.service;
import java.util.List;
import tsp.itch.model.JefeAcademico;

public interface IJefeAcademicoService {
    List<JefeAcademico> buscarTodos();
    void guardar(JefeAcademico jefe);
    JefeAcademico buscarPorId(Integer id);
    void eliminar(Integer id);
    JefeAcademico buscarPorUsername(String username);
}