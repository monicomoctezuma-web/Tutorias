package tsp.itch.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tsp.itch.model.PatCarrera;
import tsp.itch.repository.PatCarreraRepository;

@Service
public class PatCarreraServiceImpl implements IPatCarreraService {

    @Autowired
    private PatCarreraRepository patCarreraRepository;

    @Override
    public List<PatCarrera> buscarTodas() {
        return patCarreraRepository.findAll();
    }

    @Override
    public PatCarrera buscarPorId(Integer id) {
        return patCarreraRepository.findById(id).orElse(null);
    }

    @Override
    public void guardar(PatCarrera patCarrera) {
        patCarreraRepository.save(patCarrera);
    }

    @Override
    public void eliminar(Integer id) {
        patCarreraRepository.deleteById(id);
    }

    @Override
    public List<PatCarrera> buscarPorCarrera(Integer idCarrera) {
        return patCarreraRepository.findByCarreraIdCarrera(idCarrera);
    }

    @Override
    public List<PatCarrera> buscarPorPat(Integer idPat) {
        return patCarreraRepository.findByPatIdPat(idPat);
    }
    
    @Override
    public PatCarrera buscarPorCarreraYSemestre(Integer idCarrera, Integer idSemestre) {
        return null; // implementación en memoria vacía
    }
}