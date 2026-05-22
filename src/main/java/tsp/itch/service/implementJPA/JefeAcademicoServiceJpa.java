package tsp.itch.service.implementJPA;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tsp.itch.model.JefeAcademico;
import tsp.itch.repository.JefeAcademicoRepository;
import tsp.itch.service.IJefeAcademicoService;

@Primary
@Service
public class JefeAcademicoServiceJpa implements IJefeAcademicoService {

    @Autowired
    private JefeAcademicoRepository repoJefe;

    @Override
    public List<JefeAcademico> buscarTodos() {
        return repoJefe.findAll();
    }

    @Override
    public void guardar(JefeAcademico jefe) {
        repoJefe.save(jefe);
    }

    @Override
    public JefeAcademico buscarPorId(Integer id) {
        return repoJefe.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Integer id) {
        repoJefe.deleteById(id);
    }

    @Override
    public JefeAcademico buscarPorUsername(String username) {
        return repoJefe.findByUsuario_Username(username);
    }
}