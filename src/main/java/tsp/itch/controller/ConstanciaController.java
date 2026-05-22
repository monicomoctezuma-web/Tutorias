package tsp.itch.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import tsp.itch.model.AsignacionTutorado;
import tsp.itch.model.Constancia;
import tsp.itch.model.JefeAcademico;
import tsp.itch.service.IAsistenciaService;
import tsp.itch.service.IAsignacionService;
import tsp.itch.service.IAsignacionTutoradoService;
import tsp.itch.service.IConstanciaService;
import tsp.itch.service.ICoordinadorCarreraService;
import tsp.itch.service.IJefeAcademicoService;
import tsp.itch.service.ITutorService;
import tsp.itch.service.ITutoradoService;
import tsp.itch.service.IUsuarioService;

@RequestMapping("/constancia")
@Controller
public class ConstanciaController {

    @Autowired
    private IConstanciaService constanciaService;

    @Autowired
    private ITutoradoService tutoradoService;

    @Autowired
    private IAsignacionService asignacionService;

    @Autowired
    private IAsignacionTutoradoService asignacionTutoradoService;

    @Autowired
    private IAsistenciaService asistenciaService;

    @Autowired
    private IUsuarioService usuarioService;
    
    @Autowired
    private ITutorService tutorService;
    @Autowired
    private IJefeAcademicoService jefeService;
    
    @Autowired
    private ICoordinadorCarreraService coordinadorService;

    // ===================== CRUD =====================
    @GetMapping("/constancias")
    public String mostrarConstancias(Model model) {
        List<Constancia> lista = constanciaService.buscarTodos();
        model.addAttribute("constancias", lista);
        return "constancia/datosConstancia";
    }

    @GetMapping("/constancias/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("constancia", new Constancia());
        model.addAttribute("tutorados", tutoradoService.buscarTodos());
        model.addAttribute("asignaciones", asignacionService.buscarTodas());
        return "constancia/formConstancia";
    }

    @PostMapping("/constancias/guardar")
    public String guardarConstancia(Constancia constancia, RedirectAttributes attributes) {
        constanciaService.guardar(constancia);
        attributes.addFlashAttribute("msg", "Constancia guardada correctamente");
        return "redirect:/constancia/constancias";
    }

    @GetMapping("/ver/{id}")
    public String verDetalle(@PathVariable("id") int id, Model model) {
        Constancia constancia = constanciaService.buscarPorId(id);
        model.addAttribute("constancia", constancia);
        return "constancia/detalleConstancia";
    }

    @GetMapping("/editar/{id}")
    public String editarConstancia(@PathVariable("id") int id, Model model) {
        Constancia constancia = constanciaService.buscarPorId(id);
        model.addAttribute("constancia", constancia);
        model.addAttribute("tutorados", tutoradoService.buscarTodos());
        model.addAttribute("asignaciones", asignacionService.buscarTodas());
        return "constancia/formConstancia";
    }

    @GetMapping("/eliminar")
    public String eliminarConstancia(@RequestParam Integer id, RedirectAttributes attributes) {
        constanciaService.eliminar(id);
        attributes.addFlashAttribute("msg", "Constancia eliminada correctamente");
        return "redirect:/constancia/constancias";
    }

