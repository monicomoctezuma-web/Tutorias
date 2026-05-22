package tsp.itch.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tsp.itch.model.Tutor;
import tsp.itch.service.ICarreraService;
import tsp.itch.service.ITutorService;

@RequestMapping("/tutor")
@Controller
public class TutorController {

    @Autowired
    private ITutorService tutorService;

    @Autowired
    private ICarreraService carreraService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.upload.dir.tutor}")
    private String dirTutor;

    @GetMapping("/tutores")
    public String mostrarTutores(Model model) {
        model.addAttribute("tutores", tutorService.buscarTodos());
        model.addAttribute("carreras", carreraService.buscarActivas());
        return "tutor/datosTutor";
    }

    @GetMapping("/tutores/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("tutor", new Tutor());
        model.addAttribute("carreras", carreraService.buscarActivas());
        return "tutor/formTutor";
    }

   /* @PostMapping("/tutores/guardar")
    public String guardarTutor(Tutor tutor,
            @RequestParam("fotoArchivo") MultipartFile fotoArchivo,
            RedirectAttributes attributes) throws Exception {

        if (!fotoArchivo.isEmpty()) {
            String nombreFoto = fotoArchivo.getOriginalFilename();
            Path rutaFoto = Paths.get(dirTutor + nombreFoto);
            Files.copy(fotoArchivo.getInputStream(), rutaFoto);
            tutor.setFoto(nombreFoto);
        }

        if (tutor.getIdTutor() == null) {
            tutor.setEstatus(1);
        }

        tutorService.guardar(tutor);
        attributes.addFlashAttribute("msg", "Tutor guardado correctamente");
        return "redirect:/tutor/tutores";
    }

    @GetMapping("/ver/{id}")
    public String verDetalle(@PathVariable("id") int id, Model model) {
        Tutor tutor = tutorService.buscarPorId(id);
        model.addAttribute("tutor", tutor);
        return "tutor/detalleTutor";
    }*/
    
    @PostMapping("/tutores/guardar")
    public String guardarTutor(Tutor tutor,
            @RequestParam("fotoArchivo") MultipartFile fotoArchivo,
            RedirectAttributes attributes) throws Exception {

        // Validar número de trabajador duplicado
        Tutor existente = tutorService.buscarPorNumeroTrabajador(tutor.getNumeroTrabajador());
        if (existente != null && !existente.getIdTutor().equals(tutor.getIdTutor())) {
            attributes.addFlashAttribute("error", "⚠ Ya existe un tutor con ese número de trabajador.");
            if (tutor.getIdTutor() != null) {
                return "redirect:/tutor/editar/" + tutor.getIdTutor();
            }
            return "redirect:/tutor/tutores/nuevo";
        }

        if (!fotoArchivo.isEmpty()) {
            String nombreFoto = fotoArchivo.getOriginalFilename();
            Path rutaFoto = Paths.get(dirTutor + nombreFoto);
           // Files.copy(fotoArchivo.getInputStream(), rutaFoto);
            Files.copy(fotoArchivo.getInputStream(), rutaFoto, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            tutor.setFoto(nombreFoto);
        }

        if (tutor.getIdTutor() == null) {
            tutor.setEstatus(1);
        }

        tutorService.guardar(tutor);
        attributes.addFlashAttribute("msg", "Tutor guardado correctamente");
        return "redirect:/tutor/tutores";
    }

    @GetMapping("/editar/{id}")
    public String editarTutor(@PathVariable("id") int id, Model model) {
        Tutor tutor = tutorService.buscarPorId(id);
        model.addAttribute("tutor", tutor);
        model.addAttribute("carreras", carreraService.buscarActivas());
        return "tutor/formTutor";
    }

    @GetMapping("/eliminar")
    public String eliminarTutor(@RequestParam Integer id, RedirectAttributes attributes) {
        Tutor tutor = tutorService.buscarPorId(id);
        tutor.setEstatus(0);
        tutorService.guardar(tutor);
        attributes.addFlashAttribute("msg", "Tutor eliminado correctamente");
        return "redirect:/tutor/tutores";
    }

    @GetMapping("/activar")
    public String activarTutor(@RequestParam Integer id, RedirectAttributes attributes) {
        Tutor tutor = tutorService.buscarPorId(id);
        tutor.setEstatus(1);
        tutorService.guardar(tutor);
        attributes.addFlashAttribute("msg", "Tutor activado correctamente");
        return "redirect:/tutor/tutores";
    }

    @GetMapping("/buscarPorCarrera")
    public String buscarPorCarrera(
            @RequestParam(required = false) Integer idCarrera,
            Model model) {
        model.addAttribute("carreras", carreraService.buscarActivas());
        if (idCarrera != null) {
            model.addAttribute("tutores", tutorService.buscarPorCarrera(idCarrera));
        } else {
            model.addAttribute("tutores", tutorService.buscarTodos());
        }
        model.addAttribute("idCarreraSeleccionada", idCarrera);
        return "tutor/datosTutor";
    }
    
    @GetMapping("/buscarPorNumero")
    @ResponseBody
    public Tutor buscarPorNumero(@RequestParam String numeroTrabajador) {
        return tutorService.buscarPorNumeroTrabajador(numeroTrabajador);
    }
    @PostMapping("/guardarCredenciales")
    public String guardarCredenciales(
            @RequestParam Integer idTutor,
            @RequestParam String username,
            @RequestParam String password,
            RedirectAttributes attributes) {

        Tutor tutor = tutorService.buscarPorId(idTutor);
       /* tutor.setUsername(username);
        tutor.setPassword(password);
        tutorService.guardar(tutor);*/
        
        tutor.setUsername(username);
        tutor.setPassword(passwordEncoder.encode(password));
        tutorService.guardar(tutor);
        attributes.addFlashAttribute("msg", "Credenciales guardadas correctamente");
        return "redirect:/usuario/usuarios";
    }
    
    @GetMapping("/inicio")
    public String inicioTutor() {
        return "tutor/inicioTutor";
    }
}