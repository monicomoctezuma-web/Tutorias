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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tsp.itch.model.Actividad;
import tsp.itch.model.CoordinadorCarrera;
import tsp.itch.model.JefeAcademico;
import tsp.itch.model.Pat;
import tsp.itch.model.PatCarrera;
import tsp.itch.model.SesionTutoria;
import tsp.itch.service.IActividadService;
import tsp.itch.service.ICarreraService;
import tsp.itch.service.ICoordinadorCarreraService;
import tsp.itch.service.IJefeAcademicoService;
import tsp.itch.service.IPatCarreraService;
import tsp.itch.service.IPatService;
import tsp.itch.service.ISemestreService;
import tsp.itch.service.ISesionTutoriaService;

@RequestMapping("/patcarrera")
@Controller
public class PatCarreraController {

    @Autowired private IPatCarreraService patCarreraService;
    @Autowired private IPatService patService;
    @Autowired private ICarreraService carreraService;
    @Autowired private ISemestreService semestreService;
    @Autowired private IActividadService actividadService;
    @Autowired
    private ISesionTutoriaService sesionService;
    @Autowired 
    private IJefeAcademicoService jefeService;
    
    @Autowired private ICoordinadorCarreraService coordinadorService;

  /*  @GetMapping("/lista")
    public String mostrarLista(Model model) {
        List<PatCarrera> lista = patCarreraService.buscarTodas();
        model.addAttribute("patCarreras", lista);
        return "patcarrera/datosPatCarrera";
    }*/
    
    @GetMapping("/lista")
    public String mostrarLista(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        boolean esCoordinadorCarrera = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_COORDINADOR_CARRERA"));

       /* List<PatCarrera> lista;
        if (esCoordinadorCarrera) {
            CoordinadorCarrera coordinador = coordinadorService.buscarPorUsername(username);
            if (coordinador != null) {
                lista = patCarreraService.buscarPorCarrera(coordinador.getCarrera().getIdCarrera());
            } else {
                lista = new java.util.ArrayList<>();
            }
        } else {
            lista = patCarreraService.buscarTodas();
        }*/
        boolean esJefeAcademico = auth.getAuthorities().stream()
        	    .anyMatch(a -> a.getAuthority().equals("ROLE_JEFE_ACADEMICO"));

        	List<PatCarrera> lista;
        	if (esCoordinadorCarrera) {
        	    CoordinadorCarrera coordinador = coordinadorService.buscarPorUsername(username);
        	    if (coordinador != null) {
        	        lista = patCarreraService.buscarPorCarrera(coordinador.getCarrera().getIdCarrera());
        	    } else {
        	        lista = new java.util.ArrayList<>();
        	    }
        	} else if (esJefeAcademico) {
        	    JefeAcademico jefe = jefeService.buscarPorUsername(username);
        	    if (jefe != null) {
        	        lista = patCarreraService.buscarPorCarrera(jefe.getCarrera().getIdCarrera());
        	    } else {
        	        lista = new java.util.ArrayList<>();
        	    }
        	} else {
        	    lista = patCarreraService.buscarTodas();
        	}
        
        model.addAttribute("patCarreras", lista);
        return "patcarrera/datosPatCarrera";
    }

 /*   @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("patCarrera", new PatCarrera());
        model.addAttribute("pats", patService.buscarActivas());
        // model.addAttribute("pats", patService.buscarTodas());
       // model.addAttribute("carreras", carreraService.buscarTodas());
        model.addAttribute("carreras", carreraService.buscarActivas());
        model.addAttribute("semestres", semestreService.buscarActivos());
        //model.addAttribute("semestres", semestreService.buscarTodos());
        return "patcarrera/formPatCarrera";
    }*/
    
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        boolean esCoordinadorCarrera = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_COORDINADOR_CARRERA"));

        model.addAttribute("patCarrera", new PatCarrera());
        model.addAttribute("pats", patService.buscarActivas());
        model.addAttribute("semestres", semestreService.buscarActivos());

      /*  if (esCoordinadorCarrera) {
            CoordinadorCarrera coordinador = coordinadorService.buscarPorUsername(username);
            model.addAttribute("carreras", java.util.List.of(coordinador.getCarrera()));
            model.addAttribute("esCoordinadorCarrera", true);
        } else {
            model.addAttribute("carreras", carreraService.buscarActivas());
            model.addAttribute("esCoordinadorCarrera", false);
        }*/
        
        boolean esJefeAcademico = auth.getAuthorities().stream()
        	    .anyMatch(a -> a.getAuthority().equals("ROLE_JEFE_ACADEMICO"));

