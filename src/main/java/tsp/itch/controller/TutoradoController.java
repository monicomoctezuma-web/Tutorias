package tsp.itch.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tsp.itch.model.AsignacionTutorado;
import tsp.itch.model.Tutorado;

import tsp.itch.service.ITutoradoService;
import tsp.itch.service.IAsignacionTutoradoService;
import tsp.itch.service.ICarreraService;

@RequestMapping("/tutorado")
@Controller
public class TutoradoController {

    @Autowired
    private ITutoradoService tutoradoService;

    @Autowired
    private ICarreraService carreraService;
    
    @Autowired
    private IAsignacionTutoradoService asignacionTutoradoService;

    @Value("${app.upload.dir.tutorado}")
    private String dirTutorado;

   /* @GetMapping("/tutorados")
    public String mostrarTutorados(Model model) {
        List<Tutorado> lista = tutoradoService.buscarTodos();
        model.addAttribute("tutorados", lista);
        return "tutorado/datosTutorado";
    }*/
    @GetMapping("/tutorados")
    public String mostrarTutorados(Model model) {
        model.addAttribute("tutorados", tutoradoService.buscarTodos());
       // model.addAttribute("carreras", carreraService.buscarTodas());
        model.addAttribute("carreras", carreraService.buscarActivas());

        return "tutorado/datosTutorado";
    }
    
    @GetMapping("/buscarTutorias")
    public String buscarTutorias(
            @RequestParam(required = false) Integer idTutorado,
            Model model) {
        model.addAttribute("tutorados", tutoradoService.buscarTodos());
        if (idTutorado != null) {
            List<AsignacionTutorado> tutorias = asignacionTutoradoService.buscarPorTutorado(idTutorado);
            model.addAttribute("tutorias", tutorias);
            model.addAttribute("idTutoradoSeleccionado", idTutorado);
        }
        return "tutorado/datosTutorado";
    }

    @GetMapping("/tutorados/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("tutorado", new Tutorado());
       // model.addAttribute("carreras", carreraService.buscarTodas());
        model.addAttribute("carreras", carreraService.buscarActivas());

        return "tutorado/formTutorado";
    }

    @PostMapping("/tutorados/guardar")
    public String guardarTutorado(Tutorado tutorado,
            @RequestParam("fotoArchivo") MultipartFile fotoArchivo,
            RedirectAttributes attributes) throws Exception {

        if (!fotoArchivo.isEmpty()) {
            String nombreFoto = fotoArchivo.getOriginalFilename();
            Path rutaFoto = Paths.get(dirTutorado + nombreFoto);
           // Files.copy(fotoArchivo.getInputStream(), rutaFoto);
            Files.copy(fotoArchivo.getInputStream(), rutaFoto, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            tutorado.setFoto(nombreFoto);
        }

        if (tutorado.getIdTutorado() == null) {
            tutorado.setEstatus(1);
        }

        tutoradoService.guardar(tutorado);
        attributes.addFlashAttribute("msg", "Tutorado guardado correctamente");
        return "redirect:/tutorado/tutorados";
    }

    @GetMapping("/ver/{id}")
    public String verDetalle(@PathVariable("id") int id, Model model) {
        Tutorado tutorado = tutoradoService.buscarPorId(id);
        model.addAttribute("tutorado", tutorado);
        return "tutorado/detalleTutorado";
    }

    @GetMapping("/editar/{id}")
    public String editarTutorado(@PathVariable("id") int id, Model model) {
        Tutorado tutorado = tutoradoService.buscarPorId(id);
        model.addAttribute("tutorado", tutorado);
       // model.addAttribute("carreras", carreraService.buscarTodas());
        model.addAttribute("carreras", carreraService.buscarActivas());

        return "tutorado/formTutorado";
    }

    @GetMapping("/eliminar")
    public String eliminarTutorado(@RequestParam Integer id, RedirectAttributes attributes) {
        Tutorado tutorado = tutoradoService.buscarPorId(id);
        tutorado.setEstatus(0);
        tutoradoService.guardar(tutorado);
        attributes.addFlashAttribute("msg", "Tutorado eliminado correctamente");
        return "redirect:/tutorado/tutorados";
    }
    
    @GetMapping("/buscarPorCarrera")
    public String buscarPorCarrera(
            @RequestParam(required = false) Integer idCarrera,
            Model model) {
       // model.addAttribute("carreras", carreraService.buscarTodas());
    	model.addAttribute("carreras", carreraService.buscarActivas());

        model.addAttribute("tutorados", tutoradoService.buscarTodos());
        model.addAttribute("idCarreraSeleccionada", idCarrera);

        if (idCarrera != null) {
            model.addAttribute("tutorados", tutoradoService.buscarPorCarrera(idCarrera));
        } else {
            model.addAttribute("tutorados", tutoradoService.buscarTodos());
        }
        return "tutorado/datosTutorado";
    }
    
    @GetMapping("/activar")
    public String activarTutorado(@RequestParam Integer id, RedirectAttributes attributes) {
        Tutorado tutorado = tutoradoService.buscarPorId(id);
        tutorado.setEstatus(1);
        tutoradoService.guardar(tutorado);
        attributes.addFlashAttribute("msg", "Tutorado activado correctamente");
        return "redirect:/tutorado/tutorados";
    }
}