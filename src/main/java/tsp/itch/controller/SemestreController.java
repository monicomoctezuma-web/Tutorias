package tsp.itch.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tsp.itch.model.Semestre;
import tsp.itch.service.ISemestreService;

@RequestMapping("/semestre")
@Controller
public class SemestreController {

    @Autowired
    private ISemestreService semestreService;

    @GetMapping("/semestres")
    public String mostrarSemestres(Model model) {
        List<Semestre> lista = semestreService.buscarTodos();
        model.addAttribute("semestres", lista);
        return "semestre/datosSemestre";
    }

    @GetMapping("/semestres/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("semestre", new Semestre());
        return "semestre/formSemestre";
    }

    @PostMapping("/semestres/guardar")
    public String guardarSemestre(Semestre semestre, RedirectAttributes attributes) {
        if (semestre.getIdSemestre() == null) {
            semestre.setActivo(1);
        }
        semestreService.guardar(semestre);
        attributes.addFlashAttribute("msg", "Semestre guardado correctamente");
        return "redirect:/semestre/semestres";
    }

    @GetMapping("/ver/{id}")
    public String verDetalle(@PathVariable("id") int id, Model model) {
        Semestre semestre = semestreService.buscarPorId(id);
        model.addAttribute("semestre", semestre);
        return "semestre/detalleSemestre";
    }

    @GetMapping("/editar/{id}")
    public String editarSemestre(@PathVariable("id") int id, Model model) {
        Semestre semestre = semestreService.buscarPorId(id);
        model.addAttribute("semestre", semestre);
        return "semestre/formSemestre";
    }

    @GetMapping("/eliminar")
    public String eliminarSemestre(@RequestParam Integer id, RedirectAttributes attributes) {
        Semestre semestre = semestreService.buscarPorId(id);
        semestre.setActivo(0);
        semestreService.guardar(semestre);
        attributes.addFlashAttribute("msg", "Semestre eliminado correctamente");
        return "redirect:/semestre/semestres";
    }
    
    @GetMapping("/activar")
    public String activarSemestre(@RequestParam Integer id, RedirectAttributes attributes) {
        Semestre semestre = semestreService.buscarPorId(id);
        semestre.setActivo(1);
        semestreService.guardar(semestre);
        attributes.addFlashAttribute("msg", "Semestre activado correctamente");
        return "redirect:/semestre/semestres";
    }
}