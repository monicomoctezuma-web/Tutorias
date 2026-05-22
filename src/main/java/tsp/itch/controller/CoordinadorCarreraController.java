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
import tsp.itch.model.CoordinadorCarrera;
import tsp.itch.model.Usuario;
import tsp.itch.service.ICoordinadorCarreraService;
import tsp.itch.service.IUsuarioService;
import tsp.itch.service.ICarreraService;

@RequestMapping("/coordinador")
@Controller
public class CoordinadorCarreraController {

    @Autowired
    private ICoordinadorCarreraService coordinadorService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private ICarreraService carreraService;

    @GetMapping("/coordinadores")
    public String mostrarCoordinadores(Model model) {
        List<CoordinadorCarrera> lista = coordinadorService.buscarTodos();
        model.addAttribute("coordinadores", lista);
        return "coordinador/datosCoordinador";
    }
    /*

    @GetMapping("/coordinadores/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("coordinador", new CoordinadorCarrera());
        model.addAttribute("usuarios", usuarioService.buscarTodos());
        model.addAttribute("carreras", carreraService.buscarTodas());
        return "coordinador/formCoordinador";
    }
*/
    @GetMapping("/coordinadores/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("coordinador", new CoordinadorCarrera());
        model.addAttribute("usuarios", usuarioService.buscarActivosPorRol("COORDINADOR_CARRERA"));
        // model.addAttribute("usuarios", usuarioService.buscarPorRol("COORDINADOR_CARRERA"));  // <-- cambia esto
       // model.addAttribute("carreras", carreraService.buscarTodas());
     // En mostrarFormulario:
        model.addAttribute("carreras", carreraService.buscarActivas());
        return "coordinador/formCoordinador";
    }
  
    
   /* @PostMapping("/coordinadores/guardar")
    public String guardarCoordinador(CoordinadorCarrera coordinador,
            RedirectAttributes attributes) {
        coordinadorService.guardar(coordinador);
        attributes.addFlashAttribute("msg", "Coordinador guardado correctamente");
        return "redirect:/coordinador/coordinadores";
    }*/
    @PostMapping("/coordinadores/guardar")
    public String guardarCoordinador(CoordinadorCarrera coordinador,
            RedirectAttributes attributes) {

        // Cargar el usuario completo desde la BD
        Usuario usuario = usuarioService.buscarPorId(
            coordinador.getUsuario().getIdUsuario()
        );
        coordinador.setUsuario(usuario);

        // Cargar la carrera completa desde la BD
        Carrera carrera = carreraService.buscarPorId(
            coordinador.getCarrera().getIdCarrera()
        );
        coordinador.setCarrera(carrera);

        coordinadorService.guardar(coordinador);
        attributes.addFlashAttribute("msg", "Coordinador guardado correctamente");
        return "redirect:/coordinador/coordinadores";
    }

    

    @GetMapping("/ver/{id}")
    public String verDetalle(@PathVariable("id") int id, Model model) {
        CoordinadorCarrera coordinador = coordinadorService.buscarPorId(id);
        model.addAttribute("coordinador", coordinador);
        return "coordinador/detalleCoordinador";
    }

  /*  @GetMapping("/editar/{id}")
    public String editarCoordinador(@PathVariable("id") int id, Model model) {
        CoordinadorCarrera coordinador = coordinadorService.buscarPorId(id);
        model.addAttribute("coordinador", coordinador);
        model.addAttribute("usuarios", usuarioService.buscarTodos());
        model.addAttribute("carreras", carreraService.buscarTodas());
        return "coordinador/formCoordinador";
    }*/
    @GetMapping("/editar/{id}")
    public String editarCoordinador(@PathVariable("id") int id, Model model) {
        CoordinadorCarrera coordinador = coordinadorService.buscarPorId(id);
        model.addAttribute("coordinador", coordinador);
        model.addAttribute("usuarios", usuarioService.buscarActivosPorRol("COORDINADOR_CARRERA"));
        // model.addAttribute("usuarios", usuarioService.buscarPorRol("COORDINADOR_CARRERA"));  // <-- cambia esto
      //  model.addAttribute("carreras", carreraService.buscarTodas());
     // En editarCoordinador:
        model.addAttribute("carreras", carreraService.buscarActivas()); 
        return "coordinador/formCoordinador";
    }

    @GetMapping("/eliminar")
    public String eliminarCoordinador(@RequestParam Integer id,
            RedirectAttributes attributes) {
        coordinadorService.eliminar(id);
        attributes.addFlashAttribute("msg", "Coordinador eliminado correctamente");
        return "redirect:/coordinador/coordinadores";
    }
    
    @GetMapping("/inicioCoordinadorInstitucional")
    public String inicioCoordinadorInstitucional() {
        return "coordinador/inicioCoordinadorInstitucional";
    }
    
    @GetMapping("/inicioCoordinadorCarrera")
    public String inicioCoordinadorCarrera() {
        return "coordinador/inicioCoordinadorCarrera";
    }
    
    @GetMapping("/inicioJefeAcademico")
    public String inicioJefeAcademico() {
        return "jefe/inicioJefeAcademico";
    }
    
    @GetMapping("/inicioDesarrolloAcademico")
    public String inicioDesarrolloAcademico() {
        return "desarrollo/inicioDesarrolloAcademico";
    }
    
    
}