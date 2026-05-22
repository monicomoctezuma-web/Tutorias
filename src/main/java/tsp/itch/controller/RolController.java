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
import tsp.itch.model.Rol;
import tsp.itch.service.IRolService;

@RequestMapping("/rol")
@Controller
public class RolController {

    @Autowired
    private IRolService rolService;

    @GetMapping("/roles")
    public String mostrarRoles(Model model) {
        List<Rol> lista = rolService.buscarTodos();
        model.addAttribute("roles", lista);
        return "rol/datosRol";
    }

    @GetMapping("/roles/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("rol", new Rol());
        return "rol/formRol";
    }

    @PostMapping("/roles/guardar")
    public String guardarRol(Rol rol, RedirectAttributes attributes) {
        rolService.guardar(rol);
        attributes.addFlashAttribute("msg", "Rol guardado correctamente");
        return "redirect:/rol/roles";
    }

    @GetMapping("/ver/{id}")
    public String verDetalle(@PathVariable("id") int id, Model model) {
        Rol rol = rolService.buscarPorId(id);
        model.addAttribute("rol", rol);
        return "rol/detalleRol";
    }

    @GetMapping("/editar/{id}")
    public String editarRol(@PathVariable("id") int id, Model model) {
        Rol rol = rolService.buscarPorId(id);
        model.addAttribute("rol", rol);
        return "rol/formRol";
    }

    @GetMapping("/eliminar")
    public String eliminarRol(@RequestParam Integer id, RedirectAttributes attributes) {
        rolService.eliminar(id);
        attributes.addFlashAttribute("msg", "Rol eliminado correctamente");
        return "redirect:/rol/roles";
    }
}