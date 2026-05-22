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
import tsp.itch.model.Asignacion;
import tsp.itch.model.AsignacionTutorado;
import tsp.itch.model.CoordinadorCarrera;
import tsp.itch.model.JefeAcademico;
import tsp.itch.model.Tutorado;
import tsp.itch.service.IAsignacionService;
import tsp.itch.service.IAsignacionTutoradoService;
import tsp.itch.service.ICarreraService;
import tsp.itch.service.ICoordinadorCarreraService;
import tsp.itch.service.IJefeAcademicoService;
import tsp.itch.service.ISemestreService;
import tsp.itch.service.ITutorService;
import tsp.itch.service.ITutoradoService;
import tsp.itch.service.IUsuarioService;

@RequestMapping("/asignacion")
@Controller
public class AsignacionController {

    @Autowired
    private IAsignacionService asignacionService;
    @Autowired
    private IAsignacionTutoradoService asignacionTutoradoService;
    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private ICarreraService carreraService;
    @Autowired
    private ISemestreService semestreService;
    @Autowired
    private ITutoradoService tutoradoService;
    
    @Autowired
    private ITutorService tutorService;
    
    @Autowired 
    private IJefeAcademicoService jefeService;
    
    @Autowired private ICoordinadorCarreraService coordinadorService;
    
    /*@GetMapping("/lista")
    public String mostrarLista(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        boolean esCoordinadorCarrera = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_COORDINADOR_CARRERA"));

        List<Asignacion> lista;
        if (esCoordinadorCarrera) {
            CoordinadorCarrera coordinador = coordinadorService.buscarPorUsername(username);
            lista = asignacionService.buscarPorCarrera(coordinador.getCarrera().getIdCarrera());
        } else {
            lista = asignacionService.buscarTodas();
        }
        model.addAttribute("asignaciones", lista);
        return "asignacion/datosAsignacion";
    } */
    
    @GetMapping("/lista")
    public String mostrarLista(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        boolean esCoordinadorCarrera = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_COORDINADOR_CARRERA"));
        boolean esJefeAcademico = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_JEFE_ACADEMICO"));

        List<Asignacion> lista;
        if (esCoordinadorCarrera) {
            CoordinadorCarrera coordinador = coordinadorService.buscarPorUsername(username);
            lista = asignacionService.buscarPorCarrera(coordinador.getCarrera().getIdCarrera());
        } else if (esJefeAcademico) {
            JefeAcademico jefe = jefeService.buscarPorUsername(username);
            lista = asignacionService.buscarPorCarrera(jefe.getCarrera().getIdCarrera());
        } else {
            lista = asignacionService.buscarTodas();
        }
        model.addAttribute("asignaciones", lista);
        return "asignacion/datosAsignacion";
    }

  /*  @GetMapping("/lista")
    public String mostrarLista(Model model) {
        List<Asignacion> lista = asignacionService.buscarTodas();
        model.addAttribute("asignaciones", lista);
        return "asignacion/datosAsignacion";
    }*/

   /* @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("asignacion", new Asignacion());
        model.addAttribute("usuarios", usuarioService.buscarTodos());
        model.addAttribute("carreras", carreraService.buscarTodas());
        model.addAttribute("semestres", semestreService.buscarTodos());
        model.addAttribute("tutorados", tutoradoService.buscarTodos());
        return "asignacion/formAsignacion";
    }*/
    
   /*@GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        // IDs de tutorados que ya están en alguna asignación
        List<AsignacionTutorado> todosAsignados = asignacionTutoradoService.buscarTodas();
        List<Integer> idsOcupados = new java.util.ArrayList<>();
        for (AsignacionTutorado at : todosAsignados) {
            idsOcupados.add(at.getTutorado().getIdTutorado());
        }

        model.addAttribute("asignacion", new Asignacion());
        //----------------------------------------------------------------------------
        //model.addAttribute("usuarios", usuarioService.buscarActivos());
        // model.addAttribute("usuarios", usuarioService.buscarTodos());
        /*model.addAttribute("carreras", carreraService.buscarTodas());*/
     // En mostrarFormulario:
        //model.addAttribute("usuarios", usuarioService.buscarActivosPorRol("TUTOR"));
        //------------------------------------------------------------------------------
    /*    model.addAttribute("tutores", tutorService.buscarTodos());
        model.addAttribute("carreras", carreraService.buscarActivas());
        model.addAttribute("semestres", semestreService.buscarActivos());
        //  model.addAttribute("semestres", semestreService.buscarTodos());
        model.addAttribute("tutorados", tutoradoService.buscarTodos());
        model.addAttribute("idsAsignados", new java.util.ArrayList<>());
        model.addAttribute("idsOcupados", idsOcupados);
        return "asignacion/formAsignacion";
    }*/
    
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        List<AsignacionTutorado> todosAsignados = asignacionTutoradoService.buscarTodas();
        List<Integer> idsOcupados = new java.util.ArrayList<>();
        for (AsignacionTutorado at : todosAsignados) {
            idsOcupados.add(at.getTutorado().getIdTutorado());
        }

