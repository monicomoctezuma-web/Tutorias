package tsp.itch.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tsp.itch.model.CoordinadorCarrera;
import tsp.itch.model.Necesidad;
import tsp.itch.service.INecesidadService;
import tsp.itch.service.IAsignacionService;
import tsp.itch.service.ICoordinadorCarreraService;

@RequestMapping("/necesidad")
@Controller
public class NecesidadController {

    @Autowired
    private INecesidadService necesidadService;

    @Autowired
    private IAsignacionService asignacionService;
    
    @Autowired 
    private ICoordinadorCarreraService coordinadorService;

   /* @GetMapping("/necesidades")
    public String mostrarNecesidades(Model model) {
        List<Necesidad> lista = necesidadService.buscarTodos();
        model.addAttribute("necesidades", lista);
        return "necesidad/datosNecesidad";
    }*/
    
    @GetMapping("/necesidades")
    public String mostrarNecesidades(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        boolean esTutor = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_TUTOR"));
        boolean esCoordinadorCarrera = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_COORDINADOR_CARRERA"));

        List<Necesidad> lista;
        if (esTutor) {
            lista = necesidadService.buscarPorTutorUsername(username);
        } else if (esCoordinadorCarrera) {
            CoordinadorCarrera coordinador = coordinadorService.buscarPorUsername(username);
            lista = necesidadService.buscarPorCarrera(coordinador.getCarrera().getIdCarrera());
        } else {
            lista = necesidadService.buscarTodos();
        }
        model.addAttribute("necesidades", lista);
        return "necesidad/datosNecesidad";
    }

   /* @GetMapping("/necesidades/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("necesidad", new Necesidad());
        model.addAttribute("asignaciones", asignacionService.buscarTodas());
        return "necesidad/formNecesidad";
    }*/
    @GetMapping("/necesidades/nuevo")
    public String mostrarFormulario(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        boolean esTutor = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_TUTOR"));

        model.addAttribute("necesidad", new Necesidad());
        if (esTutor) {
            model.addAttribute("asignaciones", asignacionService.buscarPorTutorUsername(username));
        } else {
            model.addAttribute("asignaciones", asignacionService.buscarTodas());
        }
        return "necesidad/formNecesidad";
    }

    @PostMapping("/necesidades/guardar")
    public String guardarNecesidad(Necesidad necesidad,
            RedirectAttributes attributes) {
        necesidadService.guardar(necesidad);
        attributes.addFlashAttribute("msg", "Necesidad guardada correctamente");
        return "redirect:/necesidad/necesidades";
    }

    @GetMapping("/ver/{id}")
    public String verDetalle(@PathVariable("id") int id, Model model) {
        Necesidad necesidad = necesidadService.buscarPorId(id);
        model.addAttribute("necesidad", necesidad);
        return "necesidad/detalleNecesidad";
    }

    @GetMapping("/editar/{id}")
    public String editarNecesidad(@PathVariable("id") int id, Model model) {
        Necesidad necesidad = necesidadService.buscarPorId(id);
        model.addAttribute("necesidad", necesidad);
        model.addAttribute("asignaciones", asignacionService.buscarTodas());
        return "necesidad/formNecesidad";
    }

    @GetMapping("/eliminar")
    public String eliminarNecesidad(@RequestParam Integer id,
            RedirectAttributes attributes) {
        necesidadService.eliminar(id);
        attributes.addFlashAttribute("msg", "Necesidad eliminada correctamente");
        return "redirect:/necesidad/necesidades";
    }
    
    
}