        	if (esCoordinadorCarrera) {
        	    CoordinadorCarrera coordinador = coordinadorService.buscarPorUsername(username);
        	    model.addAttribute("carreras", java.util.List.of(coordinador.getCarrera()));
        	    model.addAttribute("esCoordinadorCarrera", true);
        	} else if (esJefeAcademico) {
        	    JefeAcademico jefe = jefeService.buscarPorUsername(username);
        	    model.addAttribute("carreras", java.util.List.of(jefe.getCarrera()));
        	    model.addAttribute("esCoordinadorCarrera", true);
        	} else {
        	    model.addAttribute("carreras", carreraService.buscarActivas());
        	    model.addAttribute("esCoordinadorCarrera", false);
        	}
        return "patcarrera/formPatCarrera";
    }

    // ← NUEVO: endpoint AJAX para obtener actividades del PAT general
    @GetMapping("/actividades/{idPat}")
    @ResponseBody
    public List<Actividad> obtenerActividades(@PathVariable Integer idPat) {
        return actividadService.buscarPorPat(idPat);
    }
    
    @GetMapping("/infoPat/{idPat}")
    @ResponseBody
    public Pat obtenerInfoPat(@PathVariable Integer idPat) {
        return patService.buscarPorId(idPat);
    }
    
    @GetMapping("/actividadesPatCarrera/{idPatCarrera}")
    @ResponseBody
    public List<Actividad> obtenerActividadesPatCarrera(@PathVariable Integer idPatCarrera) {
        PatCarrera pc = patCarreraService.buscarPorId(idPatCarrera);
        return pc.getActividades();
    }

    @PostMapping("/guardar")
    public String guardar(
            @RequestParam(value = "pat.idPat", required = false) Integer idPat,
            @RequestParam(value = "carrera.idCarrera", required = false) Integer idCarrera,
            @RequestParam(value = "semestre.idSemestre", required = false) Integer idSemestre,
            @RequestParam(value = "idPatCarrera", required = false) Integer idPatCarrera,
            @RequestParam(value = "estatus", required = false) Integer estatus,
            @RequestParam(value = "actNombre[]", required = false) List<String> nombres,
            @RequestParam(value = "actDescripcion[]", required = false) List<String> descripciones,
            @RequestParam(value = "actSemana[]", required = false) List<String> semanas,
            @RequestParam(value = "actFecha[]", required = false) List<String> fechas,
            RedirectAttributes attributes) {

        PatCarrera patCarrera = new PatCarrera();
        patCarrera.setIdPatCarrera(idPatCarrera);
        patCarrera.setEstatus(idPatCarrera == null ? 1 : estatus);

        // Buscar objetos completos desde BD
        if (idPat != null) patCarrera.setPat(patService.buscarPorId(idPat));
        if (idCarrera != null) patCarrera.setCarrera(carreraService.buscarPorId(idCarrera));
        if (idSemestre != null) patCarrera.setSemestre(semestreService.buscarPorId(idSemestre));

      /*  patCarreraService.guardar(patCarrera);

        if (nombres != null) {
            for (int i = 0; i < nombres.size(); i++) {
                String nombre = nombres.get(i);
                if (nombre != null && !nombre.trim().isEmpty()) {
                    Actividad actividad = new Actividad();
                    actividad.setNombre(nombre);
                    actividad.setDescripcion(descripciones != null ? descripciones.get(i) : "");
                    actividad.setSemana(semanas != null ? semanas.get(i) : "");
                    actividad.setPatCarrera(patCarrera);
                    actividad.setEstatus(1);
                    actividadService.guardar(actividad);
                }
            }
        }
       */
        patCarreraService.guardar(patCarrera);

     // Borrar actividades anteriores para evitar duplicados
   /*  if (idPatCarrera != null) {
         List<Actividad> anteriores = actividadService.buscarPorPatCarrera(idPatCarrera);
         for (Actividad a : anteriores) {
             actividadService.eliminar(a.getIdActividad());
         }
     }*/
        
        if (idPatCarrera != null) {
            List<Actividad> anteriores = actividadService.buscarPorPatCarrera(idPatCarrera);
            for (Actividad a : anteriores) {
                List<SesionTutoria> sesiones = sesionService.buscarPorActividad(a.getIdActividad());
                for (SesionTutoria s : sesiones) {
                    s.setActividad(null);
                    sesionService.guardar(s);
                }
                actividadService.eliminar(a.getIdActividad());
            }
        } 
     
  // ← AGREGAR ESTO: borrar actividades anteriores del PAT general
   /*  if (idPat != null) {
         List<Actividad> anterioresPat = actividadService.buscarPorPat(idPat);
         for (Actividad a : anterioresPat) {
             actividadService.eliminar(a.getIdActividad());
         }
     }*/
     // Guardar actividades nuevas
     if (nombres != null) {
         for (int i = 0; i < nombres.size(); i++) {
             String nombre = nombres.get(i);
             if (nombre != null && !nombre.trim().isEmpty()) {

                 // Guardar en PAT Carrera
                 Actividad actividad = new Actividad();
                 actividad.setNombre(nombre);
                 actividad.setDescripcion(descripciones != null ? descripciones.get(i) : "");
                 actividad.setSemana(semanas != null ? semanas.get(i) : "");
                 actividad.setPatCarrera(patCarrera);
                 actividad.setFecha(fechas != null && fechas.get(i) != null && !fechas.get(i).isEmpty() 
                		    ? java.time.LocalDate.parse(fechas.get(i)) : null);
                 actividad.setEstatus(1);
                 actividadService.guardar(actividad);

                 // Guardar también en PAT general
                /* Actividad actividadPat = new Actividad();
                 actividadPat.setNombre(nombre);
                 actividadPat.setDescripcion(descripciones != null ? descripciones.get(i) : "");
                 actividadPat.setSemana(semanas != null ? semanas.get(i) : "");
                 actividadPat.setFecha(fechas != null && fechas.get(i) != null && !fechas.get(i).isEmpty() 
                		    ? java.time.LocalDate.parse(fechas.get(i)) : null);
                 actividadPat.setPat(patCarrera.getPat());
                 actividadPat.setEstatus(1);
                 actividadService.guardar(actividadPat);*/
             }
         }
     }

        attributes.addFlashAttribute("msg", "PAT por carrera guardado correctamente");
        return "redirect:/patcarrera/lista";
    }

    @GetMapping("/ver/{id}")
    public String verDetalle(@PathVariable("id") int id, Model model) {
        PatCarrera patCarrera = patCarreraService.buscarPorId(id);
        model.addAttribute("patCarrera", patCarrera);
        return "patcarrera/detallePatCarrera";
    }

   /* @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") int id, Model model) {
        PatCarrera patCarrera = patCarreraService.buscarPorId(id);
        model.addAttribute("patCarrera", patCarrera);
        model.addAttribute("pats", patService.buscarActivas());
        // model.addAttribute("pats", patService.buscarTodas());
       // model.addAttribute("carreras", carreraService.buscarTodas());
        model.addAttribute("carreras", carreraService.buscarActivas());
        model.addAttribute("semestres", semestreService.buscarActivos());
        //  model.addAttribute("semestres", semestreService.buscarTodos());
        return "patcarrera/formPatCarrera";
    }*/
    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") int id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        boolean esCoordinadorCarrera = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_COORDINADOR_CARRERA"));

        PatCarrera patCarrera = patCarreraService.buscarPorId(id);
        model.addAttribute("patCarrera", patCarrera);
        model.addAttribute("pats", patService.buscarActivas());
        model.addAttribute("semestres", semestreService.buscarActivos());

        if (esCoordinadorCarrera) {
            CoordinadorCarrera coordinador = coordinadorService.buscarPorUsername(username);
            model.addAttribute("carreras", java.util.List.of(coordinador.getCarrera()));
            model.addAttribute("esCoordinadorCarrera", true);
        } else {
            model.addAttribute("carreras", carreraService.buscarActivas());
            model.addAttribute("esCoordinadorCarrera", false);
        }
        return "patcarrera/formPatCarrera";
    }

    @GetMapping("/eliminar")
    public String eliminar(@RequestParam Integer id, RedirectAttributes attributes) {
        PatCarrera patCarrera = patCarreraService.buscarPorId(id);
        patCarrera.setEstatus(0);
        patCarreraService.guardar(patCarrera);
        attributes.addFlashAttribute("msg", "PAT por carrera eliminado correctamente");
        return "redirect:/patcarrera/lista";
    }
    
    @GetMapping("/activar")
    public String activarPatCarrera(@RequestParam Integer id, RedirectAttributes attributes) {
        PatCarrera patCarrera = patCarreraService.buscarPorId(id);
        patCarrera.setEstatus(1);
        patCarreraService.guardar(patCarrera);
        attributes.addFlashAttribute("msg", "PAT por carrera activado correctamente");
        return "redirect:/patcarrera/lista";
    }
}