        // Semestres que ya tienen asignaciones
      /*  java.util.Set<Integer> semestreConAsignaciones = new java.util.HashSet<>();
        for (AsignacionTutorado at : todosAsignados) {
            if (at.getAsignacion().getSemestre() != null) {
                semestreConAsignaciones.add(at.getAsignacion().getSemestre().getIdSemestre());
            }
        }*/
        java.util.List<Integer> semestreConAsignaciones = new java.util.ArrayList<>();
        for (AsignacionTutorado at : todosAsignados) {
            if (at.getAsignacion().getSemestre() != null) {
                Integer idSem = at.getAsignacion().getSemestre().getIdSemestre();
                if (!semestreConAsignaciones.contains(idSem)) {
                    semestreConAsignaciones.add(idSem);
                }
            }
        }

       /* model.addAttribute("asignacion", new Asignacion());
       //model.addAttribute("tutores", tutorService.buscarTodos());
        model.addAttribute("tutores", tutorService.buscarActivos());
        model.addAttribute("carreras", carreraService.buscarActivas());
        model.addAttribute("semestres", semestreService.buscarActivos());
        model.addAttribute("tutorados", tutoradoService.buscarTodos());
        model.addAttribute("idsAsignados", new java.util.ArrayList<>());
        model.addAttribute("idsOcupados", idsOcupados);
        model.addAttribute("semestreConAsignaciones", semestreConAsignaciones);
        return "asignacion/formAsignacion";*/
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        boolean esJefeAcademico = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_JEFE_ACADEMICO"));

        Asignacion asignacion = new Asignacion();

      /*  if (esJefeAcademico) {
            JefeAcademico jefe = jefeService.buscarPorUsername(username);
            asignacion.setCarrera(jefe.getCarrera());
            model.addAttribute("asignacion", asignacion);
            model.addAttribute("tutores", tutorService.buscarActivosPorCarrera(jefe.getCarrera().getIdCarrera()));
            model.addAttribute("carreras", List.of(jefe.getCarrera()));
            model.addAttribute("carreraFija", true);
        } else {
            model.addAttribute("asignacion", asignacion);
            model.addAttribute("tutores", tutorService.buscarActivos());
            model.addAttribute("carreras", carreraService.buscarActivas());
            model.addAttribute("carreraFija", false);
        }*/
        
        boolean esCoordinadorCarrera = auth.getAuthorities().stream()
        	    .anyMatch(a -> a.getAuthority().equals("ROLE_COORDINADOR_CARRERA"));

        	if (esJefeAcademico) {
        	    JefeAcademico jefe = jefeService.buscarPorUsername(username);
        	    asignacion.setCarrera(jefe.getCarrera());
        	    model.addAttribute("asignacion", asignacion);
        	    model.addAttribute("tutores", tutorService.buscarActivosPorCarrera(jefe.getCarrera().getIdCarrera()));
        	    model.addAttribute("carreras", List.of(jefe.getCarrera()));
        	    model.addAttribute("carreraFija", true);
        	} else if (esCoordinadorCarrera) {
        	    CoordinadorCarrera coordinador = coordinadorService.buscarPorUsername(username);
        	    asignacion.setCarrera(coordinador.getCarrera());
        	    model.addAttribute("asignacion", asignacion);
        	    model.addAttribute("tutores", tutorService.buscarActivosPorCarrera(coordinador.getCarrera().getIdCarrera()));
        	    model.addAttribute("carreras", List.of(coordinador.getCarrera()));
        	    model.addAttribute("carreraFija", true);
        	} else {
        	    model.addAttribute("asignacion", asignacion);
        	    model.addAttribute("tutores", tutorService.buscarActivos());
        	    model.addAttribute("carreras", carreraService.buscarActivas());
        	    model.addAttribute("carreraFija", false);
        	}

