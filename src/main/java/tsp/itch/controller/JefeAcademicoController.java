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

import tsp.itch.model.Carrera;
import tsp.itch.model.JefeAcademico;
import tsp.itch.model.Usuario;
import tsp.itch.service.IJefeAcademicoService;
import tsp.itch.service.IUsuarioService;
import tsp.itch.service.ICarreraService;

@RequestMapping("/jefe")
@Controller
public class JefeAcademicoController {

    @Autowired
    private IJefeAcademicoService jefeService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private ICarreraService carreraService;

    @GetMapping("/jefes")
    public String mostrarJefes(Model model) {
        List<JefeAcademico> lista = jefeService.buscarTodos();
        model.addAttribute("jefes", lista);
        return "jefe/datosJefe";
    }

    @GetMapping("/jefes/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("jefe", new JefeAcademico());
        model.addAttribute("usuarios", usuarioService.buscarActivosPorRol("JEFE_ACADEMICO"));
        model.addAttribute("carreras", carreraService.buscarActivas());
        return "jefe/formJefe";
    }

    @PostMapping("/jefes/guardar")
    public String guardarJefe(JefeAcademico jefe, RedirectAttributes attributes) {
        Usuario usuario = usuarioService.buscarPorId(
            jefe.getUsuario().getIdUsuario()
        );
        jefe.setUsuario(usuario);

        Carrera carrera = carreraService.buscarPorId(
            jefe.getCarrera().getIdCarrera()
        );
        jefe.setCarrera(carrera);

        jefeService.guardar(jefe);
        attributes.addFlashAttribute("msg", "Jefe Académico guardado correctamente");
        return "redirect:/jefe/jefes";
    }

    @GetMapping("/ver/{id}")
    public String verDetalle(@PathVariable("id") int id, Model model) {
        JefeAcademico jefe = jefeService.buscarPorId(id);
        model.addAttribute("jefe", jefe);
        return "jefe/detalleJefe";
    }

    @GetMapping("/editar/{id}")
    public String editarJefe(@PathVariable("id") int id, Model model) {
        JefeAcademico jefe = jefeService.buscarPorId(id);
        model.addAttribute("jefe", jefe);
        model.addAttribute("usuarios", usuarioService.buscarActivosPorRol("JEFE_ACADEMICO"));
        model.addAttribute("carreras", carreraService.buscarActivas());
        return "jefe/formJefe";
    }

    @GetMapping("/eliminar")
    public String eliminarJefe(@RequestParam Integer id, RedirectAttributes attributes) {
        jefeService.eliminar(id);
        attributes.addFlashAttribute("msg", "Jefe Académico eliminado correctamente");
        return "redirect:/jefe/jefes";
    }
}