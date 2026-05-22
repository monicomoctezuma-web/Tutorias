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
import tsp.itch.service.ICarreraService;

@RequestMapping("/carrera")
@Controller
public class CarreraController {

    @Autowired
    private ICarreraService carreraService;

    @GetMapping("/carreras")
    public String mostrarCarreras(Model model) {
        List<Carrera> lista = carreraService.buscarTodas();
        model.addAttribute("carreras", lista);
        return "carrera/datosCarrera";
    }
    
  /*  @GetMapping("/carreras")
    public String mostrarCarreras(Model model) {
        List<Carrera> todas = carreraService.buscarTodas();
        List<Carrera> activas = new java.util.ArrayList<>();
        List<Carrera> inactivas = new java.util.ArrayList<>();
        for (Carrera c : todas) {
            if (c.getEstatus() == 1) activas.add(c);
            else inactivas.add(c);
        }
        model.addAttribute("activas", activas);
        model.addAttribute("inactivas", inactivas);
        return "carrera/datosCarrera";
    }*/

    @GetMapping("/carreras/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("carrera", new Carrera());
        return "carrera/formCarrera";
    }
    
   

    @PostMapping("/carreras/guardar")
    public String guardarCarrera(Carrera carrera, RedirectAttributes attributes) {
        if (carrera.getIdCarrera() == null) {
            carrera.setEstatus(1);
        }
        carreraService.guardar(carrera);
        attributes.addFlashAttribute("msg", "Carrera guardada correctamente");
        return "redirect:/carrera/carreras";
    }

    @GetMapping("/ver/{id}")
    public String verDetalle(@PathVariable("id") int id, Model model) {
        Carrera carrera = carreraService.buscarPorId(id);
        model.addAttribute("carrera", carrera);
        return "carrera/detalleCarrera";
    }

    @GetMapping("/editar/{id}")
    public String editarCarrera(@PathVariable("id") int id, Model model) {
        Carrera carrera = carreraService.buscarPorId(id);
        model.addAttribute("carrera", carrera);
        return "carrera/formCarrera";
    }

    @GetMapping("/eliminar")
    public String eliminarCarrera(@RequestParam Integer id, RedirectAttributes attributes) {
        Carrera carrera = carreraService.buscarPorId(id);
        carrera.setEstatus(0);
        carreraService.guardar(carrera);
        attributes.addFlashAttribute("msg", "Carrera eliminada correctamente");
        return "redirect:/carrera/carreras";
    }
    


    // Agregar método reactivar:
    @GetMapping("/activar")
    public String activarCarrera(@RequestParam Integer id, RedirectAttributes attributes) {
        Carrera carrera = carreraService.buscarPorId(id);
        carrera.setEstatus(1);
        carreraService.guardar(carrera);
        attributes.addFlashAttribute("msg", "Carrera activada correctamente");
        return "redirect:/carrera/carreras";
    }}