        model.addAttribute("semestres", semestreService.buscarActivos());
        model.addAttribute("tutorados", tutoradoService.buscarTodos());
        model.addAttribute("idsAsignados", new java.util.ArrayList<>());
        model.addAttribute("idsOcupados", idsOcupados);
        model.addAttribute("semestreConAsignaciones", semestreConAsignaciones);
        return "asignacion/formAsignacion";
    }
    
 

   @PostMapping("/guardar")
    public String guardar(
            Asignacion asignacion,
            @RequestParam(value = "idsTutorados", required = false) List<Integer> idsTutorados,
            RedirectAttributes attributes) {

        if (asignacion.getIdAsignacion() == null) {
            asignacion.setEstatus(1);
        }
        asignacionService.guardar(asignacion);

        if (idsTutorados != null) {
            for (Integer idTutorado : idsTutorados) {
                AsignacionTutorado at = new AsignacionTutorado();
                at.setAsignacion(asignacion);
                at.setTutorado(tutoradoService.buscarPorId(idTutorado));
                asignacionTutoradoService.guardar(at);
            }
        }

        attributes.addFlashAttribute("msg", "Asignación guardada correctamente");
        return "redirect:/asignacion/lista";
    }
    
    
    
   

    @GetMapping("/ver/{id}")
    public String verDetalle(@PathVariable("id") int id, Model model) {
        Asignacion asignacion = asignacionService.buscarPorId(id);
        List<AsignacionTutorado> tutorados = asignacionTutoradoService.buscarPorAsignacion(id);
        model.addAttribute("asignacion", asignacion);
        model.addAttribute("tutorados", tutorados);
        return "asignacion/detalleAsignacion";
    }

    /*@GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") int id, Model model) {
        Asignacion asignacion = asignacionService.buscarPorId(id);
        model.addAttribute("asignacion", asignacion);
        model.addAttribute("usuarios", usuarioService.buscarTodos());
        model.addAttribute("carreras", carreraService.buscarTodas());
        model.addAttribute("semestres", semestreService.buscarTodos());
        model.addAttribute("tutorados", tutoradoService.buscarTodos());
        return "asignacion/formAsignacion";
    }*/
    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") int id, Model model) {
        Asignacion asignacion = asignacionService.buscarPorId(id);
        
        // IDs de tutorados ya asignados a esta asignación
        List<AsignacionTutorado> asignados = asignacionTutoradoService.buscarPorAsignacion(id);
        List<Integer> idsAsignados = new java.util.ArrayList<>();
        for (AsignacionTutorado at : asignados) {
            idsAsignados.add(at.getTutorado().getIdTutorado());
        }

        // IDs de tutorados que ya están en CUALQUIER otra asignación
        List<AsignacionTutorado> todosAsignados = asignacionTutoradoService.buscarTodas();
        List<Integer> idsOcupados = new java.util.ArrayList<>();
        for (AsignacionTutorado at : todosAsignados) {
            // Si no pertenece a esta asignación, está ocupado
            if (!at.getAsignacion().getIdAsignacion().equals(id)) {
                idsOcupados.add(at.getTutorado().getIdTutorado());
            }
        }

        model.addAttribute("asignacion", asignacion);
        model.addAttribute("tutores", tutorService.buscarTodos());
      //  model.addAttribute("usuarios", usuarioService.buscarActivosPorRol("TUTOR"));
      //  model.addAttribute("usuarios", usuarioService.buscarActivos());
        //model.addAttribute("usuarios", usuarioService.buscarTodos());
       // model.addAttribute("carreras", carreraService.buscarTodas());
        model.addAttribute("carreras", carreraService.buscarActivas());
        model.addAttribute("semestres", semestreService.buscarActivos());
        //model.addAttribute("semestres", semestreService.buscarTodos());
        model.addAttribute("tutorados", tutoradoService.buscarTodos());
        model.addAttribute("idsAsignados", idsAsignados);
        model.addAttribute("idsOcupados", idsOcupados);
        return "asignacion/formAsignacion";
    }
    

    @GetMapping("/eliminar")
    public String eliminar(@RequestParam Integer id, RedirectAttributes attributes) {
        Asignacion asignacion = asignacionService.buscarPorId(id);
        asignacion.setEstatus(0);
        asignacionService.guardar(asignacion);
        attributes.addFlashAttribute("msg", "Asignación eliminada correctamente");
        return "redirect:/asignacion/lista";
    }
    @GetMapping("/buscarPorSemestre")
    public String buscarPorSemestre(
            @RequestParam(required = false) Integer idSemestre,
            Model model) {
        if (idSemestre != null) {
            model.addAttribute("asignaciones", asignacionService.buscarPorSemestre(idSemestre));
        } else {
            model.addAttribute("asignaciones", asignacionService.buscarTodas());
        }
        model.addAttribute("semestres", semestreService.buscarActivos());
        // model.addAttribute("semestres", semestreService.buscarTodos());
        model.addAttribute("idSemestreSeleccionado", idSemestre);
        return "asignacion/datosAsignacion";
    }
    
    @GetMapping("/buscarPorCarrera")
    public String buscarPorCarrera(
            @RequestParam(required = false) Integer idCarrera,
            Model model) {
        if (idCarrera != null) {
            model.addAttribute("asignaciones", asignacionService.buscarPorCarrera(idCarrera));
        } else {
            model.addAttribute("asignaciones", asignacionService.buscarTodas());
        }
       // model.addAttribute("carreras", carreraService.buscarTodas());
        model.addAttribute("carreras", carreraService.buscarActivas());
        model.addAttribute("semestres", semestreService.buscarTodos());
        model.addAttribute("idCarreraSeleccionada", idCarrera);
        return "asignacion/datosAsignacion";
    }
    
    @GetMapping("/activar")
    public String activarAsignacion(@RequestParam Integer id, RedirectAttributes attributes) {
        Asignacion asignacion = asignacionService.buscarPorId(id);
        asignacion.setEstatus(1);
        asignacionService.guardar(asignacion);
        attributes.addFlashAttribute("msg", "Asignación activada correctamente");
        return "redirect:/asignacion/lista";
    }
}