    // ===================== GENERAR CONSTANCIAS POR 80% =====================
    @GetMapping("/generarConstancias")
    public String mostrarGenerarConstancias(
            @RequestParam(required = false) Integer idTutor,
            @RequestParam(required = false) Integer idAsignacion,
            Model model) {

       // model.addAttribute("tutores", usuarioService.buscarTodos());
    	//model.addAttribute("tutores", usuarioService.buscarActivosPorRol("TUTOR"));
    	//model.addAttribute("tutores", usuarioService.buscarTodos());
    	//model.addAttribute("tutores", tutorService.buscarTodos());
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String username = auth.getName();

    	/*boolean esJefeAcademico = auth.getAuthorities().stream()
    	    .anyMatch(a -> a.getAuthority().equals("ROLE_JEFE_ACADEMICO"));

    	if (esJefeAcademico) {
    	    JefeAcademico jefe = jefeService.buscarPorUsername(username);
    	  //  model.addAttribute("tutores", tutorService.buscarPorCarrera(jefe.getCarrera().getIdCarrera()));
    	    model.addAttribute("tutores", tutorService.buscarActivosPorCarrera(jefe.getCarrera().getIdCarrera()));
    	} else {
    	   // model.addAttribute("tutores", tutorService.buscarTodos());
    		model.addAttribute("tutores", tutorService.buscarActivos());
    	}*/
    	
    	boolean esJefeAcademico = auth.getAuthorities().stream()
    		    .anyMatch(a -> a.getAuthority().equals("ROLE_JEFE_ACADEMICO"));
    		boolean esCoordinadorCarrera = auth.getAuthorities().stream()
    		    .anyMatch(a -> a.getAuthority().equals("ROLE_COORDINADOR_CARRERA"));

    		if (esJefeAcademico) {
    		    JefeAcademico jefe = jefeService.buscarPorUsername(username);
    		    model.addAttribute("tutores", tutorService.buscarActivosPorCarrera(jefe.getCarrera().getIdCarrera()));
    		} else if (esCoordinadorCarrera) {
    		    tsp.itch.model.CoordinadorCarrera coordinador = coordinadorService.buscarPorUsername(username);
    		    model.addAttribute("tutores", tutorService.buscarActivosPorCarrera(coordinador.getCarrera().getIdCarrera()));
    		} else {
    		    model.addAttribute("tutores", tutorService.buscarActivos());
    		}
        if (idTutor != null) {
            model.addAttribute("asignaciones", asignacionService.buscarPorTutor(idTutor));
            model.addAttribute("idTutorSeleccionado", idTutor);
        }

        if (idAsignacion != null) {
            model.addAttribute("asignaciones", asignacionService.buscarPorTutor(idTutor));
            model.addAttribute("idTutorSeleccionado", idTutor);
            model.addAttribute("idAsignacionSeleccionada", idAsignacion);

            List<AsignacionTutorado> tutorados = asignacionTutoradoService.buscarPorAsignacion(idAsignacion);
            List<AsignacionTutorado> aprobados = new ArrayList<>();
            Map<Integer, Double> porcentajes = new HashMap<>();
            Map<Integer, Boolean> tieneConstancia = new HashMap<>();

          /*  for (AsignacionTutorado at : tutorados) {
                Integer idTutorado = at.getTutorado().getIdTutorado();
                double pct = asistenciaService.calcularPorcentaje(idTutorado);
                porcentajes.put(idTutorado, pct);
                if (pct >= 80) {
                    aprobados.add(at);
                    boolean yaConstancia = constanciaService.buscarTodos().stream()
                            .anyMatch(c -> c.getTutorado().getIdTutorado().equals(idTutorado)
                                    && c.getAsignacion().getIdAsignacion().equals(idAsignacion));
                    tieneConstancia.put(idTutorado, yaConstancia);
                }
            }
            model.addAttribute("aprobados", aprobados);
            model.addAttribute("porcentajes", porcentajes);
            model.addAttribute("tieneConstancia", tieneConstancia);*/
            Map<Integer, Integer> idsConstancia = new HashMap<>();

            for (AsignacionTutorado at : tutorados) {
                Integer idTutorado = at.getTutorado().getIdTutorado();
                double pct = asistenciaService.calcularPorcentaje(idTutorado);
                porcentajes.put(idTutorado, pct);
                if (pct >= 80) {
                    aprobados.add(at);
                    boolean yaConstancia = constanciaService.buscarTodos().stream()
                            .anyMatch(c -> c.getTutorado().getIdTutorado().equals(idTutorado)
                                    && c.getAsignacion().getIdAsignacion().equals(idAsignacion));
                    tieneConstancia.put(idTutorado, yaConstancia);

                    // Guardar el id de la constancia si ya existe
                    constanciaService.buscarTodos().stream()
                            .filter(c -> c.getTutorado().getIdTutorado().equals(idTutorado)
                                    && c.getAsignacion().getIdAsignacion().equals(idAsignacion))
                            .findFirst()
                            .ifPresent(c -> idsConstancia.put(idTutorado, c.getIdConstancia()));
                }
            }
            model.addAttribute("aprobados", aprobados);
            model.addAttribute("porcentajes", porcentajes);
            model.addAttribute("tieneConstancia", tieneConstancia);
            model.addAttribute("idsConstancia", idsConstancia);
        }

        return "constancia/generarConstancias";
    }

    @GetMapping("/crearConstancia")
    public String crearConstancia(
            @RequestParam Integer idTutorado,
            @RequestParam Integer idAsignacion,
            @RequestParam Integer idTutor,
            RedirectAttributes attributes) {

        boolean yaExiste = constanciaService.buscarTodos().stream()
                .anyMatch(c -> c.getTutorado().getIdTutorado().equals(idTutorado)
                        && c.getAsignacion().getIdAsignacion().equals(idAsignacion));

        if (!yaExiste) {
            Constancia constancia = new Constancia();
            constancia.setTutorado(asignacionTutoradoService.buscarPorAsignacion(idAsignacion)
                    .stream()
                    .filter(at -> at.getTutorado().getIdTutorado().equals(idTutorado))
                    .findFirst().get().getTutorado());
            constancia.setAsignacion(asignacionService.buscarPorId(idAsignacion));
            constancia.setFechaEmision(LocalDate.now());
            constanciaService.guardar(constancia);
            attributes.addFlashAttribute("msg", "Constancia generada correctamente");
        } else {
            attributes.addFlashAttribute("msg", "Este tutorado ya tiene constancia generada");
        }

        return "redirect:/constancia/generarConstancias?idTutor=" + idTutor + "&idAsignacion=" + idAsignacion;
    }
}